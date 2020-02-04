package ir.sinasoheili.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import model.BloodGlucose;
import model.BloodPressure;
import model.Day;
import model.MedicationSchedule;
import presenter.Home_page_contract;

public class HomePage_Fragment extends Fragment implements Home_page_contract.Main_page_view, View.OnClickListener , SensorEventListener
{
    private Home_page_contract.Home_page_presenter presenter_obj;

    private String day[] = {Day.en_day2fa_day(Day.saturday) , Day.en_day2fa_day(Day.sunday) , Day.en_day2fa_day(Day.monday) , Day.en_day2fa_day(Day.tuesday) , Day.en_day2fa_day(Day.wednesday) , Day.en_day2fa_day(Day.thursday) , Day.en_day2fa_day(Day.friday)};
    private String time[] = {"قبل صبحانه" , "بعد صبحانه" , "قبل نهار" , "بعد ناهار" , "قبل شام" , "بعد شام" , "قبل خواب"};

    private View root_view;

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
    private Spinner spinner_blood_glucose_day;
    private Spinner  spinner_blood_glucose_time;
    private Button btn_blood_glucose_submit;
    private Dialog Blood_Glucose_Dialog;

    //blood Pressure register
    private TextView tv_BloodPressure_register;
    private Dialog Blood_Pressure_Dialog;
    private EditText et_blood_pressure_sys;
    private EditText et_blood_pressure_dias;
    private EditText et_blood_pressure_date;
    private Spinner  spinner_blood_pressure_day;
    private Button btn_blood_pressure_submit;

    //step Counter
    public static final String PREF_FILE_MAX_STEP_NAME = "MAX_STEP_PREF_FILE";
    public static final String PREF_KEY_MAX_STEP = "MAX_STEP";
    private CircularProgressIndicator circule_progress;
    private SensorManager smanager;
    private Sensor sensor;
    private TextView tv_stepCounter_support;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        root_view = inflater.inflate(R.layout.home_page_layout, null , false);

        init_obj();

        show_today_medication_schedule();

        return root_view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if(sensor == null)
        {
            tv_stepCounter_support.setVisibility(View.VISIBLE);
        }
        else
        {
            smanager.registerListener(this , sensor , smanager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();

        smanager.unregisterListener(this);
    }

    private void init_obj()
    {
        presenter_obj = new presenter.Home_page_presenter(this);

        //Medication Schedule
        MainView_CardView_MedicationSchedule_Title = root_view.findViewById(R.id.MainView_MedicationSchedule_Title);
        layout_MedicationSchdule_item_title = root_view.findViewById(R.id.layout_MedicationSchdule_item);
        MainView_CardView_MedicationSchedule_ListView = root_view.findViewById(R.id.MainView_MedicationSchedule_ListView);

        //blood glucose register
        tv_BloodGlucose_register = root_view.findViewById(R.id.MainView_BloodGlucose_Title);
        tv_BloodGlucose_register.setOnClickListener(this);

        //blood pressure register
        tv_BloodPressure_register = root_view.findViewById(R.id.MainView_BloodPressure_Title);
        tv_BloodPressure_register.setOnClickListener(this);

        //step counter
        smanager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = smanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        circule_progress = root_view.findViewById(R.id.progress_circular);
        circule_progress.setDirection(CircularProgressIndicator.DIRECTION_CLOCKWISE);
        tv_stepCounter_support = root_view.findViewById(R.id.tv_stepcounter_dont_support);
        circule_progress.setProgressTextAdapter(new CircularProgressIndicator.ProgressTextAdapter()
        {
            @NonNull
            @Override
            public String formatText(double currentProgress)
            {
                return ((int)currentProgress+"\nStep");
            }
        });
    }

    //contract function's
    @Override
    public void show_today_medication_schedule()
    {
        Date d = Calendar.getInstance().getTime();
        Day today = null;
        switch (d.getDay())
        {
            case 0 :
                today = Day.sunday;
                break;

            case 1 :
                today = Day.monday;
                break;

            case 2 :
                today = Day.tuesday;
                break;

            case 3 :
                today = Day.wednesday;
                break;

            case 4 :
                today = Day.thursday;
                break;

            case 5 :
                today = Day.friday;
                break;

            case 6 :
                today = Day.saturday;
                break;
        }

        s_today = Day.en_day2fa_day(today);

        //set day to card view
        MainView_CardView_MedicationSchedule_Title.append("  "+s_today);

        ArrayList<MedicationSchedule> items = presenter_obj.get_Medication_schedule(today);
        if(items.size() > 0)
        {
            //set visibility
            layout_MedicationSchdule_item_title.setVisibility(View.VISIBLE);
            MainView_CardView_MedicationSchedule_ListView.setVisibility(View.VISIBLE);

            //set list item
            MedicationSchedule_List_Adapter adapter = new MedicationSchedule_List_Adapter(root_view.getContext() , items);
            MainView_CardView_MedicationSchedule_ListView.setAdapter(adapter);
        }
    }

    //dialog register blood glucose
    @Override
    public void show_dialog_blood_glucose()
    {
        Blood_Glucose_Dialog = new Dialog(root_view.getContext());
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

        ArrayAdapter<String> day_adapter = new ArrayAdapter<String>(root_view.getContext() , android.R.layout.simple_spinner_item , day);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_glucose_day.setAdapter(day_adapter);

        ArrayAdapter<String> time_adapter = new ArrayAdapter<String>(root_view.getContext() , android.R.layout.simple_spinner_item , time);
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
                        DatePickerDialog dpd = new DatePickerDialog(root_view.getContext());
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

    public boolean check_valid_blood_glucose_et() // TODO: make validation code better logic
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

    //dialog register blood prssure
    @Override
    public void show_dialog_blood_pressure()
    {
        Blood_Pressure_Dialog = new Dialog(root_view.getContext());
        Blood_Pressure_Dialog.setContentView(getLayoutInflater().inflate(R.layout.blood_pressure_registere_dialog , null , false));
        Blood_Pressure_Dialog.show();
        Blood_Pressure_Dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT);

        View rootview = Blood_Pressure_Dialog.getWindow().getDecorView();

        et_blood_pressure_sys = rootview.findViewById(R.id.blood_pressure_register_dialog_systolic);
        et_blood_pressure_dias = rootview.findViewById(R.id.blood_pressure_register_dialog_diastolic);
        et_blood_pressure_date = rootview.findViewById(R.id.blood_pressure_register_dialog_date);
        spinner_blood_pressure_day = rootview.findViewById(R.id.blood_pressure_register_dialog_spinner_day);
        btn_blood_pressure_submit = rootview.findViewById(R.id.blood_Pressure_register_dialog_btn_submit);

        btn_blood_pressure_submit.setOnClickListener(this);

        ArrayAdapter<String> day_adapter = new ArrayAdapter<String>(root_view.getContext() , android.R.layout.simple_spinner_item , day);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_pressure_day.setAdapter(day_adapter);

        et_blood_pressure_date.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus == true)
                {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    {
                        DatePickerDialog dpd = new DatePickerDialog(root_view.getContext());
                        dpd.show();

                        dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                            {
                                et_blood_pressure_date.setText("");
                                et_blood_pressure_date.setText(year+"/"+month+"/"+dayOfMonth);
                            }
                        });
                    }
                }
            }
        });
    }

    public boolean check_valid_blood_pressure_et() // TODO: make validation code better logic
    {
        boolean f1=true , f2=true , f3=true ;

        if(et_blood_pressure_dias.getText().toString().isEmpty())
        {
            et_blood_pressure_dias.setError("لطفا این قسمت را پر کنید!");
            et_blood_pressure_dias.requestFocus();
            f1 = false;
        }
        else if(et_blood_pressure_sys.getText().toString().isEmpty())
        {
            et_blood_pressure_sys.setError("لطفا این قسمت را پر کنید!");
            et_blood_pressure_sys.requestFocus();
            f2 = false;
        }
        else if(et_blood_pressure_date.getText().toString().isEmpty())
        {
            et_blood_pressure_date.setError("لطفا این قسمت را پر کنید!");
            et_blood_pressure_date.requestFocus();
            f3 = false;
        }

        if(f1 == f2 == f3 == true)
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
        if(v.equals(tv_BloodGlucose_register))
        {
            show_dialog_blood_glucose();
        }
        else if(v.equals(btn_blood_glucose_submit))
        {
            boolean valid = check_valid_blood_glucose_et();
            if(valid == true)
            {
                float blood_glucose_value = Float.valueOf(et_blood_glucoe_value.getText().toString());
                float insuline_unit_count = Float.valueOf(et_blood_glucoe_unit_count.getText().toString());
                String insulin_name = et_blood_glucoe_insuline_name.getText().toString();
                String day = this.day[spinner_blood_glucose_day.getSelectedItemPosition()];
                String time = spinner_blood_glucose_time.getSelectedItem().toString();

                String sdate[] = et_blood_glucoe_date.getText().toString().split("/");
                Date date = new Date(Integer.valueOf(sdate[0]) , Integer.valueOf(sdate[1]) , Integer.valueOf(sdate[2]));

                boolean result = presenter_obj.register_BloodGlucose(new BloodGlucose(blood_glucose_value , insuline_unit_count , insulin_name , day , time , date));

                if(result == true)
                {
                    Toast.makeText(root_view.getContext(), "ذخیره شد", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(root_view.getContext(), "ذخیره نشد!!!", Toast.LENGTH_SHORT).show();
                }

                Blood_Glucose_Dialog.dismiss();
            }
        }
        else if(v.equals(tv_BloodPressure_register))
        {
            show_dialog_blood_pressure();
        }
        else if(v.equals(btn_blood_pressure_submit))
        {
            boolean valid = check_valid_blood_pressure_et();
            if(valid == true)
            {
                float systolic = Float.valueOf(et_blood_pressure_sys.getText().toString());
                float diastolic = Float.valueOf(et_blood_pressure_dias.getText().toString());
                String day = this.day[spinner_blood_pressure_day.getSelectedItemPosition()];

                String sdate[] = et_blood_pressure_date.getText().toString().split("/");
                Date date = new Date(Integer.valueOf(sdate[0]) , Integer.valueOf(sdate[1]) , Integer.valueOf(sdate[2]));

                boolean result = presenter_obj.register_BloodPressure(new BloodPressure(systolic , diastolic , date , day));

                if(result == true)
                {
                    Toast.makeText(root_view.getContext(), "ذخیره شد", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(root_view.getContext(), "ذخیره نشد!!!", Toast.LENGTH_SHORT).show();
                }

                Blood_Pressure_Dialog.dismiss();
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        SharedPreferences pref = getContext().getSharedPreferences(PREF_FILE_MAX_STEP_NAME, Context.MODE_PRIVATE);
        int max_step = pref.getInt(PREF_KEY_MAX_STEP, 6000);

        circule_progress.setProgress(event.values[0] , max_step);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}
