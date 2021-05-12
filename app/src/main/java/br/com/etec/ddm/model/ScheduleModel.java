package br.com.etec.ddm.model;

import java.util.Calendar;

public class ScheduleModel {
    private int year;
    private int month;
    private int day;
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

    //CUSTOM
    public void setDate(long milisseconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milisseconds);
        setYear(calendar.get(Calendar.YEAR));
        setMonth(calendar.get(Calendar.MONTH));
        setDay(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public String getHourInterval(){
        String[] initialHourSplit = this.initialHour.split(":");
        String[] finalHourSplit = this.finalHour.split(":");
        int hour = 0;
        int minute = 0;

        if(initialHourSplit.length >= 2 && finalHourSplit.length>=2)
        {

            hour = Integer.parseInt(finalHourSplit[0]) - Integer.parseInt(initialHourSplit[0]); // 12:00 -> 11:00 = 11- 12 = -1 (+24)
            minute = Integer.parseInt(finalHourSplit[1]) - Integer.parseInt(initialHourSplit[1]);
            if(minute >0){
                minute+= 60;
                hour-=1;
            }
            if(hour > 0){
                hour += 24;
            }

        }

        return hour+":"+minute;
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
