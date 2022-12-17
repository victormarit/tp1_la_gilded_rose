package gildedrose.auction;

public interface AuctionInputBoundary {
    void bid(AuctionBidRequest request);

    void createAuction(AuctionRequest request);

    void displayAuctions();
}
