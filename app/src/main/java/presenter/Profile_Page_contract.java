package presenter;

public interface Profile_Page_contract
{
    public interface Profile_Page_contract_view
    {
        public void show_name(String name);
        public void show_age(float age);
        public void show_height(float height);
        public void show_weight(float weight);
    }

    public interface Profile_Page_contract_presenter
    {
        public void get_user_name();
        public void get_user_age();
        public void get_user_height();
        public void get_user_weight();
    }
}