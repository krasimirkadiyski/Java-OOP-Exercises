package catHouse.core;

import catHouse.common.ConstantMessages;
import catHouse.common.ExceptionMessages;
import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller {
    private ToyRepository toys;
    private Collection<House> houses;

    public ControllerImpl() {
        this.houses = new ArrayList<>();
        this.toys = new ToyRepository();
    }

    @Override
    public String addHouse(String type, String name) {
        House house;
        if (type.equals("ShortHouse")) {
            house = new ShortHouse(name);
            houses.add(house);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
        } else if (type.equals("LongHouse")) {
            house = new LongHouse(name);
            houses.add(house);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
        }
        throw new NullPointerException(ExceptionMessages.INVALID_HOUSE_TYPE);

    }

    @Override
    public String buyToy(String type) {
        Toy toy;
        if (type.equals("Ball")) {
            toy = new Ball();
            toys.buyToy(toy);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_TYPE, type);
        } else if (type.equals("Mouse")) {
            toy = new Mouse();
            toys.buyToy(toy);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_TYPE, type);
        }
            throw new IllegalArgumentException(ExceptionMessages.INVALID_TOY_TYPE);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        if (toys.findFirst(toyType) == null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_TOY_FOUND,toyType));
        }
        Toy searchedToy = toys.findFirst(toyType);
        toys.removeToy(searchedToy);
        for (House house : houses) {
            if (house.getName().equals(houseName)){
                house.getToys().add(searchedToy);
                break;
            }
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_IN_HOUSE,toyType,houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        Cat cat;
        if (catType.equals("ShorthairCat")){
            cat = new ShorthairCat(catName,catBreed,price);
        }else if (catType.equals("LonghairCat")){
            cat = new LonghairCat(catName,catBreed,price);
        }else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_CAT_TYPE);
        }
        if (catType.startsWith("Long")){
            for (House house : houses) {
               if (house.getName().equals(houseName)){
                    if (house.getClass().getSimpleName().startsWith("Long")){
                        house.addCat(cat);
                        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CAT_IN_HOUSE,catType,houseName);
                    }
                }
            }
        }
        if (catType.startsWith("Short")){
            for (House house : houses) {
                if (house.getName().equals(houseName)){
                    if (house.getClass().getSimpleName().startsWith("Short")){
                        house.addCat(cat);
                        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CAT_IN_HOUSE,catType,houseName);

                    }
                }
            }
        }
        return ConstantMessages.UNSUITABLE_HOUSE;

    }

    @Override
    public String feedingCat(String houseName) {
        int count = 0;
        for (House house : houses) {
            if (house.getName().equals(houseName)){
                house.feeding();
                count = house.getCats().size();
                }
            }
        return String.format(ConstantMessages.FEEDING_CAT,count);
    }

    @Override
    public String sumOfAll(String houseName) {
        double sumToysPrice = 0;
        double sumCatsPrice = 0;
        for (House house : houses) {
            if (house.getName().equals(houseName)){
                for (Toy toy : house.getToys()) {
                    sumToysPrice += toy.getPrice();
                }
                for (Cat cat : house.getCats()) {
                    sumCatsPrice += cat.getPrice();
                }
            }
        }
        double totalPrice = sumToysPrice + sumCatsPrice;
        return String.format(ConstantMessages.VALUE_HOUSE,houseName,totalPrice);
    }

    @Override
    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        for (House house : houses) {
            builder.append(house.getStatistics());
        }
        return builder.toString().trim();
    }
}
