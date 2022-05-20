package lk.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import lk.example.pharmacyapp.db.DBHelper;
import lk.example.pharmacyapp.db.Doctor;
import lk.example.pharmacyapp.db.Drug;

public class DrugActivity extends AppCompatActivity {

    EditText name, qty, desc, price;
    Button btnAdd, btnDelete, btnUpdate;
    TextView title;
    String drugId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);

        Bundle extras = getIntent().getExtras();

        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        qty = findViewById(R.id.qty);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);

        DBHelper db = new DBHelper(this);


        if (extras != null) {
            drugId = String.valueOf(extras.getInt("drugId"));
            Drug byId = db.findDrugById(drugId);
            name.setText(byId.getName());
            qty.setText(String.valueOf(byId.getQty()));
            desc.setText(byId.getDescription());
            price.setText(String.valueOf(byId.getPrice()));
            title.setText("Manage Drug");

            btnAdd.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnAdd.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isReg = db.addDrug(name.getText().toString(), qty.getText().toString(), desc.getText().toString(), price.getText().toString());
                if (isReg) Toast.makeText(DrugActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
                DrugActivity.this.finish();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isDeleted = db.deleteDrug(drugId);
                if (isDeleted) Toast.makeText(DrugActivity.this, "Successfully Deleted", Toast.LENGTH_LONG).show();
                DrugActivity.this.finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateDrug(drugId, name.getText().toString(), qty.getText().toString(), desc.getText().toString(), price.getText().toString());
                Toast.makeText(DrugActivity.this, "Successfully Updated", Toast.LENGTH_LONG).show();
                DrugActivity.this.finish();
            }
        });
    }
}