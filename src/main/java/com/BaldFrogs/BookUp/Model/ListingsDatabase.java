package com.BaldFrogs.BookUp.Model;

import java.util.ArrayList;

public class ListingsDatabase
{
    private static ArrayList<Listing> Database = new ArrayList<>();

    public static void Insert(Listing l)
    {
        Database.add(l);
    }

    public static ArrayList<Listing> QueryAll()
    {
        return Database;
    }

    public static Listing Query(int i)
    {
        return Database.get(i);
    }

    public static int Size()
    {
        return Database.size();
    }
}
