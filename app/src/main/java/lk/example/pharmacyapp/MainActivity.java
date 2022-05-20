package lk.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import lk.example.pharmacyapp.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    private EditText email,password;
    private TextView loginBtn,link_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.btn_login);
        link_signup = findViewById(R.id.link_signup);

        DBHelper db = new DBHelper(this);

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String loginType= db.login(email.getText().toString(),password.getText().toString());

               if(loginType.equalsIgnoreCase("USER")){
                   startActivity(new Intent(MainActivity.this,UserActivity.class));
               }else if(loginType.equalsIgnoreCase("MERCHANT")){
                   startActivity(new Intent(MainActivity.this,MerchantActivity.class));
               }else if(loginType.equalsIgnoreCase("invaild")){
                   Toast.makeText(MainActivity.this,"Invalid email or password",Toast.LENGTH_LONG).show();
               }
               Prefs.putString("UTYPE",loginType);
            }
        });

        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

    }
}