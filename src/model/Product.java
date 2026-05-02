package model;

public class Product {
    private long id;
    private String name;
    private double price;
    private int stock;

    //для джексона
    public Product(){}

    public Product(long id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }


    public long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Остаток не может быть отрицательным");
        }
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("%d. %s — %.0f руб. (осталось: %d)", id, name, price, stock);
    }
}