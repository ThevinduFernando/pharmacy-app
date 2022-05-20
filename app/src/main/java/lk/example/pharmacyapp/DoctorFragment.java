package lk.example.pharmacyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import lk.example.pharmacyapp.db.DBHelper;
import lk.example.pharmacyapp.db.Doctor;


public class DoctorFragment extends Fragment implements Events {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ListView doctor_list;
    DBHelper db;
    FloatingActionButton btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_doctor, container, false);

        btn = inflate.findViewById(R.id.btn_addDoctor);
        doctor_list = inflate.findViewById(R.id.doctor_list);
        db = new DBHelper(getActivity().getApplicationContext());

        loadAll();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DoctorActivity.class));
            }
        });

        return inflate;
    }

    @Override
    public void manageById(int doctorId) {

        Intent intent = new Intent(getContext(), DoctorActivity.class);
        intent.putExtra("doctorId", doctorId);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAll();
    }

    private void loadAll() {
        List<Doctor> all = db.findAll();
        String utype = Prefs.getString("UTYPE");
        if (utype.equalsIgnoreCase("USER")) {
            AdapterDoctorUser adapterDoctor = new AdapterDoctorUser(getContext(), all, DoctorFragment.this);
            doctor_list.setAdapter(adapterDoctor);
            btn.setVisibility(View.GONE);
        } else {
            AdapterDoctor adapterDoctor = new AdapterDoctor(getContext(), all, DoctorFragment.this);
            doctor_list.setAdapter(adapterDoctor);
        }
    }
}