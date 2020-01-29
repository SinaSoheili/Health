package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.DB_MedicationSchedule;
import model.MedicationSchedule;
import presenter.Main_page_contract;
import presenter.Main_page_presenter;

public class MainActivity extends AppCompatActivity implements Main_page_contract.Main_page_view
{
    private Main_page_contract.Main_page_presenter presenter_obj;

    private TextView MainView_CardView_MedicationSchedule_Title;
    private ListView MainView_CardView_MedicationSchedule_ListView;

    private View layout_MedicationSchdule_item_title ;
    private String s_today = "";

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
}
