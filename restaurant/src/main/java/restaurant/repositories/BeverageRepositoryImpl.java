package restaurant.repositories;

import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.repositories.interfaces.BeverageRepository;

import java.util.*;

public class BeverageRepositoryImpl implements BeverageRepository<Beverages> {
    private Collection<Beverages> entities;

    public BeverageRepositoryImpl() {
        this.entities = new ArrayList<>();
    }

    @Override
    public Beverages beverageByName(String drinkName, String drinkBrand) {
        for (Beverages entity : entities) {
            if (entity.getName().equals(drinkName) && entity.getBrand().equals(drinkBrand)){
                return entity;
            }
        }
        return null;
    }

    @Override
    public Collection<Beverages> getAllEntities() {
        return Collections.unmodifiableCollection(entities);
    }

    @Override
    public void add(Beverages entity) {
        entities.add(entity);
    }
}
