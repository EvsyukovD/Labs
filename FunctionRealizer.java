package Lab1;

import java.util.Scanner;

public class FunctionRealizer implements IFunctions {
 private boolean flag;
    public String readLine(Scanner scan) {
        String res = null;
        if (scan.hasNextLine()) {
            res = scan.nextLine();
        }
        return res;
    }
    public void setFlag(String s){
        flag = s.equals("q");
    }
    public boolean getFlag(){
        return flag;
    }

    public boolean checkLength(String s) {
        return s.length() <= 17 && s.length() != 0;
    }

    public int processString(String s) {
        if (s.equals("--h")) {
            String info = "Программа принимает на вход любую последовательность символов длинной до 17 знаков, что-то делает и продолжает работать, если только на вход на подана строка “q”. В последнем случае – программа завершается\n" +
                    "Если длина более 17 знаков – в консоль пишется сообщение, говорящее, что ввод слишком длинный и программа продолжает ожидать следующего ввода\n" +
                    "Если на вход подали строку вида «--h», то выводится информация о функциях программы\n" +
                    "Если на вход подали число, то программа выводит квадрат числа и кубический корень квадрата числа, с точностью до трех знаков после запятой\n" +
                    "Если в начале строки подали два числа, разделенных пробелом (например, “10 34agagaga”), то программа опускает все нечисловые знаки и делит первое число на второе число и выводит результат с точностью до трех знаков после запятой.\n" +
                    "Если на вход подана строка, то программа сортирует символы в строке в лексикографическом порядке (в порядке кодировки юникод) и вводит отсортированную строку и число уникальных символов в строке.\n";
            System.out.println(info);
        }
        if (isDigit(s.charAt(0)) || (s.length() >= 2 && s.charAt(0) == '-' && isDigit(s.charAt(1)))) {
            int[] endIdx = {0};//Индекс последней цифры числа
            double num1 = getNum(s,0,endIdx);
            //System.out.printf("s.charAt(endIdx[0]) = %c\n",s.charAt(endIdx[0]));

            //Проверяем есть ли второе число и если есть то какое: положительное или отрицательное
            boolean flagSecondPlus = endIdx[0] + 2 < s.length() && s.charAt(endIdx[0] + 1) == ' ' && isDigit(s.charAt(endIdx[0] + 2));
            boolean flagSecondMinus = endIdx[0] + 3 < s.length() && s.charAt(endIdx[0] + 1) == ' ' && s.charAt(endIdx[0] + 2) == '-' && isDigit(s.charAt(endIdx[0] + 3));

            if (!(flagSecondPlus || flagSecondMinus)) {
                double sqr = num1 * num1;
                System.out.println("Квадрат числа : ");
                System.out.printf("%.3f\n",sqr);
                double sqrt3 = Math.pow(sqr, (double) 1 / 3);
                System.out.printf("Кубический корень из квадрата : %.3f\n", sqrt3);
            } else {
                double num2 = getNum(s,endIdx[0] + 2,endIdx);
                if (!Double.isNaN(num1 / num2) && !Double.isInfinite(num1 / num2)) {
                    double res = num1 / num2;
                    System.out.println("Результат деления :");
                    System.out.printf("%.3f\n", res);
                } else {
                    System.out.println("Деление невозможно");
                }
            }

        }
        String res = sort(s);
        System.out.println("Результат сортировки :");
        System.out.println(res);
        System.out.println(("Количество уникальных символов: " + getNumOfUniqSym(res)));
        return 1;
    }

    public boolean isDigit(char c) {
        return (0 <= c - '0' && c - '0' <= 9);
    }

    public String sort(String s) {
        StringBuilder build = new StringBuilder();
        build.append(s);
        int N = build.length();
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (build.charAt(i) > build.charAt(j)) {
                    swap(build, i, j);
                }
            }
        }
        String res = build.toString();
        return res;
    }
    public int getNumOfUniqSym(String s){
        int k = 1;
        for(int i = 1;i < s.length();i++){
            if(s.charAt(i) != s.charAt(i - 1)){
                k++;
            }
        }
        return k;
    }
    /*public double getNum(String s, int startIdx, int[] endIdx) {
        double res = 0.0;
        int i;
        endIdx[0] = startIdx;
        for (i = startIdx; i < s.length() && isDigit(s.charAt(i)); i++) {
            //flag = s.charAt(i) == '.';
          //  if(flag && isDigit(s.charAt(i))) {
             //   res = res / 10 + s.charAt(i) - '0';
           // } else if(isDigit(s.charAt(i))){
                res = res * 10.0 + (double)(s.charAt(i) - '0');
           // }
            endIdx[0]++;
        }
        if(i < s.length() && s.charAt(i) == '.'){
            int k = 10;
            for (int j = i + 1; j < s.length() && (isDigit(s.charAt(j))); j++) {
                res = res + (double)(s.charAt(j) - '0') / (double) k;
                k *= 10;
                endIdx[0]++;
            }
        }
        return res;
    }*/


    public double getNum(String s, int startIdx, int[] endIdx) {
        double res = 0.0;
        int i;
        int firstDigitIdx = s.charAt(startIdx) == '-'? startIdx + 1:startIdx;
        for (i = firstDigitIdx; i < s.length() && isDigit(s.charAt(i)); i++) {
            res = res * 10.0 + (double)(s.charAt(i) - '0');
            endIdx[0] = i;
        }
        if(i + 1 < s.length() && s.charAt(i) == '.' && isDigit(s.charAt(i + 1))){
            int k = 10;
            for (int j = i + 1; j < s.length() && (isDigit(s.charAt(j))); j++) {
                res = res + (double)(s.charAt(j) - '0') / (double) k;
                k *= 10;
                endIdx[0] = j;
            }
        }
        if(firstDigitIdx == startIdx + 1){
            res = (-1.0) * res;
        }
        return res;
    }


    public void swap(StringBuilder s, int i, int j) {
        char c = s.charAt(i);
        s.setCharAt(i, s.charAt(j));
        s.setCharAt(j, c);
    }
}
