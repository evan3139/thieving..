package com.bravebots.bravethieving.Master_Farmer;


import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Health;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Distance;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

import java.awt.event.KeyEvent;
import java.util.List;


/**
 * NOTES:
 * 
 */
public class Steal extends LeafTask {

    Area Draynor = new Area.Rectangular(new Coordinate(3075,3244,0), new Coordinate(3086,3255,0));
    Npc MasterFarmer = Npcs.newQuery().names("Master Farmer").actions("Pickpocket").reachable().results().nearestTo(Players.getLocal());
    Player c = Players.getLocal();
    SpriteItem Shark = Inventory.newQuery().names("Shark").results().first();

    public void HealthCheckAndPickPocket(){
        int Eat = Random.nextInt(12,15);
        if(MasterFarmer != null && c!= null)
        {
            if(MasterFarmer.isValid())
            {
                if(!MasterFarmer.isVisible())
                {
                    Camera.turnTo(MasterFarmer);
                    Execution.delayWhile(() -> !MasterFarmer.isVisible(), 100,2000);
                }
                else
                {
                    if(MasterFarmer.interact("Pickpocket"))
                    {
                        Execution.delayUntil(() -> Health.getCurrent() <= Eat || !MasterFarmer.isVisible() || Inventory.isFull(), 1000,2000);
                    }
                    if(Health.getCurrent() <= Eat)
                    {
                        if(Shark != null && Shark.interact("Eat"))
                        {
                            Execution.delayUntil(() -> Health.getCurrent() > Eat,200,800);
                        }
                    }
                }
            }
        }
    }

    public void InventoryIsFull()
    {

        if(!Keyboard.isPressed(KeyEvent.VK_SHIFT))
        {
            Keyboard.pressKey(KeyEvent.VK_SHIFT);
        }
        List<SpriteItem> listOfItems = Inventory.getItems().asList();
        if(Inventory.isFull())
        {
            //Put this in a loop so itll drop multiple times not missing anything in the inventory if it does the first time
            for(int i = 0; i < 3; i++)
            listOfItems.forEach(a ->{
                ItemDefinition def = a.getDefinition();
                     if(def != null && !def.getName().equals("Shark") && !def.getName().equals("Ranarr seed") && !def.getName().equals("Torstol seed") && !def.getName().equals("Toadflax seed")
                             && !def.getName().equals("Snapdragon seed") && !def.getName().equals("Lantadyme seed") && !def.getName().equals("Kwuarm seed"))
                        a.click();
            });
            if (Keyboard.isPressed(KeyEvent.VK_SHIFT))
            {
                Keyboard.releaseKey(KeyEvent.VK_SHIFT);
            }
        }

    }




    @Override
    public void execute() {
        if(Distance.between(Players.getLocal(),Draynor) > 10)
        {
            WebPath ToDraynor = Traversal.getDefaultWeb().getPathBuilder().buildTo(Draynor);
            if(ToDraynor != null && MasterFarmer != null)
            {
                ToDraynor.step(Path.TraversalOption.MANAGE_RUN, Path.TraversalOption.MANAGE_DISTANCE_BETWEEN_STEPS);
                Execution.delayUntil(() -> MasterFarmer.isVisible() || Draynor.contains(Players.getLocal()), 200,60000);
            }
        }
      HealthCheckAndPickPocket();
        if(Inventory.isFull())
        {
            InventoryIsFull();
        }

    }
}
