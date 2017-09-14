package co.kpham.ilovezappos.POJOS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PricePOJO {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return ("{"+getDate()+"," +getAmount()+"}");
    }
}

