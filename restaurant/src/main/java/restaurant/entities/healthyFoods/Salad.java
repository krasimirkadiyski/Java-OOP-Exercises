package restaurant.entities.healthyFoods;

public class Salad extends Food{
    private static final double InitialSaladPortion  = 105;
    public Salad(String name, double price) {
        super(name, InitialSaladPortion, price);
    }
}
