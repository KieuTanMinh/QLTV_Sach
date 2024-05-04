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

import fpoly.duanmau.ph40749.DAO.SachDAO;
import fpoly.duanmau.ph40749.Interface.ItemClickListener;
import fpoly.duanmau.ph40749.Model.Sach;
import fpoly.duanmau.ph40749.R;


import java.util.ArrayList;


public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {

    Context context;
    ArrayList<Sach> list;
    SachDAO sachDao;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
        sachDao = new SachDAO(context);
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        Sach sach = list.get(position);
        if (sach != null) {
            holder.txt_tenSach.setText("Tên sách: " + sach.getTenSach());
            holder.txt_maSach.setText("Mã sách: " + sach.getMaSach());
            holder.txt_giaThe.setText("Giá thuê: " + sach.getGiaThue()+ " VND");
            holder.txt_maLoai.setText("Mã loại: " + sach.getMaLoai());
        }
        holder.imgdelete.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }

    public void showDeleteDialog(int position) {
        Sach sach = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + sach.getTenSach() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (sachDao.Delete(sach)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(sach);
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

    class SachViewHolder extends RecyclerView.ViewHolder {

        TextView txt_maSach, txt_tenSach, txt_giaThe, txt_maLoai;
        ImageView imgdelete;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_maSach = itemView.findViewById(R.id.txtmaSach);
            txt_tenSach = itemView.findViewById(R.id.txttenSach);
            txt_giaThe = itemView.findViewById(R.id.txtgiaThueS);
            txt_maLoai = itemView.findViewById(R.id.txtmaLoaiS);
            imgdelete = itemView.findViewById(R.id.imgDeleteS);
        }
    }
}