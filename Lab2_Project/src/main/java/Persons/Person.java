package Persons;

import LabUtils.DataExceptions;
import LabUtils.FunctionHelper;

public abstract class Person {
    protected String surname;
    protected String name;
    protected String patronymic;
    protected long number;
    protected int year;

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public long getNumber() {
        return number;
    }

    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setYear(String birthYear) throws NumberFormatException, DataExceptions {
        if (!FunctionHelper.isInt(birthYear)) {
            throw new DataExceptions(birthYear + "NotANumber");
        }
        if (Integer.parseInt(birthYear) <= 0) {
            throw new DataExceptions(birthYear + "- WrongYear");
        }
        this.year = Integer.parseInt(birthYear);
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNumber(String telNumber) throws NumberFormatException, DataExceptions {
        if (!FunctionHelper.isLong(telNumber)) {
            throw new DataExceptions(telNumber + "- NotANumber");
        }
        if (Long.parseLong(telNumber) <= 0) {
            throw new DataExceptions(telNumber + "- WrongNumber");
        }
        this.number = Long.parseLong(telNumber);
    }
}
