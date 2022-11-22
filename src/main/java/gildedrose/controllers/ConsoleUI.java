package gildedrose.controllers;

import gildedrose.beans.*;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class ConsoleUI {
    static Shop shop = new Shop();

    static {
        List<Item> items = List.of(
            new GenericItem("+5 Dexterity Vest", 10, 20, 10),
            new AgingItem("Aged Brie", 2, 0, 5),
            new GenericItem("Elixir of the Mongoose", 5, 7, 6),
            new LegendaryItem("Sulfuras", 0, 80, 8),
            new LegendaryItem("Sulfuras", -1, 80, 12),
            new EventItem("Backstage passes", 15, 20, 15),
            new EventItem("Backstage passes", 10, 49, 10),
            new EventItem("Backstage passes", 5, 49, 9),
            new ConjuredItem("Test", 3, 6, 7),
            new ConjuredItem("Test", 0, 6, 4),
            new AgingItem("Aged Brie", -1, 49, 6),
            new AgingItem("Aged Brie", 10, 48, 8)
        );
        shop.updateItems(items);
    }

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        String userEntry = "";
        do {
            System.out.println("--- Welcome to Gilded Rose ---");
            userEntry = displayMenu();
        } while (!Objects.equals(userEntry, "4") && !Objects.equals(userEntry, "3") && !Objects.equals(userEntry, "2") && !Objects.equals(userEntry, "1"));

        switch (userEntry) {
            case "1" -> displayItems();
            case "2" -> displayBalance();
            case "3" -> {
                String type = "";
                String quality = "";
                do {
                    System.out.println("Enter item type: ");
                    type = reader.readLine();
                    System.out.println("Enter item quality: ");
                    quality = reader.readLine();
                } while (type.isEmpty() || quality.isEmpty());
                sellItem(type, Integer.parseInt(quality));
            }
            default -> {
                System.out.println("Goodbye!");
                return;
            }
        }
        main(args);
    }

    public static void displayItems() {
        for (Item item : shop.getItems()) {
            System.out.println("---");
            System.out.println("name : " + item.getName());
            System.out.println("sellIn : " + item.getSellIn());
            System.out.println("quality : " + item.getQuality());
        }
    }

    public static void displayBalance() {
        System.out.println("---");
        System.out.println("Balance : " + shop.getBalance());
        System.out.println("---");
    }

    public static void sellItem(String type, int quality) {
        shop.sellItem(type, quality);
        System.out.println("Item sold");
        shop.updateItemsQuality();
    }

    public static String displayMenu() throws IOException {
        String userEntry = "";
        System.out.println("1. Display items");
        System.out.println("2. Display balance");
        System.out.println("3. Sell item");
        System.out.println("4. Exit");
        System.out.println("Your choice: ");
        userEntry = reader.readLine();
        return userEntry;
    }
}
