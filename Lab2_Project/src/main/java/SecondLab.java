import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class SecondLab {
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);

        String msgs[] = { "0.Выход","1.Обработать объекты учитель и ученик с помощью PeopleDAO" };
	    String peopleDaoMsgs[] = {"0.Перейти к другой опции","1.Создать персону","2.Обновить персону","3.Удалить персону","4.Найти персону"};
        String peopleDaoFuncNames[] = {"quit","createPerson","update","deletePerson","find"};
        Class[] classes = {PeopleDAO.class};
        String methodsNames[][] = new String[classes.length][];
        String classMsgs[][] = new String[classes.length][];

        classMsgs[0] = peopleDaoMsgs;
        methodsNames[0] = peopleDaoFuncNames;

        /*HashMap<Subject,Integer> subjects = new HashMap<Subject,Integer>();
        subjects.put(Subject.DIFFEQS,0);
        subjects.put(Subject.ENGLISH,1);
        Student s = new Student("Ivanov","Ivan","Ivanich",6564664,2020,subjects);
        ArrayList<Student> students = new ArrayList<>();
        students.add(s);
        ArrayList<Teacher> teachers = new ArrayList<>();*/
	     int rc;
	     do {
             rc = FunctionHelper.dialog(msgs,msgs.length,scan);
             try {
                 while(rc != 0 && (boolean)(
                         classes[rc - 1].getMethod(
                                 methodsNames[rc - 1][
                                         FunctionHelper.dialog(classMsgs[rc - 1],classMsgs[rc - 1].length,scan)],
                                 null).
                         invoke(classes[rc - 1].getConstructor(Scanner.class).newInstance(scan),null)));

             } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                 e.printStackTrace();
                System.out.println("Не удалось запустить метод.Завершение работы программы");
                return;
             }
	     } while (rc != 0);
         scan.close();

        /*FunctionHelper functionHelper = new FunctionHelper();
        File file;
        do {
            String s = functionHelper.readLine(new Scanner(System.in));
            if(s == null){
                System.out.println("Ошибка чтения.Завершение работы программы");
                return;
            }
            file = new File(s);
            if(!file.exists()){
                System.out.println("Такого файла или каталога не существует");
            }
        } while(!file.exists());*/

    }
}
