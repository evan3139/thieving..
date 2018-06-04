package com.bravebots.bravethieving.Master_Farmer;

import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.framework.tree.TreeBot;
import com.runemate.game.api.script.framework.tree.TreeTask;
import com.bravebots.bravethieving.Master_Farmer.InventoryContainsFood;






public class Start extends TreeBot {



    @Override
    public TreeTask createRootTask() {
        return new InventoryContainsFood();

        }



    }
