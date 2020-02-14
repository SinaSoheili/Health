package presenter;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import ir.sinasoheili.view.MainActivity;

public class Profile_Page_presenter implements Profile_Page_contract.Profile_Page_contract_presenter
{
    private Context context;
    private Profile_Page_contract.Profile_Page_contract_view view;

    public Profile_Page_presenter(Context context , Fragment fragment)
    {
        this.context = context;
        this.view = (Profile_Page_contract.Profile_Page_contract_view) fragment;
    }

    @Override
    public void get_user_name()
    {
        SharedPreferences pref = context.getSharedPreferences(MainActivity.PREF_DIALOG_REGISTER_PRFNAME , Context.MODE_PRIVATE);
        String name = pref.getString(MainActivity.PREF_DIALOG_REGISTER_KEY_NAME , null);
        view.show_name(name);
    }

    @Override
    public void get_user_age()
    {
        SharedPreferences pref = context.getSharedPreferences(MainActivity.PREF_DIALOG_REGISTER_PRFNAME , Context.MODE_PRIVATE);
        String age = pref.getString(MainActivity.PREF_DIALOG_REGISTER_KEY_AGE , "0");
        view.show_age(Float.parseFloat(age));
    }

    @Override
    public void get_user_height()
    {
        SharedPreferences pref = context.getSharedPreferences(MainActivity.PREF_DIALOG_REGISTER_PRFNAME , Context.MODE_PRIVATE);
        String height = pref.getString(MainActivity.PREF_DIALOG_REGISTER_KEY_HEIGHT , "0");
        view.show_height(Float.parseFloat(height));
    }

    @Override
    public void get_user_weight()
    {
        SharedPreferences pref = context.getSharedPreferences(MainActivity.PREF_DIALOG_REGISTER_PRFNAME , Context.MODE_PRIVATE);
        String weight = pref.getString(MainActivity.PREF_DIALOG_REGISTER_KEY_WEIGHT , "0");
        view.show_weight(Float.parseFloat(weight));
    }
}
