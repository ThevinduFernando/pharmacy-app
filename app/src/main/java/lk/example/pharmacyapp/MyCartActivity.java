package lk.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import lk.example.pharmacyapp.db.DBHelper;
import lk.example.pharmacyapp.db.Payment;

public class MyCartActivity extends AppCompatActivity {

    ListView myCartList;
    FloatingActionButton btnBuyNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        myCartList = findViewById(R.id.my_cart_list);
        btnBuyNow = findViewById(R.id.btn_buyNow);
        DBHelper db = new DBHelper(this);
        int userId = Prefs.getInt("userid");
        List<Payment> all=db.loadOrdersByType("CART", String.valueOf(userId));

        AdapterMyDrugCart myDrugCart = new AdapterMyDrugCart(MyCartActivity.this, all, null);
        myCartList.setAdapter(myDrugCart);
        if(all.size()==0){
            btnBuyNow.setVisibility(View.GONE);
        }else {
            btnBuyNow.setVisibility(View.VISIBLE);
        }

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCartActivity.this, BuyNowActivity.class);
                startActivity(intent);
            }
        });

    }
}