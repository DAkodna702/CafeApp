package es.oaemdl.apkcavoshcafe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Db extends SQLiteOpenHelper {

    SQLiteDatabase db;

    String _SQL;

    public Db(Context context) {
        super(context, "cavoshcafe_1.10.db", null, 1);
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, Nombres TEXT, Correo TEXT, Passwordd TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void Sentencia(String sql) {
        this._SQL = sql;
    }

    public Cursor getCursor() {
        return  db.rawQuery(_SQL, null);
    }

    public int Insert(String sTabla, ContentValues values) {
        return (int) db.insert(sTabla, null, values);

    }
}
