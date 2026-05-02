package model;

//просто данные для корзины: что и сколько
public class CartItem {
    private long productId;
    private int quantity;

    //для джексона
    public CartItem(){}

    public CartItem(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getProductId() { return productId; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть > 0");
        }
        this.quantity = quantity;
    }
}