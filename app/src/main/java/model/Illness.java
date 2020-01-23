package model;

import androidx.annotation.NonNull;

public class Illness
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

    @NonNull
    @Override
    public String toString()
    {
        return this.name_fa;
    }
}
