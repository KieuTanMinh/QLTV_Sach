package fpoly.duanmau.ph40749.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.duanmau.ph40749.Database.DbHelper;
import fpoly.duanmau.ph40749.Model.Sach;


import java.util.ArrayList;


public class SachDAO {
    DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(Sach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maLoai", obj.getMaLoai());
        contentValues.put("tenSach", obj.getTenSach());
        contentValues.put("giaSach", obj.getGiaThue());
        long check = sqLiteDatabase.insert("SACH", null, contentValues);
        obj.setMaSach((int) check);
        return check != -1;
    }

    public boolean Delete(Sach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaSach())};
        long check = sqLiteDatabase.delete("SACH", "maSach = ?", dk);
        return check != -1;
    }

    public boolean Update(Sach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaSach())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("maLoai", obj.getMaLoai());
        contentValues.put("tenSach", obj.getTenSach());
        contentValues.put("giaSach", obj.getGiaThue());
        long check = sqLiteDatabase.update("SACH", contentValues, "maSach = ?", dk);
        return check != -1;
    }


    private ArrayList<Sach> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Sach> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            do {
//                int maSach = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_SACH));
//                String tenSach = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_SACH));
//                int giaThue = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GIA_THUE));
//                int maLoai = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_LOAI));
//                list.add(new Sach(maSach, tenSach, giaThue, maLoai));
//            } while (cursor.moveToNext());
            while (cursor.moveToNext()) {
                int maSach = cursor.getInt(cursor.getColumnIndexOrThrow("maSach"));
                String tenSach = cursor.getString(cursor.getColumnIndexOrThrow("tenSach"));
                int giaThue = cursor.getInt(cursor.getColumnIndexOrThrow("giaSach"));
                int maLoai = cursor.getInt(cursor.getColumnIndexOrThrow("maLoai"));
                list.add(new Sach(maSach, tenSach, giaThue, maLoai));
            }
        }
        return list;
    }

    public ArrayList<Sach> selectAll() {
        String sql = "SELECT * FROM SACH";
        return getAll(sql);
    }

//    public Sach selectID(String id) {
//        String sql = "SELECT * FROM Sach WHERE maSach = ?";
//        ArrayList<Sach> list = getAll(sql, id);
//        return list.get(0);
//    }
    public Sach selectID(String id) {
        String sql = "SELECT * FROM SACH WHERE maSach = ?";
        ArrayList<Sach> list = getAll(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
