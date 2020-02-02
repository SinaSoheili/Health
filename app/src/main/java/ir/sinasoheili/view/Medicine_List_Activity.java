package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import model.Medicine;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;

public class Medicine_List_Activity extends AppCompatActivity implements Dashboard_page_contract.Dashboard_page_contract_view, AdapterView.OnItemClickListener
{
    public static final String MEDICINE_KEY_INTENT = "MEDICINE";

    private Dashboard_Page_presenter presenter;

    private ListView listview;
    private ArrayList<Medicine> all_item;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine__list_);

        init_obj();

        all_item = presenter.get_all_medicine();

        show_list_view();
    }

    private void init_obj()
    {
        presenter = new Dashboard_Page_presenter(this);

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

        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(this , Medicine_Activity_Item.class);
        intent.putExtra(MEDICINE_KEY_INTENT , all_item.get(position));
        startActivity(intent);
    }
}
