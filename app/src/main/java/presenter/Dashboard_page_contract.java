package presenter;

import java.util.ArrayList;

import model.BloodGlucose;
import model.BloodPressure;
import model.Calories;
import model.Illness;
import model.MedicationSchedule;
import model.Medicine;

public interface Dashboard_page_contract
{
    public interface Dashboard_page_contract_view
    {
    }

    public interface Dashboard_page_contract_presenter
    {
        public ArrayList<Medicine> get_all_medicine();
        public ArrayList<Medicine> search_medicine(String query);
        public ArrayList<Illness> get_all_illness();
        public ArrayList<Illness> search_illness(String query);
        public ArrayList<MedicationSchedule> get_all_Medication_Schedule();
        public boolean insert_new_medicationSchedule(MedicationSchedule m);
        public boolean update_medication_Schedule(MedicationSchedule mold , MedicationSchedule mnew);
        public boolean delete_medication_Schedule(MedicationSchedule m);
        public ArrayList<BloodGlucose> get_all_blood_glucose();
        public ArrayList<BloodPressure> get_all_blood_pressure();
        public ArrayList<Calories> get_all_calories();
        public ArrayList<Calories> search_calories(String name);
        public Calories search_calories(int id);
    }
}
