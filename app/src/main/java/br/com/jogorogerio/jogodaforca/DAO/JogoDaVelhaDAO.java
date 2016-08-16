package br.com.jogorogerio.jogodaforca.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.jogorogerio.jogodaforca.model.OptionName;

/**
 * Created by Rogerio on 15/08/2016.
 */
public class JogoDaVelhaDAO extends SQLiteOpenHelper{
    public JogoDaVelhaDAO(Context context) {
        super(context, "JogoDaVelha", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table palavras (id INTEGER PRIMARY KEY, palavra TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        switch(i) {

        }
    }

    public void insertWord(String word) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("palavra", word);

        db.insert("palavras", null, content);
    }

    public List<OptionName> retrieveWords() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * from palavras";
        Cursor c = db.rawQuery(sql, null);
        List<OptionName> words = new ArrayList<OptionName>();
        while (c.moveToNext()) {
            OptionName option = new OptionName();
            option.setId(c.getLong(c.getColumnIndex("id")));
            option.setNome(c.getString(c.getColumnIndex("palavra")));
            words.add(option);
        }
        c.close();
        return words;
    }

    public void deleteOption(OptionName option) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {option.getId()+""};

        db.delete("palavras", "id = ?", params);
    }
}
