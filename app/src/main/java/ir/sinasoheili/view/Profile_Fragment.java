package ir.sinasoheili.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

import presenter.Profile_Page_contract;
import presenter.Profile_Page_presenter;

import static android.app.Activity.RESULT_OK;

public class Profile_Fragment extends Fragment implements Profile_Page_contract.Profile_Page_contract_view, View.OnClickListener
{
    private Profile_Page_contract.Profile_Page_contract_presenter presenter;

    private View root_view;
    private ImageView iv_avatar;
    private ImageView iv_add_avatar;

    private TextView tv_name;
    private TextView tv_age;
    private TextView tv_height;
    private TextView tv_weight;

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
        set_avatar();

        return root_view;
    }

    private void init_obj()
    {
        presenter = new Profile_Page_presenter(root_view.getContext() , Profile_Fragment.this);

        iv_avatar = root_view.findViewById(R.id.profile_imageview_avatar);

        tv_name = root_view.findViewById(R.id.profile_tv_name);
        tv_age = root_view.findViewById(R.id.profile_tv_age);
        tv_height = root_view.findViewById(R.id.profile_tv_height);
        tv_weight = root_view.findViewById(R.id.profile_tv_weight);

        btn_contact_us = root_view.findViewById(R.id.profile_btn_contact_us);
        btn_contact_us.setOnClickListener(this);

        iv_add_avatar = root_view.findViewById(R.id.profile_imageview_add_avatar);
        iv_add_avatar.setOnClickListener(this);
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

    private void show_contact_dialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(root_view.getContext());
        dialog.setMessage("برای ارتباط با ما و یا گزارش مشکل لطفا از ایمیل زیر استفاده کنید .");

        dialog.setPositiveButton("ارسال ایمیل", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Intent.ACTION_SENDTO , Uri.parse("mailto:sinasoheili79@gmail.com"));
                startActivity(intent);
            }
        });

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
        if(v.equals(btn_contact_us))
        {
            show_contact_dialog();
        }
        else if(v.equals(iv_add_avatar))
        {
            change_avatar();
        }
    }

    private void change_avatar()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent , "SELECT AVATAR") , 100); // 100 is my request code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if((requestCode == 100) && (resultCode == RESULT_OK))
        {
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver() , data.getData());
                write_bitmap_to_file(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            set_avatar();
        }
    }

    private void set_avatar()
    {
        Bitmap bitmap = read_from_file();
        if(bitmap == null)
        {
            iv_avatar.setImageResource(R.drawable.person);
        }
        else
        {
            iv_avatar.setImageBitmap(bitmap);
        }
    }

    private boolean write_bitmap_to_file(Bitmap bitmap)
    {
        File file = new File(getContext().getFilesDir().getAbsolutePath()+"/"+"bitmap_file.png");
        if(! file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            return bitmap.compress(Bitmap.CompressFormat.PNG , 100 , new FileOutputStream(file));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    private Bitmap read_from_file()
    {
        try
        {
            File file = new File(getContext().getFilesDir().getAbsolutePath()+"/"+"bitmap_file.png");
            return BitmapFactory.decodeStream(new FileInputStream(file));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
