package lk.example.pharmacyapp.db;

public class Payment {
    private int id;
    private Drug drug;
    private int qty;
    private String date;
    private Double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", drug=" + drug +
                ", qty=" + qty +
                ", date='" + date + '\'' +
                ", price=" + price +
                '}';
    }
}
