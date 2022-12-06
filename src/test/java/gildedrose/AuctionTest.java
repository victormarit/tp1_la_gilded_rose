package gildedrose;

import gildedrose.auction.AuctionInteractor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuctionTest {
    @Test
    void should_build(){
        AuctionInteractor auctionInteractorLocal = new AuctionInteractor();
        assertEquals(AuctionInteractor.class, auctionInteractorLocal.getClass());
    }

    @Test
    void should_increase_value_by_10_percent(){
    }

    @Test
    void should_win_auction(){}

    @Test
    void should_increase_balance(){}

    //TODO add a balance repository
    //Update update shop to add balance repository





}
