package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import model.BloodGlucose;
import model.BloodPressure;
import presenter.Dashboard_Page_presenter;
import presenter.Dashboard_page_contract;

public class Blood_Pressure_History extends AppCompatActivity implements Dashboard_page_contract.Dashboard_page_contract_view, ViewPager.OnPageChangeListener
{
    private int id_page[] = {R.layout.blood_pressure_history_chart , R.layout.blood_pressure_history_log};
    private String page_name[] = {"نمودار" , "گزارش"};

    private Dashboard_page_contract.Dashboard_page_contract_presenter presenter;

    private ViewPager viewpager;
    private TabLayout tablayout;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood__pressure__history);

        init_obj();
        init_viewpager();

        viewpager.post(new Runnable()
        {
            @Override
            public void run()
            {
                Blood_Pressure_History.this.onPageSelected(0);
            }
        });
    }

    private void init_obj()
    {
        presenter = new Dashboard_Page_presenter(this);

        viewpager = findViewById(R.id.Blood_Pressure_history_viewpager);

        tablayout = findViewById(R.id.Blood_Pressure_history_tablayout);
    }

    private void init_viewpager()
    {
        Blood_Pressure_pager_adapter adapter = new Blood_Pressure_pager_adapter(this , id_page , page_name);
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
        ArrayList<BloodPressure> all_item = presenter.get_all_blood_pressure();

        if(position == 0)
        {
            View root_view = viewpager.getChildAt(0);
            CardView cardview = root_view.findViewById(R.id.Blood_Pressure_history_cardview_chart);

            LineChart line_chart = new LineChart(this);
            line_chart.setPadding(10 , 15 , 10 , 15);
            line_chart.getAxisRight().setEnabled(false);
            line_chart.getXAxis().setDrawGridLines(false);
            cardview.addView(line_chart);

            ArrayList<Entry> values_diastolic = new ArrayList<>();
            ArrayList<Entry> values_systolic = new ArrayList<>();

            for(int i=0 ; i<all_item.size() ; i++)
            {
                BloodPressure bp = all_item.get(i);

                values_diastolic.add(new Entry(bp.getId() , bp.getDiastolic()));
                values_systolic.add(new Entry(bp.getId() , bp.getSystolic()));
            }

            LineDataSet dataset_diastolic = new LineDataSet(values_diastolic , "دیاستولیک");
            LineDataSet dataset_systolic = new LineDataSet(values_systolic , "سیستولیک");

            dataset_diastolic.setColor(Color.BLUE);
            dataset_diastolic.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataset_diastolic.setDrawFilled(true);

            dataset_systolic.setColor(Color.RED);
            dataset_systolic.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataset_systolic.setDrawFilled(true);

            line_chart.setData(new LineData(dataset_systolic , dataset_diastolic));
        }
        else if(position == 1)
        {
            View view_log = viewpager.getChildAt(1);

            listview = findViewById(R.id.Blood_Pressure_history_listview_log);

            Blood_Pressure_History_List_Adapter adapter = new Blood_Pressure_History_List_Adapter(this , all_item);
            listview.setAdapter(adapter);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
    }

}

