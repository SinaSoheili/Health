package model;

import java.util.Date;

public class BloodGlucose
{
    private float blood_glucose_value;
    private float insuline_unit_count;
    private String insulin_name;
    private String day;
    private String time;
    private Date date;
    private int id;

    //costructor
    public BloodGlucose(float blood_glucose_value, float insuline_unit_count, String insulin_name, String day, String time, Date date)
    {
        this.blood_glucose_value = blood_glucose_value;
        this.insuline_unit_count = insuline_unit_count;
        this.insulin_name = insulin_name;
        this.day = day;
        this.time = time;
        this.date = date;
    }

    public BloodGlucose(int id , float blood_glucose_value, float insuline_unit_count, String insulin_name, String day, String time, Date date)
    {
        this.blood_glucose_value = blood_glucose_value;
        this.insuline_unit_count = insuline_unit_count;
        this.insulin_name = insulin_name;
        this.day = day;
        this.time = time;
        this.date = date;
        this.id = id;
    }

    //getter
    public float getBlood_glucose_value()
    {
        return blood_glucose_value;
    }

    public float getInsuline_unit_count()
    {
        return insuline_unit_count;
    }

    public String getInsulin_name()
    {
        return insulin_name;
    }

    public String getDay()
    {
        return day;
    }

    public String getTime()
    {
        return time;
    }

    public Date getDate()
    {
        return date;
    }

    public int getId()
    {
        return id;
    }

    //setter
    public void setBlood_glucose_value(float blood_glucose_value)
    {
        this.blood_glucose_value = blood_glucose_value;
    }

    public void setInsuline_unit_count(float insuline_unit_count)
    {
        this.insuline_unit_count = insuline_unit_count;
    }

    public void setInsulin_name(String insulin_name)
    {
        this.insulin_name = insulin_name;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    //to string
    @Override
    public String toString()
    {
        return "BloodGlucose{" +
                "blood_glucose_value=" + blood_glucose_value +
                ", insuline_unit_count=" + insuline_unit_count +
                ", insulin_name='" + insulin_name + '\'' +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
