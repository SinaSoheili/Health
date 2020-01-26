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

public class DB_BloodPressure extends SQLiteOpenHelper
{
    public static final String SYSTOLIC_COLUMN = "Systolic";
    public static final String DIASTOLIC_COLUMN = "Diastolic";
    public static final String DATE_COLUMN = "Date";
    public static final String DAY_COLUMN = "Day";
    public static final String ID_COLUMN = "ID";

    private static final String DB_NAME = "BloodPressure.db";
    private static final String TABLE_NAME = "BloodPressure";
    private static final int    DB_VERSION = 1 ;

    public DB_BloodPressure(@Nullable Context context)
    {
        super(context, DB_NAME , null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String cmd = "CREATE TABLE IF NOT EXISTS '"+TABLE_NAME+"' "+
                "("+
                    ID_COLUMN        + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                    SYSTOLIC_COLUMN  + " FLOAT ,"+
                    DIASTOLIC_COLUMN + " FLOAT ,"+
                    DATE_COLUMN      + " DATE  ,"+
                    DAY_COLUMN       + " TEXT"+
                ");";
        db.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    // db function's
    private long insert(ContentValues cv)
    {
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(TABLE_NAME , null , cv);
        db.close();
        return id;
    }

    public long insert(BloodPressure bp)
    {
        ContentValues cv = new ContentValues();

        cv.put(SYSTOLIC_COLUMN  ,bp.getSystolic());
        cv.put(DIASTOLIC_COLUMN ,bp.getDiastolic());
        cv.put(DAY_COLUMN       ,bp.getDay());
        cv.put(DATE_COLUMN      ,Date2String(bp.getDate()));

        long id = this.insert(cv);

        return id;
    }

    public ArrayList<BloodPressure> get_all()
    {
        ArrayList<BloodPressure> all_items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String cmd = "SELECT * FROM '"+TABLE_NAME+"';";
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();

        int count = cursor.getCount();

        int id;
        float sys;
        float dias;
        Date date;
        String day;

        for(int i=0 ; i<count ; i++)
        {
            id   = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
            sys  = cursor.getFloat(cursor.getColumnIndex(SYSTOLIC_COLUMN));
            dias = cursor.getFloat(cursor.getColumnIndex(DIASTOLIC_COLUMN));
            day  = cursor.getString(cursor.getColumnIndex(DAY_COLUMN));
            date = String2Date(cursor.getString(cursor.getColumnIndex(DATE_COLUMN)));

            all_items.add(new BloodPressure(id , sys , dias , date , day));

            cursor.moveToNext();
        }

        return all_items;
    }

    public int delete(int id)
    {
        SQLiteDatabase db = getReadableDatabase();

        String arg[] = {String.valueOf(id)};
        int count = db.delete(TABLE_NAME , ID_COLUMN+" = ?" , arg);

        db.close();

        return count;
    }

    public int update(BloodPressure bp)
    {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DIASTOLIC_COLUMN , bp.getDiastolic());
        cv.put(SYSTOLIC_COLUMN , bp.getSystolic());
        cv.put(DAY_COLUMN , bp.getDay());
        cv.put(DATE_COLUMN , Date2String(bp.getDate()));

        String arg[] = {String.valueOf(bp.getId())};
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
