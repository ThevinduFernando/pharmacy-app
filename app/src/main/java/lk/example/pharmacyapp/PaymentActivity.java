package lk.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        TextView total = findViewById(R.id.total);
        Button btnHome = findViewById(R.id.btn_home);

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            double totalAmount = extras.getDouble("total");
            total.setText("Total:Rs."+totalAmount);
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentActivity.this,UserActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PaymentActivity.this,UserActivity.class));
    }
}