package blackjack.domain;

import blackjack.dto.NameInfo;
import blackjack.dto.PersonCardsInfo;
import blackjack.dto.PersonMatchProfitInfo;
import blackjack.dto.ScoreInfo;

import java.util.stream.IntStream;

public abstract class Person {
    public static final int INIT_CARD_CNT = 2;

    protected final String name;
    protected int profit;
    protected final CardBunch cardBunch;

    protected Person(String name) {
        this.name = name;
        this.profit = 0;
        this.cardBunch = new CardBunch();
    }

    protected Person(String name, int profit) {
        this.name = name;
        this.profit = profit;
        this.cardBunch = new CardBunch();
    }

    protected Person(String name, CardBunch cardBunch) {
        this.name = name;
        this.profit = 0;
        this.cardBunch = cardBunch;
    }

    protected Person(String name, int profit, CardBunch cardBunch) {
        this.name = name;
        this.profit = profit;
        this.cardBunch = cardBunch;
    }

    public abstract boolean canDrawCard();

    public void initializeFromDeck(Deck deck) {
        IntStream
            .range(0, INIT_CARD_CNT)
            .forEach(i -> drawCardFromDeck(deck));
    }

    public void drawCardFromDeck(Deck deck) {
        cardBunch.addCard(
            deck.drawCard()
        );
    }

    public void addProfit(int profit) {
        this.profit += profit;
    }

    public NameInfo getNameInfo() {
        return new NameInfo(name);
    }

    public PersonCardsInfo getPersonCardsInfo() {
        return new PersonCardsInfo(name, cardBunch.getCardsName());
    }

    public ScoreInfo getScoreInfo() {
        return new ScoreInfo(name, cardBunch.getCardsName(), cardBunch.calcScore());
    }

    public PersonMatchProfitInfo getMatchProfitInfo() {
        return new PersonMatchProfitInfo(name, profit);
    }
}
