package br.com.etec.ddm.model;

import java.io.Serializable;
import java.util.Calendar;

import static br.com.etec.ddm.util.DateConverter.dayOfWeek;
import static br.com.etec.ddm.util.DateConverter.monthName;

public class ScheduleModel implements Serializable {
    private int year;
    private int month;
    private int day;
    private String weekDay;
    private String monthName;
    private String initialHour;
    private String finalHour;
    private String eventName;

    public ScheduleModel(String eventName, String initialHour, String finalHour, long date){
        setEventName(eventName);
        setInitialHour(initialHour);
        setFinalHour(finalHour);
        setDate(date);
    }

    public ScheduleModel(int day, int month, int year){
        setDay(day);
        setMonth(month);
        setYear(year);
    }

    public ScheduleModel(long date){
        setDate(date);
    }

    //YEAR
    public int getYear(){
        return year;
    }

    public void setYear(int value){
        this.year = value;
    }


    //MONTH
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        setMonthName(monthName(month));
        this.month = month;
    }

    //DAY
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    //HOUR
    public String getInitialHour() {
        return initialHour;
    }

    public void setInitialHour(String initialHour) {
        this.initialHour = initialHour;
    }

    public String getFinalHour() {
        return finalHour;
    }

    public void setFinalHour(String finalHour) {
        this.finalHour = finalHour;
    }


    //EVENT NAME
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    //WEEKDAY
    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    //MONTH NAME
    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    //DATE
    public void setDate(long milisseconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milisseconds);
        setYear(calendar.get(Calendar.YEAR));
        setMonth(calendar.get(Calendar.MONTH));
        setDay(calendar.get(Calendar.DAY_OF_MONTH));

        setWeekDay(dayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)));
    }

    public long getDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year, this.month, this.day);
        return calendar.getTimeInMillis();
    }

    //CUSTOM

    public String getHourInterval(){
        String[] initialHourSplit = this.initialHour.split(":");
        String[] finalHourSplit = this.finalHour.split(":");
        int hour = 0;
        int minute = 0;

        if(initialHourSplit.length >= 2 && finalHourSplit.length>=2)
        {

            hour = Integer.parseInt(finalHourSplit[0]) - Integer.parseInt(initialHourSplit[0]); // 12:00 -> 11:00 = 11- 12 = -1 (+24)
            minute = Integer.parseInt(finalHourSplit[1]) - Integer.parseInt(initialHourSplit[1]);
            if(minute < 0){
                minute+= 60;
                hour-=1;
            }
            if(hour < 0){
                hour += 24;
            }

        }

        String padding = minute<10 ? "0" : "";

        return hour+":"+padding+minute;
    }

    public String getDetail(){
        return initialHour+" ás "+finalHour+" - "+eventName;
    }

    public String getFileName() { return year+"_"+month+"_"+day+".lsm";}

    //MISC
    public boolean isInThisDate(ScheduleModel model){
        return day == model.day && month == model.month && year == model.year;
    }

    @Override
    public String toString() {
        return "ScheduleModel{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", initialHour='" + initialHour + '\'' +
                ", finalHour='" + finalHour + '\'' +
                ", eventName='" + eventName + '\'' +
                '}';
    }


}
