package gildedrose.auction;

import java.util.List;

public interface AuctionOutputBoundary {
    void displayAuction(AuctionResponse response);

    void displayAuctions(List<AuctionResponse> responseList);
}
