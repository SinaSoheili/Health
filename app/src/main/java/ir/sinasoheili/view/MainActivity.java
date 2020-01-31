package ir.sinasoheili.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
    private int fragment_id_pages[] = {R.layout.home_page_layout, R.layout.dashboard_page_layout, R.layout.profile_page_layout};

    private BottomNavigationView bnv;

   @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_obj();
    }

    private void init_obj()
    {
        BottomNavigationView bnv = findViewById(R.id.bottom_navigation_view);
        bnv.setOnNavigationItemSelectedListener(this);

        //simulate click in home page to show item
        View v = bnv.getRootView().findViewById(bnv.getSelectedItemId());
        v.performClick();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        if(menuItem.getItemId() == R.id.manu_bnv_home)
        {
            HomePage_Fragment fragment_page_1 = new HomePage_Fragment();
            show_fragment(fragment_page_1);
            return true;
        }
        else if(menuItem.getItemId() == R.id.manu_bnv_dashboard)
        {
            DashboardPage_Fragment fragment_page_2 = new DashboardPage_Fragment();
            show_fragment(fragment_page_2);
            return true;
        }
        else if(menuItem.getItemId() == R.id.manu_bnv_profile)
        {
            Profile_Fragment fragment_page_3 = new Profile_Fragment();
            show_fragment(fragment_page_3);
            return true;
        }
        return false;
    }

    //show fragment
    public void show_fragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container , fragment).commit();
    }
}
