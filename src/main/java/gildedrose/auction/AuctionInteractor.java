package gildedrose.auction;

import gildedrose.inventory.Item;
import gildedrose.inventory.ItemsGateway;
import gildedrose.inventory.SellableItem;
import gildedrose.shop.BalanceGateway;

import java.util.ArrayList;
import java.util.List;

public class AuctionInteractor implements AuctionInputBoundary {

    private final ItemsGateway itemsRepository;

    private final AuctionGateway auctionRepository;

    private final BalanceGateway balanceRepository;

    private final AuctionOutputBoundary viewBoundary;

    public AuctionInteractor(ItemsGateway itemsRepository, AuctionGateway auctionRepository, BalanceGateway balanceRepository) {
        this.itemsRepository = itemsRepository;
        this.auctionRepository = auctionRepository;
        this.balanceRepository = balanceRepository;
        this.viewBoundary = new AuctionConsoleView();
    }

    @Override
    public void bid(AuctionBidRequest request) {
        Item item = itemsRepository.findItem(request.type(), request.quality());
        Auction auction = auctionRepository.getAuction(item.type, item.getQuality());
        auction.bid(request.bid());
        if (auction.isWon()) {
            balanceRepository.saveBalance(balanceRepository.getBalance() + request.bid());
            auctionRepository.removeAuction(auction);
        }
    }

    @Override
    public void createAuction(AuctionRequest request) {
        SellableItem item = (SellableItem) itemsRepository.findItem(request.type(), request.quality());
        Auction auction = new Auction(item);
        auctionRepository.addAuction(auction);
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
