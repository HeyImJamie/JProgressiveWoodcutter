package scripts.JProgressiveWoodcutter.data;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import scripts.JProgressiveWoodcutter.JProgressiveWoodcutter;

import java.util.HashMap;

public enum TreeInfo {

    NORMAL(1, "Tree", new RSArea(new RSTile(3173, 3374, 0), new RSTile(3156, 3402, 0))),
    OAK(15, "Oak", new RSArea(new RSTile(3171, 3421, 0), new RSTile(3158, 3410, 0))),
    WILLOW(30, "Willow", new RSArea(new RSTile(3082, 3238, 0), new RSTile(3091, 3225, 0))),
    YEW(60, "Yew", new RSArea(new RSTile(3203, 3506, 0), new RSTile(3224, 3498, 0)));

    int levelRequired;
    String treeName;
    RSArea treeArea;

    TreeInfo(int levelRequired, String treeName, RSArea treeArea) {
        this.levelRequired = levelRequired;
        this.treeName = treeName;
        this.treeArea = treeArea;
    }

    public static String getTreeName() {
        int woodcuttingLevel = Skills.getActualLevel(Skills.SKILLS.WOODCUTTING);
        return woodcuttingLevel < 15 ? NORMAL.treeName : woodcuttingLevel < 30 ? OAK.treeName : woodcuttingLevel < 60 ? WILLOW.treeName : YEW.treeName;
    }

    public static RSArea getTreeArea() {
        int woodcuttingLevel = Skills.getActualLevel(Skills.SKILLS.WOODCUTTING);
        return woodcuttingLevel < 15 ? NORMAL.treeArea : woodcuttingLevel < 30 ? OAK.treeArea : woodcuttingLevel < 60 ? WILLOW.treeArea : YEW.treeArea;
    }

    public static String getBestAxe() {
            HashMap<String, Integer> cache = Vars.get().bankCache;
            int woodcuttingLevel = Skills.getActualLevel(Skills.SKILLS.WOODCUTTING);

            if (cache != null) {
                if (woodcuttingLevel >= 41 && (getInventoryAxe().equals("Rune axe") || cache.containsKey("Rune axe")))
                    return "Rune axe";

                if (woodcuttingLevel >= 31 && (getInventoryAxe().equals("Adamant axe") || cache.containsKey("Adamant axe")))
                    return "Adamant axe";

                if (woodcuttingLevel >= 21 && (getInventoryAxe().equals("Mithril axe") || cache.containsKey("Mithril axe")))
                    return "Mithril axe";

                if (woodcuttingLevel >= 6 && (getInventoryAxe().equals("Steel axe") || cache.containsKey("Steel axe")))
                    return "Steel axe";

                return getInventoryAxe();

            }

            return getInventoryAxe();

    }

    public static String getInventoryAxe(){
        RSItem[] axes = Inventory.find(Filters.Items.nameContains(" axe"));
        if (axes.length == 0)
            JProgressiveWoodcutter.stopScript("No axe found. Please start the script with an axe in your inventory.");

        if (axes[0].getDefinition() == null)
            JProgressiveWoodcutter.stopScript("Error loading axe information.");

        return axes[0].getDefinition().getName();
    }

}
