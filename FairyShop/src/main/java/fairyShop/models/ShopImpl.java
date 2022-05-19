package fairyShop.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopImpl implements Shop {
private int brokenInstrumentsCounter;
private int craftedPresents;

    public int getBrokenInstrumentsCounter() {
        return brokenInstrumentsCounter;
    }

    public int getCraftedPresents() {
        return craftedPresents;
    }

    @Override
    public void craft(Present present, Helper helper) {
        while (true) {
            helper.work();
            present.getCrafted();
            List<Instrument> all = new ArrayList<>(helper.getInstruments());
            all = all.stream().filter(instrument -> instrument.getPower() > 0).collect(Collectors.toList());
            if (all.size() == 0){
                break;
            }
            Instrument current = all.get(0);
            current.use();
            if (present.isDone() || all.size() == 0){
                craftedPresents++;
                break;
            }
        }

    }
}

