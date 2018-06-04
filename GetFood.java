package com.bravebots.bravethieving.Master_Farmer;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * 
 */
public class GetFood extends LeafTask {

    GameObject BankChest = GameObjects.newQuery().names("Bank booth").actions("Bank").results().nearestTo(Players.getLocal());
    Area BankArea = new Area.Rectangular(new Coordinate(3092, 3240, 0), new Coordinate(3097, 3246, 0));


    @Override
    public void execute() {
        Area DraynorBank = new Area.Rectangular(new Coordinate(3092, 3240, 0), new Coordinate(3097, 3246, 0));
        GameObject BankChest = GameObjects.newQuery()
                .names("Bank booth").actions("Bank").results().first();
        if (BankChest != null && !BankChest.isVisible()) {
            WebPath ToBank = Traversal.getDefaultWeb().getPathBuilder().buildTo(DraynorBank);
            ToBank.step(Path.TraversalOption.MANAGE_DISTANCE_BETWEEN_STEPS);
            Camera.turnTo(BankChest);
        }
        if (BankChest.isVisible() && !Bank.isOpen()) {
            if (BankChest.interact("Bank")) {
                Execution.delayUntil(() -> Bank.isOpen(), 100, 1400);
            }
        }
        if (Bank.isOpen()) {
            if (Bank.withdraw("Shark", 15)) {
                Execution.delayUntil(() -> Inventory.contains("Shark"), 100, 1500);
                Bank.close();
            }
        }

    }


}