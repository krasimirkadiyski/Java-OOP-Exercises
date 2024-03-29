package restaurant.entities.tables;

import restaurant.entities.tables.BaseTable;

public class Indoors extends BaseTable {
    private static final double PRICE_PER_PERSON = 3.50;
    public Indoors(int number, int size) {
        super(number, size, PRICE_PER_PERSON);
    }

    public Indoors(int number, int size, double pricePerPerson) {
        super(number, size, pricePerPerson);
    }

}
