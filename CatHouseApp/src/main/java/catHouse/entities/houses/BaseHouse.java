package catHouse.entities.houses;

import catHouse.common.ConstantMessages;
import catHouse.common.ExceptionMessages;
import catHouse.entities.cat.Cat;
import catHouse.entities.toys.Toy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class BaseHouse implements House {
    private String name;
    private int capacity;
    private Collection<Toy> toys;
    private Collection<Cat> cats;

    public BaseHouse(String name, int capacity) {
       setName(name);
       setCapacity(capacity);
       setCats(cats);
       setToys(toys);
    }

    @Override
    public int sumSoftness() {
        return toys.stream().mapToInt(Toy::getSoftness).sum();
    }

    @Override
    public void addCat(Cat cat) {
        if (cats.size() < capacity) {
            cats.add(cat);
        } else {
            throw new IllegalStateException(ConstantMessages.NOT_ENOUGH_CAPACITY_FOR_CAT);
        }
    }

    @Override
    public void removeCat(Cat cat) {
        cats.remove(cat);
    }

    @Override
    public void buyToy(Toy toy) {
        toys.add(toy);
    }

    @Override
    public void feeding() {
        for (Cat cat : cats) {
            cat.eating();
        }
    }

    @Override
    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s %s:", name, getClass().getSimpleName()));
        builder.append(System.lineSeparator());
        builder.append("Cats: ");
        if (cats.isEmpty()) {
            builder.append("none");
        } else {
            builder.append(cats.stream().map(Cat::getName).collect(Collectors.joining(" ")).trim());
        }
        builder.append(System.lineSeparator());
        builder.append(String.format("Toys: %d Softness: %d",toys.size(),sumSoftness()));
        builder.append(System.lineSeparator());
        return builder.toString();


    }

    @Override
    public String getName() {
        return name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setToys(Collection<Toy> toys) {
        this.toys = new ArrayList<>();
    }

    public void setCats(Collection<Cat> cats) {
        this.cats = new ArrayList<>();
    }

    @Override
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public Collection<Cat> getCats() {
        return cats;
    }

    @Override
    public Collection<Toy> getToys() {
        return toys;
    }
}
