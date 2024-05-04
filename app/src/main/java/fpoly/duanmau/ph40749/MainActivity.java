package fpoly.duanmau.ph40749;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import fpoly.duanmau.ph40749.DAO.ThuThuDAO;
import fpoly.duanmau.ph40749.Fragment.frgChangePassWord;
import fpoly.duanmau.ph40749.Fragment.frgDoanhThu;
import fpoly.duanmau.ph40749.Fragment.frgLoaiSach;
import fpoly.duanmau.ph40749.Fragment.frgPhieuMuon;
import fpoly.duanmau.ph40749.Fragment.frgSach;
import fpoly.duanmau.ph40749.Fragment.frgThanhVien;
import fpoly.duanmau.ph40749.Fragment.frgThemNguoiDung;
import fpoly.duanmau.ph40749.Fragment.frgTop10;

import fpoly.duanmau.ph40749.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    ActionBarDrawerToggle drawerToggle;

    ThuThuDAO thuThuDao;

    TextView txt_user;

    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.Toolbar_Main);
        thuThuDao = new ThuThuDAO(MainActivity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thư Viện Phương Nam");
        drawerLayout = findViewById(R.id.DrawerLayout);
        navigationView = findViewById(R.id.NavigationView);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        Toast.makeText(this, "Chào mừng đến với trang chủ", Toast.LENGTH_SHORT).show();
//        getSupportFragmentManager().beginTransaction().replace(R.id.Toolbar_Main, new frgPhieuMuon()).commit();
        Intent intent = getIntent();
        role = intent.getStringExtra("role");
        ReadFile(role);
        txt_user = navigationView.getHeaderView(0).findViewById(R.id.txt_HeaderTextView);
        txt_user.setText("Thư Viện PNLIB" );
        if (role != null) {
            if (!role.equalsIgnoreCase("admin")) {
                navigationView.getMenu().findItem(R.id.menu_them_nguoi_dung).setVisible(false);
            }
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                showMenu(itemId);
                return true;
            }
        });
    }

    private void ReadFile(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_use", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username_user", username);
        editor.apply();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        showMenu(itemId);
        return true;
    }

    private boolean showMenu(int itemId) {
        Fragment fragment = null;
        String title = "";
        try {
            if (itemId == R.id.menu_ql_phieu_muon) {
                fragment = new frgPhieuMuon();
                title = "Quản lý phiếu mượn";
            } else if (itemId == R.id.menu_ql_loaiSach) {
                fragment = new frgLoaiSach();
                title = "Quản lý loại sách";
            } else if (itemId == R.id.menu_ql_Sach) {
                fragment = new frgSach();
                title = "Quản lý sách";
            } else if (itemId == R.id.menu_ql_thanhVien) {
                fragment = new frgThanhVien();
                title = "Quản lý thành viên";
            } else if (itemId == R.id.menu_tk_top10) {
                fragment = new frgTop10();
                title = "Top 10 sách";
            } else if (itemId == R.id.menu_tk_DoanhThu) {
                fragment = new frgDoanhThu();
                title = "Thống kê doanh thu";
            } else if (itemId == R.id.menu_them_nguoi_dung) {
                if(!role.equalsIgnoreCase("admin")){
                    Toast.makeText(this, "Không đủ quyền để sự dụng chức năng", Toast.LENGTH_SHORT).show();
                }else{
                    fragment = new frgThemNguoiDung();
                    title = "Thêm người dùng";
                }
            } else if (itemId == R.id.menu_doi_mat_khau) {
                fragment = new frgChangePassWord();
                title = "Đổi mật khẩu";
            } else if (itemId == R.id.menu_dang_Xuat) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            }
            if (fragment != null) {
                drawerLayout.close();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                getSupportActionBar().setTitle(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}