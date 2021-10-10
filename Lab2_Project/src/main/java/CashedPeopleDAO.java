import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class CashedPeopleDAO implements Dao{
    private final File folder;
    private final Scanner scan;
    private HashMap<String,Boolean> map = new HashMap<>();
    public CashedPeopleDAO(Scanner scn){
        folder = new File("C:\\Users\\devsy\\IdeaProjects\\Lab2_Project\\CashedPersons");
        scan = scn;
    }
    public  boolean quit(){
        return false;
    }
    @Override
    public boolean createPerson() {
        return false;
    }

    @Override
    public boolean deletePerson() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean find() {
        return false;
    }
}
