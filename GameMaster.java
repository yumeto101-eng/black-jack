import java.util.Scanner;

public class GameMaster {
    public Scanner scanner;
    private Dealer dealer;
    private Player player;
    private Deck deck;
    private int bet = 0;
    private int payout = 0;
    private int result = 0;
    private int round = 1;
    private String answer;
    private boolean isContinue = true;

    // コンストラクタ
    public GameMaster(Scanner scanner) {
        this.scanner = scanner;
        this.dealer = new Dealer("DEALER");
        this.player = new Player("PLAYER");
        this.deck = new Deck(); // デッキ生成
        deck.shuffle();
    }

    // ゲーム進行
    public void start() throws Exception {
        banner("BLACK JACK", '=');
        while (isContinue) {
            System.out.printf("\n--------------- ROUND %02d ---------------\n\n", round);

            // 手札の初期化
            player.getHand().getCardList().clear();
            dealer.getHand().getCardList().clear();

            // カードの残り枚数の確認
            if (deck.getCardList().size() <= deck.getCutCard()) {
                System.out.println("カードの残り枚数が少なくなったので、新しいデッキを生成します。");
                this.deck = new Deck();

                Thread.sleep(800);
                deck.shuffle();
                System.out.println("デッキをシャッフルしました。");
            }

            Thread.sleep(500);
            placeBet();

            // 最初のカードを2枚配布
            Thread.sleep(500);
            System.out.println(player.getName() + "と" + dealer.getName() + "に2枚ずつカードを配ります。\n");
            initialCards();

            Thread.sleep(2000);
            banner(dealer.getName() + "'s First Card", '-');
            System.out.println("【" + dealer.getName() + "の1枚目のカード】");
            System.out.println();
            Thread.sleep(500);
            CardRender.flipCard(dealer.getHand().getCard(0));

            // プレイヤーのカード表示
            Thread.sleep(800);
            banner(player.getName() + "'s Cards", '-');
            System.out.println();
            Thread.sleep(500);
            CardRender.flipCard(player.getHand().getCard(0));

            Thread.sleep(1000);
            CardRender.flipCard(player.getHand().getCard(1));

            // ブラックジャック判定
            Thread.sleep(1000);
            if (player.getHand().isBlackjack()) {
                System.out.println("ブラックジャック！！");
            }

            System.out.println("\n合計：" + player.getHand().getScore());
            System.out.println();

            // プレイヤー
            if (!player.getHand().isBlackjack()) {
                playerTurn();
            }

            // ディーラー
            dealerTurn();

            // 勝敗判定
            Thread.sleep(1500);
            judge();

            // 再選判定
            if (player.getCredits() < 500) {
                System.out.println("クレジット残高が不足しています\n");
                System.out.println("=============== GAME OVER ===============\n");
                isContinue = false;
            } else {
                System.out.println("もう１戦しますか？");
                boolean boolAns = false;

                do {
                    System.out.println("1：続ける / 2：終わる");
                    System.out.print("選択 >> ");
                    answer = scanner.nextLine().trim();
                    System.out.println();

                    if ("2".equals(answer)) {
                        isContinue = false;
                        boolAns = true;
                    } else if ("1".equals(answer)) {
                        isContinue = true;
                        boolAns = true;
                    } else {
                        System.out.println("1または2を入力してください。\n");
                    }
                } while (!boolAns);

                System.out.println();
            }
            round++;
        }
    }

    // 最初のカード配布
    public void initialCards() {
        for (int i = 0; i < 2; i++) {
            player.getHand().addCard(deck.draw());
        }
        for (int i = 0; i < 2; i++) {
            dealer.getHand().addCard(deck.draw());
        }
    }

    // プレイヤー
    public void playerTurn() throws Exception {
        banner("PLAYER TURN", '-');
        while (!player.getHand().isBust() && player.getHand().getScore() < 21) {
            Thread.sleep(800);
            System.out.println("1：HIT");
            System.out.println("2：STAND");
            System.out.print("選択 >>> ");
            String input = scanner.nextLine().trim();
            System.out.println(); // 改行

            if (input.equals("1")) {
                Card card = deck.draw();
                player.getHand().addCard(card);

                CardRender.flipCard(card);

                System.out.println("合計：" + player.getHand().getScore());

                if (player.getHand().isBust()) {
                    break;
                }
            } else if (input.equals("2")) {
                break;
            } else {
                System.out.println("1か2を入力してください");
            }
        }
    }

    // ディーラー
    public void dealerTurn() throws Exception {
        banner("DEALER TURN", '-');
        Thread.sleep(500);
        System.out.println("【" + dealer.getName() + "の2枚目のカード】");
        System.out.println();
        CardRender.flipCard(dealer.getHand().getCard(1));
        System.out.println();
        System.out.println("合計：" + dealer.getHand().getScore());
        System.out.println();

        if (player.getHand().isBlackjack() || player.getHand().isBust()) {
            return;
        } else {
            while (dealer.getHand().getScore() < 17) {
                Thread.sleep(500);
                Card card = deck.draw();
                dealer.getHand().addCard(card);
                CardRender.flipCard(card);
                System.out.println();
                System.out.println("合計：" + dealer.getHand().getScore());
                System.out.println();
            }
        }
    }

    // 勝敗判定
    public void judge() {
        result = 0; // 初期化
        if (player.getHand().isBlackjack()) {
            if (dealer.getHand().isBlackjack()) {
                result = 3; // 引き分け
            } else {
                result = 2; // BJで勝利
            }
        } else if (dealer.getHand().isBlackjack()) {
            result = 0; // 負け
        } else if (player.getHand().isBust()) {
            result = 0;
        } else if (dealer.getHand().isBust()) {
            result = 1; // 通常勝利
        } else if (player.getHand().getScore() > dealer.getHand().getScore()) {
            result = 1;
        } else if (player.getHand().getScore() == dealer.getHand().getScore()) {
            result = 3;
        } else {
            result = 0;
        }
        calculatePayout();
        resultDisplay();
    }

    // リザルト画面
    public void resultDisplay() {
        banner("RESULT", '-');
        System.out.println("");
        System.out.println("%10s： %2d".formatted(player.getName(), player.getHand().getScore()));
        System.out.println("%10s： %2d".formatted(dealer.getName(), dealer.getHand().getScore()));
        System.out.println();
        String[] resultMsg = { "YOU LOSE...", "YOU WIN!", "WIN WITH BJ!", "DRAW" };
        for (int i = 0; i <= 3; i++) {
            if (i == result) {
                System.out.println(" ".repeat(8) + "*** " + resultMsg[i] + " ***");
            }
        }
        System.out.println();
        System.out.println("獲得配当：" + payout + " credits");
        System.out.println("現在の所持金：" + player.getCredits() + " credits");
        System.out.println();
        System.out.println("=".repeat(40) + "\n");
    }

    // 掛け金の選択
    public void placeBet() {
        boolean correctAns = false;
        String betAnswer = "";
        banner("Place Your Bet !", '-');
        do {
            System.out.println("現在の所持金：" + player.getCredits() + " credits");
            System.out.println("1： 500 credits");
            System.out.println("2：1000 credits");
            System.out.println("3：5000 credits");
            System.out.println("4： ALL credits");
            System.out.print("選択 >>> ");
            betAnswer = scanner.nextLine().trim();
            System.out.println();
            correctAns = betAnswer.matches("[1-4]");
            if (!correctAns) {
                System.out.println("1~4の数字を入力してください\n");
            } else {
                switch (betAnswer) {
                    case "1":
                        bet = 500;
                        break;
                    case "2":
                        bet = 1000;
                        break;
                    case "3":
                        bet = 5000;
                        break;
                    case "4":
                        bet = player.getCredits();
                        break;
                    default:
                        break;
                }

                if (bet > player.getCredits()) {
                    System.out.println("残高が不足しています");
                    System.out.println(player.getCredits() + " credits 以下の金額を選択してください");
                    correctAns = false;
                } else {
                    correctAns = true;
                }
            }
        } while (!correctAns);

        player.creditsBet(bet);
    }

    // 配当金計算
    public void calculatePayout() {
        switch (result) {
            case 1: // 通常勝利
                payout = bet;
                player.creditsResult(bet + payout);
                break;
            case 2: // BJで勝利
                payout = bet * 3 / 2;
                player.creditsResult(bet + payout);
                break;
            case 3: // 引きわけ
                payout = bet;
                player.creditsResult(payout);
                break;
            default:
                payout = 0;
                break; // 負け
        }
    }

    // タイトル装飾
    public void banner(String title, char symbol) {
        int width = 40;
        String border = "+" + String.valueOf(symbol).repeat(width) + "+";
        int left = (width - title.length()) / 2;
        int right = width - title.length() - left;
        String center = "│" + " ".repeat(left) + title + " ".repeat(right) + "│";

        System.out.println(border);
        System.out.println(center);
        System.out.println(border);
    }

}
