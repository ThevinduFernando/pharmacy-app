package lk.example.pharmacyapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import lk.example.pharmacyapp.db.DBHelper;
import lk.example.pharmacyapp.db.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {


    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_user_profile, container, false);

        DBHelper db = new DBHelper(getContext());
        int userid = Prefs.getInt("userid");
        User user = db.findByUserId(String.valueOf(userid));

        EditText name = inflate.findViewById(R.id.name);
        EditText mob = inflate.findViewById(R.id.mobile);
        EditText email = inflate.findViewById(R.id.email);
        EditText password = inflate.findViewById(R.id.password);

        Button updateBtn = inflate.findViewById(R.id.btn_update);
        Button deleteBtn = inflate.findViewById(R.id.btn_delete);
        Button logoutBtn = inflate.findViewById(R.id.btn_logout);

        name.setText(user.getName());
        mob.setText(user.getMobile());
        email.setText(user.getEmail());
        password.setText(user.getPassword());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_LONG).show();
                db.updateByUserId(String.valueOf(userid),name.getText().toString(),email.getText().toString(),password.getText().toString(),mob.getText().toString());
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Profile Deleted",Toast.LENGTH_LONG).show();
                db.deleteByUserId(String.valueOf(userid));
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });

        return inflate;
    }
}