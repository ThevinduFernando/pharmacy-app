package lk.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;

import lk.example.pharmacyapp.db.DBHelper;

public class BuyNowActivity extends AppCompatActivity {

    private EditText name,address,mobile;
    private Spinner type;
    private Button payNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);
        type = findViewById(R.id.pay_type);
        payNow = findViewById(R.id.btn_pay);
        DBHelper db = new DBHelper(this);

        String[] items = new String[]{"CARD", "CASH ON DELIVERY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        type.setAdapter(adapter);

        int userId = Prefs.getInt("userid");

        Double total=db.getTotal("CART", String.valueOf(userId));

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.buyAll(String.valueOf(userId));
                db.addOrder(name.getText().toString(),String.valueOf(total),String.valueOf(userId),type.getSelectedItem().toString());
                Intent intent = new Intent(BuyNowActivity.this, PaymentActivity.class);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

    }
}