package model;

import androidx.annotation.NonNull;

public class MedicationSchedule
{
    private String medicine_name;
    private String medicine_amount;
    private String time;
    private Day day;

    //constructor
    public MedicationSchedule(String medicine_name, String medicine_amount, String time, Day day)
    {
        this.medicine_name = medicine_name;
        this.medicine_amount = medicine_amount;
        this.time = time;
        this.day = day;
    }

    //getter
    public String getMedicine_name()
    {
        return medicine_name;
    }

    public String getMedicine_amount()
    {
        return medicine_amount;
    }

    public String getTime()
    {
        return time;
    }

    public Day getDay()
    {
        return day;
    }

    //setter
    public void setMedicine_name(String medicine_name)
    {
        this.medicine_name = medicine_name;
    }

    public void setMedicine_amount(String medicine_amount)
    {
        this.medicine_amount = medicine_amount;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public void setDay(Day day)
    {
        this.day = day;
    }

    //to string
    @Override
    public String toString()
    {
        return "MedicationSchedule{" +
                "medicine_name='" + medicine_name + '\'' +
                ", medicine_amount='" + medicine_amount + '\'' +
                ", time='" + time + '\'' +
                ", day='" + day + '\'' +
                '}';
    }

    //enum for day's
    public enum Day
    {
        saturday , sunday , monday , tuesday , wednesday , thursday , friday;

        public static String Day2String(Day d)
        {
            return d.toString();
        }

        public static Day String2Day(String d)
        {
            Day day = null;

            if(d.equals(saturday.toString()))
            {
                day = saturday;
            }
            else if(d.equals(sunday.toString()))
            {
                day = sunday;
            }
            else if(d.equals(monday.toString()))
            {
                day = monday;
            }
            else if(d.equals(tuesday.toString()))
            {
                day = tuesday;
            }
            else if(d.equals(wednesday.toString()))
            {
                day = wednesday;
            }
            else if(d.equals(thursday.toString()))
            {
                day = thursday;
            }
            else if(d.equals(friday.toString()))
            {
                day = friday;
            }

            return day;
        }
    }
}
