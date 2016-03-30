package in.starlabs.startcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Arvind on 29/03/16.
 */
public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Complist";
    public static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    private static final String CREATE_MAIN_TABLE = CREATE_TABLE
            + Company.TABLE_NAME + " (" + Company.COMPANYNAME + " TEXT ," + Company.MODELS + "  TEXT PRIMARY KEY);";
    SQLiteDatabase db;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MAIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<String> getCompanylist() {
        List<String> companyList = new ArrayList<>();

        String query = "select " + Company.COMPANYNAME + " from " + Company.TABLE_NAME + ";";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                companyList.add(cursor.getString(cursor.getColumnIndex(Company.COMPANYNAME)));
            }

        }
        cursor.close();
        db.close();
        Set<String> comSet = new HashSet<>(companyList);
        companyList.clear();
        companyList.add("[Select Manufacturer from list...]");
        companyList.addAll(comSet);
        return companyList;
    }

    public void insertdata(String company, String model) {
        ContentValues values = new ContentValues();
        values.put(Company.COMPANYNAME, company);
        values.put(Company.MODELS, model);
        getWritableDatabase().replace(Company.TABLE_NAME, null, values);
        this.close();
    }

    public List<String> getmodels(String s) {
        List<String> modellist = new ArrayList<>();
        modellist.add("[Select model from list...]");
        String query = "select " + Company.MODELS + " from " + Company.TABLE_NAME + " where " + Company.COMPANYNAME + " = '" + s + "';";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                modellist.add(cursor.getString(cursor.getColumnIndex(Company.MODELS)));
            }

        }
        cursor.close();
        db.close();

        return modellist;
    }

    public static interface Company extends BaseColumns {
        String TABLE_NAME = "CompList";
        String COMPANYNAME = "CompName";
        String MODELS = "ModelName";
    }
}