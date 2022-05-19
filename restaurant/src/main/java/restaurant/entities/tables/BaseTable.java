package restaurant.entities.tables;

import restaurant.common.ExceptionMessages;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseTable implements Table {
    private Collection<HealthyFood> healthyFood;
    private Collection<Beverages> beverages;
    private int number;
    private int size;
    private int numberOfPeople;
    private double pricePerPerson;
    private boolean isReservedTable;
    private double allPeople;
    @Override
    public String tableInformation() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Table - %d",this.number));
        builder.append(System.lineSeparator());
        builder.append(String.format("Size - %d",this.size));
        builder.append(System.lineSeparator());
        builder.append(String.format("Type - %s",getClass().getSimpleName()));
        builder.append(System.lineSeparator());
        builder.append(String.format("All price - %f",this.pricePerPerson));
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    public BaseTable(int number, int size, double pricePerPerson) {
        this.number = number;
        this.setSize(size);
        this.pricePerPerson = pricePerPerson;
        this.healthyFood = new ArrayList<>();
        this.beverages = new ArrayList<>();
        this.isReservedTable = false;
    }

    public void setSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_TABLE_SIZE);
        }
        this.size = size;
    }

    @Override
    public int getTableNumber() {
        return this.number;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int numberOfPeople() {
        return this.numberOfPeople;
    }

    @Override
    public double pricePerPerson() {
        return this.pricePerPerson;
    }

    @Override
    public boolean isReservedTable() {
        return this.isReservedTable;
    }

    @Override
    public double allPeople() {
        return this.allPeople = this.numberOfPeople * pricePerPerson;
    }

    @Override
    public void reserve(int numberOfPeople) {
        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NUMBER_OF_PEOPLE);
        }
        this.numberOfPeople = numberOfPeople;
        this.isReservedTable = true;
    }

    @Override
    public void orderHealthy(HealthyFood food) {
        this.healthyFood.add(food);

    }

    @Override
    public void orderBeverages(Beverages beverages) {
        this.beverages.add(beverages);
    }

    @Override
    public double bill() {
        double sum = 0;
        Collection<Beverages> beverages = this.beverages;
        Collection<HealthyFood> healthyFood = this.healthyFood;
        for (Beverages beverage : beverages) {
            sum += beverage.getPrice();
        }

        for (HealthyFood food : healthyFood) {
            sum += food.getPrice();
        }
        sum += this.allPeople();
        return sum;
    }

    @Override
    public void clear() {
        this.beverages = new ArrayList<>();
        this.healthyFood = new ArrayList<>();
        this.numberOfPeople = 0;
        this.pricePerPerson = 0;
        this.isReservedTable = false;
        this.bill();

    }

}
