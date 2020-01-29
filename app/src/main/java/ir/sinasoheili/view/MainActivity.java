package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
{
    private ViewPager viewpager;
    private TabLayout tablayout;

    private int id_pages[] = {R.layout.page1 , R.layout.page2 , R.layout.page3};
    private String page_name[] = {"Page 1" , "Page 2" , "Page 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_obj();
    }

    private void init_obj()
    {
        viewpager = findViewById(R.id.viewpager);
        init_viewpager();

        tablayout = findViewById(R.id.tablayout);
        init_tablayout();
    }

    private void init_viewpager()
    {
        ViewPager_adapter adapter = new ViewPager_adapter(this , id_pages , page_name);

        if(viewpager != null)
        {
            viewpager.setAdapter(adapter);
        }
    }

    private void init_tablayout()
    {
        if(tablayout != null)
        {
            tablayout.setupWithViewPager(viewpager);

            tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tablayout.setTabMode(TabLayout.MODE_FIXED);
        }
    }
}
