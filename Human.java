public abstract class Human {
    private String name;
    private Hand hand;

    // コンストラクタ
    public Human(String name) {
        setName(name);
        hand = new Hand();
    }

    // getter
    public String getName() {
        return this.name;
    }
    public Hand getHand() {
        return this.hand;
    }

    // setter
    public void setName(String name) {
        this.name = name;
    }
}
