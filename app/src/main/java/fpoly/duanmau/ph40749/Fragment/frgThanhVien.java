package fpoly.duanmau.ph40749.Fragment;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.Toast;

import fpoly.duanmau.ph40749.Adapter.ThanhVienAdapter;
import fpoly.duanmau.ph40749.DAO.ThanhVienDAO;
import fpoly.duanmau.ph40749.Interface.ItemClickListener;
import fpoly.duanmau.ph40749.Model.ThanhVien;
import fpoly.duanmau.ph40749.R;

import java.util.ArrayList;


public class frgThanhVien extends Fragment {

    View view;

    RecyclerView recyclerView;

    ThanhVienDAO thanhVienDao;

    EditText edt_hoten, edt_maTV, edt_namsinh;

    String strHoten, strNamSinh;

    ArrayList<ThanhVien> list = new ArrayList<>();

    ThanhVienAdapter thanhVienAdapter;

    private static final String TAG = "QlThanhVien";

    public boolean isChuoi(String str) {
        return str.matches("[a-z A-Z 0-9]+");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frg_thanh_vien, container, false);
        recyclerView = view.findViewById(R.id.rcvThanhVien);
        thanhVienDao = new ThanhVienDAO(getContext());
        list = thanhVienDao.selectAll();
        thanhVienAdapter = new ThanhVienAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(thanhVienAdapter);
        view.findViewById(R.id.fltAddTV).setOnClickListener(v -> {
            showAddOrUpdateDialog(getContext(), 0, null);
        });
        thanhVienAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                ThanhVien thanhVien = list.get(position);
                showAddOrUpdateDialog(getContext(), 1, thanhVien);
            }
        });
        return view;
    }

    public void showAddOrUpdateDialog(Context context, int type, ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.item_add_thanhvien, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        edt_maTV = view1.findViewById(R.id.edt_add_maTV);
        edt_hoten = view1.findViewById(R.id.edt_add_hoTen);
        edt_namsinh = view1.findViewById(R.id.edt_add_namSinh);
        edt_maTV.setEnabled(false);
        if (type != 0) {
            if (thanhVien != null) {
                edt_maTV.setText(String.valueOf(thanhVien.getMaTV()));
                edt_hoten.setText(thanhVien.getHoTen());
                edt_namsinh.setText(String.valueOf(thanhVien.getNamSinh()));
            }
        }
        view1.findViewById(R.id.btnSave_ql_ThanhVien).setOnClickListener(v -> {
            strHoten = edt_hoten.getText().toString().trim();
            strNamSinh = edt_namsinh.getText().toString().trim();
            if (validate(strHoten, strNamSinh)) {
                if (type == 0) {
                    ThanhVien thanhVienNew = new ThanhVien();
                    thanhVienNew.setHoTen(strHoten);
                    thanhVienNew.setNamSinh(strNamSinh);
                    try {
                        if (thanhVienDao.insertData(thanhVienNew)) {
                            Toast.makeText(context,"Thêm thành công", Toast.LENGTH_SHORT).show();
                            list.add(thanhVienNew);
                            alertDialog.dismiss();
                            thanhVienAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi thao tác Database: ", e);
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    thanhVien.setHoTen(strHoten);
                    thanhVien.setNamSinh(strNamSinh);
                    try {
                        if (thanhVienDao.Update(thanhVien)) {
                            Toast.makeText(context,"Sửa thành công", Toast.LENGTH_SHORT).show();
                            Update_List();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi thao tác Database: ", e);
                        Toast.makeText(context,"Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view1.findViewById(R.id.btnCancle_ql_ThanhVien).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private boolean validate(String hoTen, String namSinh) {
        try {
            if (hoTen.isEmpty() || namSinh.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
                return false;
            }
           /* if (!isChuoi(hoTen)) {
                Toast.makeText(getContext(), "Nhập sai định dạng chuỗi", Toast.LENGTH_SHORT).show();
                return false;
            }*/
           /* if (Integer.parseInt(namSinh) < 1900 || Integer.parseInt(namSinh) > 2023) {
                Toast.makeText(getContext(), "Nhập sai định dạng ngày sinh", Toast.LENGTH_SHORT).show();
                return false;
            }*/
        } catch (Exception e) {
            Log.e(TAG, "Xảy ra lỗi trong quá trình validate: ", e);
            return false;
        }
        return true;
    }

    private void Update_List() {
        list.clear();
        list.addAll(thanhVienDao.selectAll());
        thanhVienAdapter.notifyDataSetChanged();
    }
}