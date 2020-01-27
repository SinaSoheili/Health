package model;

public class Calories
{
    private int id;
    private String name;
    private String amount;
    private String calories;

    //constructor
    public Calories(int id, String name, String amount, String calories)
    {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.calories = calories;
    }

    //getter
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getAmount()
    {
        return amount;
    }

    public String getCalories()
    {
        return calories;
    }

    //setter
    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public void setCalories(String calories)
    {
        this.calories = calories;
    }

    //to string
    @Override
    public String toString()
    {
        return "Calories{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                ", calories='" + calories + '\'' +
                '}';
    }
}
