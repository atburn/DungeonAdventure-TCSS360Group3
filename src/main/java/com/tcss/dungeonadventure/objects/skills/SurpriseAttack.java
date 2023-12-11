package com.tcss.dungeonadventure.objects.skills;


import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Player;
import com.tcss.dungeonadventure.objects.DungeonCharacter;

public class SurpriseAttack extends Skill {

    /**
     * The chance for a SurpriseAttack to be successful.
     */
    private static final double DEFAULT_SUCCESSFUL = 0.4;

    /**
     * The chance for a SurpriseAttack to be unsuccessful, but do nothing.
     */
    private static final double DEFAULT_NONE = 0.4;


    public SurpriseAttack() {

    }

    @Override
    public Integer activateSkill(final DungeonCharacter theSource, final DungeonCharacter theTarget) {
        final int ranInt = Helper.getRandomIntBetween(0, 1);

        if (ranInt < DEFAULT_SUCCESSFUL) { // SUCCESSFUL
            PCS.firePropertyChanged(PCS.COMBAT_LOG, "Surprise Attack was successful, doing 2 attacks.");
            for (int i = 0; i < 2; i++) {
                final int d = theSource.attack(theTarget);
                if (d > 0) {
                    Player.Stats.increaseCounter(Player.Stats.DAMAGE_DEALT, d);
                    PCS.firePropertyChanged(PCS.COMBAT_LOG,
                            "Player attacked, dealing " + d + " damage.");
                } else {
                    Player.Stats.increaseCounter(Player.Stats.MISSED_ATTACKS);
                    PCS.firePropertyChanged(PCS.COMBAT_LOG, "Player missed!");
                }

            }
            return 1;

        } else if (ranInt < DEFAULT_SUCCESSFUL + DEFAULT_NONE) { // NOTHING
            PCS.firePropertyChanged(PCS.COMBAT_LOG, "Surprise Attack was somewhat successful, doing 1 attack.");
            final int d = theSource.attack(theTarget);
            if (d > 0) {
                Player.Stats.increaseCounter(Player.Stats.DAMAGE_DEALT, d);
                PCS.firePropertyChanged(PCS.COMBAT_LOG,
                        "Player attacked, dealing " + d + " damage.");
            } else {
                Player.Stats.increaseCounter(Player.Stats.MISSED_ATTACKS);
                PCS.firePropertyChanged(PCS.COMBAT_LOG, "Player missed!");
            }

            return 0;

        } else { // UNSUCCESSFUL
            PCS.firePropertyChanged(PCS.COMBAT_LOG, "Surprise Attack was unsuccessful!");
            return null;
        }

    }

    @Override
    public String getDescription() {
        return "40% chance to do two attacks, 40% chance to do one attack, 20% chance to have no attack at all!";
    }

}
