import java.util.Locale;

public enum Subject {
    MATH,
    INFORMATICS,
    PHYSICS,
    DIFFEQS,//ОДУ
    ENGLISH,
    TENZORS,
    PROJECTS;
    public static Subject value(String subject) throws DataExceptions{
        String str = subject.toUpperCase(Locale.ROOT);
         for(Subject s : Subject.values()){
             if(s.toString().equals(str)){
                 return s;
             }
         }
         throw new DataExceptions("notASubject");
    }
    public static boolean isSubject(String subject){
        String str = subject.toUpperCase(Locale.ROOT);
        for(Subject s : Subject.values()){
            if(s.toString().equals(str)){
                return true;
            }
        }
        return false;
    }
}
