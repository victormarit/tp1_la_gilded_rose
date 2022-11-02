import gildedrose.Shop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    @Test
    void should_build() {
        Shop shop = new Shop();
        assertEquals(shop.getClass(), Shop.class);
    }
}
