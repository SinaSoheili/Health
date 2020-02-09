package presenter;

import android.app.Activity;

import java.util.ArrayList;

import model.BloodGlucose;
import model.BloodPressure;
import model.Day;
import model.MedicationSchedule;

public interface Home_page_contract
{
    public interface Main_page_view
    {
        public void show_today_medication_schedule();

        public void show_dialog_blood_glucose();

        public void show_dialog_blood_pressure();
    }

    public interface Home_page_presenter
    {
        public ArrayList<MedicationSchedule> get_Medication_schedule(Day day);

        public ArrayList<MedicationSchedule> get_Medication_schedule_all_item();

        public boolean register_BloodGlucose(BloodGlucose bg);

        public boolean register_BloodPressure(BloodPressure bp);

        public int water_getcurrent();
        public int water_increment();
        public int water_restart();
        public int water_decrement();
    }
}
