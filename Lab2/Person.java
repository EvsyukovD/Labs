public abstract class Person {
    protected String surname;
    protected String name;
    protected String patronymic;
    protected long telNumber;
    protected int birthYear;

    public  String getSurname(){
        return surname;
    }
    public  String getName(){
        return name;
    }
    public  String getPatronymic(){
        return patronymic;
    }
    public  long getTelNumber(){
        return telNumber;
    }
    public  int getBirthYear(){
        return birthYear;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setBirthYear(String birthYear) throws NumberFormatException, DataExceptions {
        if(Integer.parseInt(birthYear) <= 0){
            throw new DataExceptions("OutOfData");
        }
        this.birthYear = Integer.parseInt(birthYear);
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTelNumber(String telNumber) throws NumberFormatException, DataExceptions {
        if(Integer.parseInt(telNumber) <= 0){
            throw new DataExceptions("OutOfData");
        }
        this.telNumber = Long.parseLong(telNumber);
    }
}
