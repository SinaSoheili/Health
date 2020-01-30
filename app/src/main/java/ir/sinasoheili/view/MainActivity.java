package ir.sinasoheili.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
    private int fragment_id_pages[] = {R.layout.page1_layout , R.layout.page2_layout , R.layout.page3_layout};

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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        if(menuItem.getItemId() == R.id.manu_bnv_home)
        {
            Page1_Fragment fragment_page_1 = new Page1_Fragment();
            show_fragment(fragment_page_1);
            return true;
        }
        else if(menuItem.getItemId() == R.id.manu_bnv_dashboard)
        {
            Page2_Fragment fragment_page_2 = new Page2_Fragment();
            show_fragment(fragment_page_2);
            return true;
        }
        else if(menuItem.getItemId() == R.id.manu_bnv_profile)
        {
            Page3_Fragment fragment_page_3 = new Page3_Fragment();
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
