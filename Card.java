public class Card {
    private int suit;
    private int rank;

    // コンストラクタ
    public Card(int suit, int rank) {
        setSuit(suit);
        setRank(rank);
    }

    // getter
    public int getSuit() {
        return this.suit;
    }

    public int getRank() {
        return this.rank;
    }

    public int getValue() {
        if (getRank() == 1) {
            return 11;
        } else if (getRank() > 9) {
            return 10;
        } else {
            return getRank();
        }
    }

    public String getSuitSymbol() {
        if (this.suit == 0) {
            return "\u2665"; // ハート
        } else if (this.suit == 1) {
            return "\u2666"; // ダイヤ
        } else if (this.suit == 2) {
            return "\u2663"; // クラブ
        } else {
            return "\u2660"; // スペード
        }
    }

    public String getRankDisplay() {
        if (this.rank == 1) {
            return "A";
        } else if (this.rank == 11) {
            return "J";
        } else if (this.rank == 12) {
            return "Q";
        } else if (this.rank == 13) {
            return "K";
        } else {
            return "%s".formatted(this.rank);
        }
    }

    // setter
    public void setSuit(int suit) {
        this.suit = suit;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}