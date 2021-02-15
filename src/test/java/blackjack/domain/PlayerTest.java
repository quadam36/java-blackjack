package blackjack.domain;

import blackjack.dto.NameInfo;
import blackjack.dto.PersonCardsInfo;
import blackjack.dto.PersonMatchProfitInfo;
import blackjack.dto.ScoreInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    private static Player testPlayer;

    @BeforeAll
    static void beforeAll() {
        testPlayer = new Player(
            "player",
            new CardBunch(
                List.of(1, 7, 10),
                Suit.HEARTS
            )
        );
    }

    @DisplayName("Check player return drawable status well")
    @ParameterizedTest
    @MethodSource("providerCanDrawCardParams")
    void canDrawCard(List<Integer> numbers, boolean canDrawCard) {
        Player player = new Player(
            "player",
            new CardBunch(
                numbers, Suit.HEARTS
            )
        );

        assertEquals(
            canDrawCard,
            player.canDrawCard()
        );
    }

    private static Stream<Arguments> providerCanDrawCardParams() {
        return Stream.of(
            Arguments.of(Arrays.asList(10, 10), true),
            Arguments.of(Arrays.asList(1, 10), false),
            Arguments.of(Arrays.asList(5, 6, 10), false),
            Arguments.of(Arrays.asList(10, 10, 2), false)
        );
    }

    @DisplayName("Check Dealer initialized card from deck well")
    @Test
    void initializeFromDeck() {
        Player player = new Player("player");
        Deck deck = new Deck(
            new ArrayList<>() {{
                add(new Card(Denomination.ACE, Suit.HEARTS));
                add(new Card(Denomination.SEVEN, Suit.HEARTS));
            }}
        );
        player.initializeFromDeck(deck);

        assertEquals(
            new Player(
                "player",
                new CardBunch(
                    List.of(1, 7),
                    Suit.HEARTS
                )
            ).getScoreInfo(),
            player.getScoreInfo()
        );
    }

    @DisplayName("Check player draw card from deck well")
    @Test
    void drawCardFromDeck() {
        Player player = new Player("player");
        Deck deck = new Deck(
            new ArrayList<>() {{
                add(new Card(Denomination.ACE, Suit.HEARTS));
            }}
        );
        player.drawCardFromDeck(deck);

        assertEquals(
            new Player(
                "player",
                new CardBunch(
                    List.of(1),
                    Suit.HEARTS
                )
            ).getScoreInfo(),
            player.getScoreInfo()
        );
    }

    @DisplayName("Check player get proper match profit")
    @ParameterizedTest
    @MethodSource("providerGetPlayerMatchProfitInfoParams")
    void getPlayerMatchProfitInfo(List<Integer> playerNumbers, List<Integer> dealerNumbers, Integer profit) {
        Player player = new Player(
            10,
            "player",
            new CardBunch(
                playerNumbers, Suit.HEARTS
            )
        );
        Dealer dealer = new Dealer(
            new CardBunch(
                dealerNumbers, Suit.HEARTS
            )
        );

        player.playMatch(dealer);

        assertEquals(
            new PersonMatchProfitInfo("player", profit),
            player.getMatchProfitInfo()
        );
    }

    private static Stream<Arguments> providerGetPlayerMatchProfitInfoParams() {
        return Stream.of(
            Arguments.of(List.of(10, 10, 2), List.of(10, 10), -10),
            Arguments.of(List.of(10, 10), List.of(10, 10, 2), 10),
            Arguments.of(List.of(1, 10), List.of(1, 10), 0),
            Arguments.of(List.of(1, 10), List.of(10, 10), 15),
            Arguments.of(List.of(10, 10), List.of(1, 10), -10),
            Arguments.of(List.of(9, 10), List.of(9, 10), 0),
            Arguments.of(List.of(10, 10), List.of(10, 9), 10),
            Arguments.of(List.of(10, 9), List.of(10, 10), -10)
        );
    }

    @DisplayName("Check player return correct player name information")
    @Test
    void getNameInfo() {
        assertEquals(
            new NameInfo("player"),
            testPlayer.getNameInfo()
        );
    }

    @DisplayName("Check player return correct player cards information")
    @Test
    void getPersonCardsInfo() {
        assertEquals(
            new PersonCardsInfo("player", List.of("A하트", "7하트", "10하트")),
            testPlayer.getPersonCardsInfo()
        );
    }

    @DisplayName("Check player return correct player score information")
    @Test
    void getScoreInfo() {
        assertEquals(
            new ScoreInfo("player", List.of("A하트", "7하트", "10하트"), 18),
            testPlayer.getScoreInfo()
        );
    }
}