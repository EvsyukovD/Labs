import java.io.File;
import java.io.IOException;
import java.util.*;

public class SecondLab {
    public static void main(String args[]) {
        File manageFolder = new File("C:\\Users\\devsy\\IdeaProjects\\Lab2_Project\\FolderWithManageFiles");
        File errorFolder = new File("C:\\Users\\devsy\\IdeaProjects\\Lab2_Project\\FolderWithErrors");
        File dataFolder = new File("C:\\Users\\devsy\\IdeaProjects\\Lab2_Project\\FolderWithDataFiles");
        PeopleService pService;
        Scanner scan = new Scanner(System.in);

        /*do {
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
        }while(!folder.exists() || (folder.exists() && !folder.isDirectory()));*/
        //try {
        pService = new PeopleService(scan, dataFolder);
        /*} catch (IOException e) {
            e.printStackTrace();
        }*/
        DataQueue<String> queue = new DataQueue<>();
        Controller controller = new Controller(manageFolder, queue, scan);
        Dispathcher dispathcher = new Dispathcher(queue, pService, errorFolder);
        controller.start();
        try {
            controller.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dispathcher.start();
        /*if (!controller.isAlive()) {
            dispathcher.doStop();
        }*/
        if (dispathcher.isAlive() && !controller.isAlive()) {
            dispathcher.doStop();
            scan.close();
        }
        /*if(!controller.isAlive()){
                dispathcher.doStop(true);
            }
            if(!controller.isAlive() || !dispathcher.isAlive()) {
                scan.close();
            }*/
    }
}
