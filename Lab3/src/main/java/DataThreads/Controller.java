package DataThreads;

import DAOClasses.PeopleService;
import LabUtils.DataQueue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Controller extends Thread {
    private File folder;
    private DataQueue<String> queue;
    private boolean stop = false;
    private File bufFolder = new File("C:\\Users\\devsy\\IdeaProjects\\Lab2_Project\\BufferFolder");
    private Scanner scan;
    private Dispathcher disp;

    public Controller(File folder, DataQueue<String> queue, Scanner scn, Dispathcher dispathcher) {
        this.queue = queue;
        this.folder = folder;
        this.scan = scn;
        this.disp = dispathcher;
    }

    private synchronized boolean keepRunning() {
        return disp.isAlive();
    }

    public synchronized void doStop() {
        stop = true;
    }

    private void toBuff(File file) throws IOException {
        File bufFile = new File(bufFolder.getPath() + "\\" + file.getName());
        if (bufFile.exists()) {
            bufFile.delete();
        }
        Files.move(file.toPath(), Path.of(bufFolder.getPath() + "\\" + file.getName()));
    }

    private synchronized void processFile(File[] files) {
        for (int i = 0; i < files.length && disp.isAlive(); i++) {
            LinkedList<String> list;
            File file = files[i];
            if (file.isFile()) {
                try {
                    list = PeopleService.readManageFile(file.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (list.size() > 0) {
                    try {
                        toBuff(file);
                        if (!disp.isAlive()) {
                            return;
                        }
                        this.queue.offer(Integer.toString(list.size()));
                        TimeUnit.MILLISECONDS.sleep(200);
                        if (!disp.isAlive()) {
                            return;
                        }
                        this.queue.offer(bufFolder.getPath() + "\\" + file.getName());
                        TimeUnit.MILLISECONDS.sleep(200);
                        if (!disp.isAlive()) {
                            return;
                        }
                        while (list.size() != 0) {
                            if (!disp.isAlive()) {
                                return;
                            }
                            this.queue.offer(list.removeFirst());
                            TimeUnit.MILLISECONDS.sleep(200);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        do {
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("manage") && name.endsWith(".txt");
                }
            });
            if (files.length == 0) {
                disp.doStop();
                return;
            }
            processFile(files);
        } while (keepRunning());
    }

}
