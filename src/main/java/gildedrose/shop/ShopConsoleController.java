package gildedrose.shop;

import gildedrose.inventory.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class ShopConsoleController {

    private static final ShopInputBoundary shopBoundary;

    private static final ItemsGateway itemsRepository;

    private static final BalanceGateway balanceRepository;

    static List<Item> samples = List.of(
        new AgingItem("Aged Brie", 2, 0, 5),
        new EventItem("Backstage passes to a TAFKAL80ETC concert", 15, 20, 8),
        new ConjuredItem("Conjured Mana Cake", 3, 6, 10)
    );

    static {
        itemsRepository = InMemoryItemsRepository.getInstance();
        balanceRepository = InMemoryBalanceRepository.getInstance();
        itemsRepository.saveInventory(samples);
        balanceRepository.saveBalance(50);
        shopBoundary = new ShopInteractor(itemsRepository, balanceRepository);
    }

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private ShopConsoleController() {}

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
                boolean itemSold = shopBoundary.sellItem(request);

                if (itemSold) {
                    System.out.println("Item sold!");
                } else {
                    System.out.println("Item not found!");
                }
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
