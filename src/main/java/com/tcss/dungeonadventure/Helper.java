package com.tcss.dungeonadventure;


import com.tcss.dungeonadventure.model.factories.MonsterFactory;
import com.tcss.dungeonadventure.objects.items.GreaterHealingPotion;
import com.tcss.dungeonadventure.objects.items.HealingPotion;
import com.tcss.dungeonadventure.objects.items.Item;
import com.tcss.dungeonadventure.objects.items.PillarOfAbstraction;
import com.tcss.dungeonadventure.objects.items.PillarOfEncapsulation;
import com.tcss.dungeonadventure.objects.items.PillarOfInheritance;
import com.tcss.dungeonadventure.objects.items.PillarOfPolymorphism;
import com.tcss.dungeonadventure.objects.items.SkillOrb;
import com.tcss.dungeonadventure.objects.items.VisionPotion;
import com.tcss.dungeonadventure.objects.monsters.Monster;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * This class contains static helper methods to use around the program.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public final class Helper {


    /**
     * Random object to generate random numbers.
     */
    private static final Random RANDOM = new Random();

    /**
     * Contains all the class declaration of items that
     * can randomly generate in rooms.
     */
    private static final Class<?>[] ITEM_POOL =
            new Class[]{
                HealingPotion.class,
                VisionPotion.class,
                SkillOrb.class,
                GreaterHealingPotion.class};

    /**
     * Contains all the class declarations of the monsters
     * that can randomly generate in rooms.
     */
    private static final Characters[] MONSTER_POOL =
            new Characters[]{Characters.GREMLIN, Characters.OGRE, Characters.SKELETON};
    /**
     * Contains all the class declarations of all pillars.
     */
    private static final Class<?>[] PILLARS =
            new Class[]{
                PillarOfInheritance.class,
                PillarOfAbstraction.class,
                PillarOfPolymorphism.class,
                PillarOfEncapsulation.class};

    private Helper() {

    }


    /**
     * Returns a random integer between the
     * specified minimum (inclusive) and specified max (exclusive).
     *
     * @param theMin The minimum (included)
     * @param theMax The maximum (excluded)
     * @return The random integer.
     */
    public static int getRandomIntBetween(final int theMin, final int theMax) {
        return RANDOM.nextInt(theMax - theMin) + theMin;
    }

    /**
     * Returns a random integer between the
     * specified minimum (inclusive) and specified max (exclusive).
     *
     * @param theMin The minimum (included)
     * @param theMax The maximum (excluded)
     * @return The random double.
     */
    public static double getRandomDoubleBetween(final double theMin, final double theMax) {
        return theMin + (theMax - theMin) * RANDOM.nextDouble();
    }

    /**
     * Gets a random item out of the item pool {@link #ITEM_POOL}.
     *
     * @return A new instance of the random item.
     */
    public static Item getRandomItem() {
        try {
            return (Item) ITEM_POOL[Helper.getRandomIntBetween(0, ITEM_POOL.length)].
                    getDeclaredConstructor().newInstance();
        } catch (final InstantiationException
                       | NoSuchMethodException
                       | IllegalAccessException
                       | InvocationTargetException e) {
            e.printStackTrace();


            throw new RuntimeException("Randomly generated item threw an exception.");
        }
    }


    /**
     * Gets a random monster out of the monster pool {@link #MONSTER_POOL}.
     *
     * @return A new instance of the random monster.
     */
    public static Monster getRandomMonster() {
        return MonsterFactory.createCharacter(
                MONSTER_POOL[Helper.getRandomIntBetween(0, MONSTER_POOL.length)]);
    }

    /**
     * Get an array of the classes of the pillars.
     *
     * @return A Class[] populated by the 4 Pillars.
     */
    public static Class<?>[] getPillarList() {
        return PILLARS;
    }

    /**
     * Converts an upper camel-cased string to sentence case.
     * <p>
     * e.g. CrushingBlow -> Crushing Blow
     * 
     * @param theString The input string.
     * @return The formatted string.
     */
    public static String camelToSpaced(final String theString) {
        return theString.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }


    public enum Characters {

        /**
         * Enum representing the Warrior hero.
         */
        WARRIOR,
        /**
         * Enum representing the Thief hero.
         */
        THIEF,
        /**
         * Enum representing the Priestess hero.
         */
        PRIESTESS,

        /**
         * Enum representing the Ogre monster.
         */
        OGRE,
        /**
         * Enum representing the Skeleton monster.
         */
        SKELETON,

        /**
         * Enum representing the Gremlin monster.
         */
        GREMLIN;

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }


    }


}
