package ir.sinasoheili.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

import presenter.Profile_Page_contract;
import presenter.Profile_Page_presenter;

public class Profile_Fragment extends Fragment implements Profile_Page_contract.Profile_Page_contract_view, View.OnClickListener
{
    private Profile_Page_contract.Profile_Page_contract_presenter presenter;

    private View root_view;

    private TextView tv_name;
    private TextView tv_age;
    private TextView tv_height;
    private TextView tv_weight;

    private Button btn_change_font;
    private Button btn_change_theme;
    private Button btn_bug_report;
    private Button btn_contact_us;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        root_view = inflater.inflate(R.layout.profile_page_layout, null , false);

        init_obj();

        presenter.get_user_name();
        presenter.get_user_age();
        presenter.get_user_height();
        presenter.get_user_weight();

        return root_view;
    }

    private void init_obj()
    {
        presenter = new Profile_Page_presenter(root_view.getContext() , Profile_Fragment.this);

        tv_name = root_view.findViewById(R.id.profile_tv_name);
        tv_age = root_view.findViewById(R.id.profile_tv_age);
        tv_height = root_view.findViewById(R.id.profile_tv_height);
        tv_weight = root_view.findViewById(R.id.profile_tv_weight);

        btn_change_font = root_view.findViewById(R.id.profile_btn_change_font);
        btn_change_font.setOnClickListener(this);

        btn_change_theme = root_view.findViewById(R.id.profile_btn_change_theme);
        btn_change_theme.setOnClickListener(this);

        btn_bug_report = root_view.findViewById(R.id.profile_btn_bug_reporting);
        btn_bug_report.setOnClickListener(this);

        btn_contact_us = root_view.findViewById(R.id.profile_btn_contact_us);
        btn_contact_us.setOnClickListener(this);
    }

    @Override
    public void show_name(String name)
    {
        if(name != null)
        {
            tv_name.setText("نام : "+name);
        }
        else
        {
            tv_name.setText("نام : ");
        }
    }

    @Override
    public void show_age(float age)
    {
        if(age != 0)
        {
            tv_age.setText("سن : "+age);
        }
        else
        {
            tv_age.setText("سن : ");
        }

    }

    @Override
    public void show_height(float height)
    {
        if(height != 0)
        {
            tv_height.setText("قد : "+height);
        }
        else
        {
            tv_height.setText("قد : ");
        }
    }

    @Override
    public void show_weight(float weight)
    {
        if(weight != 0)
        {
            tv_weight.setText("وزن : "+weight);
        }
        else
        {
            tv_weight.setText("وزن : ");
        }
    }

    private void temp_dialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(root_view.getContext());
        dialog.setMessage("این قسمت با عنوان good first issues به جهت سادگی برای مشارکت با دیگر برنامه نویس ها در github  و gitlab  قرار گرفته است");
        dialog.setNegativeButton("باشه", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(btn_change_font))
        {
            temp_dialog();
        }
        else if(v.equals(btn_change_theme))
        {
            temp_dialog();
        }
        else if(v.equals(btn_bug_report))
        {
            temp_dialog();
        }
        else if(v.equals(btn_contact_us))
        {
            temp_dialog();
        }
    }
}
