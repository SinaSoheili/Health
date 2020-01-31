package ir.sinasoheili.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import model.Illness;

public class Illness_Activivty_Item extends AppCompatActivity
{
    private Illness currentIllness;

    private TextView tv_name_fa;
    private TextView tv_name_en;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illness__activivty__item);

        init_obj();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            if(bundle.containsKey(Illness_List_Activity.ILLNESS_KEY_INTENT))
            {
                currentIllness = (Illness) bundle.get(Illness_List_Activity.ILLNESS_KEY_INTENT);
                show_content();
            }
        }
    }

    private void init_obj()
    {
        tv_name_fa = findViewById(R.id.Illness_Activity_tv_name_fa);
        tv_name_en = findViewById(R.id.Illness_Activity_tv_name_en);
        tv_content = findViewById(R.id.Illness_Activity_tv_content);
    }

    private void show_content()
    {
        tv_name_fa.setText(currentIllness.getName_fa());
        tv_name_en.setText(currentIllness.getName_en());
        tv_content.setText(currentIllness.getContent());
    }
}
