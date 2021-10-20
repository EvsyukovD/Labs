import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Controller extends Thread {
    private File folder;
    private DataQueue<String> queue;
    private boolean stop = false;
    private String[] msgs = {"0.Нет","1.Да"};
    private Scanner scan;
    public Controller(File folder,DataQueue<String> queue,Scanner scn){
        this.queue = queue;
        this.folder = folder;
        this.scan = scn;
    }

    private synchronized boolean keepRunning(){
        return stop == false;
    }

    public synchronized void doStop(){
        stop = true;
    }


    @Override
    public void run() {
        do {
            //System.out.println("C run");
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("manage") && name.endsWith(".txt");
                }
            });
            if(files.length == 0){
                //System.out.println("Файлов управления нет");
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
            for(int i = 0;i < files.length;i++) {
                String s = null;
                File file = files[i];
                //File file = files.getFirst();
                if (file.isFile()) {
                    try {
                        s = PeopleService.readManageFile(file.getPath());

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Ошибка чтения файла");
                    }
                    if(s.equals("")){
                        file.delete();
                    }
                    if (!s.equals("")) {
                        String[] subs = s.split(";");
                        for (String sub : subs) {
                            try {
                                this.queue.offer(sub);
                                TimeUnit.SECONDS.sleep(1L);
                                file.delete();
                            } catch (InterruptedException e) {
                                System.out.println("Прерывание потока");
                                e.printStackTrace();
                            }
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
       } while(keepRunning());
    }

}
