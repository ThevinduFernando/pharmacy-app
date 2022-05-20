package lk.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import lk.example.pharmacyapp.db.DBHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText name,email,password,mobile;
    private Spinner type;
    private TextView regBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regBtn = findViewById(R.id.btn_reg);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);
        type = findViewById(R.id.type);
        DBHelper db = new DBHelper(this);

        String[] items = new String[]{"USER", "MERCHANT"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        type.setAdapter(adapter);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isReg= db.register(name.getText().toString(),email.getText().toString(),
                        password.getText().toString(),mobile.getText().toString(),type.getSelectedItem().toString());

                if(isReg){
                    Toast.makeText(RegisterActivity.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                }
            }
        });

    }
}