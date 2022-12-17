package gildedrose.auction;

import gildedrose.inventory.*;
import gildedrose.shop.BalanceGateway;
import gildedrose.shop.FileBalanceRepository;
import gildedrose.shop.InMemoryBalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileAuctionTest {
    List<Item> items = List.of(
        new GenericItem("+5 Dexterity Vest", 10, 20, 10)
    );

    private AuctionInputBoundary auctionBoundary;

    private AuctionGateway auctionRepository;

    private BalanceGateway balanceRepository;

    @BeforeEach
    void setUp() {
        auctionRepository = FileAuctionRepository.getInstance();
        auctionRepository.saveAuctions(new ArrayList<>());
        ItemsGateway itemsRepository = FileItemsRepository.getInstance();
        itemsRepository.saveInventory(items);
        balanceRepository = FileBalanceRepository.getInstance();
        balanceRepository.saveBalance(50);
        auctionBoundary = new AuctionInteractor(itemsRepository, auctionRepository, balanceRepository);
    }

    @Test
    void should_build(){
        AuctionInteractor auctionInteractorLocal = new AuctionInteractor(FileItemsRepository.getInstance(), FileAuctionRepository.getInstance(), FileBalanceRepository.getInstance());
        assertEquals(AuctionInteractor.class, auctionInteractorLocal.getClass());
    }

    @Test
    void should_increase_value_by_10_percent_without_interactor(){
        if (items.get(0) instanceof SellableItem sellableItem) {
            Auction auction = new Auction(sellableItem);
            auction.bid((sellableItem.getValue() / 2) + (10 * (sellableItem.getValue() / 2) / 100));
            assertEquals((sellableItem.getValue() / 2) + (10 * (sellableItem.getValue() / 2) / 100), auction.getCurrentPrice());
        }
    }

    @Test
    void should_increase_value_by_10_percent_with_interactor(){
        AuctionRequest auctionRequest = new AuctionRequest(items.get(0).type, items.get(0).getQuality());
        auctionBoundary.createAuction(auctionRequest);
        Auction auction = auctionRepository.getAuction(items.get(0).type, items.get(0).getQuality());
        AuctionBidRequest request = new AuctionBidRequest(items.get(0).type, items.get(0).getQuality(), auction.getCurrentPrice() + (10*auction.getCurrentPrice()/100));
        auctionBoundary.bid(request);
        if (items.get(0) instanceof SellableItem sellableItem) {
            auction = auctionRepository.getAuction(sellableItem.type, sellableItem.getQuality());
            assertEquals(sellableItem.getValue() / 2 + (10 * (sellableItem.getValue() / 2) / 100), auction.getCurrentPrice());
        }
    }

    @Test
    void shouldnt_take_bid_without_interactor(){
        if(items.get(0) instanceof SellableItem sellableItem){
            Auction auction = new Auction(sellableItem);
            auction.bid((sellableItem.getValue()/2)+(5*(sellableItem.getValue()/2)/100));
            assertEquals(0, auction.getNbAuction());
        }

    }

    @Test
    void shouldnt_take_bid_with_interactor(){
        AuctionRequest auctionRequest = new AuctionRequest(items.get(0).type, items.get(0).getQuality());
        auctionBoundary.createAuction(auctionRequest);
        Auction auction = auctionRepository.getAuction(items.get(0).type, items.get(0).getQuality());
        AuctionBidRequest request = new AuctionBidRequest(items.get(0).type, items.get(0).getQuality(), auction.getCurrentPrice() + (5*auction.getCurrentPrice()/100));
        auctionBoundary.bid(request);
        if (items.get(0) instanceof SellableItem sellableItem) {
            assertNotEquals((sellableItem.getValue() / 2) * 1.1, auction.getCurrentPrice());
        }
    }
}
