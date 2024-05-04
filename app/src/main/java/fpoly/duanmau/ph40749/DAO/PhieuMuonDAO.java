package fpoly.duanmau.ph40749.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.duanmau.ph40749.Database.DbHelper;
import fpoly.duanmau.ph40749.Model.PhieuMuon;


import java.util.ArrayList;

public class PhieuMuonDAO {
    DbHelper dbHelper;
    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insert(PhieuMuon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT", obj.getMaTT());
        contentValues.put("maTV", obj.getMaTV());
        contentValues.put("maSach", obj.getMaSach());
        contentValues.put( "ngay",String.valueOf(obj.getNgay()));
        contentValues.put("tienThue", obj.getTienThue());
        contentValues.put("traSach", obj.getTraSach());
        contentValues.put("gioMuon", obj.getGioMuon());
        long check = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        obj.setMaPM((int) check);
        return check != -1;
    }


    public boolean delete(PhieuMuon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaPM())};
        long check = sqLiteDatabase.delete("PHIEUMUON", "maPM = ?", dk);
        return check != -1;
    }

    public boolean update(PhieuMuon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaPM())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT", obj.getMaTT());
        contentValues.put("maTV", obj.getMaTV());
        contentValues.put("maSach", obj.getMaSach());
        contentValues.put( "ngay", String.valueOf(obj.getNgay()));
        contentValues.put("tienThue", obj.getTienThue());
        contentValues.put("traSach", obj.getTraSach());
        contentValues.put("gioMuon", obj.getGioMuon());
        long check = sqLiteDatabase.update("PHIEUMUON", contentValues, "maPM = ?", dk);
        return check != -1;
    }

    public ArrayList<PhieuMuon> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase(); // Use getReadableDatabase()
        ArrayList<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                int maPM = cursor.getInt(cursor.getColumnIndexOrThrow("maPM"));
                int maTV = cursor.getInt(cursor.getColumnIndexOrThrow("maTV"));
                int maSach = cursor.getInt(cursor.getColumnIndexOrThrow("maSach"));
                String maTT = cursor.getString(cursor.getColumnIndexOrThrow("maTT"));
                int tienThue = cursor.getInt(cursor.getColumnIndexOrThrow("tienThue"));
                int traSach = cursor.getInt(cursor.getColumnIndexOrThrow("traSach"));
                String ngay = cursor.getString(cursor.getColumnIndexOrThrow( "ngay"));
                String gioMuon = cursor.getString((cursor.getColumnIndexOrThrow("gioMuon")));

                PhieuMuon phieuMuon = new PhieuMuon(maPM, maTT,maTV,maSach, tienThue, traSach, ngay, gioMuon);
                list.add(phieuMuon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<PhieuMuon> selectAll() {
        String sql = "SELECT * FROM  PHIEUMUON";
        return getAll(sql);
    }
    public PhieuMuon selectID(String id) {
        String sql = "SELECT * FROM PHIEUMUON WHERE id = ?";
        ArrayList<PhieuMuon> list = getAll(sql, id);
        return list.get(0);
    }
}
