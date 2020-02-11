package ir.sinasoheili.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class IntroSlider extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    public static final String PREF_NAME = "IntroSlider";
    public static final String PREF_KEY = "show";

    private ViewPager viewpager;
    private Button btn_next;
    private Button btn_prev;
    private LinearLayout circle_continer;

    private int id_slide[] = {R.layout.introslider_slide1 , R.layout.introslider_slide2 , R.layout.introslider_slide3};
    private int slide_color[] = {R.color.IntroSlider_slide1_background , R.color.IntroSlider_slide2_background , R.color.IntroSlider_slide3_background};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        init_obj();

        hide_actionbar();

        show_slide();
    }

    private void init_obj()
    {
        viewpager = findViewById(R.id.IntroSlider_ViewPager);
        viewpager.setOnPageChangeListener(this);

        btn_next = findViewById(R.id.IntroSlider_btn_next);
        btn_next.setOnClickListener(this);

        btn_prev = findViewById(R.id.IntroSlider_btn_prev);
        btn_prev.setOnClickListener(this);

        circle_continer = findViewById(R.id.IntroSlider_LinearLayout_CircleContiner);
    }

    private void hide_actionbar()
    {
        ActionBar ab = this.getSupportActionBar();
        if(ab != null)
        {
            ab.hide();
        }
    }

    private void show_slide()
    {
        IntorSlider_Adapter adapter = new IntorSlider_Adapter(this , id_slide);
        viewpager.setAdapter(adapter);

        show_dot();

        change_color_statusbar();
    }

    private void change_color_statusbar()
    {
        int i = viewpager.getCurrentItem();
        this.getWindow().setStatusBarColor(getResources().getColor(slide_color[i]));
    }

    private void show_dot()
    {
        circle_continer.removeAllViews();

        for(int i=0 ; i<id_slide.length ; i++)
        {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(new LinearLayout.LayoutParams(50 , 50));
            iv.setPadding(10 , 10 , 10 , 10);

            if(i == viewpager.getCurrentItem())
            {
                iv.setImageResource(R.drawable.circle_selected);
            }
            else
            {
                iv.setImageResource(R.drawable.circle_unselected);
            }
            circle_continer.addView(iv);
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(btn_next))
        {
            next_slide();
        }
        else if(v.equals(btn_prev))
        {
            prev_slide();
        }
    }

    private void next_slide()
    {
        if(viewpager.getCurrentItem() == id_slide.length-2)
        {
            btn_next.setText("شروع برنامه");
        }
        else if(viewpager.getCurrentItem() == id_slide.length-1)
        {
            SharedPreferences pref = getSharedPreferences( PREF_NAME , MODE_PRIVATE);
            pref.edit().putBoolean(PREF_KEY , false).commit();

            startActivity(new Intent(IntroSlider.this , MainActivity.class));
            finish();
        }

        viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
        show_dot();
        change_color_statusbar();
        btn_prev.setVisibility(View.VISIBLE);
    }

    private void prev_slide()
    {
        viewpager.setCurrentItem(viewpager.getCurrentItem()-1);
        show_dot();
        change_color_statusbar();
        if(viewpager.getCurrentItem() == 0)
        {
            btn_prev.setVisibility(View.INVISIBLE);
        }
        if(viewpager.getCurrentItem() != id_slide.length-1)
        {
            btn_next.setText("بعدی");
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        if(position == id_slide.length-1)
        {
            btn_next.setText("شروع برنامه");
        }
        else
        {
            btn_next.setText("بعدی");
        }

        if(position == 0)
        {
            btn_prev.setVisibility(View.INVISIBLE);
        }
        else
        {
            btn_prev.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
        change_color_statusbar();
        show_dot();
    }
}
