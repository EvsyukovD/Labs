package dao.classes;

import utils.DataExceptions;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

public class PeopleService implements DAO {
    private File folder;
    private final File output = new File("C:\\Users\\devsy\\IdeaProjects\\Lab2_Project\\PeopleService\\output.txt");

    public PeopleService(File fldr) {
        folder = fldr;
        try {
            PrintWriter wr = new PrintWriter(output);
            wr.close();
        } catch (Exception e) {
        }
    }

    public void out(String msg) {
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(output, true));
            buf.write(msg);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
        }
    }

    @Override
    public boolean createPerson(String id, String data) throws IOException, DataExceptions {
        PeopleDAO pDao = new PeopleDAO(folder,output);
        return pDao.createPerson(id, data);
    }

    @Override
    public boolean deletePerson(String id) throws IOException, DataExceptions {
        //CashedPeopleDAO cDao = new CashedPeopleDAO(folder,output);
        PeopleDAO pDao = new PeopleDAO(folder,output);
        return pDao.deletePerson(id);
    }

    @Override
    public boolean update(String id, String field, String data) throws IOException, DataExceptions, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        CashedPeopleDAO cDao = new CashedPeopleDAO(folder,output);
        return cDao.update(id, field, data);
    }

    @Override
    public boolean find(String id) throws IOException {
        CashedPeopleDAO cDao = new CashedPeopleDAO(folder,output);
        return cDao.find(id);
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
