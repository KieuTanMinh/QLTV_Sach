package fpoly.duanmau.ph40749.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PNLIB";
    private static final int DATABASE_VERSION = 3;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tạo bảng thủ thư
        String createTableThuThu = "CREATE TABLE THUTHU (" +
                "maTT TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL," +
                "role INTEGER NOT NULL)";
        db.execSQL(createTableThuThu);

        String insertDefaultThuThu = "INSERT INTO THUTHU (maTT, hoTen, matKhau,role) VALUES ('admin', 'Nam', 'admin',0),('thuthu', 'NamOk', 'thuthu',1)";
        db.execSQL(insertDefaultThuThu);

        // Tạo bảng thành viên
        String createTableThanhVien = "CREATE TABLE THANHVIEN (" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT NOT NULL, " +
                "namSinh TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);



        // Tạo bảng loại sách
        String createTableLoaiSach = "CREATE TABLE LOAISACH(" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoai TEXT NOT NULL,"+
                "cungCap  TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);

        db.execSQL("insert into LOAISACH values (1, '01','Nam'), (2, '02','Minh'), (3, '03','Hiếu')");

        // Tạo bảng sách
        String createTableSach = "CREATE TABLE SACH (" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSach TEXT NOT NULL, " +
                "giaSach INTEGER NOT NULL, " +
                "maLoai INTEGER NOT NULL, " +
                "FOREIGN KEY(maLoai) REFERENCES LOAISACH(maLoai))";
        db.execSQL(createTableSach);



        // Tạo bảng phiếu mượn
        String createTablePhieuMuon = "CREATE TABLE PHIEUMUON(" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maTT TEXT NOT NULL, " +
                "maTV INTEGER NOT NULL, " +
                "maSach INTEGER NOT NULL, " +
                "tienThue INTEGER NOT NULL," +
                "ngay DATE NOT NULL," +
                "traSach INTEGER NOT NULL, " +
                "gioMuon TIME NOT NULL,"+
                "FOREIGN KEY(maTT) REFERENCES THUTHU(maTT), " +
                "FOREIGN KEY(maTV) REFERENCES THANHVIEN(maTV), " +
                "FOREIGN KEY(maSach) REFERENCES SACH(maSach))";
        db.execSQL(createTablePhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            /*db.execSQL("DROP TABLE IF EXISTS ThuThu");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS TheLoai");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            onCreate(db);*/
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            String createTablePhieuMuon = "CREATE TABLE PHIEUMUON(" +
                    "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "maTT TEXT NOT NULL, " +
                    "maTV INTEGER NOT NULL, " +
                    "maSach INTEGER NOT NULL, " +
                    "tienThue INTEGER NOT NULL," +
                    "ngay DATE NOT NULL," +
                    "gioMuon TIME NOT NULL,"+
                    "traSach INTEGER NOT NULL, " +
                    "FOREIGN KEY(maTT) REFERENCES THUTHU(maTT), " +
                    "FOREIGN KEY(maTV) REFERENCES THANHVIEN(maTV), " +
                    "FOREIGN KEY(maSach) REFERENCES SACH(maSach))";
            db.execSQL(createTablePhieuMuon);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
