package gildedrose.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gildedrose.inventory.SellableItem;

public class Auction {
    private final SellableItem item;

    private int nbAuction = 0;

    private float currentPrice;

    public Auction(@JsonProperty("item") SellableItem item) {
        this.item = item;
        this.currentPrice = item.getValue()/2;
    }

    public void bid(float price) {
        if (price >= (currentPrice + (10*currentPrice/100)) && nbAuction < 3) {
            currentPrice = price;
            nbAuction++;
        }
    }

    @JsonIgnore
    public boolean isWon() {
        return nbAuction == 3;
    }

    public SellableItem getItem() {
        return item;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public int getNbAuction(){
        return nbAuction;
    }
}
