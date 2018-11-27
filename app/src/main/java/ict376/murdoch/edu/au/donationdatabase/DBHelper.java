package ict376.murdoch.edu.au.donationdatabase;

/**
 * Created by Hamid on 5/10/2017.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import 	android.util.Pair;

public class DBHelper extends SQLiteOpenHelper {

    // The database name
    public static final String DATABASE_NAME = "DonationsDatabase.db";

    // The table "contacts"
    public static final String CONTACTS_TABLE_NAME    = "contacts";
    public static final String CONTACTS_COLUMN_ID     = "id";
    public static final String CONTACTS_COLUMN_NAME   = "name";
    public static final String CONTACTS_COLUMN_EMAIL  = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY   = "place";
    public static final String CONTACTS_COLUMN_PHONE  = "phone";


    // Donation table
    public static final String DONATIONS_TABLE_NAME            = "donations";
    public static final String DONATIONS_COLUMN_ID             = "id";
    public static final String DONATIONS_COLUMN_CONTACT_ID     = "contact_id";
    public static final String DONATIONS_COLUMN_AMOUNT_DONATED = "amount_donated";


    static int ver = 1;

    public DBHelper(Context context) {
        // Syntax: SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        // The third argument is used to allow returning subclasses of Cursor when calling query

        super(context, DATABASE_NAME , null, ver);
    }

    /**
     * onCreate callback is invoked when the database is actually opened,
     * for example by a call to getWritableDatabase().
     * onCreate is run only when the database file did not exist
     * @param db SQLiteDatabase to create
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creating the tables - Here I will create just one table called "contacts"
        db.execSQL(
                "create table " +  CONTACTS_TABLE_NAME + "(" +
                        CONTACTS_COLUMN_ID + " integer primary key, " +
                        CONTACTS_COLUMN_NAME   + " text, " +
                        CONTACTS_COLUMN_PHONE  + " text, " +
                        CONTACTS_COLUMN_EMAIL  + " text, " +
                        CONTACTS_COLUMN_STREET + " text, " +
                        CONTACTS_COLUMN_CITY   + " text)"
        );

        // Foreign key should be at the last in the query
        db.execSQL(
                "create table " +  DONATIONS_TABLE_NAME + "(" +
                        DONATIONS_COLUMN_ID + " integer primary key, " +
                        DONATIONS_COLUMN_AMOUNT_DONATED   + " real, " +
                        DONATIONS_COLUMN_CONTACT_ID + " integer,  FOREIGN KEY (" +  DONATIONS_COLUMN_CONTACT_ID + ") REFERENCES " +
                        CONTACTS_TABLE_NAME + "(" +  CONTACTS_COLUMN_ID + "))"
        );

    }

    /**
     * In case of upgrade, I will delete the current tables and create a new one
     * onUpgrade() is only called when the database file exists but the stored
     * version number is lower than requested in constructor.
     * The onUpgrade() should update the table schema to the requested version.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DONATIONS_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Insert a row (a new contact) into the contacts table
     * @param name
     * @param phone
     * @param email
     * @param street
     * @param place
     * @return
     */
    public boolean insertContact  (String name, String phone, String email, String street, String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Prepare the row to insert
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);

        // Insert the row
        db.insert("contacts", null, contentValues);
        return true;
    }

    public boolean insertDonation (int donation, int contactId) {

        SQLiteDatabase db = this.getWritableDatabase();

        // Prepare the new values
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount_donated", donation);
        contentValues.put("contact_id", contactId);

        db.insert("donations", null, contentValues);
        return true;

    }

    /**
     * Update the row of ID = id with these new values
     * @param id
     * @param name
     * @param phone
     * @param email
     * @param street
     * @param place
     * @return
     */
    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {

        SQLiteDatabase db = this.getWritableDatabase();

        // Prepare the new values
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);

        // run the update query
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    /**
     * Delete the row of ID = id
     * @param id
     * @return
     */
    public Integer deleteContact (Integer id) {

        SQLiteDatabase db = this.getWritableDatabase();


        db.delete(DONATIONS_TABLE_NAME,
                  DONATIONS_COLUMN_CONTACT_ID + " = ?" ,  new String[] { Integer.toString(id) });


                // delete contact
        return db.delete(CONTACTS_TABLE_NAME,
                         CONTACTS_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<Integer> getDonationHistory(int donorId)
    {
        ArrayList<Integer> donation_list = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from donations where contact_id="+donorId+"", null);

        if (res.getCount() > 0) {
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                donation_list.add(res.getInt(res.getColumnIndex(DONATIONS_COLUMN_AMOUNT_DONATED)));
                res.moveToNext();
            }

        }

        return donation_list;
    }

    public int getDonorTotal(int donorId)
    {
        int total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from donations where contact_id="+donorId+"", null);

        if (res.getCount() > 0) {
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                total = total + res.getInt(res.getColumnIndex(DONATIONS_COLUMN_AMOUNT_DONATED));
                res.moveToNext();
            }

        }

        return total;
    }

    public int getAllDonorTotal()
    {
        int total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from donations", null);

        if (res.getCount() > 0) {
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                total = total + res.getInt(res.getColumnIndex(DONATIONS_COLUMN_AMOUNT_DONATED));
                res.moveToNext();
            }

        }

        return total;
    }



    /**
     * Get the list of all contacts
     * @return
     */
    public ArrayList<Pair<Integer, String>> getAllContacts()
    {
        ArrayList<Pair<Integer, String>> array_list = new ArrayList<Pair<Integer, String>>();

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor res =  db.rawQuery( "select * from contacts", null );

        if (res.getCount() > 0) {
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                array_list.add(new Pair(res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)), res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME))));
                res.moveToNext();
            }

        }
        return array_list;
    }

    public String getName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from contacts where id="+id+"", null );
        cursor.moveToFirst();
        String out = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME));
        return out;
    }



    /**
     * Retrieving a row given a contact ID
     * Here, we will use the method rawQuery
     * @param id
     * @return
     */
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );

        /*
        Cursor res =  db.rawQuery( "select " + CONTACTS_TABLE_NAME + ".*, sum(" + DONATIONS_TABLE_NAME + "." + DONATIONS_COLUMN_AMOUNT_DONATED + ") " +
                                    "from " +  CONTACTS_TABLE_NAME + ", " + DONATIONS_TABLE_NAME + " " +
                                    "where " + CONTACTS_TABLE_NAME + "." + CONTACTS_COLUMN_ID + "=" + id  + " and " + DONATIONS_TABLE_NAME +  "." + DONATIONS_COLUMN_CONTACT_ID + " = " + id, null );

        */

        // select contacts.*, sum(donations.amount_donated) from contacts, donations where contacts.id = id  and  donations.contact_id = id


        return res;
    }

    /**
     * Count the number of rows in a table
     * @return
     */
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();

        return  (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);

    }


}