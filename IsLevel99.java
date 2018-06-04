package com.bravebots.bravethieving.Master_Farmer;

import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.script.framework.logger.BotLogger;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

//import path.to.your.Steal
//import path.to.your.GetFood

/**
 * NOTES:
 * 
 */
public class IsLevel99 extends BranchTask {

    private Steal steal = new Steal();
    private GetFood getfood = new GetFood();

    @Override
    public boolean validate() {
        if(Skill.THIEVING.getCurrentLevel() == 99)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public TreeTask failureTask() {
        return getfood;
    }

    @Override
    public TreeTask successTask() {
        return steal;
    }
}
