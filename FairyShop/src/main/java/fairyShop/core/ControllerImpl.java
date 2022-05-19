package fairyShop.core;

import fairyShop.common.ConstantMessages;
import fairyShop.common.ExceptionMessages;
import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;

import java.lang.reflect.Constructor;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller{
    HelperRepository helperRepository = new HelperRepository();
    PresentRepository presentRepository = new PresentRepository();
    ShopImpl shop = new ShopImpl();
    @Override
    public String addHelper(String type, String helperName) {
        Helper currentHelper;
        if (type.equals("Happy")){
            currentHelper = new Happy(helperName);
            helperRepository.add(currentHelper);
            return String.format(ConstantMessages.ADDED_HELPER,type,helperName);
        }else if (type.equals("Sleepy")){
            currentHelper = new Sleepy(helperName);
            helperRepository.add(currentHelper);
            return String.format(ConstantMessages.ADDED_HELPER,type,helperName);
        }else{
            throw new IllegalArgumentException(ExceptionMessages.HELPER_TYPE_DOESNT_EXIST);
        }
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {
        InstrumentImpl currentInstrument = new InstrumentImpl(power);
        if (helperRepository.findByName(helperName) == null){
            throw new IllegalArgumentException(ExceptionMessages.HELPER_DOESNT_EXIST);
        }else{
            Helper currentHelper = helperRepository.findByName(helperName);
            currentHelper.addInstrument(currentInstrument);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER,power,helperName);
        }
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        PresentImpl currentPresent = new PresentImpl(presentName,energyRequired);
        presentRepository.add(currentPresent);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PRESENT,presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        String isDone;
        Helper readyHelper = null;
        Present searchedPresent = presentRepository.findByName(presentName);
        for (Helper currentHelper : helperRepository.getModels()) {
            if (currentHelper.getEnergy() > 50){
                readyHelper = currentHelper;
                break;
            }
        }if (readyHelper == null){
            throw new IllegalArgumentException(ExceptionMessages.NO_HELPER_READY);
        }

        shop.craft(searchedPresent,readyHelper);
        if (searchedPresent.isDone()){
            isDone = "done";
        }else{
            isDone = "not done";
        }
        return String.format("Present %s is %s. %d instrument/s have been broken while working on it!",presentName,isDone,readyHelper.getInstruments().stream().filter(instrument -> instrument.getPower() == 0).collect(Collectors.toList()).size());
    }

    @Override
    public String report() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%d presents are done!",shop.getCraftedPresents()));
        builder.append(System.lineSeparator());
        builder.append("Helpers info:");
        builder.append(System.lineSeparator());
        for (Helper helper : helperRepository.getModels()) {
            builder.append(String.format("Name: %s",helper.getName()));
            builder.append(System.lineSeparator());
            builder.append(String.format("Energy: %d",helper.getEnergy()));
            builder.append(System.lineSeparator());
            builder.append(String.format("Instruments: %d not broken left",helper.getInstruments().stream().filter(instrument -> instrument.getPower() > 0).collect(Collectors.toList()).size()));
            builder.append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
