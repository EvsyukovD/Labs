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
                int res = FRealizer.ProcessString(s);
                if(res == 0){
                    s = null;
                    System.out.println("Завершение работы программы\n");
                    scan.close();
                    return;
                }
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
/*interface IFunc{
    public  String readLine();
    public  boolean checkLength(String s);
    public  int ProcessString(String s);
    public  String Sort(String s);
    public  boolean isDigit(char c);
    public  int getNum(String s,int startIdx,int[] endIdx);
    public  void swap(char[] s,int i,int j);
}*/
/*interface IFunctions{
    public static String readLine(Scanner scan){
        return null;
    }
    public  static boolean checkLength(String s){
        return false;
    }
    public static int ProcessString(String s){
        return 0;
    }
    public static String Sort(String s){
     return null;
    }
    public static boolean isDigit(char c){
       return false;
    }
    public static int getNum(String s,int startIdx,int[] endIdx){
        return 0;
    }
    public static void swap(StringBuilder s,int i,int j){

    }
}*/
/*class F implements IFunc{

    @Override
    public String readLine() {
        return null;
    }

    @Override
    public boolean checkLength(String s) {
        return false;
    }

    @Override
    public int ProcessString(String s) {
        return 0;
    }

    @Override
    public String Sort(String s) {
        return null;
    }

    @Override
    public boolean isDigit(char c) {
        return false;
    }

    @Override
    public int getNum(String s, int startIdx, int[] endIdx) {
        return 0;
    }

    @Override
    public void swap(char[] s, int i, int j) {

    }
}*/

/*class FunctionRealizer implements IFunctions{

    public  String readLine(Scanner scan){
        String res = null;
        if (scan.hasNextLine()) {
            res = scan.nextLine();
        }
        return res;
    }

    public  boolean checkLength(String s) {
        return s.length() > 17 || s.length() == 0 ? false:true;
    }

    public  int ProcessString(String s) {
            if(s.equals("q")){
                return 0;
            }
            if(s.equals("--h")){
                String info = "Программа принимает на вход любую последовательность символов длинной до 17 знаков, что-то делает и продолжает работать, если только на вход на подана строка “q”. В последнем случае – программа завершается\n" +
                        "Если длина более 17 знаков – в консоль пишется сообщение, говорящее, что ввод слишком длинный и программа продолжает ожидать следующего ввода\n" +
                        "Если на вход подали строку вида «--h», то выводится информация о функциях программы\n" +
                        "Если на вход подали число, то программа выводит квадрат числа и кубический корень квадрата числа, с точностью до трех знаков после запятой\n" +
                        "Если в начале строки подали два числа, разделенных пробелом (например, “10 34agagaga”), то программа опускает все нечисловые знаки и делит первое число на второе число и выводит результат с точностью до трех знаков после запятой.\n" +
                        "Если на вход подана строка, то программа сортирует символы в строке в лексикографическом порядке (в порядке кодировки юникод) и вводит отсортированную строку и число уникальных символов в строке.\n";
                System.out.println(info);
            }
            if(isDigit(s.charAt(0))){
               int[] endIdx = {0};
               int num1 = getNum(s,0,endIdx);
               if(!(endIdx[0] + 1 < s.length() && s.charAt(endIdx[0]) == ' ' && isDigit(s.charAt(endIdx[0] + 1)))) {
                   long sqr = num1 * num1;
                   System.out.println("Квадрат числа : " + sqr);
                   double sqrt3 = Math.pow((double) sqr, (double) 1 / 3);
                   System.out.printf("Кубический корень из квадрата : %.3f\n",sqrt3);
               } else {
                    int num2 = getNum(s,endIdx[0] + 1,endIdx);

                    if(num2 != 0) {
                       double res = num1 / num2;
                       System.out.println("Результат деления :");
                       System.out.printf("%.3f\n",res);
                    } else {
                        System.out.println("Деление невозможно");
                    }
               }

            }
            String res = Sort(s);
            System.out.println("Результат сортировки :");
            System.out.println(res);
            return 1;
    }

    public  boolean isDigit(char c){
        return  (0 <= c - '0' && c - '0'<= 9);
    }

    public  String Sort(String s) {
        StringBuilder build = new StringBuilder();
        build.append(s);
        int N = build.length();
        for(int i = 0;i < N;i++) {
            for (int j = i + 1; j < N; j++) {
                if (build.charAt(i) > build.charAt(j)) {
                    swap(build, i, j);
                }
            }
        }
        String res = build.toString();
        return res;
    }

    public  int getNum(String s,int startIdx,int[] endIdx){
        int res = 0;
        endIdx[0] = 0;
        for(int i = startIdx;i < s.length() && isDigit(s.charAt(i));i++){
            res = res * 10 + s.charAt(i) - '0';
            endIdx[0]++;
        }
        return res;
    }

    public  void swap(StringBuilder s,int i,int j){
                char c = s.charAt(i);
                s.setCharAt(i,s.charAt(j));
                s.setCharAt(j,c);
    }
}*/