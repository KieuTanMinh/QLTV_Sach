package fpoly.duanmau.ph40749.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import fpoly.duanmau.ph40749.Database.DbHelper;
import fpoly.duanmau.ph40749.Model.ThuThu;

import java.util.ArrayList;


public class ThuThuDAO {
    DbHelper dbHelper;

    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(ThuThu obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT", obj.getMaTT());
        contentValues.put("hoTen", obj.getHoTen());
        contentValues.put("matKhau", obj.getMatKhau());
        contentValues.put("role", obj.getRole());
        long check = sqLiteDatabase.insert("THUTHU", null, contentValues);
        return check != -1;
    }

    public boolean delete(ThuThu obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {obj.getMaTT()};
        long check = sqLiteDatabase.delete("THUTHU", "maTT = ?", dk);
        return check != -1;
    }

    public boolean update(ThuThu obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {obj.getMaTT()};
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", obj.getHoTen());
        contentValues.put("matKhau", obj.getMatKhau());
        contentValues.put("role", obj.getRole());
        long check = sqLiteDatabase.update("THUTHU", contentValues, "maTT = ?", dk);
        return check != -1;
    }

    public boolean updatePass(ThuThu obj) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {obj.getMaTT()};
        contentValues.put("hoTen", obj.getHoTen());
        contentValues.put("matKhau", obj.getMatKhau());
        long check = sqLiteDatabase.update("THUTHU", contentValues, "maTT = ?", dk);
        return check != -1;
    }

    public ArrayList<ThuThu> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String maTT = cursor.getString(cursor.getColumnIndexOrThrow("maTT"));
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("hoTen"));
                String matKhau = cursor.getString(cursor.getColumnIndexOrThrow("matKhau"));
                int role = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("role")));
                list.add(new ThuThu(maTT, hoTen, matKhau, role));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<ThuThu> SelectAll() {
        String sql = "SELECT * FROM THUTHU";
        return getAll(sql);
    }

    public ThuThu SelectID(String id) {
        String sql = "SELECT * FROM THUTHU WHERE maTT = ?";
        ArrayList<ThuThu> list = getAll(sql, id);
        return list.get(0);
    }

    public boolean checkLogin(String username, String password, String role) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM ThuThu WHERE " + "maTT" + "=? AND " + "matKhau" + "=? AND " + "role" + " = ?";
        String[] selectionArgs = new String[]{username, password, role};
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        boolean result = cursor.getCount() > 0;
        return result;
    }
}
