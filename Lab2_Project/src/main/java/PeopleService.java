import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class PeopleService implements DAO {
    private PeopleDAO pDao;
    private CashedPeopleDAO cDao;
    public Scanner scn;
    private File folder;

    public PeopleService(Scanner scan, File fldr) /*throws IOException*/ {
        scn = scan;
        folder = fldr;
    }

    @Override
    public boolean createPerson(String id, String data) throws IOException, DataExceptions {
        PeopleDAO pDao = new PeopleDAO(scn, folder);
            return pDao.createPerson(id, data);
    }

    @Override
    public boolean deletePerson(String id) {
        try {
            CashedPeopleDAO cDao = new CashedPeopleDAO(scn, folder);
            return cDao.deletePerson(id);
        } catch (Exception e) {
            System.out.println("Не удалось считать данные из файла");
            return false;
        }
    }

    @Override
    public boolean update(String id, String field, String data) {
        try {
            CashedPeopleDAO cDao = new CashedPeopleDAO(scn, folder);
            return cDao.update(id, field, data);
        } catch (Exception e) {
            System.out.println("Не удалось считать данные из файла");
            return false;
        }
    }

    @Override
    public boolean find(String id) {
        try {
            CashedPeopleDAO cDao = new CashedPeopleDAO(scn, folder);
            return cDao.find(id);
        } catch (Exception e) {
            System.out.println("Не удалось считать данные из файла");
            return false;
        }
    }

    public static LinkedList<String> readManageFile(String path) throws IOException {
        File file = new File(path);
        LinkedList<String> strs = new LinkedList<>();
        String buffer = "";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (buffer != null) {
            buffer = reader.readLine();
            if (buffer != null) {
                strs.add(buffer);
            }
        }
        reader.close();
        return strs;
    }
}
