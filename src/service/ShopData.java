package service;

import model.Product;
import model.CartItem;

import java.util.List;

public class ShopData {
    public List<Product> products;
    public List<CartItem> cart;

    public ShopData() {}

    public ShopData(List<Product> products, List<CartItem> cart) {
        this.products = products;
        this.cart = cart;
    }
}