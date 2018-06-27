package br.ufg.inf.es.eir.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.es.eir.model.Remedy;

/**
 * Created by marceloquinta on 10/02/17.
 */

public class RemedyDAO extends SQLiteOpenHelper {

    private static final String DB_NAME = "remedies.db";
    private static final int DB_VERSION = 3;
    private static final String TABLE_REMEDIES = "remedies";

    //COLUMN_NAMES
    private static final String ROW_ID = "id";
    private static final String ROW_NAME = "name";

    public RemedyDAO(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + TABLE_REMEDIES + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ROW_NAME + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMEDIES);
        onCreate(db);
    }

    public void create(Remedy remedy) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROW_NAME, remedy.getName());

        db.insert(TABLE_REMEDIES, null, values);
        db.close();
    }

    public List<Remedy> getAll() {
        List<Remedy> remedyList = new ArrayList<Remedy>();

        String selectQuery = "SELECT * FROM " + TABLE_REMEDIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Remedy remedy = new Remedy();
                remedy.setId(Integer.parseInt(cursor.getString(
                        0)));
                remedy.setName(cursor.getString(1));
                // Adding contact to list
                remedyList.add(remedy);
            } while (cursor.moveToNext());
        }

        return remedyList;
    }

    public Remedy retrieve(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMEDIES, new String[] { ROW_ID,
                        ROW_NAME },
                ROW_ID + "=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null,
                null);
        Remedy remedy = null;
        if (cursor != null) {
            cursor.moveToFirst();
            remedy = new Remedy();
            remedy.setId(Integer.parseInt(cursor.getString(
                    0)));
            remedy.setName(cursor.getString(1));
            return remedy;
        } else{
            throw new RuntimeException("Não existe");
        }

    }

    public int update(Remedy remedy) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROW_NAME, remedy.getName());

        // updating row
        return db.update(TABLE_REMEDIES, values,
                ROW_ID + " = ?",
                new String[] { String.valueOf(remedy.getId()) });
    }

    public void delete(Remedy remedy) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMEDIES, ROW_ID + " = ?",
                new String[] { String.valueOf(remedy.getId()) });
        db.close();
    }
}