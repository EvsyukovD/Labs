import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Dispathcher extends Thread {
    private DataQueue<String> queue;
    private PeopleService pService;
    private DState state = DState.NONE;
    private boolean stop = false;
    private String[] msgs = {"0.Нет","1.Да"};
    public Dispathcher(DataQueue<String> queue,PeopleService pService){
    this.queue = queue;
     this.pService = pService;
    }

    private boolean checkString(String s){
        return s.equals("update") || s.equals("createPerson") || s.equals("deletePerson") || s.equals("find");
    }

    public void doStop(boolean b){
       this.stop = b;
    }

    @Override
    public void run() {
        boolean stop = false;
          while(!stop) {
              state = DState.NONE;
              //queue.setSignal(true);
              //D start
              if(this.stop){
                  System.out.println("Файлов управления нет");
                  return;
              }
              try {
                  String name = "";
                  name = queue.poll();
                  if (checkString(name)) {
                      System.out.println("Команда :" + name);
                      state = DState.getValue((boolean) pService.getClass().getMethod(name, null).invoke(pService, null));
                      while (state != DState.WORK) {
                          wait();
                      }
                  }
                  TimeUnit.SECONDS.sleep(1);
                  /*if(queue.size() != 0){
                  System.out.println("Продолжть работу программы?");
                  stop = FunctionHelper.dialog(msgs, msgs.length, pService.getScn()) == 0;
                  //TimeUnit.SECONDS.sleep(2);
                  if(stop){
                      return;
                  }
                  }*/
                  if(queue.size() == 0){
                      System.out.println("Файлов управления нет");
                      return;
                  }
              } catch (InterruptedException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                  e.printStackTrace();
              }
          }
    }
  enum DState{
        WORK,
        NONE;
      public static DState getValue(boolean b){
            return WORK;
      }
  }
}
