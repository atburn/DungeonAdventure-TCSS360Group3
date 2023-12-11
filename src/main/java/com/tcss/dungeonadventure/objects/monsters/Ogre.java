package com.tcss.dungeonadventure.objects.monsters;

import com.tcss.dungeonadventure.objects.TileChars;

public class Ogre extends Monster {
    public Ogre(final String theName,
                   final int theHealth,
                   final int theDamageMin,
                   final int theDamageMax,
                   final int theAttackSpeed,
                   final double theAccuracy,
                   final double theHealChance,
                   final int theHealMin,
                   final int theHealMax) {
        super(theName,
                TileChars.Monster.OGRE,
                theHealth,
                theDamageMin,
                theDamageMax,
                theAttackSpeed,
                theAccuracy,
                theHealChance,
                theHealMin,
                theHealMax);
    }
}
