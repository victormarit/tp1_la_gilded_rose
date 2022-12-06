package gildedrose.auction;

import gildedrose.inventory.Item;

public class Auction {
    private final Item item;

    private int nbAuction = 0;

    private float currentPrice;

    public Auction(Item item) {
        this.item = item;
        this.currentPrice = item.getValue()/2;
    }

    public void bid(float price) {
        if (price >= (currentPrice + (10*currentPrice/100)) && nbAuction < 3) {
            currentPrice = price;
            nbAuction++;
        }
    }

    public boolean isWon() {
        return nbAuction == 3;
    }

    public Item getItem() {
        return item;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public int getNbAuction(){
        return nbAuction;
    }
}
