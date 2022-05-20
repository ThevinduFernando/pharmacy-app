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
import lk.example.pharmacyapp.db.Payment;

public class AdapterMyDrugCart extends BaseAdapter {
    private Context context;
    private List<Payment> payments;
    Events events;

    public AdapterMyDrugCart(Context context, List<Payment> payments, Events events) {
        this.context = context;
        this.payments = payments;
        this.events = events;
    }


    public void add(List<Payment> payments) {
        this.payments = payments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return payments.size();
    }

    @Override
    public Payment getItem(int i) {
        return payments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_drug_cart_list, parent, false);
        }
        Button removeBtn, updateBtn;
        TextView name, qty, price, desc;
        EditText cartQty;

        removeBtn = convertView.findViewById(R.id.btn_remove_from_cart);
        updateBtn = convertView.findViewById(R.id.btn_update);

        name = (TextView) convertView.findViewById(R.id.name);
        qty = (TextView) convertView.findViewById(R.id.qty);
        price = (TextView) convertView.findViewById(R.id.price);
        desc = (TextView) convertView.findViewById(R.id.desc);
        cartQty = (EditText) convertView.findViewById(R.id.cart_qty);

        final Payment payment = payments.get(position);
        Drug drug = payment.getDrug();
        name.setText("Name:" + drug.getName());
        qty.setText("Qty:" + payment.getQty() + " (Available Qty:" + drug.getQty() + ")");
        price.setText("Price:" + payment.getPrice());
        desc.setText("Description:" + drug.getDescription());
        DBHelper db = new DBHelper(context);

        cartQty.setText(String.valueOf(payment.getQty()));

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cQty = Integer.parseInt(cartQty.getText().toString());
                if (cQty == 0) {
                    Toast.makeText(context, "Qty Must be Greater Than 0", Toast.LENGTH_LONG).show();
                } else {
                    if (drug.getQty() >= cQty) {
                        db.updateCart(String.valueOf(payment.getId()), drug, cQty);
                        Toast.makeText(context, "Successfully Updated", Toast.LENGTH_LONG).show();
                        payment.setQty(cQty);
                        payment.setPrice(cQty*drug.getPrice());
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Qty Must be Less Than Available Item Qty", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.removeFromCart(String.valueOf(payment.getId()),drug,payment.getQty());
                payments.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
