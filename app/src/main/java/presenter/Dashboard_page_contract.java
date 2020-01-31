package presenter;

import java.util.ArrayList;

import model.Illness;
import model.Medicine;

public interface Dashboard_page_contract
{
    public interface Dashboard_page_contract_view
    {
    }

    public interface Dashboard_page_contract_presenter
    {
        public ArrayList<Medicine> get_all_medicine();
        public ArrayList<Illness> get_all_illness();
    }
}
