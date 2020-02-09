package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import model.Calories;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;

public class Calories_Activity extends AppCompatActivity implements Dashboard_page_contract.Dashboard_page_contract_view, SearchView.OnQueryTextListener
{
    private Dashboard_page_contract.Dashboard_page_contract_presenter presenter;

    private androidx.appcompat.widget.SearchView searchview;
    private ListView listview;

    private ArrayList<Calories> items;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_);

        init_obj();

        items = presenter.get_all_calories();

        show_list_view();
    }

    private void init_obj()
    {
        presenter = new Dashboard_Page_presenter(this);

        listview = findViewById(R.id.Calories_Activity_listview);

        searchview = findViewById(R.id.Calories_Activity_searchview);
        searchview.setOnQueryTextListener(this);
    }

    private void show_list_view()
    {
        ArrayAdapter adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , items);
        listview.setAdapter(adapter);
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
            items = presenter.get_all_calories();
            show_list_view();
        }
        else
        {
            items = presenter.search_calories(newText);
            show_list_view();
        }
        return false;
    }
}
