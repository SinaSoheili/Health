package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import model.MedicationSchedule;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;

public class Medication_Schedule_Activity extends AppCompatActivity implements Dashboard_page_contract.Dashboard_page_contract_view
{
    private Dashboard_page_contract.Dashboard_page_contract_presenter presenter;
    private ListView listView;
    private ArrayList<MedicationSchedule> items;


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
}
