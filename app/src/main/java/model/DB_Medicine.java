package model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB_Medicine extends SQLiteOpenHelper
{
    public static final String NAME_TEJARY_COLUMN = "name_tejary";
    public static final String GOROH_DAROEI_COLUMN = "goroh_daroei";
    public static final String GOROH_DARMANI_COLUMN = "goroh_darmani";
    public static final String NAME_FARSI_COLUMN = "name_farsi";
    public static final String SHEKL_DAROEI_COLUMN = "shekl_daroei";
    public static final String MASRAF_HAMElEGI_COLUMN = "masraf_hamelegi";
    public static final String MAVARED_MASRAF_COLUMN = "mavared_masraf";
    public static final String MIZAN_MASRAF_COLUMN = "mizan_masraf";
    public static final String MAVARED_MANE_COLUMN = "mavared_mane";
    public static final String AVAREZ_JANEBI_COLUMN = "avarez_janebi";
    public static final String TAVAJOHAT_COLUMN = "tavajohat";
    public static final String AMOZESH_COLUMN = "amozesh";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String SHARAYET_NEGAH_COLUMN = "sharayet_negah";
    public static final String NOKTE_COLUMN = "nokte";


    private static final String DB_NAME = "Medicines.db";
    private static final String TABLE_NAME = "darooha";
    private static final int DB_VERSION = 1;

    public DB_Medicine(@Nullable Context context)
    {
        super(context , DB_NAME , null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String cmd = "CREATE TABLE IF NOT EXISTS '"+TABLE_NAME+"' " +
                "("
                    +NAME_TEJARY_COLUMN     +" TEXT ,"
                    +GOROH_DAROEI_COLUMN    +" TEXT ,"
                    +GOROH_DARMANI_COLUMN   +" TEXT ,"
                    +NAME_FARSI_COLUMN      +" TEXT ,"
                    +SHEKL_DAROEI_COLUMN    +" TEXT ,"
                    +MASRAF_HAMElEGI_COLUMN +" TEXT ,"
                    +MAVARED_MASRAF_COLUMN  +" TEXT ,"
                    +MIZAN_MASRAF_COLUMN    +" TEXT ,"
                    +MAVARED_MANE_COLUMN    +" TEXT ,"
                    +AVAREZ_JANEBI_COLUMN   +" TEXT ,"
                    +TAVAJOHAT_COLUMN       +" TEXT ,"
                    +AMOZESH_COLUMN         +" TEXT ,"
                    +DESCRIPTION_COLUMN     +" TEXT ,"
                    +SHARAYET_NEGAH_COLUMN  +" TEXT ,"
                    +NOKTE_COLUMN           +" TEXT"+
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

    //functions query
    public ArrayList<Medicine> get_all()
    {
        ArrayList<Medicine> all_item = new ArrayList<>();

        String cmd = "SELECT DISTINCT * FROM '"+TABLE_NAME+"'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();

        String name_tejary;
        String goroh_daroei;
        String goroh_darmani;
        String name_farsi;
        String shekl_daroei;
        String masraf_hamelegi;
        String mavared_masraf;
        String mizan_masraf;
        String mavared_mane;
        String avarez_janebi;
        String tavajohat;
        String amozesh;
        String description;
        String sharyet_negah;
        String nokte;

        for(int i=0 ; i<cursor.getCount() ; i++)
        {
            name_tejary     = cursor.getString(cursor.getColumnIndex(NAME_TEJARY_COLUMN));
            goroh_daroei    = cursor.getString(cursor.getColumnIndex(GOROH_DAROEI_COLUMN));
            goroh_darmani   = cursor.getString(cursor.getColumnIndex(GOROH_DARMANI_COLUMN));
            name_farsi      = cursor.getString(cursor.getColumnIndex(NAME_FARSI_COLUMN));
            shekl_daroei    = cursor.getString(cursor.getColumnIndex(SHEKL_DAROEI_COLUMN));
            masraf_hamelegi = cursor.getString(cursor.getColumnIndex(MASRAF_HAMElEGI_COLUMN));
            mavared_masraf  = cursor.getString(cursor.getColumnIndex(MAVARED_MASRAF_COLUMN));
            mizan_masraf    = cursor.getString(cursor.getColumnIndex(MIZAN_MASRAF_COLUMN));
            mavared_mane    = cursor.getString(cursor.getColumnIndex(MAVARED_MANE_COLUMN));
            avarez_janebi   = cursor.getString(cursor.getColumnIndex(AVAREZ_JANEBI_COLUMN));
            tavajohat       = cursor.getString(cursor.getColumnIndex(TAVAJOHAT_COLUMN));
            amozesh         = cursor.getString(cursor.getColumnIndex(AMOZESH_COLUMN));
            description     = cursor.getString(cursor.getColumnIndex(DESCRIPTION_COLUMN));
            sharyet_negah   = cursor.getString(cursor.getColumnIndex(SHARAYET_NEGAH_COLUMN));
            nokte           = cursor.getString(cursor.getColumnIndex(NOKTE_COLUMN));

            all_item.add(new Medicine( name_tejary,  goroh_daroei,  goroh_darmani,  name_farsi,  shekl_daroei,  masraf_hamelegi,  mavared_masraf,  mizan_masraf,  mavared_mane,  avarez_janebi,  tavajohat,  amozesh,  description,  sharyet_negah, nokte));

            cursor.moveToNext();
        }

        return all_item;
    }

    public ArrayList<Medicine> search(String text)
    {
        ArrayList<Medicine> items = new ArrayList<>();

        String cmd = "SELECT DISTINCT * FROM '"+TABLE_NAME+"' WHERE "+NAME_TEJARY_COLUMN+" LIKE '%"+text+"%' OR "+NAME_FARSI_COLUMN+" LIKE '%"+text+"%'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(cmd , null);
        cursor.moveToFirst();

        String name_tejary;
        String goroh_daroei;
        String goroh_darmani;
        String name_farsi;
        String shekl_daroei;
        String masraf_hamelegi;
        String mavared_masraf;
        String mizan_masraf;
        String mavared_mane;
        String avarez_janebi;
        String tavajohat;
        String amozesh;
        String description;
        String sharyet_negah;
        String nokte;

        for(int i=0 ; i<cursor.getCount() ; i++)
        {
            name_tejary     = cursor.getString(cursor.getColumnIndex(NAME_TEJARY_COLUMN));
            goroh_daroei    = cursor.getString(cursor.getColumnIndex(GOROH_DAROEI_COLUMN));
            goroh_darmani   = cursor.getString(cursor.getColumnIndex(GOROH_DARMANI_COLUMN));
            name_farsi      = cursor.getString(cursor.getColumnIndex(NAME_FARSI_COLUMN));
            shekl_daroei    = cursor.getString(cursor.getColumnIndex(SHEKL_DAROEI_COLUMN));
            masraf_hamelegi = cursor.getString(cursor.getColumnIndex(MASRAF_HAMElEGI_COLUMN));
            mavared_masraf  = cursor.getString(cursor.getColumnIndex(MAVARED_MASRAF_COLUMN));
            mizan_masraf    = cursor.getString(cursor.getColumnIndex(MIZAN_MASRAF_COLUMN));
            mavared_mane    = cursor.getString(cursor.getColumnIndex(MAVARED_MANE_COLUMN));
            avarez_janebi   = cursor.getString(cursor.getColumnIndex(AVAREZ_JANEBI_COLUMN));
            tavajohat       = cursor.getString(cursor.getColumnIndex(TAVAJOHAT_COLUMN));
            amozesh         = cursor.getString(cursor.getColumnIndex(AMOZESH_COLUMN));
            description     = cursor.getString(cursor.getColumnIndex(DESCRIPTION_COLUMN));
            sharyet_negah   = cursor.getString(cursor.getColumnIndex(SHARAYET_NEGAH_COLUMN));
            nokte           = cursor.getString(cursor.getColumnIndex(NOKTE_COLUMN));

            items.add(new Medicine( name_tejary,  goroh_daroei,  goroh_darmani,  name_farsi,  shekl_daroei,  masraf_hamelegi,  mavared_masraf,  mizan_masraf,  mavared_mane,  avarez_janebi,  tavajohat,  amozesh,  description,  sharyet_negah, nokte));

            cursor.moveToNext();
        }

        return items;
    }
}
