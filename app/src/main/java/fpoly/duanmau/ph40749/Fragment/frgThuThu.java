package fpoly.duanmau.ph40749.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpoly.duanmau.ph40749.Adapter.ThuThuAdapter;
import fpoly.duanmau.ph40749.DAO.ThuThuDAO;
import fpoly.duanmau.ph40749.Model.ThuThu;
import fpoly.duanmau.ph40749.R;

import java.util.ArrayList;


public class frgThuThu extends Fragment {

    View view;
    ThuThuDAO thuThuDao;
    RecyclerView recyclerView;
    ArrayList<ThuThu> list = new ArrayList<>();

    ThuThuAdapter thuThuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frg_thu_thu, container, false);
        recyclerView = view.findViewById(R.id.rcvThuThu);
        thuThuDao = new ThuThuDAO(getContext());
        list = thuThuDao.SelectAll();
        thuThuAdapter = new ThuThuAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(thuThuAdapter);
        return view;
    }
}