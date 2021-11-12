package LabUtils;

import java.util.Scanner;

public class FunctionHelper {
    /*private static boolean hasNextIntFlag = true;
    private static boolean eofFlag = false;

    private static void setHasNextIntFlag(boolean bool) {
        hasNextIntFlag = bool;
    }

    private static boolean getHasNextIntFlag() {
        return hasNextIntFlag;
    }

    private static void setEofFlag(boolean bool) {
        eofFlag = bool;
    }

    private static boolean getEofFlag() {
        return eofFlag;
    }*/

    public static boolean isLong(String s) {
        try {
            long i = Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*public static String readLine(Scanner scan) {
        String res = null;
        if (scan.hasNextLine()) {
            res = scan.nextLine();
        }
        return res;
    }*/

    /*public static int readInt(Scanner scan) {
        int res = -1;
        String s = readLine(scan);
        setEofFlag(s == null);
        if (getEofFlag()) {
            return res;
        }
        Scanner scan2 = new Scanner(s);
        setHasNextIntFlag(scan2.hasNextInt());
        if (getHasNextIntFlag()) {
            res = scan2.nextInt();
        }
        scan2.close();
        return res;
    }*/

    /*public static int dialog(String s[], int size, Scanner scan) {
        int choice, i;
        String error = "Ошибка ввода.Попробуйте ещё раз";
        do {
            System.out.println("Меню:");
            for (i = 0; i < size; i++) {
                System.out.println(s[i]);
            }
            System.out.println("Выберите опцию:");
            choice = readInt(scan);
            if (getEofFlag()) {
                return 0;
            }
            if (getHasNextIntFlag()) {
                System.out.printf("Ваша опция: %d\n", choice);
                if (choice < 0 || choice > (size - 1)) {
                    System.out.println(error);
                }
            } else {
                setHasNextIntFlag(true);
                System.out.println(error);
            }
        } while (choice < 0 || choice > (size - 1));
        return choice;
    }*/

}
