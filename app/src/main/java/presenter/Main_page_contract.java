package presenter;

import android.app.Activity;

import java.util.ArrayList;

import model.BloodGlucose;
import model.BloodPressure;
import model.MedicationSchedule;

public interface Main_page_contract
{
    public interface Main_page_view
    {
        public void show_today_medication_schedule();

        public void show_dialog_blood_glucose();

        public void show_dialog_blood_pressure();
    }

    public interface Main_page_presenter
    {
        public ArrayList<MedicationSchedule> get_Medication_schedule(MedicationSchedule.Day day);

        public ArrayList<MedicationSchedule> get_Medication_schedule_all_item();

        public boolean register_BloodGlucose(BloodGlucose bg);

        public boolean register_BloodPressure(BloodPressure bp);
    }
}
