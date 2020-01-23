package model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DB_Illness extends SQLiteOpenHelper
{
    public static final String ID_COLUMN = "ID";
    public static final String NAME_FA_COLUMN = "name_fa";
    public static final String CONTENT_COLUMN = "content";
    public static final String NAME_EN_COLUMN = "name_en";

    private static final String DB_NAME = "Illness.db";
    private static final String TABLE_NAME = "bimariha";
    private static final int DB_VERSION = 1;

    public DB_Illness(@Nullable Context context)
    {
        super(context , DB_NAME , null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String cmd = "CREATE TABLE IF NOT EXISTS '"+TABLE_NAME+"'" +
                "("+
                    ID_COLUMN      +" TEXT ,"+
                    NAME_FA_COLUMN +" TEXT ,"+
                    CONTENT_COLUMN +" TEXT ,"+
                    NAME_EN_COLUMN +" TEXT "+
                ");";
        db.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    //GETTER
    public static String getDbName()
    {
        return DB_NAME;
    }

    public static String getTableName()
    {
        return TABLE_NAME;
    }

    //function query
    public ArrayList<Illness> get_all()
    {
        ArrayList<Illness> all_item = new ArrayList<>();

        String cmd = "SELECT * FROM '"+TABLE_NAME+"'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();

        int id;
        String name_fa;
        String name_en;
        String content;

        for(int i=0 ; i<cursor.getCount() ; i++)
        {
            id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
            name_fa = cursor.getString(cursor.getColumnIndex(NAME_FA_COLUMN));
            name_en = cursor.getString(cursor.getColumnIndex(NAME_EN_COLUMN));
            content = cursor.getString(cursor.getColumnIndex(CONTENT_COLUMN));

            all_item.add(new Illness(id , name_fa , content , name_en));

            cursor.moveToNext();
        }

        return all_item;
    }

    public ArrayList<Illness> search(String text)
    {
        //TODO:persian search don't work
        ArrayList<Illness> items = new ArrayList<>();

        String cmd = "SELECT * FROM '"+TABLE_NAME+"' WHERE "+NAME_EN_COLUMN+" LIKE '%"+text+"%' OR "+NAME_FA_COLUMN+" LIKE '%"+text+"%'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();

        int id;
        String name_fa;
        String name_en;
        String content;
        Log.i("tag" , "count :: "+cursor.getCount());
        for(int i=0 ; i<cursor.getCount() ; i++)
        {
            id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
            name_fa = cursor.getString(cursor.getColumnIndex(NAME_FA_COLUMN));
            name_en = cursor.getString(cursor.getColumnIndex(NAME_EN_COLUMN));
            content = cursor.getString(cursor.getColumnIndex(CONTENT_COLUMN));

            items.add(new Illness(id , name_fa , content , name_en));

            cursor.moveToNext();
        }

        return items;
    }
}
