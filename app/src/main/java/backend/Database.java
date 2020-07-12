package backend;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DiaryDatabase.db";
    public static final String TABLE_NAME = "Records";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, sugar REAL, record TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }

    public boolean insertData(String date, float sugar, String record) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("sugar", sugar);
        contentValues.put("record", record);
        long result = database.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    return cursor;
    }
}

