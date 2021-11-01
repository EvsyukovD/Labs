import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Controller extends Thread {
    private File folder;
    private DataQueue<String> queue;
    private boolean stop = false;
    private String[] msgs = {"0.Нет", "1.Да"};
    private Scanner scan;

    public Controller(File folder, DataQueue<String> queue, Scanner scn) {
        this.queue = queue;
        this.folder = folder;
        this.scan = scn;
    }

    private synchronized boolean keepRunning() {
        return !stop;
    }

    public synchronized void doStop() {
        stop = true;
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
                return;
            }
            /*LinkedList<File> files = new LinkedList<>();
            for (File f : fls) {
                files.add(f);
            }*/
            /*try {
                if (files.length == 0 && !queue.signal()) {
                    boolean stop;
                    System.out.println("Продолжть работу программы?");
                    stop = FunctionHelper.dialog(msgs, msgs.length, scan) == 0;
                    if (stop) {
                        return;
                    }
                }
                Thread.currentThread().sleep(1000L);
            } catch (InterruptedException e){
                e.printStackTrace();
            }*/
            // processFile();
            for (int i = 0; i < files.length; i++) {
                LinkedList<String> list;
                File file = files[i];
                if (file.isFile()) {
                    try {
                        list = PeopleService.readManageFile(file.getPath());
                        TimeUnit.SECONDS.sleep(2L);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Ошибка чтения файла");
                        return;
                    }
                    if (list.size() > 0) {
                        try {
                            this.queue.setLimit(list.size() + 1);
                            this.queue.offer(file.getPath());
                            while (list.size() != 0) {
                                this.queue.offer(list.removeFirst());
                                TimeUnit.SECONDS.sleep(1L);
                            }
                        } catch (InterruptedException e) {
                            System.out.println("Прерывание потока");
                            e.printStackTrace();
                        }
                    }
                }
            }
            /*for(File f : files){
                if(f.isFile()) {
                    f.delete();
                }
            }*/
            /*try{
                Thread.currentThread().sleep(1000L);
                //TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException  e){
                System.out.println("Прерывание программы");
                return;
            }*/
            /*while(state == CState.NONE){
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }*/
            /*try {
                System.out.println("Controller->queue");
                stop = queue.getStopFlag();
            } catch (InterruptedException e){
                 e.printStackTrace();
            }*/
        } while (keepRunning());
    }

}
