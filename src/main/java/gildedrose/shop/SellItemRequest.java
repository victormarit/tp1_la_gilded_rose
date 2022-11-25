package gildedrose.shop;

public class SellItemRequest {
    private String type;
    private int quality;

    public SellItemRequest(String type, int quality) {
        this.type = type;
        this.quality = quality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
