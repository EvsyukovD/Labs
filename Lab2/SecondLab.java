import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class SecondLab {
    public static void main(String args[]){
        File folder;
        PeopleService pService = null;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Укажите путь для папки:");
            String path = FunctionHelper.readLine(scan);
            if(path == null){
                System.out.println("Завершение работы");
                return;
            }
            folder = new File(path);
            if(!folder.exists() || (folder.exists() && !folder.isDirectory())){
                System.out.println("Это не папка или такой папки нет");
            }
        }while(!folder.exists() || (folder.exists() && !folder.isDirectory()));
        try {
            pService = new PeopleService(scan, folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataQueue<String> queue = new DataQueue<>();
        Controller controller = new Controller(folder,queue,scan);
        Dispathcher dispathcher = new Dispathcher(queue,pService);
        //Thread controllerThread = new Thread(controller);
        //Thread dispathcherThread = new Thread(dispathcher);
            controller.start();
            try {
                controller.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        /*if(!controller.isAlive()){
            dispathcher.doStop(true);
        }*/
            dispathcher.start();
            /*try {
                dispathcherThread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            /*try{
                stop = queue.getStopFlag();
            } catch (InterruptedException e){
                e.printStackTrace();
            }*/
        /*while(!stop){
            controllerThread.run();
            try {
                controllerThread.sleep(200L);
                controllerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dispathcherThread.run();

            try {
                dispathcherThread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                stop = queue.getStopFlag();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }*/
            /*if (!dispathcher.isAlive()) {
                controller.doStop();
            }*/
            if(!controller.isAlive()){
                dispathcher.doStop(true);
            }
            /*if(!controller.isAlive() && dispathcher.isAlive()){
                dispathcher.doStop(true);
            }*/
            if(!controller.isAlive() || !dispathcher.isAlive()) {
                scan.close();
            }
        /*String msgs[] = { "0.Выход","1.Обработать объекты учитель и ученик с помощью PeopleDAO","2.Обработать объекты учитель и ученик с помощью CashedPeopleDAO" };
	    String DaoMsgs[] = {"0.Перейти к другой опции","1.Создать персону","2.Обновить персону","3.Удалить персону","4.Найти персону"};
        String DaoFuncNames[] = {"quit","createPerson","update","deletePerson","find"};
        Class[] classes = {PeopleDAO.class,CashedPeopleDAO.class};
        String methodsNames[][] = new String[classes.length][];
        String classMsgs[][] = new String[classes.length][];

        classMsgs[0] = DaoMsgs;
        classMsgs[1] = DaoMsgs;
        methodsNames[0] = DaoFuncNames;
        methodsNames[1] = DaoFuncNames;
	     int rc;
	     do {
             rc = FunctionHelper.dialog(msgs,msgs.length,scan);
             try {
                 while(rc != 0 && (boolean)(
                         classes[rc - 1].getMethod(
                                 methodsNames[rc - 1][
                                         FunctionHelper.dialog(classMsgs[rc - 1],classMsgs[rc - 1].length,scan)],
                                 null).
                         invoke(classes[rc - 1].getConstructor(Scanner.class,File.class).newInstance(scan,folder),null)));

             } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                 e.printStackTrace();
                System.out.println("Не удалось запустить метод.Завершение работы программы");
                return;
             }
	     } while (rc != 0);*/
    }
}
