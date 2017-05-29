package silptech.developerabhi.databaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by abhi on 5/25/2017.
 */

public class DatabaseAssistant extends SQLiteOpenHelper {
    final static String DATABASENAME = "mydatabase";

    public DatabaseAssistant(Context context) {
        super(context, DATABASENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Mytable(id INTEGER PRIMARY KEY AUTOINCREMENT,rollno INTEGER, name VARCHAR, phone VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Mytable");
        onCreate(db);

    }

    public void addData(Module m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("rollno", m.getRoll());
        cv.put("name", m.getName());
        cv.put("phone", m.getPhone());
        db.insert("Mytable", null, cv);
        db.close();
    }

    public ArrayList<Module> readData() {
        ArrayList<Module> pulledData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Mytable", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Module m = new Module();
                    m.setRoll(cursor.getInt(cursor.getColumnIndex("rollno")));
                    m.setName(cursor.getString(cursor.getColumnIndex("name")));
                    m.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                    pulledData.add(m);
                } while (cursor.moveToNext());

            }
        }
        cursor.close();
        db.close();
        return pulledData;
    }

    public void updateData(Module m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", m.getName());
        cv.put("phone", m.getPhone());
        db.update("Mytable", cv, "rollno=" + m.getRoll(), null);
        db.close();
    }

    public void deleteData(int roll) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Mytable where rollno="+roll);
        db.close();

    }
}
