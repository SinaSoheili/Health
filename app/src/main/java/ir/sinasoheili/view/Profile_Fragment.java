package ir.sinasoheili.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

import presenter.Profile_Page_contract;
import presenter.Profile_Page_presenter;

public class Profile_Fragment extends Fragment implements Profile_Page_contract.Profile_Page_contract_view
{
    private Profile_Page_contract.Profile_Page_contract_presenter presenter;

    private View root_view;

    private TextView tv_name;
    private TextView tv_age;
    private TextView tv_height;
    private TextView tv_weight;

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
}
