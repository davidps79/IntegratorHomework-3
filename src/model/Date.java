package model;

/**
 * It is used for representing dates.
 */
public class Date {
    /**
     * Day of the date.
     */
    private int day;

    /**
     * Month of the date.
     */
    private int month;

    /**
     * Year of the date.
     */
    private int year;

    /**
     * Constructor of the class
     * @param day int.
     * @param month int.
     * @param year int.
     */
    public Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // Getters and Setters

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * toString method. It returns the properties in human readable format.
     */
    @Override
    public String toString(){
        return day + "/" + month + "/" + year;
    }
}