package ir.sinasoheili.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import model.BloodGlucose;

public class Blood_Glucose_History_List_Adapter extends ArrayAdapter
{
    private ArrayList<BloodGlucose> all_item;
    private Context context;

    public Blood_Glucose_History_List_Adapter(@NonNull Context context , @NonNull ArrayList<BloodGlucose> objects)
    {
        super(context, R.layout.blood_glucose_history_list_item, objects);

        this.context = context;
        this.all_item = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        blood_glucose_history_view_holder vh;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.blood_glucose_history_list_item , null , false);
            vh = new blood_glucose_history_view_holder(convertView);
            convertView.setTag(vh);
        }
        else
        {
            vh = (blood_glucose_history_view_holder) convertView.getTag();
        }

        vh.fill(all_item.get(position));

        return convertView;
    }

    private class blood_glucose_history_view_holder
    {
        TextView tv_blood_glucose_value , tv_insuline_name , tv_insuline_unit_count , tv_day , tv_time , tv_date;

        public blood_glucose_history_view_holder(View view)
        {
            tv_blood_glucose_value = view.findViewById(R.id.blood_glucose_history_list_item_tv_blood_glucose_value);
            tv_insuline_name = view.findViewById(R.id.blood_glucose_history_list_item_tv_insulin_name);
            tv_insuline_unit_count = view.findViewById(R.id.blood_glucose_history_list_item_tv_insuline_unit_count);
            tv_day = view.findViewById(R.id.blood_glucose_history_list_item_tv_day);
            tv_time = view.findViewById(R.id.blood_glucose_history_list_item_tv_time);
            tv_date = view.findViewById(R.id.blood_glucose_history_list_item_tv_date);
        }

        public void fill(BloodGlucose bg)
        {
            //empty tv
            tv_blood_glucose_value.setText(" ");
            tv_insuline_name.setText(" ");
            tv_insuline_unit_count.setText(" ");

            //fill text view's
            tv_blood_glucose_value.append("مقدار قند خون :" + bg.getBlood_glucose_value());
            tv_insuline_name.append("نام انسولین :" + bg.getInsulin_name());
            tv_insuline_unit_count.append("مقدار انسولین :" + bg.getInsuline_unit_count());
            tv_day.setText(bg.getDay());
            tv_time.setText(bg.getTime());
            tv_date.setText(bg.getDate().getYear()+"/"+bg.getDate().getMonth());
        }
    }
}
