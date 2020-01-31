package presenter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import java.util.ArrayList;

import model.DB_Medicine;
import model.Medicine;

public class Dashboard_Page_presenter implements Dashboard_page_contract.Dashboard_page_contract_presenter
{
    private Dashboard_page_contract.Dashboard_page_contract_view view_obj;
    private Context context;

    public Dashboard_Page_presenter(Fragment view_obj)
    {
        this.view_obj = (Dashboard_page_contract.Dashboard_page_contract_view) view_obj;
        this.context = view_obj.getContext();
    }

    @Override
    public ArrayList<Medicine> get_all_medicine()
    {
        DB_Medicine db = new DB_Medicine(context);
        ArrayList<Medicine> all_item = db.get_all();
        return all_item;
    }
}
