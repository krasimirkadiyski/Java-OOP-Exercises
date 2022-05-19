package fairyShop.models;

import fairyShop.common.ExceptionMessages;

public class InstrumentImpl implements Instrument{
    private int power;

    public InstrumentImpl(int power) {
        setPower(power);
    }

    public void setPower(int power) {
        if (power < 0){
            throw new IllegalArgumentException(ExceptionMessages.PRESENT_ENERGY_LESS_THAN_ZERO);
        }
        this.power = power;
    }

    @Override
    public int getPower() {
        return this.power;
    }

    @Override
    public void use() {
        if (this.power - 10 >= 0){
            this.power -= 10;
        }else{
            this.power = 0;
        }
    }

    @Override
    public boolean isBroken() {
        if (power == 0){
            return true;
        }
        return false;
    }
}
