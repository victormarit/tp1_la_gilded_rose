package gildedrose.shop;

public interface BalanceGateway {
    float getBalance();

    void saveBalance(float balance);
}
