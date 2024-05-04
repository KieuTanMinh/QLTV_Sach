package fpoly.duanmau.ph40749.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.duanmau.ph40749.DAO.LoaiSachDAO;
import fpoly.duanmau.ph40749.Interface.ItemClickListener;
import fpoly.duanmau.ph40749.Model.LoaiSach;
import fpoly.duanmau.ph40749.R;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.viewholder> {
    Context context;
    ArrayList<LoaiSach> list;

    private ItemClickListener itemClickListener;

    LoaiSachDAO loaiSachDAO;

    private static final String TAG = "LoaiSachAdapter";

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }


    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
        loaiSachDAO = new LoaiSachDAO(context);
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loaisach, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.itemView.setOnLongClickListener(v -> {
            try {
                if (itemClickListener != null) {
                    itemClickListener.UpdateItem(position);
                }
            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: " + e);
            }
            return false;
        });
        holder.txt_maLoai.setText("Mã loại: " + list.get(position).getMaLoai());
        holder.txt_ten_loai.setText("Tên loại: " + list.get(position).getTenLoai());
        holder.txt_cungcap.setText("Cung cấp: " + list.get(position).getCungcap());
        holder.imgDelete.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txt_maLoai, txt_ten_loai, txt_cungcap;
        ImageView imgDelete;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txt_maLoai = itemView.findViewById(R.id.txtmaLoai);
            txt_ten_loai = itemView.findViewById(R.id.txttenLoai);
            txt_cungcap = itemView.findViewById(R.id.txtcungcap);
            imgDelete = itemView.findViewById(R.id.imgDeleteLS);
        }
    }

    public void showDeleteDialog(int position) {
        LoaiSach loaiSach = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + loaiSach.getTenLoai() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (LoaiSachDAO.Delete(loaiSach)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(loaiSach);
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
}