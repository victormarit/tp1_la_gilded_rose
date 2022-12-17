package gildedrose.shop;

public class InMemoryBalanceRepository implements BalanceGateway{
    private float balance;

    private static final InMemoryBalanceRepository INSTANCE = new InMemoryBalanceRepository();

    /** Point d'accès pour l'instance unique du singleton */
    public static InMemoryBalanceRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public float getBalance() {
        return balance;
    }

    @Override
    public void saveBalance(float balance) {
        this.balance = balance;
    }
}
