package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.objects.DungeonCharacter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.sqlite.SQLiteDataSource;


/**
 * Utility class that uses JDBC API to store and manage the game's SQLite database.
 * The database stores all of {@link DungeonCharacter}'s initial statistics.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public final class SQLiteDB {

    /**
     * Represents the connection with the data source.
     */
    private static Connection myConn;

    /**
     * No instance of SQLiteDB should be created. This is a utility class.
     */
    private SQLiteDB() { }

    static {
        /*
         * Initializes the data source and establishes a connection with it.
         * Auto-generates the data source if it doesn't already exist.
         */
        try {
            final SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:dungeonCharacters.sqlite");
            myConn = ds.getConnection();

            //Inserts data into the data source if it's empty
            if (!hasData()) {
                createTable();
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public enum Keys {
        /**
         * The key to get the characters name from the database.
         */
        NAME,

        /**
         * The key to get the characters health from the database.
         */
        HEALTH,

        /**
         * The key to get the characters min damage from the database.
         */
        DAMAGE_MIN,

        /**
         * The key to get the characters max damage from the database.
         */
        DAMAGE_MAX,

        /**
         * The key to get the characters attack speed from the database.
         */
        ATTACK_SPEED,

        /**
         * The key to get the characters accuracy from the database.
         */
        ACCURACY,

        /**
         * The key to get the characters chance-to-heal from the database.
         */
        HEAL_CHANCE,

        /**
         * The key to get the characters min heal from the database.
         */
        HEAL_MIN,

        /**
         * The key to get the characters max damage from the database.
         */
        HEAL_MAX,

        /**
         * The key to get the characters block-chance from the database.
         */
        BLOCK_CHANCE
    }

    /**
     * Returns a {@link DungeonCharacter}'s initial statistics based on its name.
     *
     * @param theCharacter the enum of the {@link DungeonCharacter} to be printed
     * @return the queried {@link DungeonCharacter}'s initial statistics
     */
    public static Map<Keys, Object> getCharacterByName(final Helper.Characters theCharacter) {
        final String querySearch = "SELECT * FROM dungeonCharacters WHERE NAME = ?";

        final Map<Keys, Object> dataMap = new HashMap<>();

        try (PreparedStatement stmt = myConn.prepareStatement(querySearch)) {
            stmt.setString(1, theCharacter.toString());
            final ResultSet rs = stmt.executeQuery();

            //Retrieves all relevant stats of both Monster and Hero characters
            if (rs.next()) {
                dataMap.put(Keys.NAME, rs.getString(Keys.NAME.toString()));
                dataMap.put(Keys.HEALTH, rs.getInt(Keys.HEALTH.toString()));
                dataMap.put(Keys.DAMAGE_MIN, rs.getInt(Keys.DAMAGE_MIN.toString()));
                dataMap.put(Keys.DAMAGE_MAX, rs.getInt(Keys.DAMAGE_MAX.toString()));
                dataMap.put(Keys.ATTACK_SPEED, rs.getInt(Keys.ATTACK_SPEED.toString()));
                dataMap.put(Keys.ACCURACY, rs.getDouble(Keys.ACCURACY.toString()));
                dataMap.put(Keys.HEAL_CHANCE, rs.getDouble(Keys.HEAL_CHANCE.toString()));
                dataMap.put(Keys.HEAL_MIN, rs.getInt(Keys.HEAL_MIN.toString()));
                dataMap.put(Keys.HEAL_MAX, rs.getInt(Keys.HEAL_MAX.toString()));
                dataMap.put(Keys.BLOCK_CHANCE, rs.getDouble(Keys.BLOCK_CHANCE.toString()));

                //Instantiates the appropriate DungeonCharacter object based on its name
                return dataMap;

            }

        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return null;
    }

    /**
     * Checks if the {@link SQLiteDataSource} already has data.
     *
     * @return true if data already exists in the {@link SQLiteDataSource}
     */
    private static boolean hasData() {
        final String queryCount = "SELECT COUNT(*) FROM dungeonCharacters";
        int count = 0;

        try (ResultSet rs = myConn.createStatement().executeQuery(queryCount)) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return count > 0;
    }

    /**
     * Creates a new table that stores all of
     * {@link DungeonCharacter}'s statistics.
     */
    private static void createTable() {
        final String tableName = "dungeonCharacters";
        final String queryCreate = String.format(
                """
                        CREATE TABLE IF NOT EXISTS %s
                        (NAME TEXT NOT NULL,\s
                        DISPLAY_CHAR TEXT NOT NULL,\s
                        HEALTH INTEGER NOT NULL,\s
                        DAMAGE_MIN REAL NOT NULL,\s
                        DAMAGE_MAX REAL NOT NULL,\s
                        ATTACK_SPEED INTEGER NOT NULL,\s
                        ACCURACY REAL NOT NULL,\s
                        HEAL_CHANCE REAL,\s
                        HEAL_MIN INTEGER,\s
                        HEAL_MAX INTEGER,\s
                        BLOCK_CHANCE REAL)
                        """, tableName);

        try (PreparedStatement stmt = myConn.prepareStatement(queryCreate)) {
            stmt.executeUpdate();
            insertCharacters();
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Inserts all of {@link DungeonCharacter}'s stats into the database's table.
     */
    private static void insertCharacters() {
        final String insertPriestess = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, "
                + "DAMAGE_MAX, ATTACK_SPEED, ACCURACY, BLOCK_CHANCE) "
                + "VALUES (\"Priestess\", \"+\", 75, 25, 45, 5, 0.7, 0.3)";

        final String insertThief = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, "
                + "DAMAGE_MAX, ATTACK_SPEED, ACCURACY, BLOCK_CHANCE) "
                + "VALUES (\"Thief\", \"+\", 75, 20, 40, 6, 0.8, 0.4)";

        final String insertWarrior = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, "
                + "DAMAGE_MAX, ATTACK_SPEED, ACCURACY, BLOCK_CHANCE) "
                + "VALUES (\"Warrior\", \"+\", 125, 35, 60, 4, 0.8, 0.2)";

        final String insertGremlin = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, "
                + "DAMAGE_MAX, ATTACK_SPEED, ACCURACY, HEAL_CHANCE, HEAL_MIN, HEAL_MAX) "
                + "VALUES (\"Gremlin\", \"9\", 70, 15, 30, 5, 0.8, 0.4, 20, 40)";

        final String insertOgre = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, "
                + "DAMAGE_MAX, ATTACK_SPEED, ACCURACY, HEAL_CHANCE, HEAL_MIN, HEAL_MAX) "
                + "VALUES (\"Ogre\", \"7\", 200, 30, 60, 2, 0.6, 0.1, 30, 60)";

        final String insertSkeleton = "INSERT INTO dungeonCharacters "
                + "(NAME, DISPLAY_CHAR, HEALTH, DAMAGE_MIN, "
                + "DAMAGE_MAX, ATTACK_SPEED, ACCURACY, HEAL_CHANCE, HEAL_MIN, HEAL_MAX) "
                + "VALUES (\"Skeleton\", \"8\", 100, 30, 50, 3, 0.8, 0.3, 30, 50)";

        try (Statement stmt = myConn.createStatement()) {
            //Disables auto-commit to enable batching
            myConn.setAutoCommit(false);

            //Adds statements to the batch to execute all at once
            stmt.addBatch(insertPriestess);
            stmt.addBatch(insertThief);
            stmt.addBatch(insertWarrior);
            stmt.addBatch(insertGremlin);
            stmt.addBatch(insertOgre);
            stmt.addBatch(insertSkeleton);

            //Executes the batch
            System.out.println(Arrays.toString(stmt.executeBatch()));

            //Commits the transaction
            myConn.commit();

        } catch (final SQLException e) {
            e.printStackTrace();

            //Rolls back the transaction in case of an error to maintain data consistency
            try {
                if (myConn != null) {
                    myConn.rollback();
                }
            } catch (final SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }
}
