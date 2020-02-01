package presenter;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

import model.DB_Illness;
import model.DB_MedicationSchedule;
import model.DB_Medicine;
import model.Illness;
import model.MedicationSchedule;
import model.Medicine;

public class Dashboard_Page_presenter implements Dashboard_page_contract.Dashboard_page_contract_presenter
{
    private Dashboard_page_contract.Dashboard_page_contract_view view_obj;
    private Context context;

    public Dashboard_Page_presenter(Fragment view_obj)
    {
        this.view_obj = (Dashboard_page_contract.Dashboard_page_contract_view) view_obj;
        this.context = view_obj.getContext();
    }

    public Dashboard_Page_presenter(AppCompatActivity view_obj)
    {
        this.view_obj = (Dashboard_page_contract.Dashboard_page_contract_view) view_obj;
        this.context = view_obj.getApplicationContext();
    }

    @Override
    public ArrayList<Medicine> get_all_medicine()
    {
        DB_Medicine db = new DB_Medicine(context);
        ArrayList<Medicine> all_item = db.get_all();
        return all_item;
    }

    @Override
    public ArrayList<Illness> get_all_illness()
    {
        DB_Illness db = new DB_Illness(context);
        ArrayList<Illness> all_item = db.get_all();
        return all_item;
    }

    @Override
    public ArrayList<MedicationSchedule> get_all_Medication_Schedule()
    {
        DB_MedicationSchedule db = new DB_MedicationSchedule(context);
        ArrayList<MedicationSchedule> all_items = db.get_all();
        return all_items;
    }

}
