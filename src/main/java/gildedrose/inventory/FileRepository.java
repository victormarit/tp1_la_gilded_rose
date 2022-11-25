package gildedrose.inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;

import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public class FileRepository extends ItemsGateway {



    ObjectMapper mapper = new ObjectMapper();

    private static FileRepository INSTANCE = new FileRepository();

    /** Point d'accès pour l'instance unique du singleton */
    public static FileRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public List<Item> getInventory() {

        try (FileReader reader = new FileReader("items.json")) {
            return mapper.readValue(reader, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void saveInventory(List<Item> items) {
        try {
            mapper.registerSubtypes(new NamedType(AgingItem.class, "AgingItem"));
            mapper.registerSubtypes(new NamedType(LegendaryItem.class, "LegendaryItem"));
            mapper.registerSubtypes(new NamedType(ConjuredItem.class, "ConjuredItem"));
            mapper.registerSubtypes(new NamedType(EventItem.class, "EventItem"));
            mapper.registerSubtypes(new NamedType(GenericItem.class, "GenericItem"));
            mapper.writeValue(new File("items.json"), items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item findItem(String type, int quality) {
        Item itemResult = null;
        try (FileReader reader = new FileReader("items.json")) {
            List<Item> items = mapper.readValue(reader, new TypeReference<>() {
            });
            itemResult = items.stream().filter(item -> item.getClass().getSimpleName().equals(type) && item.getQuality() == quality).findFirst().orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemResult;
    }
}
