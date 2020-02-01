package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import model.MedicationSchedule;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;

public class Medication_Schedule_Activity extends AppCompatActivity implements Dashboard_page_contract.Dashboard_page_contract_view, View.OnClickListener
{
    private Dashboard_page_contract.Dashboard_page_contract_presenter presenter;

    private ListView listView;
    private FloatingActionButton btn_add;

    private ArrayList<MedicationSchedule> items;

    //medication schedule dialog
    private Dialog medication_schedule_dialog_add;
    private EditText et_name;
    private EditText et_amount;
    private EditText et_time;
    private Spinner  sp_day;
    private Button   btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication__schedule_);

        init_obj();

        items = presenter.get_all_Medication_Schedule();
        items = sort_items(items);

        show_list(items);
    }

    private void init_obj()
    {
        presenter = new Dashboard_Page_presenter(this);

        //add button
        btn_add = findViewById(R.id.medication_schadule_floating_action_button);
        btn_add.setOnClickListener(this);

        //list view
        listView = findViewById(R.id.medication_schadule_List_view);
    }

    private ArrayList<MedicationSchedule> sort_items(ArrayList<MedicationSchedule> items)
    {
        ArrayList<MedicationSchedule> sorted_item = new ArrayList<>();

        MedicationSchedule.Day all_day[] = {MedicationSchedule.Day.saturday , MedicationSchedule.Day.sunday    , MedicationSchedule.Day.monday   ,
                                          MedicationSchedule.Day.tuesday  , MedicationSchedule.Day.wednesday , MedicationSchedule.Day.thursday , MedicationSchedule.Day.friday };

        for(int i=0 ; i<all_day.length ; i++)
        {
            MedicationSchedule.Day current_day = all_day[i];

            for(int j=0 ; j<items.size() ; j++)
            {
                MedicationSchedule current_medication = items.get(j);

                if(current_medication.getDay().equals(current_day))
                {
                    sorted_item.add(current_medication);
                }
            }
        }

        return  sorted_item;
    }

    public void show_list(ArrayList<MedicationSchedule> items)
    {
        if(items == null)
        {
            items = new ArrayList<>();
        }
        MedicationSchedule_All_List_Adapter adapter = new MedicationSchedule_All_List_Adapter(this , items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(btn_add))
        {
            show_add_dialog();
        }
        if(v.equals(btn_submit))
        {
            boolean valid = medication_shcedule_dialog_is_valid();
            if(valid == true)
            {
                String name = et_name.getText().toString();
                String amount = et_amount.getText().toString();
                String time = et_time.getText().toString();
                String day = sp_day.getSelectedItem().toString();

                MedicationSchedule m = new MedicationSchedule(name , amount , time , fa_string2Day(day));

                boolean b = presenter.insert_new_medicationSchedule(m);
                if(b == true)
                {
                    Toast.makeText(v.getContext() , "ثبت شد" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(v.getContext() , "ثبت نشد !!!" , Toast.LENGTH_SHORT).show();
                }

                medication_schedule_dialog_add.dismiss();
                update_listview(presenter.get_all_Medication_Schedule());
            }
        }
    }

    private void show_add_dialog()
    {
        medication_schedule_dialog_add = new Dialog(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root_view = inflater.inflate(R.layout.medication_schedule_dialog , null , false);

        et_name     = root_view.findViewById(R.id.medication_schedule_dialog_et_name);
        et_amount   = root_view.findViewById(R.id.medication_schedule_dialog_et_amount);
        et_time     = root_view.findViewById(R.id.medication_schedule_dialog_et_time);
        btn_submit  = root_view.findViewById(R.id.medication_schedule_dialog_btn_submit);
        sp_day      = root_view.findViewById(R.id.medication_schedule_dialog_spinner_days);

        String days[] = {"شنبه" , "یکشنبه" , "دوشنبه" , "سه شنبه" , "چهار شنبه" , "پنج شنبه" , "جمعه" };
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_item , days);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_day.setAdapter(spinner_adapter);

        btn_submit.setOnClickListener(this);

        medication_schedule_dialog_add.setContentView(root_view);
        medication_schedule_dialog_add.show();
        medication_schedule_dialog_add.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT , WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private boolean medication_shcedule_dialog_is_valid()
    {
        String name = et_name.getText().toString();
        String amount = et_amount.getText().toString();
        String time = et_time.getText().toString();

        if(name.isEmpty())
        {
            et_name.setError("لطفا این قسمت را پر کنید");
            et_name.requestFocus();

            return false;
        }
        else if(amount.isEmpty())
        {
            et_amount.setError("لطفا این قسمت را پر کنید");
            et_amount.requestFocus();

            return false;
        }
        else if(time.isEmpty())
        {
            et_time.setError("لطفا این قسمت را پر کنید");
            et_time.requestFocus();

            return false;
        }

        return true;
    }

    private MedicationSchedule.Day fa_string2Day(String day)
    {

        if(day.equals("شنبه"))
        {
            return MedicationSchedule.Day.saturday;
        }
        else if(day.equals("یکشنبه"))
        {
            return MedicationSchedule.Day.sunday;
        }
        else if(day.equals("دو شنبه"))
        {
            return MedicationSchedule.Day.monday;
        }
        else if(day.equals("سه شنبه"))
        {
            return MedicationSchedule.Day.tuesday;
        }
        else if(day.equals("چهار شنبه"))
        {
            return MedicationSchedule.Day.wednesday;
        }
        else if(day.equals("پنج شنبه"))
        {
            return MedicationSchedule.Day.thursday;
        }
        else if(day.equals("جمعه"))
        {
            return MedicationSchedule.Day.friday;
        }

        return null;
    }

    public void update_listview(ArrayList<MedicationSchedule> items)
    {
        items = sort_items(items);
        show_list(items);
    }
}
