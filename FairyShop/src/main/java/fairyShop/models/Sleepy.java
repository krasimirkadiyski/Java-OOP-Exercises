package fairyShop.models;

public class Sleepy extends BaseHelper{
    private static final int ENERGY_OF_SLEEPY_ELF = 50;

    public Sleepy(String name) {
        super(name, ENERGY_OF_SLEEPY_ELF);
    }

    @Override
    public void work() {
        if (super.getEnergy() - 5 >= 0){
            super.setEnergy(super.getEnergy() - 5);
        }else{
            super.setEnergy(0);
        }
    }
}
