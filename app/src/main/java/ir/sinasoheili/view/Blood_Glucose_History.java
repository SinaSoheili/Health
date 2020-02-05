package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import model.BloodGlucose;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;

public class Blood_Glucose_History extends AppCompatActivity implements Dashboard_page_contract.Dashboard_page_contract_view, ViewPager.OnPageChangeListener
{
    private int id_page[] = {R.layout.blood_glucose_history_chart , R.layout.blood_glucose_history_log};
    private String page_name[] = {"نمودار" , "گزارش"};

    private Dashboard_page_contract.Dashboard_page_contract_presenter presenter;

    private ViewPager viewpager;
    private TabLayout tablayout;
    private ListView  listview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood__glucose__history);

        init_obj();
        init_viewpager();

        viewpager.post(new Runnable()
        {
            @Override
            public void run()
            {
                Blood_Glucose_History.this.onPageSelected(0);
            }
        });
    }

    private void init_obj()
    {
        presenter = new Dashboard_Page_presenter(this);

        viewpager = findViewById(R.id.Blood_Glucose_history_viewpager);

        tablayout = findViewById(R.id.Blood_Glucose_history_tablayout);
    }

    private void init_viewpager()
    {
        Blood_Glucose_pager_adapter adapter = new Blood_Glucose_pager_adapter(this , id_page , page_name);
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(this);
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        ArrayList<BloodGlucose> all_item = presenter.get_all_blood_glucose();

        if(position == 0)
        {
            View root_view = viewpager.getChildAt(0);
            CardView cardview = root_view.findViewById(R.id.Blood_Glucose_history_cardview_chart);

            LineChart line_chart = new LineChart(this);
            line_chart.setPadding(10 , 15 , 10 , 15);
            line_chart.getAxisRight().setEnabled(false);
            line_chart.getXAxis().setDrawGridLines(false);
            cardview.addView(line_chart);

            ArrayList<Entry> values = new ArrayList<>();
            for(int i=0 ; i<all_item.size() ; i++)
            {
                BloodGlucose bg = all_item.get(i);

                values.add(new Entry(bg.getId() , bg.getBlood_glucose_value()));
            }

            LineDataSet dataset = new LineDataSet(values , "قند خون");
            dataset.setCircleColor(Color.RED);
            dataset.setColor(Color.BLUE);
            dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataset.setDrawFilled(true);
            LineData lineData = new LineData(dataset);
            line_chart.setData(lineData);
        }
        else if(position == 1)
        {
            View view_log = viewpager.getChildAt(1);

            listview = findViewById(R.id.Blood_Glucose_history_listview_log);
            listview.setDivider(null);

            Blood_Glucose_History_List_Adapter adapter = new Blood_Glucose_History_List_Adapter(this , all_item);
            listview.setAdapter(adapter);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
    }
}
