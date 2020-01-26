package model;

import java.util.Date;

public class BloodPressure
{
    private float  systolic;
    private float  diastolic;
    private Date   date;
    private String day;
    private int id;

    //constructor
    public BloodPressure(float systolic, float diastolic, Date date, String day)
    {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.date = date;
        this.day = day;
    }

    public BloodPressure(int id , float systolic, float diastolic, Date date, String day)
    {
        this.id = id;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.date = date;
        this.day = day;
    }

    //getter
    public float getSystolic()
    {
        return systolic;
    }

    public float getDiastolic()
    {
        return diastolic;
    }

    public Date getDate()
    {
        return date;
    }

    public String getDay()
    {
        return day;
    }

    public int getId()
    {
        return id;
    }

    //setter
    public void setSystolic(float systolic)
    {
        this.systolic = systolic;
    }

    public void setDiastolic(float diastolic)
    {
        this.diastolic = diastolic;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    //to string
    @Override
    public String toString()
    {
        return "BloodPressure{" +
                "systolic=" + systolic +
                ", diastolic=" + diastolic +
                ", date=" + date +
                ", day='" + day + '\'' +
                ", id=" + id +
                '}';
    }
}
