package presenter;

import android.content.Context;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import model.BloodGlucose;
import model.BloodPressure;
import model.DB_BloodGlucose;
import model.DB_BloodPressure;
import model.DB_MedicationSchedule;
import model.MedicationSchedule;

public class Home_page_presenter implements Home_page_contract.Home_page_presenter
{
    private Home_page_contract.Main_page_view view_obj;
    private Context context;

    public Home_page_presenter(Fragment view_obj)
    {
        this.context = view_obj.getContext();
        this.view_obj = (Home_page_contract.Main_page_view) view_obj;
    }

    @Override
    public ArrayList<MedicationSchedule> get_Medication_schedule(MedicationSchedule.Day day)
    {
        DB_MedicationSchedule db = new DB_MedicationSchedule(context);

        ArrayList<MedicationSchedule> items = db.search(day);

        return items;
    }

    @Override
    public ArrayList<MedicationSchedule> get_Medication_schedule_all_item()
    {
        DB_MedicationSchedule db = new DB_MedicationSchedule(context);

        ArrayList<MedicationSchedule> all_item = db.get_all();

        return all_item;
    }

    @Override
    public boolean register_BloodGlucose(BloodGlucose bg)
    {
        DB_BloodGlucose db = new DB_BloodGlucose(context);
        long id = db.insert(bg);
        if(id != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean register_BloodPressure(BloodPressure bp)
    {
        DB_BloodPressure db = new DB_BloodPressure(context);
        long id = db.insert(bp);
        if(id != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
