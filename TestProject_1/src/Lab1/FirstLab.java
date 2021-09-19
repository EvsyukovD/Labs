package Lab1;

import java.util.Scanner;

public class FirstLab {
    public static void main(String args[]) {
        String s;
        FunctionRealizer FRealizer = new FunctionRealizer();
        Scanner scan = new Scanner(System.in);
        do {
            s = null;
            System.out.println("Введите строку");
            s = FRealizer.readLine(scan);
            if (s == null) {
                System.out.println("Завершение работы программы\n");
                scan.close();
                return;
            }
            System.out.println("Ваша строка : " + s);
            if(FRealizer.checkLength(s)){
                FRealizer.setFlag(s);
                if(FRealizer.getFlag()){
                    s = null;
                    System.out.println("Завершение работы программы\n");
                    scan.close();
                    return;
                }
                FRealizer.processString(s);
            } else {
                if(s.length() == 0) {
                    System.out.println("Мало символов");
                } else {
                    System.out.println("Слишком много символов");
                }
            }
        }while(!FRealizer.checkLength(s) || s != null);
         scan.close();
    }
}
