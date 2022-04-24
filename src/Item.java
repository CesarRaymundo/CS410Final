public class Item {
    private int id;
    private String code;
    private String description;
    private double price;
    private int inventoryAmount;

    public Item(int id, String code, String description, double price, int inventoryAmount) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.inventoryAmount = inventoryAmount;
    }

    public int getItem_id() {
        return id;
    }
    public String getItem_code() {
        return code;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public int getInventoryAmount() {
        return inventoryAmount;
    }

    public String toString() {
        return String.format("(%s, %s, %s, %s, %s)", id, code, description, price, inventoryAmount);
    }
}