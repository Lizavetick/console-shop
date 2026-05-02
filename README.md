# ConsoleShop

**ConsoleShop** is a simple console-based application written in Java that simulates the core functionality of an online store. It allows users to browse a product catalog, manage a shopping cart, and complete orders with stock validation.

The application persists data between sessions using JSON storage.

---

## Download

Clone the repository:

```sh
git clone https://github.com/Lizavetick/console-shop
cd console-shop
```

---

## Build & Run

### Requirements
- **Java JDK 11+**
- **Maven** (for dependency management)

### Build

Compile the project:

```sh
mvn clean compile
```

### Run

Start the application:

```sh
mvn exec:java -Dexec.mainClass="Main"
```

---

## How to Use

Upon launching, you will see a menu with 5 options:

| Option | Action | Description |
|--------|--------|-------------|
| `1` | View Catalog | Display all available products with IDs, prices, and stock |
| `2` | Buy Product | Add a product to cart by ID and quantity |
| `3` | View Cart | Show cart contents and calculate total price |
| `4` | Checkout | Finalize order: validate stock, deduct items, clear cart |
| `5` | Save & Exit | Persist data to `shop_data.json` and exit |

### Example Session

```
=== МЕНЮ ===
1. Посмотреть каталог
2. Купить товар
3. Посмотреть корзину
4. Оформить заказ
5. Сохранить и выйти
Выберите действие: 1

 КАТАЛОГ ТОВАРОВ:
   1. Хлеб — 50 руб. (осталось: 10)
   2. Молоко — 80 руб. (осталось: 5)
   3. Яйца (10 шт) — 90 руб. (осталось: 8)
   4. Сыр — 200 руб. (осталось: 3)
```

---

## Planned Features

1. **Financial accuracy**: Replace `double` with `BigDecimal` for price calculations.
2. **REST API**: Migrate to Spring Boot for web access.
3. **User accounts**: Support multiple carts and order history.

