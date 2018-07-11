package br.ufg.inf.es.eir.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.es.eir.model.Remedy;
import br.ufg.inf.es.eir.model.Unit;

/**
 * Created by marceloquinta on 10/02/17.
 */

public class UnitDAO extends SQLiteOpenHelper {

    private static final String DB_NAME = "units.db";
    private static final int DB_VERSION = 3;
    private static final String TABLE_UNITS = "units";

    //COLUMN_NAMES
    private static final String ROW_ID = "id";
    private static final String ROW_NAME = "name";
    private static final String ROW_IMAGE = "image";
    private static final String ROW_STREET = "street";
    private static final String ROW_NUMBER = "number";
    private static final String ROW_COMPLEMENT = "complement";
    private static final String ROW_REGION = "region";
    private static final String ROW_ZIPCODE = "zipcode";
    private static final String ROW_CITY = "city";
    private static final String ROW_STATE = "state";
    private static final String ROW_COUNTRY = "country";
    private static final String ROW_PHONE = "phone";
    private static final String ROW_REMEDIES = "remedies";

    public UnitDAO(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + TABLE_UNITS + "("
                + ROW_ID + " INTEGER PRIMARY KEY,"
                + ROW_NAME + " TEXT,"
                + ROW_IMAGE + " TEXT, "
                + ROW_STREET + " TEXT, "
                + ROW_NUMBER + " INTEGER, "
                + ROW_COMPLEMENT + " TEXT, "
                + ROW_REGION + " TEXT, "
                + ROW_ZIPCODE + " TEXT, "
                + ROW_CITY + " TEXT, "
                + ROW_STATE + " TEXT, "
                + ROW_COUNTRY + " TEXT, "
                + ROW_PHONE + " TEXT, "
                + ROW_REMEDIES + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNITS);
        onCreate(db);
    }

    public void create(Unit unit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROW_ID, unit.getId());
        values.put(ROW_NAME, unit.getName());
        values.put(ROW_IMAGE, unit.getImage());
        values.put(ROW_STREET, unit.getStreet());
        values.put(ROW_NUMBER, unit.getNumber());
        values.put(ROW_COMPLEMENT, unit.getComplement());
        values.put(ROW_REGION, unit.getRegion());
        values.put(ROW_ZIPCODE, unit.getZipcode());
        values.put(ROW_CITY, unit.getCity());
        values.put(ROW_STATE, unit.getState());
        values.put(ROW_COUNTRY, unit.getCountry());
        values.put(ROW_PHONE, unit.getPhone());


        ArrayList remedies = (ArrayList) unit.getRemedies();
        String remediesAsString = "";

        for (int i = 0; i < remedies.size(); i++) {
            if (i == remedies.size() - 1) {
                remediesAsString = remediesAsString.concat(remedies.get(i).toString());
            } else {
                remediesAsString = remediesAsString.concat(remedies.get(i).toString() + ";");
            }
        }

        values.put(ROW_REMEDIES, remediesAsString);

        db.insert(TABLE_UNITS, null, values);
        db.close();
    }

    public List<Unit> getAll() {
        List<Unit> unitList = new ArrayList<Unit>();

        String selectQuery = "SELECT * FROM " + TABLE_UNITS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Unit unit = new Unit();
                unit.setId(Integer.parseInt(cursor.getString(
                        0)));
                unit.setName(cursor.getString(1));
                unit.setImage(cursor.getString(2));
                unit.setStreet(cursor.getString(3));
                unit.setNumber(Integer.parseInt(cursor.getString(
                        4)));
                unit.setComplement(cursor.getString(5));
                unit.setRegion(cursor.getString(6));
                unit.setZipcode(cursor.getString(7));
                unit.setCity(cursor.getString(8));
                unit.setState(cursor.getString(9));
                unit.setCountry(cursor.getString(10));
                unit.setPhone(cursor.getString(11));

                String remediesAsString = cursor.getString(12);
                String[] remediesStringArray = remediesAsString.split(";");
                ArrayList remedies = new ArrayList();

                for (String remedyId: remediesStringArray) {
                    remedies.add(Integer.parseInt(remedyId));
                }

                unit.setRemedies(remedies);

                // Adding contact to list
                unitList.add(unit);
            } while (cursor.moveToNext());
        }

        return unitList;
    }

    public Unit retrieve(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_UNITS, new String[] { ROW_ID,
                        ROW_NAME, ROW_IMAGE, ROW_STREET, ROW_NUMBER, ROW_COMPLEMENT,
                        ROW_REGION, ROW_ZIPCODE, ROW_CITY, ROW_STATE, ROW_COUNTRY,
                        ROW_PHONE },
                ROW_ID + "=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null,
                null);
        Unit unit = null;
        if (cursor != null) {
            cursor.moveToFirst();
            unit = new Unit();
            unit.setId(Integer.parseInt(cursor.getString(
                    0)));
            unit.setName(cursor.getString(1));
            unit.setImage(cursor.getString(2));
            unit.setStreet(cursor.getString(3));
            unit.setNumber(Integer.parseInt(cursor.getString(
                    4)));
            unit.setComplement(cursor.getString(5));
            unit.setRegion(cursor.getString(6));
            unit.setZipcode(cursor.getString(7));
            unit.setCity(cursor.getString(8));
            unit.setState(cursor.getString(9));
            unit.setCountry(cursor.getString(10));
            unit.setPhone(cursor.getString(11));
            return unit;
        } else {
            throw new RuntimeException("Não existe");
        }

    }

    public int update(Unit unit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROW_NAME, unit.getName());

        // updating row
        return db.update(TABLE_UNITS, values,
                ROW_ID + " = ?",
                new String[] { String.valueOf(unit.getId()) });
    }

    public void delete(Unit unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_UNITS, ROW_ID + " = ?",
                new String[] { String.valueOf(unit.getId()) });
        db.close();
    }
}
