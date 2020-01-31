package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.Medicine;

public class Medicine_List_Activity extends AppCompatActivity
{
    private ListView listview;
    private ArrayList<Medicine> all_item;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine__list_);

        //TODO : get intent and extra and set all item

        init_obj();
        show_list_view();
    }

    private void init_obj()
    {
        listview = findViewById(R.id.Medicine_Activity_listview);
    }

    private void show_list_view()
    {
        if(all_item == null)
        {
            all_item = new ArrayList<>();
        }
        ArrayAdapter<Medicine> adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , all_item);
        listview.setAdapter(adapter);
    }
}
