package Lab1;

import java.util.Scanner;

public interface IFunctions {
      String readLine(Scanner scan);
      boolean checkLength(String s);
      int processString(String s);
      String sort(String s);
      boolean isDigit(char c);
      void setFlag(String s);
      boolean getFlag();
      double getNum(String s,int startIdx,int[] endIdx);
      void swap(StringBuilder s,int i,int j);
}
