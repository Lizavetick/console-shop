import model.Product;
import service.FileStorage;
import service.ShopData;
import service.ShopService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println(" Добро пожаловать в магазин «ВСЁ ДЛЯ ДОМА»!");

        ShopData data = FileStorage.load();
        ShopService shop = new ShopService(data.products, data.cart);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Посмотреть каталог");
            System.out.println("2. Купить товар");
            System.out.println("3. Посмотреть корзину");
            System.out.println("4. Оформить заказ");
            System.out.println("5. Сохранить и выйти");
            System.out.print("Выберите действие: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1 -> showCatalog(shop);
                    case 2 -> buyProduct(shop, scanner);
                    case 3 -> showCart(shop);
                    case 4 -> shop.checkout();
                    case 5 -> {

                        FileStorage.save(shop.getProducts(), shop.getCart());
                        System.out.println("До новых покупок! ");
                        running = false;
                    }
                    default -> System.out.println(" Неверный выбор. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                //не число
                System.out.println("  Пожалуйста, введите цифру от 1 до 5.");
            } catch (Exception e) {
                System.err.println(" Непредвиденная ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void showCatalog(ShopService shop) {
        System.out.println("\n КАТАЛОГ ТОВАРОВ:");
        List<Product> products = shop.getProducts();
        if (products.isEmpty()) {
            System.out.println("  Пусто ");
        } else {
            for (Product p : products) {
                System.out.println("  " + p);
            }
        }
    }

    private static void buyProduct(ShopService shop, Scanner scanner) {
        showCatalog(shop);
        System.out.print("\nВведите ID товара для покупки: ");

        try {
            long id = Long.parseLong(scanner.nextLine().trim());
            System.out.print("Сколько штук? ");
            int qty = Integer.parseInt(scanner.nextLine().trim());

            if (qty <= 0) {
                System.out.println("Количество должно быть больше 0.");
                return;
            }

            if (shop.addToCart(id, qty)) {
                System.out.println(" Товар добавлен в корзину!");
            } else {
                System.out.println(" Товар с ID " + id + " не найден.");
            }

        } catch (NumberFormatException e) {
            //ввели "два" вместо "2"
            System.out.println("  ID и количество должны быть целыми числами.");
        } catch (IllegalArgumentException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    private static void showCart(ShopService shop) {
        List<String> cartItems = shop.getCartDetails();
        System.out.println("\n ВАША КОРЗИНА:");
        if (cartItems.isEmpty()) {
            System.out.println("  Пусто — добавьте что-нибудь!");
        } else {
            for (String item : cartItems) {
                System.out.println(item);
            }
            System.out.printf("ИТОГО: %.0f руб.\n", shop.calculateTotal());
        }
    }
}