package model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB_Calories extends SQLiteOpenHelper
{
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String AMOUNT_COLUMN = "amount";
    public static final String CALORIES_COLUMN = "calories";

    private static final String DB_NAME = "Calories.db";
    private static final String TABLE_NAME = "calories";
    private static final int DB_VERSION = 1;

    public DB_Calories(@Nullable Context context)
    {
        super(context , DB_NAME , null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String cmd = "CREATE TABLE IF NOT EXISTS '"+TABLE_NAME+"' " +
                "(" +
                    ID_COLUMN + " INTEGER PRIMARY KEY , "+
                    NAME_COLUMN + " TEXT ,"+
                    AMOUNT_COLUMN + " TEXT ,"+
                    CALORIES_COLUMN + " TEXT"+
                ")";
        db.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    //getter
    public static String getDbName()
    {
        return DB_NAME;
    }

    public static String getTableName()
    {
        return TABLE_NAME;
    }

    //db function's
    public ArrayList<Calories> get_all()
    {
        ArrayList<Calories> all_item = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String cmd = "SELECT DISTINCT * FROM '"+TABLE_NAME+"'";
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();
        int count = cursor.getCount();

        int id;
        String name;
        String amount;
        String calories;

        for(int i=0 ; i<count ; i++)
        {
            id      = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
            name    = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
            amount  = cursor.getString(cursor.getColumnIndex(AMOUNT_COLUMN));
            calories= cursor.getString(cursor.getColumnIndex(CALORIES_COLUMN));

            all_item.add(new Calories(id , name , amount , calories));

            cursor.moveToNext();
        }
        db.close();
        return all_item;
    }

    public ArrayList<Calories> search(String text)
    {
        ArrayList<Calories> items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String cmd = "SELECT DISTINCT * FROM '"+TABLE_NAME+"' WHERE "+NAME_COLUMN+" LIKE '%"+text+"%';";
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();
        int count = cursor.getCount();

        int id;
        String name;
        String amount;
        String calories;

        for(int i=0 ; i<count ; i++)
        {
            id      = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
            name    = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
            amount  = cursor.getString(cursor.getColumnIndex(AMOUNT_COLUMN));
            calories= cursor.getString(cursor.getColumnIndex(CALORIES_COLUMN));

            items.add(new Calories(id , name , amount , calories));

            cursor.moveToNext();
        }

        return items;
    }

    public Calories search(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        String cmd = "SELECT DISTINCT * FROM '"+TABLE_NAME+"' WHERE "+ID_COLUMN+" = "+id+";";
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();

        int cid         = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
        String name     = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
        String amount   = cursor.getString(cursor.getColumnIndex(AMOUNT_COLUMN));
        String calories = cursor.getString(cursor.getColumnIndex(CALORIES_COLUMN));

        Calories item = new Calories(cid , name , amount , calories);

        return item;
    }
}
