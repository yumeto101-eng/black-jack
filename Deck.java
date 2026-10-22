import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    Random random = new Random();
    private List<Card> cardList;
    private Card card;
    private int cutCard; // デッキをリセットする目印

    // コンストラクタ(デッキ作成)
    public Deck() {
        cardList = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            for (int j = 1; j <= 13; j++) {
                card = new Card(i, j);
                cardList.add(card);
            }
        }
        cutCard = random.nextInt(15, 21);
    }

    // getter
    public List<Card> getCardList() {
        return this.cardList;
    }

    public int getCutCard() {
        return this.cutCard;
    }

    // デッキをシャッフル
    public void shuffle() {
        Collections.shuffle(this.cardList);
    }

    // カード配布
    public Card draw() {
        Card card;
        card = this.getCardList().remove(0);
        return card;
    }
}
