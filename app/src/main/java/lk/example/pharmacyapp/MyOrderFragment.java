package lk.example.pharmacyapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import lk.example.pharmacyapp.db.DBHelper;
import lk.example.pharmacyapp.db.Order;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrderFragment extends Fragment {

    public static MyOrderFragment newInstance(String param1, String param2) {
        MyOrderFragment fragment = new MyOrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_my_order, container, false);
        ListView myOrders = inflate.findViewById(R.id.my_order_list);
        int userId = Prefs.getInt("userid");

        DBHelper db = new DBHelper(getContext());
        List<Order> allOrders = db.getAllOrders(String.valueOf(userId));

        AdapterOrders adapterOrders = new AdapterOrders(getContext(),allOrders,null);
        myOrders.setAdapter(adapterOrders);

        return inflate;
    }
}