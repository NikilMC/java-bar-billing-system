package application;

import java.util.ArrayList;
import java.util.List;

public class BottleInventory {
    private List<Bottle> bottles;
    
    public BottleInventory() {
        bottles = new ArrayList<>();
        initializeBottles();
    }
    private void initializeBottles() {
        bottles.add(new OldMonkRum7Year());
        bottles.add(new MagicMomentsVodka());
        bottles.add(new AntiquityBlueUltraPremiumWhisky());
        bottles.add(new PaulJohnChristmasEdition2019());
        bottles.add(new AmrutSingleMalt());
        bottles.add(new OldMonkSupremeRum());
        bottles.add(new AmrutTwoIndiesRum());
        bottles.add(new RampurIndianSingleMaltWhiskey());
        bottles.add(new PaulJohnOlorosoSelectCask());
        bottles.add(new JaisalmerIndianCraftGin());
        bottles.add(new AmrutOldPortRum());
        bottles.add(new PaulJohnChristmasEdition2020());
        bottles.add(new PaulJohnKanyaZodiacSeries());
        bottles.add(new PaulJohnBrilliance());
        bottles.add(new PorfidioMescal());
        bottles.add(new AmrutRyeIndianSingleMaltWhisky());
    }
    public List<Bottle> getAllBottles() {
        return bottles;
    }
}