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

import model.MedicationSchedule;

public class MedicationSchedule_List_Adapter extends ArrayAdapter<MedicationSchedule>
{
    private ArrayList<MedicationSchedule> items;
    private Context context;

    public MedicationSchedule_List_Adapter(@NonNull Context context , @NonNull ArrayList<MedicationSchedule> objects)
    {
        super(context , R.layout.medication_schedule_list_item , objects);
        this.context = context;
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        MedicationSchedule_ViewHolder vh;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medication_schedule_list_item , null , false);
            vh = new MedicationSchedule_ViewHolder(convertView);
            convertView.setTag(vh);
        }
        else
        {
            vh = (MedicationSchedule_ViewHolder) convertView.getTag();
        }


        vh.fill(items.get(position));
        return convertView;
    }

    public class MedicationSchedule_ViewHolder
    {
        private TextView tv_name , tv_amount , tv_time;

        public MedicationSchedule_ViewHolder(View v)
        {
            tv_name = v.findViewById(R.id.MedicationSchedule_ListItem_name);
            tv_amount = v.findViewById(R.id.MedicationSchedule_ListItem_amount);
            tv_time = v.findViewById(R.id.MedicationSchedule_ListItem_time);
        }

        public void fill(MedicationSchedule item)
        {
            tv_name.setText(item.getMedicine_name());
            tv_amount.setText(item.getMedicine_amount());
            tv_time.setText(item.getTime());
        }
    }
}