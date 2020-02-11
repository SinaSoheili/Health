package ir.sinasoheili.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class IntorSlider_Adapter extends PagerAdapter
{
    private int item_id[];
    private Context context;

    public IntorSlider_Adapter(Context context , int item_id[])
    {
        this.context = context;
        this.item_id = item_id;
    }

    @Override
    public int getCount()
    {
        return item_id.length;
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
        View v = inflater.inflate(item_id[position] , container , false);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View)object);
    }
}
