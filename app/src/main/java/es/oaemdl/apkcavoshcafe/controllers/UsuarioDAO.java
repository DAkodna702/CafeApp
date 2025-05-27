package es.oaemdl.apkcavoshcafe.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import es.oaemdl.apkcavoshcafe.db.Db;
import es.oaemdl.apkcavoshcafe.models.Usuario;

public class UsuarioDAO {

    Db db;
    public UsuarioDAO(Context context) {
        db = new Db(context);
    }

    public boolean Guardar(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("Nombres", usuario.getNombres());
        values.put("Correo", usuario.getCorreo());
        values.put("Passwordd", usuario.getPasswordd());


        usuario.setId(db.Insert("Usuario",values));
        return usuario.getId() > 0;
    }

    public boolean Login(Usuario usuario) {
        db.Sentencia(String.format("SELECT * FROM Usuario where Correo = '%s' and Passwordd = '%s'", usuario.getCorreo(), usuario.getPasswordd()));
        Cursor cursor = db.getCursor();

        if (cursor.moveToFirst()) {
            usuario.setId(cursor.getInt(0));
            usuario.setNombres(cursor.getString(1));
        }

        cursor.close();
        return  usuario.getId() >0;
    }
}
