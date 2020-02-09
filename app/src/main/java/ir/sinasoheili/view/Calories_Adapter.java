package ir.sinasoheili.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import model.Calories;

public class Calories_Adapter extends ArrayAdapter<Calories>
{
    private Context context;
    private ArrayList<Calories> items;

    public Calories_Adapter(@NonNull Context context , @NonNull ArrayList<Calories> objects)
    {
        super(context, R.layout.calories_list_item , objects);

        this.context = context;
        items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        CaloriesList_ViewHolder viewHolder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calories_list_item , null , false);
            viewHolder = new CaloriesList_ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (CaloriesList_ViewHolder) convertView.getTag();
        }

        viewHolder.fill(items.get(position));

        return convertView;
    }

    public class CaloriesList_ViewHolder
    {
        private TextView tv_name;
        private TextView tv_amount;
        private TextView tv_calories;

        public CaloriesList_ViewHolder(View view)
        {
            tv_name = view.findViewById(R.id.Calories_List_item_name);
            tv_amount = view.findViewById(R.id.Calories_List_item_amount);
            tv_calories = view.findViewById(R.id.Calories_List_item_calories);
        }

        public void fill(Calories cal)
        {
            tv_name.setText("نام : "+cal.getName());
            tv_amount.setText("مقدار : "+cal.getAmount());

            String c = cal.getCalories().substring(0 , cal.getCalories().indexOf("."));
            c = c.replace("$" , "");
            tv_calories.setText("کالری : "+c);
        }
    }
}
