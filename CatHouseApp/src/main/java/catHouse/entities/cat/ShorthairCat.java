package catHouse.entities.cat;

public class ShorthairCat extends BaseCat{
    private static final int BASE_KILOGRAMS_OF_SHORTHAIR_CAT = 7;

    public ShorthairCat(String name, String breed, double price) {
        super(name, breed, price);
        super.setKilograms(BASE_KILOGRAMS_OF_SHORTHAIR_CAT);
    }

    @Override
    public void eating() {
        setKilograms(getKilograms() + 1);
    }
}
