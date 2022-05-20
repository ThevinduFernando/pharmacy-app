package lk.example.pharmacyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import lk.example.pharmacyapp.db.DBHelper;
import lk.example.pharmacyapp.db.Drug;
import lk.example.pharmacyapp.db.Order;

public class AdapterOrders extends BaseAdapter {
    private Context context;
    private List<Order> orders;
    Events events;

    public AdapterOrders(Context context, List<Order> orders, Events events) {
        this.context = context;
        this.orders = orders;
        this.events = events;
    }


    public void add(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Order getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_order_list, parent, false);
        }

        TextView oid, date, amount;

        oid = (TextView) convertView.findViewById(R.id.order_id);
        amount = (TextView) convertView.findViewById(R.id.amount);
        date = (TextView) convertView.findViewById(R.id.date);

        final Order order = orders.get(position);
        oid.setText("Order ID #" + order.getId());
        amount.setText("Amount: " + order.getAmount());
        date.setText("Date: " + order.getDate());

        return convertView;
    }
}
