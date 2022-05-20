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

public class DoctorActivity extends AppCompatActivity {

    EditText name, mobile;
    Button btnAdd, btnDelete, btnUpdate;
    Spinner specialty;
    String doctorId;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);


        Bundle extras = getIntent().getExtras();

        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
        title = findViewById(R.id.title);

        specialty = findViewById(R.id.specialty);
        DBHelper db = new DBHelper(this);

        String[] items = new String[]{"Allergy and immunology", "Anesthesiology", "Dermatology", "Diagnostic radiology",
                "Emergency medicine", "Family medicine", "Internal medicine", "Medical genetics", "Neurology", "Nuclear medicine",
                "Obstetrics and gynecology", "Ophthalmology", "Pathology", "Pediatrics", "Physical medicine and rehabilitation",
                "Preventive medicine", "Psychiatry", "Radiation oncology", "Surgery", "Urology"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        specialty.setAdapter(adapter);
        if(extras!=null){
            doctorId = String.valueOf(extras.getInt("doctorId"));
            Doctor byId = db.findById(doctorId);
            name.setText(byId.getName());
            mobile.setText(byId.getMobile());
            int spinnerPosition = adapter.getPosition(byId.getSpecialty());
            specialty.setSelection(spinnerPosition);
            btnAdd.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            title.setText("Manage Doctor");
        }else {
            btnAdd.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isReg= db.addDoctor(name.getText().toString(),mobile.getText().toString(),specialty.getSelectedItem().toString());

                if(isReg) Toast.makeText(DoctorActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                DoctorActivity.this.finish();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isDeleted = db.deleteDoctor(doctorId);
                if(isDeleted) Toast.makeText(DoctorActivity.this,"Successfully Deleted",Toast.LENGTH_LONG).show();
                DoctorActivity.this.finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateDoctor(doctorId,name.getText().toString(),mobile.getText().toString(),specialty.getSelectedItem().toString());
                Toast.makeText(DoctorActivity.this,"Successfully Updated",Toast.LENGTH_LONG).show();
                DoctorActivity.this.finish();
            }
        });

    }
}