package restaurant.core;

import restaurant.common.ExceptionMessages;
import restaurant.common.OutputMessages;
import restaurant.core.interfaces.Controller;
import restaurant.entities.drinks.Fresh;
import restaurant.entities.drinks.Smoothie;
import restaurant.entities.healthyFoods.Salad;
import restaurant.entities.healthyFoods.VeganBiscuits;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.tables.Indoors;
import restaurant.entities.tables.InGarden;
import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.*;

import java.util.Collection;

public class ControllerImpl implements Controller {
    private HealthFoodRepository<HealthyFood> healthFoodRepository;
    private BeverageRepository<Beverages> beverageRepository;
    private TableRepository<Table> tableRepository;
    private double sumOfBills;


    public ControllerImpl(HealthFoodRepository<HealthyFood> healthFoodRepository, BeverageRepository<Beverages> beverageRepository, TableRepository<Table> tableRepository) {
        this.healthFoodRepository = healthFoodRepository;
        this.beverageRepository = beverageRepository;
        this.tableRepository = tableRepository;
    }

    @Override
    public String addHealthyFood(String type, double price, String name) {
        HealthyFood healthyFood;

        switch (type) {
            case "Salad":
                healthyFood = new Salad(name, price);
                if (healthFoodRepository.foodByName(healthyFood.getName()) != null) {
                    throw new IllegalArgumentException(String.format(ExceptionMessages.FOOD_EXIST, name));
                }
                healthFoodRepository.add(healthyFood);
                break;
            case "VeganBiscuits":
                healthyFood = new VeganBiscuits(name, price);
                if (healthFoodRepository.foodByName(healthyFood.getName()) != null) {
                    throw new IllegalArgumentException(String.format(ExceptionMessages.FOOD_EXIST, name));
                }
                healthFoodRepository.add(healthyFood);
                break;
        }
        return String.format(OutputMessages.FOOD_ADDED, name);
    }

    @Override
    public String addBeverage(String type, int counter, String brand, String name){
        Beverages beverages;
        switch (type) {
            case "Fresh":
                beverages = new Fresh(name, counter, brand);
                if (beverageRepository.beverageByName(name, brand) != null) {
                    throw new IllegalArgumentException(String.format(ExceptionMessages.BEVERAGE_EXIST, name));
                }
                beverageRepository.add(beverages);
                break;
            case "Smoothie":
                beverages = new Smoothie(name, counter, brand);
            if (beverageRepository.beverageByName(name, brand) != null) {
                    throw new IllegalArgumentException(String.format(ExceptionMessages.BEVERAGE_EXIST, name));
                }
        beverageRepository.add(beverages);
                break;
        }
        return String.format(OutputMessages.BEVERAGE_ADDED, type, brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table table;
        switch (type) {
            case "Indoors":
                table = new Indoors(tableNumber, capacity);
                if (tableRepository.byNumber(tableNumber) != null) {
                    throw new IllegalArgumentException(String.format(ExceptionMessages.TABLE_IS_ALREADY_ADDED, tableNumber));
                }
                tableRepository.add(table);
                break;
            case "InGarden":
                table = new InGarden(tableNumber, capacity);
                if (tableRepository.byNumber(tableNumber) != null) {
                    throw new IllegalArgumentException(String.format(ExceptionMessages.TABLE_IS_ALREADY_ADDED, tableNumber));
                }
                tableRepository.add(table);
                break;
        }
        return String.format(OutputMessages.TABLE_ADDED, tableNumber);
    }

    @Override
    public String reserve(int numberOfPeople) {
        Collection<Table> allTables = tableRepository.getAllEntities();

        boolean isReserved = false;
        int tableNum = 0;
        for (Table table : allTables) {
            if (!table.isReservedTable() && table.getSize() >= numberOfPeople) {
                table.reserve(numberOfPeople);
                tableNum = table.getTableNumber();
                isReserved = true;
                break;
            }
        }

        if (!isReserved) {
           return String.format(OutputMessages.RESERVATION_NOT_POSSIBLE, numberOfPeople);
        }
        return String.format(OutputMessages.TABLE_RESERVED,tableNum,  numberOfPeople);
    }

    @Override
    public String orderHealthyFood(int tableNumber, String healthyFoodName) {
        if (tableRepository.byNumber(tableNumber) == null) {
            return String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
        }else if (healthFoodRepository.foodByName(healthyFoodName) == null) {
            return String.format(OutputMessages.NONE_EXISTENT_FOOD, healthyFoodName);
        }

        HealthyFood healthyFood = healthFoodRepository.foodByName(healthyFoodName);
        tableRepository.byNumber(tableNumber).orderHealthy(healthyFood);

        return String.format(OutputMessages.FOOD_ORDER_SUCCESSFUL, healthyFoodName, tableNumber);
    }

    @Override
    public String orderBeverage(int tableNumber, String name, String brand) {
        if (tableRepository.byNumber(tableNumber) == null) {
            return String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
        }else if (beverageRepository.beverageByName(name, brand) == null) {
            return String.format(OutputMessages.NON_EXISTENT_DRINK, name, brand);
        }

        Beverages beverages = beverageRepository.beverageByName(name, brand);
        tableRepository.byNumber(tableNumber).orderBeverages(beverages);

        return String.format(OutputMessages.BEVERAGE_ORDER_SUCCESSFUL, name, tableNumber);
    }

    @Override
    public String closedBill(int tableNumber) {

        Table table = tableRepository.byNumber(tableNumber);
        double bill = table.bill();
        table.clear();
        sumOfBills += bill;

        return String.format(OutputMessages.BILL, tableNumber, bill);
    }


    @Override
    public String totalMoney() {
        return String.format(OutputMessages.TOTAL_MONEY, this.sumOfBills);
    }
}
