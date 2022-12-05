package gildedrose.auction;

import java.util.Collections;
import java.util.List;

public class InMemoryAuctionRepository implements AuctionGateway {

    private static final InMemoryAuctionRepository INSTANCE = new InMemoryAuctionRepository();

    public static InMemoryAuctionRepository getInstance()
    {
        return INSTANCE;
    }

    private List<Auction> auctions;

    @Override
    public List<Auction> getAuctions() {
        return Collections.unmodifiableList(auctions);
    }

    @Override
    public Auction getAuction(String type, int quality) {
        return auctions.stream()
                .filter(auction -> auction.getItem().type.equals(type) && auction.getItem().getQuality() == quality)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public void saveAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }
}
