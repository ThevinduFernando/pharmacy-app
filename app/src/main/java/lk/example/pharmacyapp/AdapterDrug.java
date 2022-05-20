package lk.example.pharmacyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import lk.example.pharmacyapp.db.Doctor;
import lk.example.pharmacyapp.db.Drug;

public class AdapterDrug extends BaseAdapter {
    private Context context;
    private List<Drug> drugs;
    Events events;

    public AdapterDrug(Context context, List<Drug> drugs, Events events) {
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

    private Button manageBtn;
    TextView name, qty, price,desc;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.drug_list, parent, false);
        }

        manageBtn = convertView.findViewById(R.id.btn_manage);

        name = (TextView) convertView.findViewById(R.id.name);
        qty = (TextView) convertView.findViewById(R.id.qty);
        price = (TextView) convertView.findViewById(R.id.price);
        desc = (TextView) convertView.findViewById(R.id.desc);

        final Drug drug = drugs.get(position);
        name.setText("Name:"+drug.getName());
        qty.setText("Qty:"+drug.getQty());
        price.setText("Price:"+drug.getPrice());
        desc.setText("Description:"+drug.getDescription());

        manageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                events.manageById(drug.getId());
            }
        });

        return convertView;
    }
}
