package model;

import androidx.annotation.NonNull;

public enum Day
{
    saturday , sunday , monday , tuesday , wednesday , thursday , friday;

    public static String en_day2fa_day(Day d)
    {
        switch(d)
        {
            case saturday:
                return "شنبه";

            case sunday:
                return "یکشنبه";

            case monday:
                return "دوشنبه";

            case tuesday:
                return "سه شنبه";

            case wednesday:
                return "چهارشنبه";

            case thursday:
                return "پنجشنبه";

            case friday:
                return "جمعه";
        }

        return null;
    }

    public static Day fa_day2en_day(String fa_day)
    {
        switch(fa_day)
        {
            case "شنبه":
                return saturday;

            case "یکشنبه":
                return sunday;

            case "دوشنبه":
                return monday;

            case "سه شنبه":
                return thursday;

            case "چهارشنبه":
                return wednesday;

            case "پنجشنبه":
                return tuesday;

            case "جمعه":
                return friday;
        }

        return null;
    }

    public static Day en_string2en_day(String day)
    {
        switch (day)
        {
            case "saturday":
                return saturday;

            case "sunday":
                return sunday;

            case "monday":
                return monday;

            case "tuesday":
                return tuesday;

            case "wednesday":
                return wednesday;

            case "thursday":
                return thursday;

            case "friday":
                return friday;
        }

        return null;
    }

}
