package gildedrose.auction;

import java.util.List;

public interface AuctionGateway {
    List<Auction> getAuctions();

    Auction getAuction(String type, int quality);

    void saveAuctions(List<Auction> auction);
}
