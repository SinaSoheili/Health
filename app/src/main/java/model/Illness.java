package model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Illness implements Parcelable
{
    private int id;
    private String name_fa;
    private String content;
    private String name_en;

    //CONSTRUCTOR
    public Illness(int id, String name_fa, String content, String name_en)
    {
        this.id = id;
        this.name_fa = name_fa;
        this.content = content;
        this.name_en = name_en;
    }

    protected Illness(Parcel in) {
        id = in.readInt();
        name_fa = in.readString();
        content = in.readString();
        name_en = in.readString();
    }

    public static final Creator<Illness> CREATOR = new Creator<Illness>() {
        @Override
        public Illness createFromParcel(Parcel in) {
            return new Illness(in);
        }

        @Override
        public Illness[] newArray(int size) {
            return new Illness[size];
        }
    };

    //GETTER
    public int getId()
    {
        return id;
    }

    public String getName_fa()
    {
        return name_fa;
    }

    public String getContent()
    {
        return content;
    }

    public String getName_en()
    {
        return name_en;
    }

    //SETTER
    public void setId(int id)
    {
        this.id = id;
    }

    public void setName_fa(String name_fa)
    {
        this.name_fa = name_fa;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setName_en(String name_en)
    {
        this.name_en = name_en;
    }

    //TO STRING
    @Override
    public String toString()
    {
        return this.name_fa;
    }

    //implement parcelable
    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name_fa);
        dest.writeString(content);
        dest.writeString(name_en);
    }
}
