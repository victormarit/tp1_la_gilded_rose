package gildedrose.shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ConsoleController {

    private static final ShopInputBoundary shopBoundary;

    static {
        shopBoundary = new ShopInteractor();
    }

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private ConsoleController() {}

    public static void main(String[] args) throws IOException {
        String userEntry;
        do {
            System.out.println("--- Welcome to Gilded Rose ---");
            userEntry = displayMenu();
        } while (!Objects.equals(userEntry, "4") && !Objects.equals(userEntry, "3") && !Objects.equals(userEntry, "2") && !Objects.equals(userEntry, "1"));

        switch (userEntry) {
            case "1" -> shopBoundary.displayInventory();
            case "2" -> shopBoundary.displayBalance();
            case "3" -> {
                String type;
                String quality;
                do {
                    System.out.println("Enter item type: ");
                    type = reader.readLine();
                    System.out.println("Enter item quality: ");
                    quality = reader.readLine();
                } while (type.isEmpty() || quality.isEmpty());
                SellItemRequest request = new SellItemRequest(type, Integer.parseInt(quality));
                shopBoundary.sellItem(request);
                System.out.println("Item sold");
            }
            default -> {
                System.out.println("Goodbye!");
                return;
            }
        }
        main(args);
    }

    public static String displayMenu() throws IOException {
        String userEntry;
        System.out.println("1. Display items");
        System.out.println("2. Display balance");
        System.out.println("3. Sell item");
        System.out.println("4. Exit");
        System.out.println("Your choice: ");
        userEntry = reader.readLine();
        return userEntry;
    }
}
