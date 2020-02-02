package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DB_BloodGlucose extends SQLiteOpenHelper
{
    public static final String BLOOD_GLUCOSE_VALUE_COLUMN = "BLOOD_GLUCOSE_VALUE";
    public static final String INSULINE_UNIT_COUNT_COLUMN = "INSULINE_UNIT_COUNT";
    public static final String INSULINE_NAME_COLUMN = "INSULINE_NAME";
    public static final String DAY_COLUMN = "DAY";
    public static final String TIME_COLUMN = "TIME";
    public static final String DATE_COLUMN = "DATE";
    public static final String ID_COLUMN = "ID";

    private static final String DB_NAME = "BLOODGLUCOSE.db";
    private static final String TABLE_NAME = "BLOODGLUCOSE";
    private static final int DB_VERSION = 1;

    public DB_BloodGlucose(@Nullable Context context)
    {
        super(context , DB_NAME , null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String cmd = "CREATE TABLE IF NOT EXISTS '"+TABLE_NAME+"' "+
                "(" +
                    BLOOD_GLUCOSE_VALUE_COLUMN +" FLOAT ,"+
                    INSULINE_UNIT_COUNT_COLUMN +" FLOAT ,"+
                    INSULINE_NAME_COLUMN       +" TEXT ," +
                    DAY_COLUMN                 +" TEXT ," +
                    TIME_COLUMN                +" TEXT ," +
                    DATE_COLUMN                +" DATE ," +
                    ID_COLUMN                  +" INTEGER PRIMARY KEY AUTOINCREMENT"+
                ");";
        db.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    //db function's
    public Long insert(ContentValues cv)
    {
        SQLiteDatabase db = getWritableDatabase();

        long id = db.insert(TABLE_NAME , null , cv);

        db.close();
        return id;
    }

    public Long insert(BloodGlucose bg)
    {
        ContentValues cv = new ContentValues();

        cv.put(BLOOD_GLUCOSE_VALUE_COLUMN , bg.getBlood_glucose_value());
        cv.put(INSULINE_UNIT_COUNT_COLUMN , bg.getInsuline_unit_count());
        cv.put(INSULINE_NAME_COLUMN , bg.getInsulin_name());
        cv.put(DAY_COLUMN , bg.getDay());
        cv.put(TIME_COLUMN , bg.getTime());
        cv.put(DATE_COLUMN , Date2String(bg.getDate()));

        long id = insert(cv);
        return id;
    }

    public ArrayList<BloodGlucose> get_all()
    {
        ArrayList<BloodGlucose> all_item = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String cmd = "SELECT * FROM '"+TABLE_NAME+"'";
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();
        int count = cursor.getCount();

        float blood_glucose_value;
        float insuline_unit_count;
        String insulin_name;
        String day;
        String time;
        Date date;
        int id;

        for(int i=0 ; i<count ; i++)
        {
            blood_glucose_value = cursor.getFloat (cursor.getColumnIndex(BLOOD_GLUCOSE_VALUE_COLUMN));
            insuline_unit_count = cursor.getFloat (cursor.getColumnIndex(INSULINE_UNIT_COUNT_COLUMN));
            insulin_name        = cursor.getString(cursor.getColumnIndex(INSULINE_NAME_COLUMN));
            day                 = cursor.getString(cursor.getColumnIndex(DAY_COLUMN));
            time                = cursor.getString(cursor.getColumnIndex(TIME_COLUMN));
            id                  = cursor.getInt   (cursor.getColumnIndex(ID_COLUMN));
            date                = String2Date(cursor.getString(cursor.getColumnIndex(DATE_COLUMN)));

            BloodGlucose bg = new BloodGlucose( id ,  blood_glucose_value,  insuline_unit_count,  insulin_name,  day,  time,  date);

            all_item.add(bg);

            cursor.moveToNext();
        }
        db.close();

        return  all_item;
    }

    public int delete (int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        String arg[] = {String.valueOf(id)};
        int count = db.delete(TABLE_NAME , ID_COLUMN+" = ?" , arg);
        db.close();
        return count;
    }

    public int update(BloodGlucose bg)
    {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(BLOOD_GLUCOSE_VALUE_COLUMN , bg.getBlood_glucose_value());
        cv.put(INSULINE_UNIT_COUNT_COLUMN , bg.getInsuline_unit_count());
        cv.put(INSULINE_NAME_COLUMN , bg.getInsulin_name());
        cv.put(TIME_COLUMN , bg.getTime());
        cv.put(DAY_COLUMN , bg.getDay());
        cv.put(DATE_COLUMN , Date2String(bg.getDate()));

        String arg[] = {String.valueOf(bg.getId())};
        int count = db.update(TABLE_NAME , cv , ID_COLUMN+" = ?" , arg);
        db.close();
        return count;
    }

    private Date String2Date(String date)
    {
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD");
        try
        {
            return df.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private String Date2String(Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD");
        if(date != null)
        {
            return df.format(date);
        }
        else
        {
            return "";
        }
    }
}
