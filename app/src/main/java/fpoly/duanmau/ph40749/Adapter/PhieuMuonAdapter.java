package fpoly.duanmau.ph40749.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.duanmau.ph40749.DAO.PhieuMuonDAO;
import fpoly.duanmau.ph40749.DAO.SachDAO;
import fpoly.duanmau.ph40749.DAO.ThanhVienDAO;
import fpoly.duanmau.ph40749.Interface.ItemClickListener;
import fpoly.duanmau.ph40749.Model.PhieuMuon;
import fpoly.duanmau.ph40749.Model.Sach;
import fpoly.duanmau.ph40749.Model.ThanhVien;
import fpoly.duanmau.ph40749.R;


import java.util.ArrayList;


public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    Context context;

    ArrayList<PhieuMuon> list;

    SachDAO sachDao;

    ThanhVienDAO thanhVienDao;

    PhieuMuonDAO phieuMuonDao;

    private ItemClickListener itemClickListener;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        sachDao = new SachDAO(context);
        thanhVienDao = new ThanhVienDAO(context);
        phieuMuonDao = new PhieuMuonDAO(context);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phieumuon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        holder.txt_maPM.setText("Mã PM: " + phieuMuon.getMaPM());
        Sach sach = sachDao.selectID(String.valueOf(phieuMuon.getMaSach()));
        holder.txt_tenSach.setText("Sách: " + sach.getTenSach());
        ThanhVien thanhVien = thanhVienDao.selectID(String.valueOf(phieuMuon.getMaTV()));
        holder.txt_tenTV.setText("Thành Viên: " + thanhVien.getHoTen());
        holder.txt_ngayThue.setText("Ngày Thuê: " + phieuMuon.getNgay());
        holder.tvTime.setText("Giờ Mượn: " + phieuMuon.getGioMuon());
        holder.txt_tienThue.setText("Tiền Thuê: " + phieuMuon.getTienThue() + " VND");
        if(phieuMuon.getTienThue()<50000){
            holder.txt_tienThue.setTextColor(
                    ContextCompat.getColor(context,R.color.blue));
        }else {
            holder.txt_tienThue.setTextColor(
                    ContextCompat.getColor(context,R.color.red));
        }

        if (phieuMuon.getTraSach() == 0) {
            holder.txt_trangThai.setTextColor(Color.BLUE);
            holder.txt_trangThai.setText("Đã trả sách");
        } else {
            holder.txt_trangThai.setTextColor(Color.RED);
            holder.txt_trangThai.setText("Chưa trả sách");
        }
        holder.imgDelete.setOnClickListener(v -> {
            if (phieuMuon.getTraSach() == 0) {
                showDeleteDialog(position);
            } else {
                Toast.makeText(context, "Thành Viên chưa trả sách ", Toast.LENGTH_SHORT).show();
            }

        });
        holder.itemView.setOnLongClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PhieuMuonViewHolder extends RecyclerView.ViewHolder {

        TextView txt_maPM, txt_tenTV, txt_tenSach, txt_ngayThue,
                txt_tienThue, txt_trangThai,tvTime;
        ImageView imgDelete;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_maPM = itemView.findViewById(R.id.txtmaPM);
            txt_tenTV = itemView.findViewById(R.id.txtthanhVien);
            txt_tenSach = itemView.findViewById(R.id.txttenSach);
            txt_ngayThue = itemView.findViewById(R.id.txtngayThue);
            txt_tienThue = itemView.findViewById(R.id.txtgiaThue);
            txt_trangThai = itemView.findViewById(R.id.txttrangThai);
            tvTime= itemView.findViewById(R.id.tvTime);
            imgDelete = itemView.findViewById(R.id.imgDeletePM);
        }
    }

    public void showDeleteDialog(int position) {
        PhieuMuon phieuMuon = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa phiếu mượn " + phieuMuon.getMaPM() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (phieuMuonDao.delete(phieuMuon)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(phieuMuon);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}

