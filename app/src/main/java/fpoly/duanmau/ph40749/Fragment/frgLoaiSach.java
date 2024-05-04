package fpoly.duanmau.ph40749.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.duanmau.ph40749.Adapter.LoaiSachAdapter;
import fpoly.duanmau.ph40749.DAO.LoaiSachDAO;
import fpoly.duanmau.ph40749.Interface.ItemClickListener;
import fpoly.duanmau.ph40749.Model.LoaiSach;
import fpoly.duanmau.ph40749.R;

import java.util.ArrayList;


public class frgLoaiSach extends Fragment {
    View view;

    RecyclerView recyclerView;

    LoaiSachDAO loaiSachDAO;

    EditText edt_maLoai, edt_tenloai, edt_cungcap, edSearch;

    ArrayList<LoaiSach> list = new ArrayList<>();
    ArrayList<LoaiSach> tempList = new ArrayList<>();
    public static final String TAG = "QlTheLoai";
    LoaiSachAdapter loaiSachAdapter;

    public boolean isChuoi(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frg_loai_sach, container, false);
        recyclerView = view.findViewById(R.id.rcvLoaiSach);
        loaiSachDAO = new LoaiSachDAO(getContext());
        list = loaiSachDAO.selectAll();
        tempList = loaiSachDAO.selectAll();
        loaiSachAdapter = new LoaiSachAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(loaiSachAdapter);
        edSearch = view.findViewById(R.id.edSearch);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                for (LoaiSach ls:tempList) {
                    if(String.valueOf(ls.getCungcap()).
                            contains(charSequence.toString())){
                        list.add(ls);
                    }
                }
                loaiSachAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        view.findViewById(R.id.fltAddLS).setOnClickListener(v -> {
            showAddOrEditDialog_Tl(getContext(), 0, null);
        });
        loaiSachAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
               LoaiSach loaiSach = list.get(position);
                showAddOrEditDialog_Tl(getContext(), 1, loaiSach);
            }
        });
        return view;
    }

    protected void showAddOrEditDialog_Tl(Context context, int type, LoaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.item_add_loaisach, null);
        builder.setView(mView);
        AlertDialog alertDialog = builder.create();
        edt_maLoai = mView.findViewById(R.id.edt_add_maLoai);
        edt_tenloai = mView.findViewById(R.id.edt_add_tenLoai);
        edt_cungcap = mView.findViewById(R.id.edt_add_cungcap);
        edt_maLoai.setEnabled(false);//vô hiêu hóa tương tác với người dùng

        if (type != 0) {//Update đổ dữ liệu người dùng lên dialog
            edt_maLoai.setText(String.valueOf(loaiSach.getMaLoai()));
            edt_tenloai.setText(String.valueOf(loaiSach.getTenLoai()));
            edt_cungcap.setText(String.valueOf(loaiSach.getCungcap()));
        }
        mView.findViewById(R.id.btnCancle_ql_LoaiSach).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        mView.findViewById(R.id.btnSave_ql_LoaiSach).setOnClickListener(v -> {
            String tenLoai = edt_tenloai.getText().toString();//Lấy input từ người dùng
            String cungCap = edt_cungcap.getText().toString();//
            if (tenLoai.isEmpty() || cungCap.isEmpty()) { //kiểm tra xem người dùng có bỏ trống thông tin không
                Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (!isChuoi(tenLoai)) { //Check regex chuỗi
                Toast.makeText(context, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
            } else {
                if (type == 0) {
                    LoaiSach loaiSachnew = new LoaiSach();
                    loaiSachnew.setTenLoai(tenLoai);
                    loaiSachnew.setCungcap(cungCap);
                    try {
                        if (loaiSachDAO.insertData(loaiSachnew)) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            list.add(loaiSachnew);
                            loaiSachAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi khi thao tác với cơ sở dữ liệu", e);
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loaiSach.setTenLoai(tenLoai);
                    try {
                        if (loaiSachDAO.Update(loaiSach)) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            update();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi khi thao tác với cơ sở dữ liệu", e);
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public void update() {
        list.clear();
        list.addAll(loaiSachDAO.selectAll());
        loaiSachAdapter.notifyDataSetChanged();
    }
}