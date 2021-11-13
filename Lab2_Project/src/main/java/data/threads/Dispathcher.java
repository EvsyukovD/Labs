package data.threads;

import dao.classes.PeopleService;
import utils.DataQueue;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class Dispathcher extends Thread {
    private DataQueue<String> queue;
    private PeopleService pService;
    private boolean stop = false;
    private File errorFolder;

    public Dispathcher(DataQueue<String> queue, PeopleService pService, File errorFolder) {
        this.queue = queue;
        this.pService = pService;
        this.errorFolder = errorFolder;
    }

    private boolean checkString(String s) {
        return s.startsWith("update") || s.startsWith("createPerson") || s.startsWith("deletePerson") || s.startsWith("find");
    }

    private boolean InvokeMethods(PeopleService pService, String str) throws Exception {
        String name = str.split(" ")[0];
        String id = str.split(" ")[1];
        if (name.equals("deletePerson") || name.equals("find")) {
            return (boolean) pService.getClass().getMethod(name, String.class).invoke(pService, id);
        }
        if (name.equals("update")) {
            String field = str.split(" ")[2];
            String data = str.split(" ")[3];
            return (boolean) pService.getClass().getMethod(name, String.class, String.class, String.class).invoke(pService, id, field, data);
        }
        if (name.equals("createPerson")) {
            String data = str.split(" ")[2];
            return (boolean) pService.getClass().getMethod(name, String.class, String.class).invoke(pService, id, data);
        }
        return false;
    }

    public synchronized void doStop() {
        this.stop = true;
    }

    public synchronized boolean keepRunning() {
        return !stop;
    }

   private void doCommands(int n) throws Exception {
        String name;
       for (int i = 0; i < n; i++) {
           name = queue.poll();
           if (checkString(name)) {
               InvokeMethods(pService, name);
           }
       }
   }

   private void moveFile(BufferedWriter buffer,String fPath,File file,Exception e) throws IOException {
       buffer.newLine();
       e.getCause().printStackTrace(new PrintWriter(buffer));
       buffer.close();
       File errorFile = new File(errorFolder.getPath() + "\\" + file.getName());
       if (errorFile.exists()) {
           errorFile.delete();
       }
       Files.move(Path.of(fPath), Path.of(errorFolder.getPath() + "\\" + file.getName()));
   }

    @Override
    public void run() {
        int n;
        BufferedWriter buffer = null;
        File file = null;
        String fPath = "";
        while (true) {
            try {
                if (!keepRunning()) {
                    //Файлов управления нет.Завершение прроограммы
                    return;
                }
                n = Integer.parseInt(queue.poll());
                fPath = queue.poll();
                file = new File(fPath);
                buffer = new BufferedWriter(new FileWriter(fPath, true));
                doCommands(n);
                buffer.close();
                file.delete();
                TimeUnit.SECONDS.sleep(1L);
                if (queue.size() == 0) {
                    //Файлов управления нет.Завершение прроограммы
                    stop = true;
                    return;
                }
            } catch (Exception e) {
                try {
                    moveFile(buffer,fPath,file,e);
                } catch (Exception ex) {
                }
                pService.out("Ошибка в файле: " + file.getName());
                return;
            }
        }
    }
}
