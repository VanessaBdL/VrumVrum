package br.com.dlweb.maternidade.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import br.com.dlweb.maternidade.carro.Carro;
import br.com.dlweb.maternidade.marca.Marca;
import br.com.dlweb.maternidade.modelo.Modelo;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "vrumvrum";
    private static final String TABLE_MARCA = "marca";
    private static final String TABLE_MODELO = "modelo";
    private static final String TABLE_CARRO = "carro";

    private static final String CREATE_TABLE_MARCA = "CREATE TABLE " + TABLE_MARCA + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(100));";

    private static final String CREATE_TABLE_MODELO = "CREATE TABLE " + TABLE_MODELO + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_marca INTEGER, " +
            "nome VARCHAR(50), " +
            "CONSTRAINT fk_modelo_marca FOREIGN KEY (id_marca) REFERENCES marca (id))";

    private static final String CREATE_TABLE_CARRO = "CREATE TABLE " + TABLE_CARRO + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_modelo INTEGER, " +
            "nome VARCHAR(50), " +
            "renavam INTEGER, " +
            "placa VARCHAR(7), " +
            "valor DECIMAL(10,2), " +
            "ano INTEGER, " +
            "CONSTRAINT fk_carro_modelo FOREIGN KEY (id_modelo) REFERENCES modelo (id))";

    private static final String DROP_TABLE_MARCA = "DROP TABLE IF EXISTS " + TABLE_MARCA;
    private static final String DROP_TABLE_MODELO = "DROP TABLE IF EXISTS " + TABLE_MODELO;
    private static final String DROP_TABLE_CARRO = "DROP TABLE IF EXISTS " + TABLE_CARRO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MARCA);
        db.execSQL(CREATE_TABLE_MODELO);
        db.execSQL(CREATE_TABLE_CARRO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_MARCA);
        db.execSQL(DROP_TABLE_MODELO);
        db.execSQL(DROP_TABLE_CARRO);
        onCreate(db);
    }

    public void closeDBConnection() {
        db.close();
    }

    /* Início CRUD Marca */
    public long createMarca (Marca m) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());
        long id = db.insert(TABLE_MARCA, null, cv);
        db.close();
        return id;
    }

    public long updateMarca (Marca m) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());
        long id = db.update(TABLE_MARCA, cv, "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }

    public long deleteMarca (Marca m) {
        db = this.getWritableDatabase();
        long id = db.delete(TABLE_MARCA, "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }

    public Marca getByIdMarca (int id) {
        db = this.getReadableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_MARCA, columns, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        data.moveToFirst();
        Marca m = new Marca();
        m.setId(data.getInt(0));
        m.setNome(data.getString(1));
        data.close();
        db.close();
        return m;
    }

    public Cursor getAllMarca () {
        db = this.getReadableDatabase();
        String[] columns = {"_id", "nome"};
        return db.query(TABLE_MARCA, columns, null, null, null, null, null);
    }

    public void getAllNameMarca (ArrayList<Integer> listMarcaId, ArrayList<String> listMarcaName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_MARCA, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listMarcaId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listMarcaName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }
    /* Fim CRUD Marca */

    /* Início CRUD Modelo */
    public long createModelo(Modelo m) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_marca", m.getId_marca());
        cv.put("nome", m.getNome());
        long id = db.insert(TABLE_MODELO, null, cv);
        db.close();
        return id;
    }

    public long updateModelo(Modelo m) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_marca", m.getId_marca());
        cv.put("nome", m.getNome());
        long rows = db.update(TABLE_MODELO, cv, "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return rows;
    }

    public long deleteModelo(Modelo m) {
        db = this.getWritableDatabase();
        long rows = db.delete(TABLE_MODELO, "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return rows;
    }

    public Modelo getByIdModelo (int id) {
        db = this.getWritableDatabase();
        String[] columns = {"_id", "id_marca", "nome"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_MODELO, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Modelo m = new Modelo();
        m.setId(data.getInt(0));
        m.setId_marca(data.getInt(1));
        m.setNome(data.getString(2));
        data.close();
        db.close();
        return m;
    }

    public Cursor getAllModelo () {
        db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "id_marca"};
        return db.query(TABLE_MODELO, columns, null, null, null,
                null, "nome");
    }

    public void getAllNameModelo (ArrayList<Integer> listModeloId, ArrayList<String> listModeloName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_MODELO, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listModeloId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listModeloName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }
    /* Fim CRUD Modelo */

    /* Início CRUD Carro */
    public long createCarro(Carro c) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_modelo", c.getId_modelo());
        cv.put("nome", c.getNome());
        cv.put("renavam", c.getRenavam());
        cv.put("placa", c.getPlaca());
        cv.put("valor", c.getValor());
        cv.put("ano", c.getAno());

        long id = db.insert(TABLE_CARRO, null, cv);
        db.close();
        return id;
    }

    public long updateCarro(Carro c) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_modelo", c.getId_modelo());
        cv.put("nome", c.getNome());
        cv.put("renavam", c.getRenavam());
        cv.put("placa", c.getPlaca());
        cv.put("valor", c.getValor());
        cv.put("ano", c.getAno());

        long rows = db.update(TABLE_CARRO, cv, "_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return rows;
    }

    public long deleteCarro(Carro c) {
        db = this.getWritableDatabase();
        long rows = db.delete(TABLE_CARRO, "_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return rows;
    }

    public Carro getByIdCarro (int id) {
        db = this.getWritableDatabase();
        String[] columns = {"_id", "id_modelo", "nome", "renavam", "placa", "valor", "ano"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_CARRO, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Carro c = new Carro();
        c.setId(data.getInt(0));
        c.setId_modelo(data.getInt(1));
        c.setNome(data.getString(2));
        c.setRenavam(data.getInt(3));
        c.setPlaca(data.getString(4));
        c.setValor(data.getFloat(5));
        c.setAno(data.getInt(6));

        data.close();
        db.close();
        return c;
    }

    public Cursor getAllCarro () {
        db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "id_modelo"};
        return db.query(TABLE_CARRO, columns, null, null, null,
                null, "nome");
    }
    /* Fim CRUD Carro */


}
