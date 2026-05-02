package service;

import model.Product;
import model.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopService {
    private List<Product> products;
    private List<CartItem> cart;

    public ShopService(List<Product> products, List<CartItem> cart) {
        this.products = products;
        this.cart = cart;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public List<CartItem> getCart() {
        return new ArrayList<>(cart);
    }

    public Optional<Product> findProductById(long id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    // купить товар -> добавить в корзину
    public boolean addToCart(long productId, int quantity) {
        Optional<Product> productOpt = findProductById(productId);
        if (productOpt.isEmpty()) {
            return false;
        }

        Product product = productOpt.get();

        //проверяем остаток
        if (quantity > product.getStock()) {
            System.out.println(" Недостаточно товара на складе. Доступно: " + product.getStock());
            return false;
        }

        //ищем, есть ли уже такой товар в корзине
        for (CartItem item : cart) {
            if (item.getProductId() == productId) {
                item.setQuantity(item.getQuantity() + quantity);
                return true;
            }
        }

        cart.add(new CartItem(productId, quantity));
        return true;
    }

    //посчитать итоговую сумму корзины
    public double calculateTotal() {
        double total = 0.0;
        for (CartItem item : cart) {
            Optional<Product> product = findProductById(item.getProductId());
            if (product.isPresent()) {
                total += product.get().getPrice() * item.getQuantity();
            }
        }
        return total;
    }

    //оформить заказ: списать товары, очистить корзину
    public boolean checkout() {
        if (cart.isEmpty()) {
            System.out.println(" Корзина пуста!");
            return false;
        }

        //хватает ли товаров
        for (CartItem item : cart) {
            Optional<Product> productOpt = findProductById(item.getProductId());
            if (productOpt.isEmpty() || productOpt.get().getStock() < item.getQuantity()) {
                System.out.println(" Один из товаров закончился. Обновите корзину.");
                return false;
            }
        }

        //списываем
        for (CartItem item : cart) {
            Product product = findProductById(item.getProductId()).get();
            product.setStock(product.getStock() - item.getQuantity());
        }

        cart.clear();
        System.out.println(" Заказ оформлен! Товары списаны со склада.");
        return true;
    }

    public List<String> getCartDetails() {
        List<String> details = new ArrayList<>();
        for (CartItem item : cart) {
            Optional<Product> productOpt = findProductById(item.getProductId());
            if (productOpt.isPresent()) {
                Product p = productOpt.get();
                double lineTotal = p.getPrice() * item.getQuantity();
                details.add(String.format("  %s — %.0f руб. × %d = %.0f руб.",
                        p.getName(), p.getPrice(), item.getQuantity(), lineTotal));
            }
        }
        return details;
    }
}