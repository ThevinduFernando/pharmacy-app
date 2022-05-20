package lk.example.pharmacyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import lk.example.pharmacyapp.db.DBHelper;
import lk.example.pharmacyapp.db.Doctor;
import lk.example.pharmacyapp.db.Drug;


public class DrugFragment extends Fragment implements Events {

    ListView drug_list;
    DBHelper db;
    FloatingActionButton btn,viewCartBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_drug, container, false);
        btn = inflate.findViewById(R.id.btn_addDrug);
        viewCartBtn = inflate.findViewById(R.id.btn_viewCart);
        drug_list = inflate.findViewById(R.id.drug_list);
        db = new DBHelper(getActivity().getApplicationContext());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DrugActivity.class));
            }
        });

        viewCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = Prefs.getInt("userid");
                Double total=db.getTotal("CART", String.valueOf(userId));
                if(total==0.0){
                    Toast.makeText(getContext(),"Cart is Empty",Toast.LENGTH_LONG).show();
                }else {
                    startActivity(new Intent(getContext(), MyCartActivity.class));
                }

            }
        });
        return inflate;
    }

    @Override
    public void manageById(int drugId) {
        Intent intent = new Intent(getContext(), DrugActivity.class);
        intent.putExtra("drugId", drugId);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAll();
    }

    private void loadAll() {
        List<Drug> all = db.findAllDrugs();
        String utype = Prefs.getString("UTYPE");
        if (utype.equalsIgnoreCase("USER")) {
            AdapterDrugCart adapterDoctor = new AdapterDrugCart(getContext(), all, DrugFragment.this);
            drug_list.setAdapter(adapterDoctor);
            btn.setVisibility(View.GONE);
            viewCartBtn.setVisibility(View.VISIBLE);
        } else {
            AdapterDrug adapterDoctor = new AdapterDrug(getContext(), all, DrugFragment.this);
            drug_list.setAdapter(adapterDoctor);
        }
    }
}