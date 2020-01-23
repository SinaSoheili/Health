package model;

import androidx.annotation.NonNull;

public class Medicine
{
    private String name_tejary;
    private String goroh_daroei;
    private String goroh_darmani;
    private String name_farsi;
    private String shekl_daroei;
    private String masraf_hamelegi;
    private String mavared_masraf;
    private String mizan_masraf;
    private String mavared_mane;
    private String avarez_janebi;
    private String tavajohat;
    private String amozesh;
    private String description;
    private String sharyet_negah;
    private String nokte;

    //CONSTRUCTOR
    public Medicine(String name_tejary, String goroh_daroei, String goroh_darmani, String name_farsi, String shekl_daroei, String masraf_hamelegi, String mavared_masraf, String mizan_masraf, String mavared_mane, String avarez_janebi, String tavajohat, String amozesh, String description, String sharyet_negah, String nokte)
    {
        this.name_tejary = name_tejary;
        this.goroh_daroei = goroh_daroei;
        this.goroh_darmani = goroh_darmani;
        this.name_farsi = name_farsi;
        this.shekl_daroei = shekl_daroei;
        this.masraf_hamelegi = masraf_hamelegi;
        this.mavared_masraf = mavared_masraf;
        this.mizan_masraf = mizan_masraf;
        this.mavared_mane = mavared_mane;
        this.avarez_janebi = avarez_janebi;
        this.tavajohat = tavajohat;
        this.amozesh = amozesh;
        this.description = description;
        this.sharyet_negah = sharyet_negah;
        this.nokte = nokte;
    }

    //GETTER
    public String getName_tejary()
    {
        return name_tejary;
    }

    public String getGoroh_daroei()
    {
        return goroh_daroei;
    }

    public String getGoroh_darmani()
    {
        return goroh_darmani;
    }

    public String getName_farsi()
    {
        return name_farsi;
    }

    public String getShekl_daroei()
    {
        return shekl_daroei;
    }

    public String getMasraf_hamelegi()
    {
        return masraf_hamelegi;
    }

    public String getMavared_masraf()
    {
        return mavared_masraf;
    }

    public String getMizan_masraf()
    {
        return mizan_masraf;
    }

    public String getMavared_mane()
    {
        return mavared_mane;
    }

    public String getAvarez_janebi()
    {
        return avarez_janebi;
    }

    public String getTavajohat()
    {
        return tavajohat;
    }

    public String getAmozesh()
    {
        return amozesh;
    }

    public String getDescription()
    {
        return description;
    }

    public String getSharyet_negah()
    {
        return sharyet_negah;
    }

    public String getNokte()
    {
        return nokte;
    }

    //SETTER
    public void setName_tejary(String name_tejary)
    {
        this.name_tejary = name_tejary;
    }

    public void setGoroh_daroei(String goroh_daroei)
    {
        this.goroh_daroei = goroh_daroei;
    }

    public void setGoroh_darmani(String goroh_darmani)
    {
        this.goroh_darmani = goroh_darmani;
    }

    public void setName_farsi(String name_farsi)
    {
        this.name_farsi = name_farsi;
    }

    public void setShekl_daroei(String shekl_daroei)
    {
        this.shekl_daroei = shekl_daroei;
    }

    public void setMasraf_hamelegi(String masraf_hamelegi)
    {
        this.masraf_hamelegi = masraf_hamelegi;
    }

    public void setMavared_masraf(String mavared_masraf)
    {
        this.mavared_masraf = mavared_masraf;
    }

    public void setMizan_masraf(String mizan_masraf)
    {
        this.mizan_masraf = mizan_masraf;
    }

    public void setMavared_mane(String mavared_mane)
    {
        this.mavared_mane = mavared_mane;
    }

    public void setAvarez_janebi(String avarez_janebi)
    {
        this.avarez_janebi = avarez_janebi;
    }

    public void setTavajohat(String tavajohat)
    {
        this.tavajohat = tavajohat;
    }

    public void setAmozesh(String amozesh)
    {
        this.amozesh = amozesh;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setSharyet_negah(String sharyet_negah)
    {
        this.sharyet_negah = sharyet_negah;
    }

    public void setNokte(String nokte)
    {
        this.nokte = nokte;
    }

    //TO STRING
    @NonNull
    @Override
    public String toString()
    {
        return this.name_farsi;
    }
}
