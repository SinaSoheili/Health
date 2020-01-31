package ir.sinasoheili.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.Medicine;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;

public class DashboardPage_Fragment extends Fragment implements Dashboard_page_contract.Dashboard_page_contract_view, View.OnClickListener
{
    private Dashboard_page_contract.Dashboard_page_contract_presenter presenter;

    public static final String MEDICINE_KEY = "MEDICINE";

    private View root_view;
    private Button btn_medicine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        root_view = inflater.inflate(R.layout.dashboard_page_layout, null , false);
        init_obj();
        return root_view;
    }

    public void init_obj()
    {
        //presenter
        presenter = new Dashboard_Page_presenter(this);

        //btn medicine
        btn_medicine = root_view.findViewById(R.id.Dashboard_btn_medicine);
        btn_medicine.setOnClickListener(this);
    }

    //on click
    @Override
    public void onClick(View v)
    {
        if(v.equals(btn_medicine))
        {
            ArrayList<Medicine> all_item = presenter.get_all_medicine();
            Intent intent = new Intent(this.getContext() , Medicine_List_Activity.class);
            //TODO : add to extra all item
            this.startActivity(intent);
        }
    }
}
