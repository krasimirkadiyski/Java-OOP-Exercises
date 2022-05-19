package catHouse.entities.cat;

public class LonghairCat extends BaseCat{
    private static final int BASE_KILOGRAMS_OF_LONGHAIR_CAT = 9;

    public LonghairCat(String name, String breed, double price) {
        super(name, breed, price);
        super.setKilograms(BASE_KILOGRAMS_OF_LONGHAIR_CAT);
    }

    @Override
    public void eating() {
        setKilograms(getKilograms() + 3);
    }
}
