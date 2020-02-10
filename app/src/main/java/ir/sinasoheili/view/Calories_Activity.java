package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

import model.Calories;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;

public class Calories_Activity extends AppCompatActivity implements Dashboard_page_contract.Dashboard_page_contract_view, SearchView.OnQueryTextListener, View.OnClickListener
{
    private Dashboard_page_contract.Dashboard_page_contract_presenter presenter;

    private androidx.appcompat.widget.SearchView searchview;
    private ListView listview;
    private FloatingActionButton btn_archive;

    private ArrayList<Calories> items;
    private ArrayList<Calories> item_archive = new ArrayList<>();

    //dialog
    private ListView lv_dialog_archive;
    private TextView tv_dialog_archive;
    private int id_item_remove;

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

        btn_archive = findViewById(R.id.Calories_Activity_archive);
        btn_archive.setOnClickListener(this);
    }

    private void show_list_view()
    {
        Calories_Adapter adapter = new Calories_Adapter(this , items);
        listview.setAdapter(adapter);

        LayoutAnimationController anim = AnimationUtils.loadLayoutAnimation(this , R.anim.animation_layout_list_item);
        listview.setLayoutAnimation(anim);
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

    @Override
    public void onClick(View v)
    {
        if(v.equals(btn_archive))
        {
            show_dialog_calories_archive();
        }
    }

    public void show_dialog_calories_archive()
    {
        if(item_archive.size() == 0)
        {
            Toast.makeText(this, "گزینه ای انتخاب نشده است !!", Toast.LENGTH_SHORT).show();
            return;
        }

        Dialog dialog_archive = new Dialog(this);
        dialog_archive.setContentView(getLayoutInflater().inflate(R.layout.dialog_calories_item_archive, null , false));

        lv_dialog_archive = dialog_archive.findViewById(R.id.Calories_DialogArchive_lv_item);
        tv_dialog_archive = dialog_archive.findViewById(R.id.Calories_DialogArchive_tv_sum);

        show_list_view_archive();

        dialog_archive.show();
        dialog_archive.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void add_to_archive(View v)
    {
        Animation anim = AnimationUtils.loadAnimation(this , R.anim.btn_archive_animation);
        v.startAnimation(anim);

        //btn of list item to adding item to archive
        int id = (int) v.getTag();
        Calories cal = presenter.search_calories(id);
        item_archive.add(cal);
    }

    public void show_list_view_archive()
    {
        Collections.reverse(item_archive);
        Calories_Archive_Adapter adapter = new Calories_Archive_Adapter(this , item_archive);
        lv_dialog_archive.setAdapter(adapter);

        int sum = 0;
        for(Calories cal : item_archive)
        {
            String c = cal.getCalories().substring(0 , cal.getCalories().indexOf("."));
            c = c.replace("$" , "");

            sum += Integer.valueOf(c);
        }
        tv_dialog_archive.setText(String.valueOf(sum));
    }

    public void remove_from_archive(View v)
    {
        Animation anim = AnimationUtils.loadAnimation(this , R.anim.btn_archive_animation);
        v.startAnimation(anim);

        id_item_remove = (int)v.getTag();

        new Handler().postDelayed(new Runnable() // handler set since show animation of delete
        {
            @Override
            public void run()
            {
                for(int index=0 ; index<item_archive.size() ; index++)
                {
                    if(item_archive.get(index).getId() == id_item_remove)
                    {
                        item_archive.remove(index);
                        break; //break form 'for' loop because in item_srchive there are repeat item with same id
                    }
                }

                show_list_view_archive();
            }
        } , 180);
    }
}
