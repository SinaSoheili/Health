package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.BloodGlucose;
import model.DB_MedicationSchedule;
import model.MedicationSchedule;
import presenter.Main_page_contract;
import presenter.Main_page_presenter;

public class MainActivity extends AppCompatActivity implements Main_page_contract.Main_page_view, View.OnClickListener
{
    private Main_page_contract.Main_page_presenter presenter_obj;

    String day[] = {"شنبه" , "یکشنبه" , "دوشنبه" , "سه شنبه" , "چهار شنبه" , "پنجشنبه" , "جمعه"};
    String time[] = {"قبل صبحانه" , "بعد صبحانه" , "قبل نهار" , "بعد ناهار" , "قبل شام" , "بعد شام" , "قبل خواب"};

    //medication schdule
    private TextView MainView_CardView_MedicationSchedule_Title;
    private ListView MainView_CardView_MedicationSchedule_ListView;
    private View layout_MedicationSchdule_item_title ;
    private String s_today = "";

    //blood glucose register
    private TextView tv_BloodGlucose_register;
    private EditText et_blood_glucoe_value;
    private EditText et_blood_glucoe_date;
    private EditText et_blood_glucoe_insuline_name;
    private EditText et_blood_glucoe_unit_count;
    private Spinner  spinner_blood_glucose_day;
    private Spinner  spinner_blood_glucose_time;
    private Button   btn_blood_glucose_submit;
    private Dialog Blood_Glucose_Dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_obj();

        show_today_medication_schedule();
    }

    private void init_obj()
    {
        presenter_obj = new Main_page_presenter(this);

        //Medication Schedule
        MainView_CardView_MedicationSchedule_Title    = findViewById(R.id.MainView_MedicationSchedule_Title);
        layout_MedicationSchdule_item_title = findViewById(R.id.layout_MedicationSchdule_item);
        MainView_CardView_MedicationSchedule_ListView = findViewById(R.id.MainView_MedicationSchedule_ListView);

        //blood glucose register
        tv_BloodGlucose_register = findViewById(R.id.MainView_BloodGlucose_Title);
        tv_BloodGlucose_register.setOnClickListener(this);
    }

    //contract function's
    @Override
    public void show_today_medication_schedule()
    {
        Date d = Calendar.getInstance().getTime();
        MedicationSchedule.Day today = null;
        switch (d.getDay())
        {
            case 0 :
                today = MedicationSchedule.Day.sunday;
                s_today = "یکشنبه";
                break;

            case 1 :
                today = MedicationSchedule.Day.monday;
                s_today = "دوشنبه";
                break;

            case 2 :
                today = MedicationSchedule.Day.tuesday;
                s_today = "سه شنبه";
                break;

            case 3 :
                today = MedicationSchedule.Day.wednesday;
                s_today = "چهار شنبه";
                break;

            case 4 :
                today = MedicationSchedule.Day.thursday;
                s_today = "پنج شنبه";
                break;

            case 5 :
                today = MedicationSchedule.Day.friday;
                s_today = "جمعه";
                break;

            case 6 :
                today = MedicationSchedule.Day.saturday;
                s_today = "شنبه";
                break;
        }

        //set day to card view
        MainView_CardView_MedicationSchedule_Title.append("  "+s_today);

        ArrayList<MedicationSchedule> items = presenter_obj.get_Medication_schedule(today);
        if(items.size() > 0)
        {
            //set visibility
            layout_MedicationSchdule_item_title.setVisibility(View.VISIBLE);
            MainView_CardView_MedicationSchedule_ListView.setVisibility(View.VISIBLE);

            //set list item
            MedicationSchedule_List_Adapter adapter = new MedicationSchedule_List_Adapter(this , items);
            MainView_CardView_MedicationSchedule_ListView.setAdapter(adapter);
        }
    }

    //dialog register blood glucose
    @Override
    public void show_dialog_blood_glucose()
    {
        Blood_Glucose_Dialog = new Dialog(this);
        Blood_Glucose_Dialog.setContentView(getLayoutInflater().inflate(R.layout.blood_glucose_register_dialog , null , false));
        Blood_Glucose_Dialog.show();
        Blood_Glucose_Dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT);

        View rootview = Blood_Glucose_Dialog.getWindow().getDecorView();

        et_blood_glucoe_value           = rootview.findViewById(R.id.blood_glucose_register_dialog_blood_glucose_value);
        et_blood_glucoe_date            = rootview.findViewById(R.id.blood_glucose_register_dialog_date);
        et_blood_glucoe_insuline_name   = rootview.findViewById(R.id.blood_glucose_register_dialog_blood_glucose_insuline_name);
        et_blood_glucoe_unit_count      = rootview.findViewById(R.id.blood_glucose_register_dialog_insuline_unit);
        spinner_blood_glucose_day       = rootview.findViewById(R.id.blood_glucose_register_dialog_spinner_day);
        spinner_blood_glucose_time      = rootview.findViewById(R.id.blood_glucose_register_dialog_spinner_time);
        btn_blood_glucose_submit        = rootview.findViewById(R.id.blood_glucose_register_dialog_btn_submit);

        btn_blood_glucose_submit.setOnClickListener(this);

        ArrayAdapter<String> day_adapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item , day);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_glucose_day.setAdapter(day_adapter);

        ArrayAdapter<String> time_adapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item , time);
        time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_glucose_time.setAdapter(time_adapter);

        et_blood_glucoe_date.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus == true)
                {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    {
                        DatePickerDialog dpd = new DatePickerDialog(MainActivity.this);
                        dpd.show();

                        dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                            {
                                et_blood_glucoe_date.setText("");
                                et_blood_glucoe_date.setText(year+"/"+month+"/"+dayOfMonth);
                            }
                        });
                    }
                }
            }
        });
    }

    public boolean check_valid_blood_glucose_et()
    {
        boolean f1=true , f2=true , f3=true , f4=true;

        if(et_blood_glucoe_value.getText().toString().isEmpty())
        {
            et_blood_glucoe_value.setError("لطفا این قسمت را پر کنید!");
            et_blood_glucoe_value.requestFocus();
            f1 = false;
        }
        else if(et_blood_glucoe_date.getText().toString().isEmpty())
        {
            et_blood_glucoe_date.setError("لطفا این قسمت را پر کنید!");
            et_blood_glucoe_date.requestFocus();
            f2 = false;
        }
        else if(et_blood_glucoe_insuline_name.getText().toString().isEmpty())
        {
            et_blood_glucoe_insuline_name.setError("لطفا این قسمت را پر کنید!");
            et_blood_glucoe_insuline_name.requestFocus();
            f3 = false;
        }
        else if(et_blood_glucoe_unit_count.getText().toString().isEmpty())
        {
            et_blood_glucoe_unit_count.setError("لطفا این قسمت را پر کنید!");
            et_blood_glucoe_unit_count.requestFocus();
            f4 = false;
        }

        if(f1 == f2 == f3 == f4 == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //on click
    @Override
    public void onClick(View v)
    {
        if(v.getId() == tv_BloodGlucose_register.getId())
        {
            show_dialog_blood_glucose();
        }
        else if(v.getId() == btn_blood_glucose_submit.getId())
        {
            boolean valid = check_valid_blood_glucose_et();
            if(valid == true)
            {
                float blood_glucose_value = Float.valueOf(et_blood_glucoe_value.getText().toString());
                float insuline_unit_count = Float.valueOf(et_blood_glucoe_unit_count.getText().toString());
                String insulin_name = et_blood_glucoe_insuline_name.getText().toString();
                String day = spinner_blood_glucose_day.getSelectedItem().toString();
                String time = spinner_blood_glucose_time.getSelectedItem().toString();

                String sdate[] = et_blood_glucoe_date.getText().toString().split("/");
                Date date = new Date(Integer.valueOf(sdate[0]) , Integer.valueOf(sdate[1]) , Integer.valueOf(sdate[2]));

                boolean result = presenter_obj.register_BloodGlucose(new BloodGlucose(blood_glucose_value , insuline_unit_count , insulin_name , day , time , date));

                if(result == true)
                {
                    Toast.makeText(this, "ذخیره شد", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "ذخیره نشد!!!", Toast.LENGTH_SHORT).show();
                }

                Blood_Glucose_Dialog.dismiss();
            }
        }
    }
}
