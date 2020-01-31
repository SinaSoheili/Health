package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import model.Medicine;

public class Medicine_Activity_Item extends AppCompatActivity
{
    private Medicine current_medicine;

    private TextView tv_name_farsi;
    private TextView tv_name_tejary;
    private TextView tv_shekl_darooii;
    private TextView tv_masraf_hamelegi;
    private TextView tv_mavard_masraf;
    private TextView tv_mizan_masraf;
    private TextView tv_mavard_mane;
    private TextView tv_avarez_janebi;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine___item);

        init_obj();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            if(bundle.containsKey(Medicine_List_Activity.MEDICINE_KEY_INTENT))
            {
                current_medicine = (Medicine) bundle.get(Medicine_List_Activity.MEDICINE_KEY_INTENT);
                Log.i("tag" , current_medicine.toString());
                show_content();
            }
        }
    }

    private void init_obj()
    {
        tv_name_farsi       = findViewById(R.id.Medicine_Activity_tv_name_farsi);
        tv_name_tejary      = findViewById(R.id.Medicine_Activity_tv_name_tejary);
        tv_shekl_darooii    = findViewById(R.id.Medicine_Activity_tv_shekl_daroei);
        tv_masraf_hamelegi  = findViewById(R.id.Medicine_Activity_tv_masraf_hamelegy);
        tv_mavard_masraf    = findViewById(R.id.Medicine_Activity_tv_mavared_masraf);
        tv_mizan_masraf     = findViewById(R.id.Medicine_Activity_tv_mizan_masraf);
        tv_mavard_mane      = findViewById(R.id.Medicine_Activity_tv_mavared_mane);
        tv_avarez_janebi    = findViewById(R.id.Medicine_Activity_tv_avarez_janebi);
    }

    private void show_content()
    {
        tv_name_farsi       .setText(current_medicine.getName_farsi());
        tv_name_tejary      .setText(current_medicine.getName_tejary());
        tv_shekl_darooii    .append(current_medicine.getShekl_daroei());
        tv_masraf_hamelegi  .append(current_medicine.getMasraf_hamelegi());
        tv_mavard_masraf    .append("\n"+current_medicine.getMavared_masraf());
        tv_mizan_masraf     .append("\n"+current_medicine.getMizan_masraf());
        tv_mavard_mane      .append("\n"+current_medicine.getMavared_mane());
        tv_avarez_janebi    .append("\n"+current_medicine.getAvarez_janebi());
    }
}
