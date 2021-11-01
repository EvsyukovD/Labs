import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface DAO {
      boolean createPerson(String id,String data) throws IOException, DataExceptions;
      boolean deletePerson(String id) throws DataExceptions;
      boolean update(String id,String field,String data) throws DataExceptions, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, InstantiationException;
      boolean find(String id);

}
