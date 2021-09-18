package Lab1;

import java.util.Scanner;

public interface IFunctions {
    public  String readLine(Scanner scan);
    public   boolean checkLength(String s);
    public  int ProcessString(String s);
    public  String Sort(String s);
    public  boolean isDigit(char c);
    public  int getNum(String s,int startIdx,int[] endIdx);
    public  void swap(StringBuilder s,int i,int j);
}
