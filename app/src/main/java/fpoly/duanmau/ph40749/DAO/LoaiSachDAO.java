package fpoly.duanmau.ph40749.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.duanmau.ph40749.Database.DbHelper;
import fpoly.duanmau.ph40749.Model.LoaiSach;



import java.util.ArrayList;


public class LoaiSachDAO {
    static DbHelper dbHelper;


    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(LoaiSach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai", obj.getTenLoai());
        contentValues.put("cungCap", obj.getCungcap());
        long check = sqLiteDatabase.insert("LOAISACH", null, contentValues);
        obj.setMaLoai((int) check);
        return check != -1;
    }

    public static boolean Delete(LoaiSach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaLoai())};
        long check = sqLiteDatabase.delete("LOAISACH", "maLoai =?", dk);
        return check != -1;
    }

    public boolean Update(LoaiSach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaLoai())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai", obj.getTenLoai());
        contentValues.put("cungCap", obj.getCungcap());
        long check = sqLiteDatabase.update("LOAISACH", contentValues, "maLoai =?", dk);
        return check != -1;
    }
    private ArrayList<LoaiSach> getAll(String sql, String... selectionArgs){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<LoaiSach> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                int maLoai = cursor.getInt(cursor.getColumnIndexOrThrow("maLoai"));
                String tenLoai = cursor.getString(cursor.getColumnIndexOrThrow("tenLoai"));
                String cungcap = cursor.getString(cursor.getColumnIndexOrThrow("cungCap"));
                list.add(new LoaiSach(maLoai, tenLoai, cungcap));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<LoaiSach> selectAll() {
        String sql = "SELECT * FROM LOAISACH";
        return getAll(sql);
    }
    public LoaiSach selectID(String id) {
        String sql = "SELECT * FROM LOAISACH WHERE id = ?";
        ArrayList<LoaiSach> list = getAll(sql, id);
        return list.get(0);
    }
}
