public class Player extends Human {
    private int credits = 10000;

    // コンストラクタ
    public Player(String name) {
        super(name);
    }

    // setter
    public void setCredits(int credits) {
        this.credits = credits;
    }

    // getter
    public int getCredits() {
        return this.credits;
    }

    // ベット確定
    public void creditsBet(int betCredits) {
        this.credits -= betCredits;
    }

    // 配当受け取り
    public void creditsResult(int payoutCredits) {
        this.credits += payoutCredits;
    }
}