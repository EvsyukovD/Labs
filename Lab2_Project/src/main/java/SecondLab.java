import dao.classes.PeopleService;
import data.threads.Controller;
import data.threads.Dispathcher;
import utils.DataQueue;

import java.io.File;

public class SecondLab {
    public static void main(String args[]) {
        File manageFolder = new File("C:\\Users\\devsy\\IdeaProjects\\Lab2_Project\\FolderWithManageFiles");
        File errorFolder = new File("C:\\Users\\devsy\\IdeaProjects\\Lab2_Project\\FolderWithErrors");
        File dataFolder = new File("C:\\Users\\devsy\\IdeaProjects\\Lab2_Project\\FolderWithDataFiles");
        PeopleService pService;
        pService = new PeopleService(dataFolder);
        DataQueue<String> queue = new DataQueue<>();
        Dispathcher dispathcher = new Dispathcher(queue, pService, errorFolder);
        Controller controller = new Controller(manageFolder, queue, dispathcher);
        controller.start();
        try {
            controller.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dispathcher.start();
        while (dispathcher.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
        }
        pService.out("Завершение программы");
    }
}
