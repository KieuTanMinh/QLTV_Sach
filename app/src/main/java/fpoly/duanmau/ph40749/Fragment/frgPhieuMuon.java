package fpoly.duanmau.ph40749.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import fpoly.duanmau.ph40749.Adapter.PhieuMuonAdapter;
import fpoly.duanmau.ph40749.Adapter.SachSpinner;
import fpoly.duanmau.ph40749.Adapter.ThanhVienSpinner;
import fpoly.duanmau.ph40749.DAO.PhieuMuonDAO;
import fpoly.duanmau.ph40749.DAO.SachDAO;
import fpoly.duanmau.ph40749.DAO.ThanhVienDAO;
import fpoly.duanmau.ph40749.Interface.ItemClickListener;
import fpoly.duanmau.ph40749.Model.PhieuMuon;
import fpoly.duanmau.ph40749.Model.Sach;
import fpoly.duanmau.ph40749.Model.ThanhVien;
import fpoly.duanmau.ph40749.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class frgPhieuMuon extends Fragment {

    View view;

    PhieuMuonDAO phieuMuonDao;

    SachDAO sachDao;

    ThanhVienDAO thanhVienDao;
    RecyclerView recyclerView;

    EditText edt_maPM, edSearch;

    CheckBox chk_trangThai;

    TextView txt_ngay, txt_giaThue, txt_gioMuon;

    Spinner spinnerSach, spinnerThanhVien;

    ArrayList<Sach> listSach = new ArrayList<>();

    ArrayList<ThanhVien> listThanhVien = new ArrayList<>();

    ArrayList<PhieuMuon> listPhieuMuon = new ArrayList<>();
    ArrayList<PhieuMuon> tempListPhieuMuon = new ArrayList<>();
    SachSpinner sachSpinner;
    ThanhVienSpinner thanhVienSpinner;

    int maSach, maThanhVien, tienThueSach, positonSach, positionThanhVien;

    PhieuMuonAdapter phieuMuonAdapter;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private static final String TAG = "QlPhieuMuon";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frg_phieu_muon, container, false);
        recyclerView = view.findViewById(R.id.rcvPhieuMuon);

        phieuMuonDao = new PhieuMuonDAO(getContext());
        listPhieuMuon = phieuMuonDao.selectAll();
        tempListPhieuMuon = phieuMuonDao.selectAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), listPhieuMuon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(phieuMuonAdapter);


        view.findViewById(R.id.fltAddPM).setOnClickListener(v -> {
            showAddOrEditDialog(getContext(), 0, null);
        });
        phieuMuonAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                PhieuMuon phieuMuon = listPhieuMuon.get(position);
                showAddOrEditDialog(getContext(), 1, phieuMuon);
            }
        });
        return view;
    }

    private void showAddOrEditDialog(Context context, int type, PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.item_add_phieumuon, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        edt_maPM = dialogView.findViewById(R.id.edt_add_maPM);
        spinnerSach = dialogView.findViewById(R.id.spinner_Sach);
        spinnerThanhVien = dialogView.findViewById(R.id.spinner_ThanhVien);
        chk_trangThai = dialogView.findViewById(R.id.chk_add_trangthai);
        txt_ngay = dialogView.findViewById(R.id.txt_add_ngayThue);
        txt_giaThue = dialogView.findViewById(R.id.txt_add_giaThue);
        txt_gioMuon = dialogView.findViewById(R.id.txt_gioMuon);
        edt_maPM.setEnabled(false);

        CreateSpinnerSach(context);
        CreateSpinnerThanhVien(context);

        if (type != 0) {
            edt_maPM.setText(String.valueOf(phieuMuon.getMaPM()));
            txt_gioMuon.setText("Giờ Thuê: " + phieuMuon.getGioMuon());
            txt_ngay.setText("Ngày thuê : " + phieuMuon.getNgay());
            txt_giaThue.setText("Giá thuê : " + phieuMuon.getTienThue());
            if (phieuMuon.getTraSach() == 0) {
                chk_trangThai.setChecked(true);
            } else {
                chk_trangThai.setChecked(false);
            }
            for (int i = 0; i < listSach.size(); i++) {
                if (phieuMuon.getMaSach() == listSach.get(i).getMaSach()) {
                    positonSach = i;
                }
            }
            spinnerSach.setSelection(positonSach);

            for (int i = 0; i < listThanhVien.size(); i++) {
                if (phieuMuon.getMaTV() == listThanhVien.get(i).getMaTV()) {
                    positionThanhVien = i;
                }
            }
            spinnerThanhVien.setSelection(positionThanhVien);
        }

        dialogView.findViewById(R.id.btnSave_ql_PhieuMuon).setOnClickListener(v -> {
            if (type == 0) {
                PhieuMuon phieuMuonNew = new PhieuMuon();
                phieuMuonNew.setMaSach(maSach);
                phieuMuonNew.setMaTV(maThanhVien);
                phieuMuonNew.setTraSach(chk_trangThai.isChecked() ? 0 : 1);
                Date date = new Date();
                String ngay = dateFormat.format(date);
                phieuMuonNew.setNgay(ngay);
                phieuMuonNew.setTienThue(tienThueSach);

                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int giay = calendar.get(Calendar.SECOND);
                Time time = new Time(hour, minute, giay);
                String gio = timeFormat.format(time);
                phieuMuonNew.setGioMuon(gio);

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_use", getContext().MODE_PRIVATE);
                String maTT = sharedPreferences.getString("username_user", "");
                phieuMuonNew.setMaTT(maTT);

                try {
                    if (phieuMuonDao.insert(phieuMuonNew)) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        listPhieuMuon.add(phieuMuonNew);
                        phieuMuonAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "showAddOrEditDialog: ", e);
                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_use", getContext().MODE_PRIVATE);
                String maTT = sharedPreferences.getString("username_user", "");
                phieuMuon.setMaTT(maTT);
                phieuMuon.setMaTV(maThanhVien);
                phieuMuon.setMaSach(maSach);
                phieuMuon.setTraSach(chk_trangThai.isChecked() ? 0 : 1);
                try {
                    if (phieuMuonDao.update(phieuMuon)) {
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        updateList();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "showAddOrEditDialog: ", e);
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogView.findViewById(R.id.btnCancle_ql_PhieuMuon).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void CreateSpinnerThanhVien(Context context) {
        thanhVienDao = new ThanhVienDAO(context);
        listThanhVien = thanhVienDao.selectAll();
        thanhVienSpinner = new ThanhVienSpinner(context, listThanhVien);
        spinnerThanhVien.setAdapter(thanhVienSpinner);

        spinnerThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = listThanhVien.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void CreateSpinnerSach(Context context) {
        sachDao = new SachDAO(context);
        listSach = sachDao.selectAll();
        sachSpinner = new SachSpinner(context, listSach);
        spinnerSach.setAdapter(sachSpinner);
        spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                tienThueSach = listSach.get(position).getGiaThue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void updateList() {
        listPhieuMuon.clear();
        listPhieuMuon.addAll(phieuMuonDao.selectAll());
        phieuMuonAdapter.notifyDataSetChanged();
    }

}