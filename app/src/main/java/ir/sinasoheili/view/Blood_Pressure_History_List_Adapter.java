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

import model.BloodGlucose;
import model.BloodPressure;

public class Blood_Pressure_History_List_Adapter extends ArrayAdapter
{
    private ArrayList<BloodPressure> all_item;
    private Context context;

    public Blood_Pressure_History_List_Adapter(@NonNull Context context , @NonNull ArrayList<BloodPressure> objects)
    {
        super(context, R.layout.blood_pressure_history_list_item, objects);

        this.context = context;
        this.all_item = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        blood_pressure_history_view_holder vh;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.blood_pressure_history_list_item , null , false);
            vh = new blood_pressure_history_view_holder(convertView);
            convertView.setTag(vh);
        }
        else
        {
            vh = (blood_pressure_history_view_holder) convertView.getTag();
        }

        vh.fill(all_item.get(position));

        return convertView;
    }

    private class blood_pressure_history_view_holder
    {
        TextView tv_systolic , tv_diastolic , tv_day , tv_date;

        public blood_pressure_history_view_holder(View view)
        {
            tv_systolic  = view.findViewById(R.id.blood_pressure_history_list_item_tv_systolic);
            tv_diastolic = view.findViewById(R.id.blood_pressure_history_list_item_tv_diastolic);
            tv_day       = view.findViewById(R.id.blood_pressure_history_list_item_tv_day);
            tv_date      = view.findViewById(R.id.blood_pressure_history_list_item_tv_date);
        }

        public void fill(BloodPressure bp)
        {
            //empty tv
            tv_systolic.setText(" ");
            tv_diastolic.setText(" ");

            //fill text view's
            tv_systolic.setText("سیستولیک :"+bp.getSystolic());
            tv_diastolic.setText("دیاستولیک :"+bp.getDiastolic());
            tv_day.setText(bp.getDay());
            tv_date.setText(bp.getDate().getYear()+"/"+bp.getDate().getMonth());
        }
    }
}
