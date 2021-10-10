import java.util.Locale;

public enum Subject {
    MATH,
    INFORMATICS,
    PHYSICS,
    DIFFEQS,//ОДУ
    ENGLISH,
    TENZORS,
    PROJECTS;
    public static Subject value(String subject) throws SubjectException{
        String str = subject.toUpperCase(Locale.ROOT);
         for(Subject s : Subject.values()){
             if(s.toString().equals(str)){
                 return s;
             }
         }
         throw new SubjectException("notASubject");
    }
    public static class SubjectException extends Exception{
        public SubjectException(String s){super(s);}
    }
}
