package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DB_MedicationSchedule extends SQLiteOpenHelper
{
    public static final String MEDICINE_NAME_COLUMN = "MEDICINE_NAME";
    public static final String MEDICINE_AMOUNT_COLUMN = "MEDICINE_AMOUNT";
    public static final String TIME_COLUMN = "TIME";
    public static final String DAY_COLUMN = "DAY";

    private static final String DB_NAME = "MedicationSchedule.db";
    private static final String TABLE_NAME = "MedicationSchedule";
    private static final int DB_VERSION = 1;

    public DB_MedicationSchedule(@Nullable Context context)
    {
        super(context , DB_NAME , null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String cmd = "CREATE TABLE IF NOT EXISTS '"+TABLE_NAME+"' " +
                "(" +
                    MEDICINE_NAME_COLUMN    + " TEXT , "+
                    MEDICINE_AMOUNT_COLUMN  + " TEXT , "+
                    TIME_COLUMN             + " TEXT , "+
                    DAY_COLUMN              + " TEXT , "+
                    "primary key ("+MEDICINE_NAME_COLUMN+","+MEDICINE_AMOUNT_COLUMN+","+TIME_COLUMN+","+DAY_COLUMN+")"+
                ")";
        db.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    //db function's
    public ArrayList<MedicationSchedule> search (Day day)
    {
        ArrayList<MedicationSchedule> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String cmd = "SELECT * FROM '"+TABLE_NAME+"' WHERE "+DAY_COLUMN+" = '"+day.toString()+"'";
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();
        int count = cursor.getCount();

        String medicine_name;
        String medicine_amount;
        String time;
        Day item_day;

        for(int i=0 ; i<count ; i++)
        {
            medicine_name = cursor.getString(cursor.getColumnIndex(MEDICINE_NAME_COLUMN));
            medicine_amount = cursor.getString(cursor.getColumnIndex(MEDICINE_AMOUNT_COLUMN));
            time = cursor.getString(cursor.getColumnIndex(TIME_COLUMN));
            item_day = Day.en_string2en_day(cursor.getString(cursor.getColumnIndex(DAY_COLUMN)));

            items.add(new MedicationSchedule(medicine_name , medicine_amount , time , item_day));

            cursor.moveToNext();
        }

        db.close();
        return items;
    }

    public ArrayList<MedicationSchedule> get_all()
    {
        ArrayList<MedicationSchedule> all_item = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String cmd = "SELECT * FROM '"+TABLE_NAME+"'";
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();
        int count = cursor.getCount();

        String medicine_name;
        String medicine_amount;
        String time;
        Day item_day;

        for(int i=0 ; i<count ; i++)
        {
            medicine_name = cursor.getString(cursor.getColumnIndex(MEDICINE_NAME_COLUMN));
            medicine_amount = cursor.getString(cursor.getColumnIndex(MEDICINE_AMOUNT_COLUMN));
            time = cursor.getString(cursor.getColumnIndex(TIME_COLUMN));
            item_day = Day.en_string2en_day(cursor.getString(cursor.getColumnIndex(DAY_COLUMN)));

            all_item.add(new MedicationSchedule(medicine_name , medicine_amount , time , item_day));

            cursor.moveToNext();
        }

        db.close();
        return all_item;
    }

    public long insert (MedicationSchedule ms)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(MEDICINE_NAME_COLUMN   , ms.getMedicine_name());
        cv.put(MEDICINE_AMOUNT_COLUMN , ms.getMedicine_amount());
        cv.put(TIME_COLUMN            , ms.getTime());
        cv.put(DAY_COLUMN             , ms.getDay().toString());

        long id = db.insert(TABLE_NAME , null , cv);
        return id;
    }

    public int update (MedicationSchedule oldms , MedicationSchedule newms)
    {
        SQLiteDatabase db = getReadableDatabase();
        String wh = MEDICINE_NAME_COLUMN    +" = '"+oldms.getMedicine_name()   +"' AND "+
                    MEDICINE_AMOUNT_COLUMN  +" = '"+oldms.getMedicine_amount() +"' AND "+
                    TIME_COLUMN             +" = '"+oldms.getTime()            +"' AND "+
                    DAY_COLUMN              +" = '"+oldms.getDay().toString()+"'";

        ContentValues cv = new ContentValues();
        cv.put(MEDICINE_NAME_COLUMN   , newms.getMedicine_name());
        cv.put(MEDICINE_AMOUNT_COLUMN , newms.getMedicine_amount());
        cv.put(TIME_COLUMN            , newms.getTime());
        cv.put(DAY_COLUMN             , newms.getDay().toString());

        int count = db.update(TABLE_NAME , cv , wh , null);
        db.close();
        return count;
    }

    public int delete(MedicationSchedule ms)
    {
        SQLiteDatabase db = getReadableDatabase();

        String wh = MEDICINE_NAME_COLUMN      +" = '"+ms.getMedicine_name()+"' AND "+
                    MEDICINE_AMOUNT_COLUMN    +" = '"+ms.getMedicine_amount()+"' AND "+
                    TIME_COLUMN               +" = '"+ms.getTime()+"' AND "+
                    DAY_COLUMN                +" = '"+ms.getDay().toString()+"'";

        int count = db.delete(TABLE_NAME , wh , null);
        db.close();
        return count;
    }
}
