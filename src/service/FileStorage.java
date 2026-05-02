package service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.Product;
import model.CartItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class FileStorage {
    private static final String FILE_NAME = "shop_data.json";
    private static final ObjectMapper mapper = new ObjectMapper() //класс для работы с джейсон, сопоставляет имена полей джейсона с именами геттеров и сеттеров
            .enable(SerializationFeature.INDENT_OUTPUT)//делает JSON с отступами и переносами
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);//не сохраняет поля, равные null


    //загрузка данных из файла
    public static ShopData load() {
        File file = new File(FILE_NAME);

        //файл может не существовать (первый запуск)
        if (!file.exists()) {
            System.out.println("Файл данных не найден. Создаём новый магазин...");
            return createInitialData();
        }

        try {
            //чтение файла — может не быть прав, диск отключён
            return mapper.readValue(file, ShopData.class);
        } catch (IOException e) {
            System.err.println(" Ошибка при чтении файла: " + e.getMessage());
            System.err.println("  Будет создан новый магазин.");
            return createInitialData();
        } catch (Exception e) {
            //JSON повреждён, структура изменилась
            System.err.println(" Файл повреждён или имеет неверный формат: " + e.getMessage());
            System.err.println("  Создаётся новый магазин.");
            return createInitialData();
        }
    }

    //Сохранение данных в файл
    public static void save(List<Product> products, List<CartItem> cart) {
        try {
            //запись в файл — может не хватить места, нет прав
            mapper.writeValue(new File(FILE_NAME), new ShopData(products, cart));
            System.out.println(" Данные успешно сохранены в " + FILE_NAME);
        } catch (IOException e) {
            System.err.println(" Ошибка при сохранении: " + e.getMessage());
        }
    }

    //создаём магазин при первом запуске
    private static ShopData createInitialData() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Хлеб", 50.0, 10));
        products.add(new Product(2, "Молоко", 80.0, 5));
        products.add(new Product(3, "Яйца (10 шт)", 90.0, 8));
        products.add(new Product(4, "Сыр", 200.0, 3));

        return new ShopData(products, new ArrayList<>());
    }
}