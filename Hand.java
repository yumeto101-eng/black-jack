import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cardList;

    // コンストラクタ
    public Hand() {
        cardList = new ArrayList<>();
    }

    // カード追加
    public void addCard(Card card) {
        cardList.add(card);
    }

    // スコア計算
    public int getScore() {
        int score = 0;
        int aceCount = 0;
        for (Card card : cardList) {
            score += card.getValue();
            if (card.getRank() == 1) {
                aceCount++;
            }
        }

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }
        return score;
    }

    // バースト判定
    public boolean isBust() {
        if (getScore() > 21) {
            return true;
        } else {
            return false;
        }
    }

    // ブラックジャック判定
    public boolean isBlackjack() {
        if (getScore() == 21 && hasAce() && cardList.size() == 2) {
            return true;
        } else {
            return false;
        }
    }

    // Aが含まれているか判定
    public boolean hasAce() {
        boolean result = false;
        for (Card card : cardList) {
            if (card.getRank() == 1) {
                result = true;
            }
        }
        return result;
    }

    // getter
    public List<Card> getCardList() {
        return this.cardList;
    }

    public Card getCard(int index) {
        return this.cardList.get(index);
    }
}
