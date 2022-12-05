package gildedrose.auction;

import gildedrose.inventory.InMemoryItemsRepository;
import gildedrose.inventory.Item;
import gildedrose.inventory.ItemsGateway;
import gildedrose.shop.BalanceGateway;
import gildedrose.shop.InMemoryBalanceRepository;

import java.util.ArrayList;
import java.util.List;

public class AuctionInteractor implements AuctionInputBoundary {

    private final List<Auction> auctions = new ArrayList<>();

    private final ItemsGateway itemsRepository;

    private final AuctionGateway auctionRepository;

    private final BalanceGateway balanceRepository;

    private final AuctionOutputBoundary viewBoundary;

    public AuctionInteractor() {
        this.itemsRepository = InMemoryItemsRepository.getInstance();
        this.auctionRepository = InMemoryAuctionRepository.getInstance();
        this.auctionRepository.saveAuctions(auctions);
        this.balanceRepository = InMemoryBalanceRepository.getInstance();
        this.viewBoundary = new AuctionConsoleView();
    }

    @Override
    public void bid(AuctionBidRequest request) {
        Item item = itemsRepository.findItem(request.type(), request.quality());
    }

    @Override
    public boolean createAuction(AuctionRequest request) {
        Auction auction = null;
        Item item = itemsRepository.findItem(request.type(), request.quality());
        if (balanceRepository.getBalance() >= Float.intBitsToFloat(item.getValue())/2) {
            auction = new Auction(item);
            auctions.add(auction);
            auctionRepository.saveAuctions(auctions);
        }
        return auction != null;
    }

    @Override
    public void displayAuctions() {
        List<AuctionResponse> auctionResponses = new ArrayList<>();
        for (Auction auction : auctionRepository.getAuctions()) {
            auctionResponses.add(new AuctionResponse(auction.getItem().getName(), auction.getItem().getQuality(), auction.getItem().getValue()));
        }
        viewBoundary.displayAuctions(auctionResponses);
    }
}
