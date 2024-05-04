package fpoly.duanmau.ph40749.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import fpoly.duanmau.ph40749.Database.DbHelper;
import fpoly.duanmau.ph40749.Model.ThanhVien;

import java.util.ArrayList;


public class ThanhVienDAO {
    DbHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(ThanhVien obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", obj.getHoTen());
        contentValues.put("namSinh", obj.getNamSinh());
        long check = sqLiteDatabase.insert("THANHVIEN", null, contentValues);
        obj.setMaTV((int) check);
        return check != -1;
    }

    public boolean Delete(ThanhVien obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaTV())};
        long check = sqLiteDatabase.delete("THANHVIEN",  "maTV = ?", dk);
        return check != -1;
    }

    public boolean Update(ThanhVien obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaTV())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", obj.getHoTen());
        contentValues.put("namSinh", obj.getNamSinh());
        long check = sqLiteDatabase.update("THANHVIEN", contentValues, "maTV = ?", dk);
        return check != -1;
    }

    private ArrayList<ThanhVien> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            do {
//                int maTT = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_TV));
//                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_TV));
//                String namSinh = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMSINH_TV));
//                list.add(new ThanhVien(maTT, hoTen, namSinh));
//            } while (cursor.moveToNext());
            while ((cursor.moveToNext())) {
                int maTT = cursor.getInt(cursor.getColumnIndexOrThrow("maTV"));
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("hoTen"));
                String namSinh = cursor.getString(cursor.getColumnIndexOrThrow("namSinh"));
                list.add(new ThanhVien(maTT, hoTen, namSinh));
            }
        }
        return list;
    }

    public ArrayList<ThanhVien> selectAll() {
        String sql = "SELECT * FROM THANHVIEN";
        return getAll(sql);
    }

    //    public ThanhVien selectID(String id) {
//        String sql = "SELECT * FROM ThanhVien WHERE maTV = ?";
//        ArrayList<ThanhVien> list = getAll(sql, id);
//        return list.get(0);
//    }
    public ThanhVien selectID(String id) {
        String sql = "SELECT * FROM THANHVIEN WHERE maTV = ?";
        ArrayList<ThanhVien> list = getAll(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
