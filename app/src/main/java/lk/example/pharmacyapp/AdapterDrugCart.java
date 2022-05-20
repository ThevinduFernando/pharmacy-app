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

public class AdapterDrugCart extends BaseAdapter {
    private Context context;
    private List<Drug> drugs;
    Events events;

    public AdapterDrugCart(Context context, List<Drug> drugs, Events events) {
        this.context = context;
        this.drugs = drugs;
        this.events = events;
    }


    public void add(List<Drug> drugs) {
        this.drugs = drugs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return drugs.size();
    }

    @Override
    public Drug getItem(int i) {
        return drugs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.drug_cart_list, parent, false);
        }
        Button addtoCartBtn;
        TextView name, qty, price, desc;
        EditText cartQty;
        addtoCartBtn = convertView.findViewById(R.id.btn_add_to_cart);

        name = (TextView) convertView.findViewById(R.id.name);
        qty = (TextView) convertView.findViewById(R.id.qty);
        price = (TextView) convertView.findViewById(R.id.price);
        desc = (TextView) convertView.findViewById(R.id.desc);
        cartQty = (EditText) convertView.findViewById(R.id.cart_qty);

        final Drug drug = drugs.get(position);
        name.setText("Name:" + drug.getName());
        qty.setText("Qty:" + drug.getQty());
        price.setText("Price:" + drug.getPrice());
        desc.setText("Description:" + drug.getDescription());
        DBHelper db = new DBHelper(context);

        addtoCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cQty = Integer.parseInt(cartQty.getText().toString());
                System.out.println(cQty);
                if (cQty == 0) {
                    Toast.makeText(context, "Qty Must be Greater Than 0", Toast.LENGTH_LONG).show();
                } else {
                    if (drug.getQty() >= cQty) {
                        int userid = Prefs.getInt("userid");
                        db.addToCart(drug, userid, cQty);
                        drug.setQty(drug.getQty()-cQty);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Successfully Added", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Qty Must be Less Than Item Qty", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return convertView;
    }
}
