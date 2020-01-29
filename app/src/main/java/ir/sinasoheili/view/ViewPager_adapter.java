package ir.sinasoheili.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPager_adapter extends PagerAdapter
{
    private Context context;

    private int id_page[];
    private String page_name[];

    //constructor
    public ViewPager_adapter(Context context , int id_page[])
    {
        this.context = context;
        this.id_page = id_page;
    }

    public ViewPager_adapter(Context context , int id_page[] , String page_name[])
    {
        this.context = context;
        this.id_page = id_page;
        this.page_name = page_name;
    }


    //view pager functions
    @Override
    public int getCount()
    {
        return id_page.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    //init item's
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(id_page[position] , null , false);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
    }

    //tab title
    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        if(page_name != null)
        {
            return page_name[position];
        }

        return "";
    }
}
