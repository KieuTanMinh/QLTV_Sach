package fpoly.duanmau.ph40749.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fpoly.duanmau.ph40749.Adapter.Top10Adapter;
import fpoly.duanmau.ph40749.DAO.ThongKeDAO;
import fpoly.duanmau.ph40749.Model.Top;
import fpoly.duanmau.ph40749.R;

import java.util.ArrayList;


public class frgTop10 extends Fragment {
    View view;
    ListView listView;
    ThongKeDAO thongKeDao;

    Top10Adapter top10Adapter;
    ArrayList<Top> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frg_top10, container, false);
        listView = view.findViewById(R.id.ListView_Top10);
        thongKeDao = new ThongKeDAO(getContext());
        list = thongKeDao.getTop();
        top10Adapter = new Top10Adapter(getContext(), list);
        listView.setAdapter(top10Adapter);
        return view;
    }
}