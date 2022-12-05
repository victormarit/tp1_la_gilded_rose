package gildedrose.auction;

public interface AuctionInputBoundary {
    void bid(AuctionBidRequest request);

    boolean createAuction(AuctionRequest request);

    void displayAuctions();
}
