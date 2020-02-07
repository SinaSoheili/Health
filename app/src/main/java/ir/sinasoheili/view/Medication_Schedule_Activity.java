package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import model.Day;
import model.MedicationSchedule;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;

public class Medication_Schedule_Activity extends AppCompatActivity implements Dashboard_page_contract.Dashboard_page_contract_view, View.OnClickListener, AdapterView.OnItemClickListener
{
    private Dashboard_page_contract.Dashboard_page_contract_presenter presenter;

    private ListView listView;
    private FloatingActionButton btn_add;

    private ArrayList<MedicationSchedule> items;

    private String day[] = {Day.en_day2fa_day(Day.saturday) , Day.en_day2fa_day(Day.sunday) , Day.en_day2fa_day(Day.monday) , Day.en_day2fa_day(Day.tuesday) , Day.en_day2fa_day(Day.wednesday) , Day.en_day2fa_day(Day.thursday) , Day.en_day2fa_day(Day.friday)};

    //medication schedule dialog
    private Dialog medication_schedule_dialog_add;
    private Dialog medication_schedule_dialog_edit;
    private EditText et_name;
    private EditText et_amount;
    private EditText et_time;
    private Spinner  sp_day;
    private Button btn_submit_add;
    private Button btn_submit_edit;
    private Button btn_submit_delete;
    private MedicationSchedule old_medicationSchedule = null;

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
        listView.setOnItemClickListener(this);
        listView.setDividerHeight(4);
    }

    private ArrayList<MedicationSchedule> sort_items(ArrayList<MedicationSchedule> items)
    {
        ArrayList<MedicationSchedule> sorted_item = new ArrayList<>();

        Day all_day[] = {Day.saturday , Day.sunday    , Day.monday   , Day.tuesday  , Day.wednesday , Day.thursday , Day.friday };

        for(int i=0 ; i<all_day.length ; i++)
        {
            Day current_day = all_day[i];

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

        LayoutAnimationController anim = AnimationUtils.loadLayoutAnimation(this , R.anim.animation_layout_list_item);
        listView.setLayoutAnimation(anim);
    }

    private void show_add_dialog()
    {
        medication_schedule_dialog_add = new Dialog(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root_view = inflater.inflate(R.layout.medication_schedule_add_dialog, null , false);

        et_name     = root_view.findViewById(R.id.medication_schedule_dialog_et_name);
        et_amount   = root_view.findViewById(R.id.medication_schedule_dialog_et_amount);
        et_time     = root_view.findViewById(R.id.medication_schedule_dialog_et_time);
        btn_submit_add = root_view.findViewById(R.id.medication_schedule_dialog_btn_submit);
        sp_day      = root_view.findViewById(R.id.medication_schedule_dialog_spinner_days);

        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item , day);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_day.setAdapter(spinner_adapter);

        btn_submit_add.setOnClickListener(this);

        medication_schedule_dialog_add.setContentView(root_view);
        medication_schedule_dialog_add.show();
        medication_schedule_dialog_add.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT , WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void show_edit_dialog(MedicationSchedule current_medication_schedule)
    {
        medication_schedule_dialog_edit = new Dialog(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root_view = inflater.inflate(R.layout.medication_schedule_edit_dialog, null , false);

        et_name           = root_view.findViewById(R.id.medication_schedule_edit_dialog_et_name);
        et_amount         = root_view.findViewById(R.id.medication_schedule_edit_dialog_et_amount);
        et_time           = root_view.findViewById(R.id.medication_schedule_edit_dialog_et_time);
        btn_submit_edit   = root_view.findViewById(R.id.medication_schedule_edit_dialog_btn_submit);
        btn_submit_delete = root_view.findViewById(R.id.medication_schedule_edit_dialog_btn_delete);
        sp_day            = root_view.findViewById(R.id.medication_schedule_edit_dialog_spinner_days);

        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item , day);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_day.setAdapter(spinner_adapter);

        et_name.setText(current_medication_schedule.getMedicine_name());
        et_amount.setText(current_medication_schedule.getMedicine_amount());
        et_time.setText(current_medication_schedule.getTime());
        sp_day.setSelection(current_medication_schedule.getDay().ordinal());

        btn_submit_edit.setOnClickListener(this);
        btn_submit_delete.setOnClickListener(this);

        medication_schedule_dialog_edit.setContentView(root_view);
        medication_schedule_dialog_edit.show();
        medication_schedule_dialog_edit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT , WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(btn_add))
        {
            show_add_dialog();
        }
        else if(v.equals(btn_submit_add))
        {
            boolean valid = medication_shcedule_dialog_is_valid();
            if(valid == true)
            {
                String name   = et_name.getText().toString();
                String amount = et_amount.getText().toString();
                String time   = et_time.getText().toString();
                String day    = this.day[sp_day.getSelectedItemPosition()];

                MedicationSchedule m = new MedicationSchedule(name , amount , time , Day.fa_day2en_day(day));

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
                items = presenter.get_all_Medication_Schedule();
                update_listview(items);
            }
        }
        else if(v.equals(btn_submit_edit))
        {
            if(medication_shcedule_dialog_is_valid())
            {
                String name   = et_name.getText().toString();
                String amount = et_amount.getText().toString();
                String time   = et_time.getText().toString();
                String day    = this.day[sp_day.getSelectedItemPosition()];

                MedicationSchedule new_m = new MedicationSchedule(name , amount , time , Day.fa_day2en_day(day));

                boolean result =  presenter.update_medication_Schedule(old_medicationSchedule , new_m);
                if(result == true)
                {
                    Toast.makeText(this, "بروزرسانی انجام شد", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "بروزرسانی انجام نشد!!", Toast.LENGTH_SHORT).show();
                }
                medication_schedule_dialog_edit.dismiss();
                old_medicationSchedule = null;
                items = presenter.get_all_Medication_Schedule();
                update_listview(items);
            }
        }
        else if(v.equals(btn_submit_delete))
        {
            if(medication_shcedule_dialog_is_valid())
            {
                String name   = et_name.getText().toString();
                String amount = et_amount.getText().toString();
                String time   = et_time.getText().toString();
                String day    = this.day[sp_day.getSelectedItemPosition()];

                MedicationSchedule new_m = new MedicationSchedule(name , amount , time , Day.fa_day2en_day(day));

                boolean result =  presenter.delete_medication_Schedule(old_medicationSchedule);
                if(result == true)
                {
                    Toast.makeText(this, "حذف شد", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "حذف نشد!!", Toast.LENGTH_SHORT).show();
                }
                medication_schedule_dialog_edit.dismiss();
                old_medicationSchedule = null;
                items = presenter.get_all_Medication_Schedule();
                update_listview(items);
            }
        }
    }

    private boolean medication_shcedule_dialog_is_valid()
    {
        String name   = et_name.getText().toString();
        String amount = et_amount.getText().toString();
        String time   = et_time.getText().toString();

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

    public void update_listview(ArrayList<MedicationSchedule> items)
    {
        this.items = sort_items(items);
        show_list(this.items);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if(items == null)
        {
            return;
        }
        old_medicationSchedule = items.get(position);
        show_edit_dialog(old_medicationSchedule);
    }
}
