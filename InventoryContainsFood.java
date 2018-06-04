package com.bravebots.bravethieving.Master_Farmer;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

//import path.to.your.NearDraynor
//import path.to.your.IsLevel99

/**
 * NOTES:
 * This is the root node.

Add children of this branch using the settings to the right.
 */

public class InventoryContainsFood extends BranchTask {


    public String[] getFoodString(){
        String[] Foods= new String[] {"Shark","Salmon","Trout","Monkfish","Anglerfish","Karambwan"};
        return Foods;
    }

    private NearDraynor neardraynor = new NearDraynor();
    private IsLevel99 islevel99 = new IsLevel99();

    @Override
    public boolean validate() {
        return (Inventory.containsAnyOf(getFoodString()));
    }

    @Override
    public TreeTask failureTask() {
        return islevel99;
    }

    @Override
    public TreeTask successTask() {
        return neardraynor;
    }
}
