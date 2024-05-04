package fpoly.duanmau.ph40749.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import fpoly.duanmau.ph40749.DAO.ThanhVienDAO;
import fpoly.duanmau.ph40749.Interface.ItemClickListener;
import fpoly.duanmau.ph40749.Model.ThanhVien;
import fpoly.duanmau.ph40749.R;

import java.util.ArrayList;


public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    Context context;
    ArrayList<ThanhVien> list;

    ThanhVienDAO thanhVienDao;

    public ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        thanhVienDao = new ThanhVienDAO(context);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanhvien, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        if (thanhVien != null) {
            holder.txthoTen.setText("Họ tên: " + thanhVien.getHoTen());
            holder.txtmaTV.setText("Mã TV: " + thanhVien.getMaTV());
            holder.txtnamSinh.setText("Năm sinh: " + thanhVien.getNamSinh());
        }
        holder.imgDeleteTV.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            if(itemClickListener != null){
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }

    public void showDeleteDialog(int position) {
        ThanhVien thanhVien = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa thành viên " + thanhVien.getHoTen() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (thanhVienDao.Delete(thanhVien)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(thanhVien);
                        notifyItemChanged(position);
                        notifyItemRemoved(position);
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ThanhVienViewHolder extends RecyclerView.ViewHolder {

        TextView txthoTen, txtmaTV, txtnamSinh;

        ImageView imgDeleteTV;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txthoTen = itemView.findViewById(R.id.txthoTen);
            txtnamSinh = itemView.findViewById(R.id.txtnamSinh);
            txtmaTV = itemView.findViewById(R.id.txtmaTV);
            imgDeleteTV = itemView.findViewById(R.id.imgDeleteTV);
        }
    }
}