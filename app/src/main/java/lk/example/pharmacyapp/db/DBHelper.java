package lk.example.pharmacyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;


import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String USERS = "users";
    public static final String USERS_COL1 = "user_id";
    public static final String USERS_COL2 = "email";
    public static final String USERS_COL3 = "password";
    public static final String USERS_COL4 = "name";
    public static final String USERS_COL5 = "mobile";
    public static final String USERS_COL6 = "type";

    public static final String PAYMENT = "payment";
    public static final String PAYMENT_COL1 = "payment_id";
    public static final String PAYMENT_COL2 = "date";
    public static final String PAYMENT_COL3 = "drug_id";
    public static final String PAYMENT_COL4 = "qty";
    public static final String PAYMENT_COL5 = "amount";
    public static final String PAYMENT_COL6 = "type";
    public static final String PAYMENT_COL7 = "status";
    public static final String PAYMENT_COL8 = "user_id";

    public static final String ORDERS = "orders";
    public static final String ORDERS_COL1 = "order_id";
    public static final String ORDERS_COL2 = "date";
    public static final String ORDERS_COL3 = "amount";
    public static final String ORDERS_COL4 = "pay_type";
    public static final String ORDERS_COL5 = "user_id";
    public static final String ORDERS_COL6 = "date";

    public static final String DRUG = "drug";
    public static final String DRUG_COL1 = "drug_id";
    public static final String DRUG_COL2 = "name";
    public static final String DRUG_COL3 = "qty";
    public static final String DRUG_COL4 = "description";
    public static final String DRUG_COL5 = "price";

    public static final String DOCTOR = "doctor";
    public static final String DOCTOR_COL1 = "doctor_id";
    public static final String DOCTOR_COL2 = "name";
    public static final String DOCTOR_COL3 = "mobile";
    public static final String DOCTOR_COL4 = "specialty";


    public DBHelper(Context context) {
        super(context, "PHARMACY.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USERS + "("
                + USERS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USERS_COL2 + " TEXT,"
                + USERS_COL3 + " TEXT,"
                + USERS_COL4 + " TEXT,"
                + USERS_COL5 + " TEXT,"
                + USERS_COL6 + " TEXT" + ")");

        db.execSQL("CREATE TABLE " + DOCTOR + "("
                + DOCTOR_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DOCTOR_COL2 + " TEXT,"
                + DOCTOR_COL3 + " TEXT,"
                + DOCTOR_COL4 + " TEXT" + ")");

        db.execSQL("CREATE TABLE " + PAYMENT + "("
                + PAYMENT_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PAYMENT_COL2 + " TEXT,"
                + PAYMENT_COL3 + " TEXT,"
                + PAYMENT_COL4 + " INTEGER,"
                + PAYMENT_COL5 + " DOUBLE,"
                + PAYMENT_COL6 + " TEXT,"
                + PAYMENT_COL7 + " TEXT,"
                + PAYMENT_COL8 + " INTEGER" + ")");

        db.execSQL("CREATE TABLE " + ORDERS + "("
                + ORDERS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ORDERS_COL2 + " TEXT,"
                + ORDERS_COL3 + " DOUBLE,"
                + ORDERS_COL4 + " TEXT,"
                + ORDERS_COL5 + " TEXT" + ")");

        db.execSQL("CREATE TABLE " + DRUG + "("
                + DRUG_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DRUG_COL2 + " TEXT,"
                + DRUG_COL3 + " TEXT,"
                + DRUG_COL4 + " TEXT,"
                + DRUG_COL5 + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DOCTOR);
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        db.execSQL("DROP TABLE IF EXISTS " + PAYMENT);
        db.execSQL("DROP TABLE IF EXISTS " + DRUG);
        onCreate(db);
    }


    public String login(String email, String password) {

        String selectQuery = "SELECT * FROM " + USERS + " WHERE " + USERS_COL2 + "='" + email + "' AND " + USERS_COL3 + "='" + password+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            Prefs.putInt("userid",cursor.getInt(cursor.getColumnIndex(USERS_COL1)));
            return cursor.getString(cursor.getColumnIndex(USERS_COL6));
        }
        return "invaild";
    }

    public boolean register(String name, String email, String password, String mobile, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_COL2, email);
        values.put(USERS_COL3, password);
        values.put(USERS_COL5, mobile);
        values.put(USERS_COL4, name);
        values.put(USERS_COL6, type);
        long result = db.insert(USERS, null, values);
        return result != -1;
    }

    public User findByUserId(String userid) {
        String selectQuery = "SELECT * FROM " + USERS + " WHERE " + USERS_COL1 + "='" + userid + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(USERS_COL1)));
            user.setName(cursor.getString(cursor.getColumnIndex(USERS_COL4)));
            user.setMobile(cursor.getString(cursor.getColumnIndex(USERS_COL5)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(USERS_COL2)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(USERS_COL3)));
            return user;
        }
        return null;
    }

    public void updateByUserId(String userId,String name, String email, String password, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERS_COL2,email);
        cv.put(USERS_COL4,name);
        cv.put(USERS_COL5,mobile);
        cv.put(USERS_COL3,password);
        db.update(USERS, cv, USERS_COL1+" = ?", new String[]{userId});
    }

    public boolean deleteByUserId(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USERS, USERS_COL1 +" = ?", new String[]{userId}) > 0;
    }

    public boolean addDoctor(String name, String mobile, String specialty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DOCTOR_COL2, name);
        values.put(DOCTOR_COL3, mobile);
        values.put(DOCTOR_COL4, specialty);
        long result = db.insert(DOCTOR, null, values);
        return result != -1;
    }

    public boolean deleteDoctor(String doctorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DOCTOR, DOCTOR_COL1 +" = ?", new String[]{doctorId}) > 0;
    }

    public void updateDoctor(String doctorId, String name, String mobile, String specialty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DOCTOR_COL2, name);
        values.put(DOCTOR_COL3, mobile);
        values.put(DOCTOR_COL4, specialty);
        int update = db.update(DOCTOR, values, DOCTOR_COL1 + " = ?", new String[]{doctorId});
        System.out.println(update);
    }

    public Doctor findById(String doctorId){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+DOCTOR+" WHERE "+DOCTOR_COL1+"=?";
        Cursor c = db.rawQuery(selectQuery, new String[]{ doctorId });
        if (c.moveToFirst()) {
            Doctor doctor = new Doctor();
            doctor.setId(c.getInt(c.getColumnIndex(DOCTOR_COL1)));
            doctor.setName(c.getString(c.getColumnIndex(DOCTOR_COL2)));
            doctor.setMobile(c.getString(c.getColumnIndex(DOCTOR_COL3)));
            doctor.setSpecialty(c.getString(c.getColumnIndex(DOCTOR_COL4)));
            return doctor;
        }
        c.close();
        return null;
    }

    public List<Doctor> findAll() {
        List<Doctor> all = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DOCTOR;
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Doctor doctor = new Doctor();
            doctor.setId(c.getInt(c.getColumnIndex(DOCTOR_COL1)));
            doctor.setName(c.getString(c.getColumnIndex(DOCTOR_COL2)));
            doctor.setMobile(c.getString(c.getColumnIndex(DOCTOR_COL3)));
            doctor.setSpecialty(c.getString(c.getColumnIndex(DOCTOR_COL4)));
            System.out.println(doctor.toString());
            all.add(doctor);
            c.moveToNext();
        }
        return all;
    }

    public boolean addDrug(String name, String qty, String desc,String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DRUG_COL2, name);
        values.put(DRUG_COL3, qty);
        values.put(DRUG_COL4, desc);
        values.put(DRUG_COL5, price);
        long result = db.insert(DRUG, null, values);
        return result != -1;
    }

    public boolean deleteDrug(String drugId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DRUG, DRUG_COL1 +" = ?", new String[]{drugId}) > 0;
    }

    public void updateDrug(String drugId, String name, String qty, String desc,String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DRUG_COL2, name);
        values.put(DRUG_COL3, qty);
        values.put(DRUG_COL4, desc);
        values.put(DRUG_COL5, price);
        int update = db.update(DRUG, values, DRUG_COL1 + " = ?", new String[]{drugId});
    }

    public Drug findDrugById(String drugId){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+DRUG+" WHERE "+DRUG_COL1+"=?";
        Cursor c = db.rawQuery(selectQuery, new String[]{ drugId });
        if (c.moveToFirst()) {
            Drug drug = new Drug();
            drug.setId(c.getInt(c.getColumnIndex(DRUG_COL1)));
            drug.setName(c.getString(c.getColumnIndex(DRUG_COL2)));
            drug.setDescription(c.getString(c.getColumnIndex(DRUG_COL4)));
            drug.setQty(c.getInt(c.getColumnIndex(DRUG_COL3)));
            drug.setPrice(c.getDouble(c.getColumnIndex(DRUG_COL5)));
            return drug;
        }
        c.close();
        return null;
    }

    public List<Drug> findAllDrugs() {
        List<Drug> all = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DRUG;
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Drug drug = new Drug();
            drug.setId(c.getInt(c.getColumnIndex(DRUG_COL1)));
            drug.setName(c.getString(c.getColumnIndex(DRUG_COL2)));
            drug.setDescription(c.getString(c.getColumnIndex(DRUG_COL4)));
            drug.setQty(c.getInt(c.getColumnIndex(DRUG_COL3)));
            drug.setPrice(c.getDouble(c.getColumnIndex(DRUG_COL5)));
            all.add(drug);
            c.moveToNext();
        }
        return all;
    }

    public boolean addToCart(Drug drug, int userid, int cQty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println(cQty);
        System.out.println(userid);
        values.put(PAYMENT_COL2, getFormatDate());
        values.put(PAYMENT_COL3, drug.getId());
        values.put(PAYMENT_COL4, cQty);
        values.put(PAYMENT_COL5, cQty*drug.getPrice());
        values.put(PAYMENT_COL6, "CART");
        values.put(PAYMENT_COL7, "PENDING");
        values.put(PAYMENT_COL8, userid);
        long result = db.insert(PAYMENT, null, values);
        updateItemQty(String.valueOf(drug.getId()),drug.getQty(),cQty,true);
        return result != -1;
    }

    public List<Payment> loadOrdersByType(String cart,String userId) {
        List<Payment> all = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+PAYMENT+" p INNER JOIN "+DRUG+" d ON p.drug_id = d.drug_id WHERE p."+PAYMENT_COL6+"=? AND p."+PAYMENT_COL8+"=?";
        System.out.println(selectQuery);
        Cursor c = db.rawQuery(selectQuery, new String[]{ cart,userId });
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Payment payment = new Payment();
            Drug drug = new Drug();
            drug.setId(c.getInt(8));
            drug.setName(c.getString(9));
            drug.setDescription(c.getString(11));
            drug.setQty(c.getInt(10));
            drug.setPrice(c.getDouble(12));
            payment.setDrug(drug);
            payment.setId(c.getInt(0));
            payment.setDate(c.getString(1));
            payment.setPrice(c.getDouble(4));
            payment.setQty(c.getInt(3));
            all.add(payment);
            payment.toString();
            c.moveToNext();
        }
        c.close();
        return all;
    }
    public Double getTotal(String cart,String userId) {
        List<Payment> all = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT SUM("+PAYMENT_COL5+") FROM "+PAYMENT+" WHERE "+PAYMENT_COL6+"=? AND "+PAYMENT_COL8+"=?";
        System.out.println(selectQuery);
        Cursor c = db.rawQuery(selectQuery, new String[]{ cart,userId });
        if (c.moveToFirst()) {
            return c.getDouble(0);
        }

        c.close();
        return 0.0;
    }

    public boolean removeFromCart(String paymentId, Drug drug, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        updateItemQty(String.valueOf(drug.getId()),drug.getQty(),qty,false);
        return db.delete(PAYMENT, PAYMENT_COL1 +" = ?", new String[]{paymentId}) > 0;
    }

    public void updateCart(String id, Drug drug, int cQty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAYMENT_COL4, cQty);
        values.put(PAYMENT_COL5, cQty*drug.getPrice());
        int update = db.update(PAYMENT, values, PAYMENT_COL1 + " = ?", new String[]{id});
        updateItemQty(String.valueOf(drug.getId()),drug.getQty(),cQty,true);
    }

    public void updateItemQty(String drugId,int drugQty, int cQty,boolean isMin) {
        int qty;
        if(isMin){
             qty = drugQty-cQty;
        }else {
            qty = drugQty+cQty;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DRUG_COL3, qty);
        int update = db.update(DRUG, values, DRUG_COL1 + " = ?", new String[]{drugId});
    }

    public void buyAll(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAYMENT_COL6, "PAID");
        int update = db.update(PAYMENT, values, PAYMENT_COL8 + " = ?", new String[]{userId});
    }

    public boolean addOrder(String name, String price, String userid,String payType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDERS_COL2, name);
        values.put(ORDERS_COL3, price);
        values.put(ORDERS_COL4, payType);
        values.put(ORDERS_COL5, userid);
        values.put(ORDERS_COL6, getFormatDate());
        long result = db.insert(ORDERS, null, values);
        return result != -1;
    }

    public List<Order> getAllOrders(String userId) {
        List<Order> all = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ORDERS + " WHERE "+ORDERS_COL5+"=?";
        Cursor c = db.rawQuery(selectQuery, new String[]{userId});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Order order = new Order();
            order.setId(c.getInt(c.getColumnIndex(ORDERS_COL1)));
            order.setName(c.getString(c.getColumnIndex(ORDERS_COL2)));
            order.setAmount(c.getDouble(c.getColumnIndex(ORDERS_COL3)));
            order.setDate(c.getString(c.getColumnIndex(ORDERS_COL6)));
            all.add(order);
            c.moveToNext();
        }
        return all;
    }

    public String getFormatDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return simpleDateFormat.format(date);
    }
}