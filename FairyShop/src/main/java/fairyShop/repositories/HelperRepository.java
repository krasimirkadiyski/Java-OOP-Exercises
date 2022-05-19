package fairyShop.repositories;

import fairyShop.models.Helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HelperRepository implements Repository<Helper>{
    private Collection<Helper> helpers;

    public HelperRepository() {
       helpers = new ArrayList<>();
    }

    @Override
    public Collection<Helper> getModels() {
        return Collections.unmodifiableCollection(helpers);
    }

    @Override
    public void add(Helper model) {
        helpers.add(model);
    }

    @Override
    public boolean remove(Helper model) {
      return helpers.remove(model);
    }

    @Override
    public Helper findByName(String name) {
        for (Helper helper : helpers) {
           if (helper.getName().equals(name)){
                return helper;
            }
        }
        return null;
    }
}
