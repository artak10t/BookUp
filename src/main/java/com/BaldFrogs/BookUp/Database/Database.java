package com.BaldFrogs.BookUp.Database;

import com.BaldFrogs.BookUp.Model.Listing;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Database
{
    private static Connection connection;

    /*
        Database initialization
     */
    public static void Init(String name)
    {
        connection = null;

        //Check if database doesn't exist, create one
        File tempFile = new File(name);
        if(!tempFile.exists())
            CreateDatabase(name);

        try
        {
            String url = "jdbc:sqlite:" + name;
            connection = DriverManager.getConnection(url);
            String sql = "PRAGMA foreign_keys = ON";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);

            System.out.println("Connection to SQLite has been established.");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void CreateDatabase(String fileName)
    {
        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url))
        {
            if (conn != null)
            {
                connection = conn;

                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created in " + fileName);

                /*
                    Creating SQL tables
                 */
                //Listing table
                String sql = "CREATE TABLE IF NOT EXISTS `listings`(" +
                        "`id` INTEGER PRIMARY KEY, " +
                        "`location` TEXT NOT NULL, " +
                        "`description` TEXT NOT NULL, " +
                        "`price` FLOAT NOT NULL, " +
                        "`maxGuests` INTEGER NOT NULL, " +
                        "`contactInformation` TEXT NOT NULL)";
                Statement stmt = connection.createStatement();
                stmt.execute(sql);

                //availableDays reference table
                sql = "CREATE TABLE IF NOT EXISTS `availableDays`(" +
                        "`id` INTEGER PRIMARY KEY, " +
                        "`date` DATE NOT NULL, " +
                        "`listing_id` INTEGER NOT NULL," +
                        "FOREIGN KEY(listing_id) REFERENCES listings(id) ON DELETE CASCADE," +
                        "UNIQUE(listing_id, date))";
                stmt = connection.createStatement();
                stmt.execute(sql);

                //images reference table
                sql = "CREATE TABLE IF NOT EXISTS `images`(" +
                        "`id` INTEGER PRIMARY KEY, " +
                        "`listing_id` INTEGER NOT NULL," +
                        "FOREIGN KEY(listing_id) REFERENCES listings(id) ON DELETE CASCADE)";
                stmt = connection.createStatement();
                stmt.execute(sql);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /*
        Insert Listing into database and return id,
        in case of failure return -1
     */
    public static int InsertListing(Listing l)
    {
        String sql = "INSERT INTO listings(location, description, price, maxGuests, contactInformation) " +
                    "VALUES(?, ?, ?, ?, ?)";
        try
        {
            int id = -1;

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, l.getLocation());
            pstmt.setString(2, l.getDescription());
            pstmt.setFloat(3, l.getPrice());
            pstmt.setInt(4, l.getMaxGuests());
            pstmt.setString(5, l.getContactInformation());
            pstmt.executeUpdate();

            String sqlId = "SELECT last_insert_rowid()";
            pstmt = connection.prepareStatement(sqlId);
            ResultSet result = pstmt.executeQuery();

            id = result.getInt(1);

            return id;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    /*
        Update listing from database
     */
    public static void UpdateListing(Listing l, int id)
    {
        String sql = "UPDATE `listings` SET `location` = ?, `description` = ?, `price` = ?, `maxGuests` = ?, `contactInformation` = ? WHERE `id` = ?";
        try
        {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, l.getLocation());
            pstmt.setString(2, l.getDescription());
            pstmt.setFloat(3, l.getPrice());
            pstmt.setInt(4, l.getMaxGuests());
            pstmt.setString(5, l.getContactInformation());
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /*
        Delete listing from database
    */
    public static void DeleteListing(int id)
    {
        String sql = "DELETE FROM `listings` WHERE `id` = ?";
        try
        {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /*
        Database image
     */
    //Insert image into database and return id
    //Actual image file is saved inside images/ folder
    public static int InsertImage(int listing_id)
    {
        String sql = "INSERT INTO images(listing_id) VALUES(?)";

        try
        {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, listing_id);
            pstmt.executeUpdate();

            String sqlId = "SELECT last_insert_rowid()";
            pstmt = connection.prepareStatement(sqlId);
            ResultSet result = pstmt.executeQuery();

            int id = result.getInt(1);

            return id;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    public static void DeleteImage(int id)
    {
        String sql = "DELETE FROM `images` WHERE `id` = ?";

        try
        {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /*
        Database date
    */
    public static void InsertAvailableDay(int listing_id, Date date)
    {
        String sql = "INSERT OR IGNORE INTO availableDays(date, listing_id) VALUES(?, ?)";

        try
        {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDate(1, date);
            pstmt.setInt(2, listing_id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void DeleteAvailableDay(int listing_id, Date date)
    {
        String sql = "DELETE FROM `availableDays` WHERE `listing_id` = ? AND `date` = ?";

        try
        {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, listing_id);
            pstmt.setDate(2, date);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Listing> NewListings()
    {
        return null; //Database;
    }

    /*
        Query Listing object from database,
        in case of don't exist return null
     */
    public static Listing QueryListing(int id)
    {
        String sql = "SELECT * FROM `listings` WHERE `id` = (?)";

        try
        {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            Listing l = new Listing(
                    result.getString(2),
                    result.getString(3),
                    result.getFloat(4),
                    result.getInt(5),
                    result.getString(6));

            sql = "SELECT `date` FROM `availableDays` WHERE `listing_id` = (?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            result = pstmt.executeQuery();

            while (result.next())
                l.addAvailableDay(result.getDate(1));

            sql = "SELECT `id` FROM `images` WHERE `listing_id` = (?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            result = pstmt.executeQuery();

            while (result.next())
                l.addImage(result.getInt(1));

            return l;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
