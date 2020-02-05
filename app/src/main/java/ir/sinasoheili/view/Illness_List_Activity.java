package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import model.Illness;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;

public class Illness_List_Activity extends AppCompatActivity implements Dashboard_page_contract.Dashboard_page_contract_view, AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
    public static final String ILLNESS_KEY_INTENT = "ILLNESS";

    private ListView lv ;
    private ArrayList<Illness> all_item;
    private Dashboard_Page_presenter presenter;

    private SearchView searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illness__list_);

        init_obj();

        all_item = presenter.get_all_illness();

        show_list_viwe();
    }

    private void init_obj()
    {
        presenter = new Dashboard_Page_presenter(this);

        lv = findViewById(R.id.Illness_Activity_ListView);
        lv.setOnItemClickListener(this);

        searchview = findViewById(R.id.Illness_Activity_searchview);
        searchview.setOnQueryTextListener(this);
    }

    private void show_list_viwe()
    {
        if(all_item == null)
        {
            all_item = new ArrayList<>();
        }
        ArrayAdapter<Illness> adapter = new ArrayAdapter<Illness>(this , android.R.layout.simple_list_item_1 , all_item);
        lv.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent i = new Intent(this , Illness_Activivty_Item.class);
        i.putExtra(ILLNESS_KEY_INTENT , all_item.get(position));
        startActivity(i);
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        if(newText.isEmpty())
        {
            all_item = presenter.get_all_illness();
            show_list_viwe();
        }
        else
        {
            all_item = presenter.search_illness(newText);
            show_list_viwe();
        }
        return false;
    }
}
