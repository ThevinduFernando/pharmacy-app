package lk.example.pharmacyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import lk.example.pharmacyapp.db.Doctor;

public class AdapterDoctorUser extends BaseAdapter {
    private Context context;
    private List<Doctor> doctors;
    Events events;

    public AdapterDoctorUser(Context context, List<Doctor> doctors, Events events) {
        this.context = context;
        this.doctors = doctors;
        this.events = events;
    }


    public void add(List<Doctor> doctors) {
        this.doctors = doctors;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return doctors.size();
    }

    @Override
    public Doctor getItem(int i) {
        return doctors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private Button callBtn;
    TextView name, mobile, specialty;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.doctor_user_list, parent, false);
        }

        callBtn = convertView.findViewById(R.id.btn_call);

        name = (TextView) convertView.findViewById(R.id.name);
        mobile = (TextView) convertView.findViewById(R.id.mobile);
        specialty = (TextView) convertView.findViewById(R.id.specialty);

        final Doctor doctor = doctors.get(position);
        name.setText("Name:"+doctor.getName());
        mobile.setText("Mobile:"+doctor.getMobile());
        specialty.setText("Specialty:"+doctor.getSpecialty());

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+doctor.getMobile()));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
