package Lab1;

import java.util.Scanner;

public interface IFunctions {
    public  String readLine(Scanner scan);
    public   boolean checkLength(String s);
    public  int processString(String s);
    public  String sort(String s);
    public  boolean isDigit(char c);
    public  double getNum(String s,int startIdx,int[] endIdx);
    public  void swap(StringBuilder s,int i,int j);
}
