package blackjack.domain;

public class Player extends Person {
    private final int betMoney;

    // TODO : Use builder pattern
    public Player(String name) {
        super(name);
        this.betMoney = 0;
    }

    public Player(int betMoney, String name) {
        super(name);
        this.betMoney = betMoney;
    }

    public Player(String name, int profit) {
        super(name, profit);
        this.betMoney = 0;
    }

    public Player(int betMoney, String name, int profit) {
        super(name, profit);
        this.betMoney = betMoney;
    }

    public Player(String name, CardBunch cardBunch) {
        super(name, cardBunch);
        this.betMoney = 0;
    }

    public Player(int betMoney, String name, CardBunch cardBunch) {
        super(name, cardBunch);
        this.betMoney = betMoney;
    }

    public Player(String name, int profit, CardBunch cardBunch) {
        super(name, profit, cardBunch);
        this.betMoney = 0;
    }

    public Player(int betMoney, String name, int profit, CardBunch cardBunch) {
        super(name, profit, cardBunch);
        this.betMoney = betMoney;
    }

    @Override
    public boolean canDrawCard() {
        return !cardBunch.isBlackJackScore() && !cardBunch.isBust();
    }

    public void playMatch(Dealer dealer) {
        int playerProfit = getMatchProfit(dealer);
        
        this.addProfit(playerProfit);
        dealer.addProfit(-playerProfit);
    }

    private Integer getMatchProfit(Person dealer) {
        return (int) (MatchScore.calcDividend(this.cardBunch, dealer.cardBunch) * betMoney);
    }
}
