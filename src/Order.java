import java.util.Date;
import java.sql.Timestamp;

public class Order {
    private int id;
    private String code;
    private int quantity;
    private Timestamp orderTimestamp;
    private double orderTotal;

    public Order(int id, String code, int quantity){
        this.id = id;
        this.code = code;
        this.quantity = quantity;
        orderTotal = 0;
        orderTimestamp = null;
    }
    Order(int order_id, String item_code, int quantity, Timestamp order_timestamp){
        this.id = order_id;
        this.code = item_code;
        this.quantity = quantity;
        this.orderTimestamp = order_timestamp;
    }

    public int getOrderID() {
        return id;
    }

    public Date getOrderTimestamp() {
        return orderTimestamp;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrdercode() {
        return code;
    }

    public void setTimestamp(Timestamp orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public String toString() {
        return String.format("%s, %s, %s, %s, %s", id, code, quantity, orderTimestamp, orderTotal);
    }
}