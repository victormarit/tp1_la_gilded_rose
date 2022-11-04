package gildedrose.beans;

public class ConjuredItem extends Item {

    public ConjuredItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
        this.multiplier = 2;
    }
}
