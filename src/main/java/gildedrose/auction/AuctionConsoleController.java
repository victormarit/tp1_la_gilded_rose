package gildedrose.auction;

import gildedrose.inventory.InMemoryItemsRepository;
import gildedrose.shop.InMemoryBalanceRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AuctionConsoleController {
    private static final AuctionInputBoundary auctionBoundary;

    private static final AuctionGateway auctionRepository;

    private static final List<Auction> auctions = new ArrayList<>();


    static {
        auctionRepository = InMemoryAuctionRepository.getInstance();
        auctionRepository.saveAuctions(auctions);
        auctionBoundary = new AuctionInteractor(InMemoryItemsRepository.getInstance(), auctionRepository, InMemoryBalanceRepository.getInstance());
    }

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
}
