package ir.sinasoheili.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private int fragment_id_pages[] = {R.layout.home_page_layout, R.layout.dashboard_page_layout, R.layout.profile_page_layout};

    private BottomNavigationView bnv;

    public static final String PREF_DIALOG_REGISTER_PRFNAME = "REGISTER_DIALOG";
    public static final String PREF_DIALOG_REGISTER_KEY_show = "SHOW";
    public static final String PREF_DIALOG_REGISTER_KEY_NAME = "NAME";
    public static final String PREF_DIALOG_REGISTER_KEY_AGE = "AGE";
    public static final String PREF_DIALOG_REGISTER_KEY_HEIGHT = "HEIGHT";
    public static final String PREF_DIALOG_REGISTER_KEY_WEIGHT = "WEIGHT";

    private Dialog dialog_register_person;
    private Button   btn_person_register_dialog;
    private EditText et_name_person_register_dialog;
    private EditText et_age_person_register_dialog;
    private EditText et_height_person_register_dialog;
    private EditText et_weight_person_register_dialog;

    private TextInputLayout til_name;
    private TextInputLayout til_age;
    private TextInputLayout til_height;
    private TextInputLayout til_weight;

   @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show_IntroSlider();
        show_register_dialog();

        init_obj();
    }

    public void show_IntroSlider()
    {
        SharedPreferences pref = getSharedPreferences(IntroSlider.PREF_NAME , MODE_PRIVATE);
        boolean show = pref.getBoolean(IntroSlider.PREF_KEY , true);
        if(show == true)
        {
            startActivity(new Intent(MainActivity.this , IntroSlider.class));
        }
    }

    private void show_register_dialog()
    {
        SharedPreferences pref = getSharedPreferences(PREF_DIALOG_REGISTER_PRFNAME , MODE_PRIVATE);
        boolean show_pref = pref.getBoolean(PREF_DIALOG_REGISTER_KEY_show, true);
        if(show_pref == true)
        {
            dialog_register_person = new Dialog(this);
            View view = getLayoutInflater().inflate(R.layout.register_person_dialog_layout , null , false);

            btn_person_register_dialog       = view.findViewById(R.id.register_person_dialog_btn_register);
            et_name_person_register_dialog   = view.findViewById(R.id.register_person_dialog_et_name);
            et_age_person_register_dialog    = view.findViewById(R.id.register_person_dialog_et_age);
            et_height_person_register_dialog = view.findViewById(R.id.register_person_dialog_et_height);
            et_weight_person_register_dialog = view.findViewById(R.id.register_person_dialog_et_weight);

            til_name = view.findViewById(R.id.til_register_person_dialog_et_name);
            til_age = view.findViewById(R.id.til_register_person_dialog_et_age);
            til_height = view.findViewById(R.id.til_register_person_dialog_et_height);
            til_weight = view.findViewById(R.id.til_register_person_dialog_et_weight);

            btn_person_register_dialog.setOnClickListener(this);

            dialog_register_person.setContentView(view);
            dialog_register_person.setCancelable(false);
            dialog_register_person.show();
            dialog_register_person.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void init_obj()
    {
        BottomNavigationView bnv = findViewById(R.id.bottom_navigation_view);
        bnv.setOnNavigationItemSelectedListener(this);

        //simulate click in home page to show item
        View v = bnv.getRootView().findViewById(bnv.getSelectedItemId());
        v.performClick();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        if(menuItem.getItemId() == R.id.manu_bnv_home)
        {
            HomePage_Fragment fragment_page_1 = new HomePage_Fragment();
            show_fragment(fragment_page_1);
            return true;
        }
        else if(menuItem.getItemId() == R.id.manu_bnv_dashboard)
        {
            DashboardPage_Fragment fragment_page_2 = new DashboardPage_Fragment();
            show_fragment(fragment_page_2);
            return true;
        }
        else if(menuItem.getItemId() == R.id.manu_bnv_profile)
        {
            Profile_Fragment fragment_page_3 = new Profile_Fragment();
            show_fragment(fragment_page_3);
            return true;
        }
        return false;
    }

    //show fragment
    public void show_fragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container , fragment).commit();
    }

    @Override
    public void onClick(View v)
    {
        boolean valid = check_valid_person_information();
        if(valid == true)
        {
            SharedPreferences pref = getSharedPreferences(PREF_DIALOG_REGISTER_PRFNAME , MODE_PRIVATE);

            SharedPreferences.Editor edit = pref.edit();

            edit.putBoolean(PREF_DIALOG_REGISTER_KEY_show , false).commit();

            edit.putString(PREF_DIALOG_REGISTER_KEY_NAME   , et_name_person_register_dialog.getText().toString()).commit();
            edit.putString(PREF_DIALOG_REGISTER_KEY_AGE    , et_age_person_register_dialog.getText().toString()).commit();
            edit.putString(PREF_DIALOG_REGISTER_KEY_HEIGHT , et_height_person_register_dialog.getText().toString()).commit();
            edit.putString(PREF_DIALOG_REGISTER_KEY_WEIGHT , et_weight_person_register_dialog.getText().toString()).commit();

            dialog_register_person.dismiss();
            Toast.makeText(this, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean check_valid_person_information()
    {
        if(et_name_person_register_dialog.getText().toString().isEmpty() == true)
        {
            til_name.setError("لطفا این قسمت را پر کنید!");
            et_name_person_register_dialog.requestFocus();
            return false;
        }
        else if(et_age_person_register_dialog.getText().toString().isEmpty() == true)
        {
            til_age.setError("لطفا این قسمت را پر کنید!");
            et_age_person_register_dialog.requestFocus();
            return false;
        }
        else if(et_height_person_register_dialog.getText().toString().isEmpty() == true)
        {
            til_height.setError("لطفا این قسمت را پر کنید!");
            et_height_person_register_dialog.requestFocus();
            return false;
        }
        else if(et_weight_person_register_dialog.getText().toString().isEmpty() == true)
        {
            til_weight.setError("لطفا این قسمت را پر کنید!");
            et_weight_person_register_dialog.requestFocus();
            return false;
        }
        return true;
    }
}
