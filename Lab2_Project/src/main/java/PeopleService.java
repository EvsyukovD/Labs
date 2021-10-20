import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;

public class PeopleService implements Dao {
    private  PeopleDAO pDao;
    private  CashedPeopleDAO cDao;
    public Scanner scn;
    private File folder;
    public PeopleService(Scanner scan,File fldr) throws IOException {
        scn =  scan;
        folder = fldr;
        //pDao = new PeopleDAO(scn,fldr);
        //cDao = new CashedPeopleDAO(scn,fldr);
    }
    public Scanner getScn(){
        return this.scn;
    }
    @Override
    public boolean createPerson() {
        PeopleDAO pDao = new PeopleDAO(scn,folder);
        return pDao.createPerson();
    }

    @Override
    public boolean deletePerson() {
        try {
            CashedPeopleDAO cDao = new CashedPeopleDAO(scn, folder);
            return cDao.deletePerson();
        } catch (IOException e){
            System.out.println("Не удалось считать данные из файла");
            return false;
        }
    }

    @Override
    public boolean update() {
        try {
            CashedPeopleDAO cDao = new CashedPeopleDAO(scn, folder);
            return cDao.update();
        } catch (IOException e){
            System.out.println("Не удалось считать данные из файла");
            return false;
        }
    }

    @Override
    public boolean find() {
        try {
            CashedPeopleDAO cDao = new CashedPeopleDAO(scn, folder);
            return cDao.find();
        } catch (IOException e){
            System.out.println("Не удалось считать данные из файла");
            return false;
        }
    }

    public static String readManageFile(String path) throws IOException {
        File file =  new File(path);
        StringBuffer commands = new StringBuffer();
          boolean canReadFlag = true;
            String buffer;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while(canReadFlag){
                  buffer = reader.readLine();
                  canReadFlag = buffer != null;
                  if(buffer != null) {
                      commands.append(buffer + ";");
                  }
            }
            reader.close();
            return commands.toString();
            //System.out.println("Файл не найден");
    }
}
