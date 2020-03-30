package ir.sinasoheili.view;

import android.animation.ValueAnimator;
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
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
    private ImageView medication_schedule_arrow;
    private TextView MainView_CardView_MedicationSchedule_Title;
    private LinearLayout MainView_CardView_MedicationSchedule_item_continer;
    private View layout_MedicationSchdule_item_title ;
    private String s_today = "";
    private Day today = null;
    private View view_of_list_item;
    private ArrayList<MedicationSchedule> items_medications_schedule;

    //blood glucose register
    private CardView cv_BloodGlucose_register;
    private EditText et_blood_glucoe_value;
    private EditText et_blood_glucoe_date;
    private EditText et_blood_glucoe_insuline_name;
    private EditText et_blood_glucoe_unit_count;
    private Spinner spinner_blood_glucose_day;
    private Spinner  spinner_blood_glucose_time;
    private Button btn_blood_glucose_submit;
    private Dialog Blood_Glucose_Dialog;

    //blood Pressure register
    private CardView cv_BloodPressure_register;
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

    //water counter
    private LinearLayout water_continer;
    private ImageView water_iv_increment ;
    private ImageView water_iv_reset ;
    private ImageView water_iv_decrement ;
    private ImageView water_iv_arrow ;
    private TextView  water_tv_amount;

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
        MainView_CardView_MedicationSchedule_Title = root_view.findViewById(R.id.HomePageLayout_MedicationSchedule_Title);
        layout_MedicationSchdule_item_title = root_view.findViewById(R.id.HomePageLayout_layout_MedicationSchdule_item);
        MainView_CardView_MedicationSchedule_item_continer = root_view.findViewById(R.id.HomePageLayout_MedicationSchedule_item_continer);
        medication_schedule_arrow = root_view.findViewById(R.id.HomePageLayout_MedicationSchedule_arrow);

        //blood glucose register
        cv_BloodGlucose_register = root_view.findViewById(R.id.HomePageLayout_CardView_BloodGlucose);
        cv_BloodGlucose_register.setOnClickListener(this);

        //blood pressure register
        cv_BloodPressure_register = root_view.findViewById(R.id.HomePageLayout_CardView_BloodPressure);
        cv_BloodPressure_register.setOnClickListener(this);

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

        //water counter
        water_continer      = root_view.findViewById(R.id.HomePageLayout_Water_continer);
        water_iv_increment  = root_view.findViewById(R.id.HomePageLayout_iv_increment);
        water_iv_reset      = root_view.findViewById(R.id.HomePageLayout_iv_reset);
        water_iv_decrement  = root_view.findViewById(R.id.HomePageLayout_iv_decrement);
        water_tv_amount     = root_view.findViewById(R.id.HomePageLayout_Water_tv_amount);
        water_iv_arrow      = root_view.findViewById(R.id.HomePageLayout_Water_arrow);
        water_iv_arrow.setOnClickListener(this);
        water_iv_increment.setOnClickListener(this);
        water_iv_reset.setOnClickListener(this);
        water_iv_decrement.setOnClickListener(this);
    }

    //contract function's
    @Override
    public void show_today_medication_schedule()
    {
        Date d = Calendar.getInstance().getTime();
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

        medication_schedule_arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                items_medications_schedule = presenter_obj.get_Medication_schedule(today);

                int height_of_each_item = 100;  //TODO : get real height of each item
                int each_time_time_animate = 500;

                if(items_medications_schedule.size() <= 0)
                {
                    Toast t = Toast.makeText(getContext(), "هنوز موردی ثبت نشده است!!", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER , 0 , 0);
                    t.show();
                    return;
                }

                if(medication_schedule_arrow.getTag().toString().equals("up")) // list show to user
                {
                    medication_schedule_arrow.setTag("down");
                    medication_schedule_arrow.animate().rotation(180).setDuration(250).start();

                    int item_count = items_medications_schedule.size();

                    int title_animate_delay = 150;
                    int title_animate_duration = 700;

                    int items_animate_delay = title_animate_delay + 100;
                    int items_animate_duration = each_time_time_animate*item_count;

                    int show_items_delay = items_animate_delay + 400;

                    //set visibility title
                    layout_MedicationSchdule_item_title.setAlpha(0);
                    layout_MedicationSchdule_item_title.setVisibility(View.VISIBLE);
                    layout_MedicationSchdule_item_title.animate().alpha(1).setStartDelay(title_animate_delay).setDuration(title_animate_duration).start();

                    //animated
                    MainView_CardView_MedicationSchedule_item_continer.setAlpha(1);
                    MainView_CardView_MedicationSchedule_item_continer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , 0));
                    ValueAnimator va = ValueAnimator.ofInt(0 , (item_count * height_of_each_item));
                    va.setStartDelay(items_animate_delay);
                    va.setDuration(items_animate_duration);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                    {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation)
                        {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , (int)animation.getAnimatedValue());
                            MainView_CardView_MedicationSchedule_item_continer.setLayoutParams(params);
                        }
                    });
                    va.start();

                    //show item's
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            MedicationSchedule_List_Adapter adapter = new MedicationSchedule_List_Adapter(getContext() , items_medications_schedule);
                            for(int i=0 ; i<adapter.getCount() ; i++)
                            {
                                view_of_list_item = adapter.getView(i , null , null);

                                view_of_list_item.setAlpha(0);
                                view_of_list_item.animate().alpha(1).setStartDelay(i*30).setDuration(900).start();

                                MainView_CardView_MedicationSchedule_item_continer.addView(view_of_list_item);
                            }
                        }
                    } , show_items_delay);
                }
                else // list hide to user
                {
                    int count = items_medications_schedule.size();

                    int continer_delay = 80;
                    int container_duration = count*70;

                    int hide_layout_dalay = container_duration - 30;
                    int hide_layout_duration = (each_time_time_animate/2)*count;

                    int title_dalay = container_duration/5;
                    int title_duration = (hide_layout_duration/4)*3;

                    int delay_visibility_title = title_dalay + title_duration + 100;

                    medication_schedule_arrow.setTag("up");

                    medication_schedule_arrow.animate().rotation(360).setDuration(250).start();

                    MainView_CardView_MedicationSchedule_item_continer.setAlpha(1);
                    MainView_CardView_MedicationSchedule_item_continer.animate().alpha(0).setStartDelay(continer_delay).setDuration(container_duration).start();

                    layout_MedicationSchdule_item_title.setAlpha(1);
                    layout_MedicationSchdule_item_title.animate().alpha(0).setStartDelay(title_dalay).setDuration(title_duration).start();

                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            MainView_CardView_MedicationSchedule_item_continer.removeAllViews();
                            layout_MedicationSchdule_item_title.setVisibility(View.GONE);
                        }
                    } , delay_visibility_title);

                    ValueAnimator va = ValueAnimator.ofInt((height_of_each_item * items_medications_schedule.size()) , 0);
                    va.setStartDelay(hide_layout_dalay);
                    va.setDuration(hide_layout_duration);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                    {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation)
                        {
                            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , (int)animation.getAnimatedValue());
                            MainView_CardView_MedicationSchedule_item_continer.setLayoutParams(param);
                        }
                    });
                    va.start();
                }
            }
        });
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

    public boolean check_valid_blood_glucose_et()
    {
        if(et_blood_glucoe_value.getText().toString().isEmpty())
        {
            et_blood_glucoe_value.setError("لطفا این قسمت را پر کنید!");
            et_blood_glucoe_value.requestFocus();
            return false;
        }
        else if(et_blood_glucoe_date.getText().toString().isEmpty())
        {
            et_blood_glucoe_date.setError("لطفا این قسمت را پر کنید!");
            et_blood_glucoe_date.requestFocus();
            return false;
        }
        else if(et_blood_glucoe_insuline_name.getText().toString().isEmpty())
        {
            et_blood_glucoe_insuline_name.setError("لطفا این قسمت را پر کنید!");
            et_blood_glucoe_insuline_name.requestFocus();
            return false;
        }
        else if(et_blood_glucoe_unit_count.getText().toString().isEmpty())
        {
            et_blood_glucoe_unit_count.setError("لطفا این قسمت را پر کنید!");
            et_blood_glucoe_unit_count.requestFocus();
            return false;
        }

        return true;
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

    public boolean check_valid_blood_pressure_et()
    {
        if(et_blood_pressure_dias.getText().toString().isEmpty())
        {
            et_blood_pressure_dias.setError("لطفا این قسمت را پر کنید!");
            et_blood_pressure_dias.requestFocus();
            return false;
        }
        else if(et_blood_pressure_sys.getText().toString().isEmpty())
        {
            et_blood_pressure_sys.setError("لطفا این قسمت را پر کنید!");
            et_blood_pressure_sys.requestFocus();
            return false;
        }
        else if(et_blood_pressure_date.getText().toString().isEmpty())
        {
            et_blood_pressure_date.setError("لطفا این قسمت را پر کنید!");
            et_blood_pressure_date.requestFocus();
            return false;
        }

        return true;
    }

    //on click
    @Override
    public void onClick(View v)
    {
        if(v.equals(cv_BloodGlucose_register))
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
        else if(v.equals(cv_BloodPressure_register))
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
        else if(v.equals(water_iv_arrow))
        {
            //set value
            int amount = presenter_obj.water_getcurrent();
            water_tv_amount.setText(String.valueOf(amount));

            //animate
            int max_height = 210;  //TODO : get real height need to show

            if(water_iv_arrow.getTag().toString().equals("up"))
            {
                //animate arrow
                water_iv_arrow.setTag("down");
                water_iv_arrow.animate().rotation(180).setDuration(250).start();

                //animate continer
                water_continer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , 0));
                ValueAnimator va = ValueAnimator.ofInt(0 , max_height);
                va.setStartDelay(80);
                va.setDuration(900);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation)
                    {
                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , (int)animation.getAnimatedValue());
                        water_continer.setLayoutParams(param);
                    }
                });
                va.start();
            }
            else if(water_iv_arrow.getTag().toString().equals("down"))
            {
                //animate arrow
                water_iv_arrow.setTag("up");
                water_iv_arrow.animate().rotation(360).setDuration(250).start();

                //animate continer
                ValueAnimator va = ValueAnimator.ofInt(max_height , 0);
                va.setStartDelay(80);
                va.setDuration(900);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation)
                    {
                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , (int)animation.getAnimatedValue());
                        water_continer.setLayoutParams(param);
                    }
                });
                va.start();
            }
        }
        else if(v.equals(water_iv_increment))
        {
            int amount = presenter_obj.water_increment();
            water_tv_amount.setText(String.valueOf(amount));
        }
        else if(v.equals(water_iv_reset))
        {
            int amount = presenter_obj.water_restart();
            water_tv_amount.setText(String.valueOf(amount));
        }
        else if(v.equals(water_iv_decrement))
        {
            int amount = presenter_obj.water_decrement();
            water_tv_amount.setText(String.valueOf(amount));
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
