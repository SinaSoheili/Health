package ir.sinasoheili.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.Illness;
import model.Medicine;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;
import presenter.app;

public class DashboardPage_Fragment extends Fragment implements View.OnClickListener
{

    private View root_view;
    private Button btn_medicine;
    private Button btn_illness;
    private Button btn_medication_schedule;
    private Button btn_blood_glucose_history;
    private Button btn_blood_Pressure_history;

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
        btn_medicine = root_view.findViewById(R.id.Dashboard_btn_medicine);
        btn_medicine.setOnClickListener(this);

        //btn illness
        btn_illness = root_view.findViewById(R.id.Dashboard_btn_illness);
        btn_illness.setOnClickListener(this);

        //btn medication schedule
        btn_medication_schedule = root_view.findViewById(R.id.Dashboard_btn_Medication_Schedule);
        btn_medication_schedule.setOnClickListener(this);

        //btn blood glucose history
        btn_blood_glucose_history = root_view.findViewById(R.id.Dashboard_btn_bloodGlucose);
        btn_blood_glucose_history.setOnClickListener(this);

        //btn blood pressure history
        btn_blood_Pressure_history = root_view.findViewById(R.id.Dashboard_btn_bloodPressure);
        btn_blood_Pressure_history.setOnClickListener(this);
    }

    //on click
    @Override
    public void onClick(View v)
    {
        if(v.equals(btn_medicine))
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
        else if(v.equals(btn_illness))
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
        else if(v.equals(btn_medication_schedule))
        {
            Intent intent = new Intent(this.getContext() , Medication_Schedule_Activity.class);
            getActivity().startActivity(intent);
        }
        else if(v.equals(btn_blood_glucose_history))
        {
            Intent intent = new Intent(this.getContext() , Blood_Glucose_History.class);
            getActivity().startActivity(intent);
        }
        else if(v.equals(btn_blood_Pressure_history))
        {
            Intent intent = new Intent(this.getContext() , Blood_Pressure_History.class);
            getActivity().startActivity(intent);
        }
    }
}
