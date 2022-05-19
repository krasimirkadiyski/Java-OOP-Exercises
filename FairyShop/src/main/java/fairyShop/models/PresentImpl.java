package fairyShop.models;

import fairyShop.common.ExceptionMessages;

public class PresentImpl implements Present {
    private String name;
    private int energyRequired;

    public PresentImpl(String name, int energyRequired) {
        setName(name);
        setEnergyRequired(energyRequired);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.PRESENT_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public void setEnergyRequired(int energyRequired) {
        if (energyRequired < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INSTRUMENT_POWER_LESS_THAN_ZERO);
        }
        this.energyRequired = energyRequired;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getEnergyRequired() {
        return this.energyRequired;
    }

    @Override
    public boolean isDone() {
        if (energyRequired == 0){
            return true;
        }
            return false;
    }

    @Override
    public void getCrafted() {
        if (this.energyRequired - 10 >= 0){
            this.energyRequired -= 10;
        }else{
            this.energyRequired = 0;
        }
    }
}
