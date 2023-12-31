package com.tcss.dungeonadventure.objects.monsters;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.DungeonCharacter;


/**
 * A child of {@link DungeonCharacter} to represent enemies the player will fight.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public abstract class Monster extends DungeonCharacter {

    /**
     * The chance to heal.
     */
    private final double myHealChance;

    /**
     * The minimum heal amount.
     */
    private final int myMinHeal;

    /**
     * The maximum heal amount.
     */
    private final int myMaxHeal;

    public Monster(final String theName,
                   final char theDisplayChar,
                   final int theDefaultHealth,
                   final int theMinDamage,
                   final int theMaxDamage,
                   final int theAttackSpeed,
                   final double theAccuracy,
                   final double theHealChance,
                   final int theMinHeal,
                   final int theMaxHeal) {
        super(theName,
                theDisplayChar,
                theDefaultHealth,
                theMinDamage,
                theMaxDamage,
                theAttackSpeed,
                theAccuracy);

        this.myHealChance = theHealChance;
        this.myMinHeal = theMinHeal;
        this.myMaxHeal = theMaxHeal;
    }


    @Override
    public void changeHealth(final int theChangeInHealth) {
        super.changeHealth(theChangeInHealth);

    }

    /**
     * Heals the monster a small amount.
     *
     * @return The amount the monster healed.
     */
    public int heal() {
        int healAmount = 0;
        if (Helper.getRandomDoubleBetween(0, 1) < this.myHealChance) {
            healAmount = Helper.getRandomIntBetween(myMinHeal, myMaxHeal);
            super.changeHealth(healAmount);
        }
        return healAmount;
    }

    @Override
    public String getTileColor() {
        return "red";
    }


}
