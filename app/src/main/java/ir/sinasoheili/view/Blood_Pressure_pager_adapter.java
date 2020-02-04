package ir.sinasoheili.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

public class Blood_Pressure_pager_adapter extends PagerAdapter
{
    private Context context;
    private int page_id[];
    private String page_name[];

    public Blood_Pressure_pager_adapter(Context context , int page_id[] , String page_name[])
    {
        this.context = context;
        this.page_id = page_id;
        this.page_name = page_name;
    }

    @Override
    public int getCount()
    {
        return page_id.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root_view = inflater.inflate(page_id[position] , null , false);
        container.addView(root_view);
        return root_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return page_name[position];
    }
}
