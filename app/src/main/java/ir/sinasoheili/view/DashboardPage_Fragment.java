package ir.sinasoheili.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import presenter.app;

public class DashboardPage_Fragment extends Fragment implements View.OnClickListener
{

    private View root_view;
    private CardView cv_medicine;
    private CardView cv_illness;
    private CardView cv_medication_schedule;
    private CardView cv_blood_glucose_history;
    private CardView cv_blood_Pressure_history;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        root_view = inflater.inflate(R.layout.dashboard_page_layout, null , false);
        init_obj();
        return root_view;
    }

    public void init_obj()
    {
        //btn medicine
        cv_medicine = root_view.findViewById(R.id.DashboardPageLayout_CardView_Medicine);
        cv_medicine.setOnClickListener(this);

        //btn illness
        cv_illness = root_view.findViewById(R.id.DashboardPageLayout_CardView_Illness);
        cv_illness.setOnClickListener(this);

        //btn medication schedule
        cv_medication_schedule = root_view.findViewById(R.id.DashboardPageLayout_CardView_MedicationSchedule);
        cv_medication_schedule.setOnClickListener(this);

        //btn blood glucose history
        cv_blood_glucose_history = root_view.findViewById(R.id.DashboardPageLayout_CardView_BloodGlucose);
        cv_blood_glucose_history.setOnClickListener(this);

        //btn blood pressure history
        cv_blood_Pressure_history = root_view.findViewById(R.id.DashboardPageLayout_CardView_BloodPressure);
        cv_blood_Pressure_history.setOnClickListener(this);
    }

    //on click
    @Override
    public void onClick(View v)
    {
        if(v.equals(cv_medicine))
        {
            SharedPreferences pref = getContext().getSharedPreferences(app.pref_name , Context.MODE_PRIVATE);
            boolean result_copy = pref.getBoolean(app.key_medicins , false);
            if(result_copy == false)
            {
                Toast.makeText(root_view.getContext() , "لطفا تا کامل شدن انتقال اطلاعت منتظر بمانید ..." , Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this.getContext() , Medicine_List_Activity.class);
            getActivity().startActivity(intent);
        }
        else if(v.equals(cv_illness))
        {
            SharedPreferences pref = getContext().getSharedPreferences(app.pref_name , Context.MODE_PRIVATE);
            boolean result_copy = pref.getBoolean(app.key_illness , false);
            if(result_copy == false)
            {
                Toast.makeText(root_view.getContext() , "لطفا تا کامل شدن انتقال اطلاعت منتظر بمانید ..." , Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this.getContext() , Illness_List_Activity.class);
            getActivity().startActivity(intent);
        }
        else if(v.equals(cv_medication_schedule))
        {
            Intent intent = new Intent(this.getContext() , Medication_Schedule_Activity.class);
            getActivity().startActivity(intent);
        }
        else if(v.equals(cv_blood_glucose_history))
        {
            Intent intent = new Intent(this.getContext() , Blood_Glucose_History.class);
            getActivity().startActivity(intent);
        }
        else if(v.equals(cv_blood_Pressure_history))
        {
            Intent intent = new Intent(this.getContext() , Blood_Pressure_History.class);
            getActivity().startActivity(intent);
        }
    }
}
