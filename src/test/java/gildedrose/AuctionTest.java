package gildedrose;

import gildedrose.auction.*;
import gildedrose.inventory.*;
import gildedrose.inventory.InMemoryItemsRepository;
import gildedrose.shop.BalanceGateway;
import gildedrose.shop.InMemoryBalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuctionTest {
    List<Item> items = List.of(
        new GenericItem("+5 Dexterity Vest", 10, 20, 10)
    );

    private AuctionInputBoundary auctionBoundary;

    private AuctionGateway auctionRepository;

    private BalanceGateway balanceRepository;

    @BeforeEach
    void setUp() {
        auctionRepository = InMemoryAuctionRepository.getInstance();
        auctionRepository.saveAuctions(new ArrayList<>());
        ItemsGateway itemsRepository = InMemoryItemsRepository.getInstance();
        itemsRepository.saveInventory(items);
        balanceRepository = InMemoryBalanceRepository.getInstance();
        balanceRepository.saveBalance(50);
        auctionBoundary = new AuctionInteractor(itemsRepository, auctionRepository, balanceRepository);
    }

    @Test
    void should_build(){
        AuctionInteractor auctionInteractorLocal = new AuctionInteractor(InMemoryItemsRepository.getInstance(), InMemoryAuctionRepository.getInstance(), InMemoryBalanceRepository.getInstance());
        assertEquals(AuctionInteractor.class, auctionInteractorLocal.getClass());
    }

    @Test
    void should_increase_value_by_10_percent_without_interactor(){
        Auction auction = new Auction(items.get(0));
        auction.bid((items.get(0).getValue()/2)+(10*(items.get(0).getValue()/2)/100));
        assertEquals((items.get(0).getValue()/2)+(10*(items.get(0).getValue()/2)/100), auction.getCurrentPrice());
    }

    @Test
    void should_increase_value_by_10_percent_with_interactor(){
        AuctionRequest auctionRequest = new AuctionRequest(items.get(0).type, items.get(0).getQuality());
        auctionBoundary.createAuction(auctionRequest);
        Auction auction = auctionRepository.getAuction(items.get(0).type, items.get(0).getQuality());
        AuctionBidRequest request = new AuctionBidRequest(items.get(0).type, items.get(0).getQuality(), auction.getCurrentPrice() + (10*auction.getCurrentPrice()/100));
        auctionBoundary.bid(request);
        assertEquals(items.get(0).getValue()/2+(10*(items.get(0).getValue()/2)/100), auction.getCurrentPrice());
    }

    @Test
    void shouldnt_take_bid_without_interactor(){
        Auction auction = new Auction(items.get(0));
        auction.bid((items.get(0).getValue()/2)+(5*(items.get(0).getValue()/2)/100));
        assertEquals(0, auction.getNbAuction());
    }

    @Test
    void shouldnt_take_bid_with_interactor(){
        AuctionRequest auctionRequest = new AuctionRequest(items.get(0).type, items.get(0).getQuality());
        auctionBoundary.createAuction(auctionRequest);
        Auction auction = auctionRepository.getAuction(items.get(0).type, items.get(0).getQuality());
        AuctionBidRequest request = new AuctionBidRequest(items.get(0).type, items.get(0).getQuality(), auction.getCurrentPrice() + (5*auction.getCurrentPrice()/100));
        auctionBoundary.bid(request);
        assertNotEquals((items.get(0).getValue()/2) * 1.1, auction.getCurrentPrice());
    }

    @Test
    void should_win_auction_and_increase_balance(){
        AuctionRequest auctionRequest = new AuctionRequest(items.get(0).type, items.get(0).getQuality());
        auctionBoundary.createAuction(auctionRequest);
        Auction auction = auctionRepository.getAuction(items.get(0).type, items.get(0).getQuality());
        float expectedPrice = auction.getCurrentPrice();
        for (int i = 0; i < 3; i++) {
            expectedPrice += expectedPrice * 0.1;
            AuctionBidRequest request = new AuctionBidRequest(items.get(0).type, items.get(0).getQuality(), (float) (auction.getCurrentPrice() * 1.1));
            auctionBoundary.bid(request);
        }
        assertTrue(auction.isWon());
        assertEquals(50 + expectedPrice, balanceRepository.getBalance());
    }
}
