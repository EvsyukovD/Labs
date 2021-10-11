import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class PeopleService implements Dao {
    private final PeopleDAO pDao;
    private final CashedPeopleDAO cDao;
    public PeopleService(Scanner scn,File fldr) throws IOException {
        pDao = new PeopleDAO(scn,fldr);
        cDao = new CashedPeopleDAO(scn,fldr);
    }
    @Override
    public boolean createPerson() {
        return pDao.createPerson();
    }

    @Override
    public boolean deletePerson() {
        return cDao.deletePerson();
    }

    @Override
    public boolean update() {
        return cDao.update();
    }

    @Override
    public boolean find() {
        return cDao.find();
    }
}
