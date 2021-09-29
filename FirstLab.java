package Lab1;

import java.util.Map;
import java.util.Scanner;

public class FirstLab {
    public static void main(String args[]) {
        String s;
        //Map<K,V>
        FunctionRealizer fRealizer = new FunctionRealizer();
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Введите строку");
            s = fRealizer.readLine(scan);
            if (s == null) {
                System.out.println("Завершение работы программы\n");
                scan.close();
                return;
            }
            System.out.println("Ваша строка : " + s);
            if (fRealizer.checkLength(s)) {
                fRealizer.setFlag(s);
                if (fRealizer.getFlag()) {
                    System.out.println("Завершение работы программы\n");
                    scan.close();
                    return;
                }
                fRealizer.processString(s);
            } else {
                if (s.length() == 0) {
                    System.out.println("Мало символов");
                } else {
                    System.out.println("Слишком много символов");
                }
            }
        } while (!fRealizer.checkLength(s) || s != null);
        scan.close();
    }
}
