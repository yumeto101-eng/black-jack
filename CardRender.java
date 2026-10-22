public class CardRender {
    // ANSIエスケープシーケンス
    private static final String ESC = "\u001B[";
    // アニメーション待機時間
    private static final int DELAY = 40;
    // カードの最大幅
    private static final int MAX_WIDTH = 11;
    private static final int MIN_WIDTH = 1;
    private static final int HEIGHT = 9;
    // カードの色付け
    private static final String WHITE = "\u001B[107m"; // 城
    private static final String BLACK = "\u001B[30m"; // 黒
    private static final String RED = "\u001B[31m"; // 赤
    private static final String RESET = "\u001B[0m"; // もとに戻す（黒）

    // カードをめくる
    public static void flipCard(Card card) throws Exception {
        int[] widths = { MAX_WIDTH, 9, 7, 5, 3, MIN_WIDTH, MIN_WIDTH, 3, 3, 3, 3, 5, 5, 7, 7, 9, MAX_WIDTH };
        boolean first = true;

        for (int i = 0; i < widths.length; i++) {
            boolean faceUp = i >= 7;

            String[] lines = createFrame(card, widths[i], faceUp);

            if (!first) {
                System.out.print(ESC + HEIGHT + "A");
            }

            for (String line : lines) {
                System.out.print(ESC + "2K\r");
                System.out.println(line);
            }

            System.out.flush();
            first = false;

            Thread.sleep(DELAY);
        }
    }

    // 各フレームの生成
    public static String[] createFrame(Card card, int width, boolean faceUp) {
        String margin = " ".repeat((MAX_WIDTH - width) / 2);

        String top = margin + WHITE + BLACK + "┌" + "─".repeat(width) + "┐" + RESET;
        String bottom = margin + WHITE + BLACK + "└" + "─".repeat(width) + "┘" + RESET;

        String[] lines = new String[9];
        lines[0] = top;
        lines[HEIGHT - 1] = bottom;
        for (int i = 1; i <= HEIGHT - 2; i++) {
            String content;
            if (!faceUp) {
                content = "\u2592".repeat(width);
            } else if (i == 1) {
                content = first(card.getRankDisplay(), width);
            } else if (i == 2) {
                content = center1(card.getRank(), card.getSuitSymbol(), width);
            } else if (i == 3) {
                content = center2(card.getRank(), card.getSuitSymbol(), width);
            } else if (i == 4) {
                content = center3(card.getRank(), card.getSuitSymbol(), width);
            } else if (i == 5) {
                content = center4(card.getRank(), card.getSuitSymbol(), width);
            } else if (i == 6) {
                content = center5(card.getRank(), card.getSuitSymbol(), width);
            } else if (i == 7) {
                content = last(card.getRankDisplay(), width);
            } else {
                content = " ".repeat(width);
            }

            lines[i] = margin + WHITE + BLACK + "│" + colorSuit(content, card) + "│" + RESET;
        }
        return lines;
    }

    public static String first(String rankDisplay, int width) {
        if (width == MAX_WIDTH) {
            String rankString = "%2s".formatted(rankDisplay);
            return rankString + " ".repeat(width - rankString.length());
        } else {
            return " ".repeat(width);
        }
    }

    public static String center1(int rank, String suitSymbol, int width) {
        switch (rank) {
            case 2, 3:
                if (width < 6) {
                    return " ".repeat(width);
                } else {
                    return " ".repeat(5) + suitSymbol + " ".repeat(width - 6);
                }
            case 4, 5, 6, 7, 8, 9, 10:
                if (width < 4) {
                    return " ".repeat(width);
                } else if (width < 8) {
                    return " ".repeat(3) + suitSymbol + " ".repeat(width - 4);
                } else {
                    return " ".repeat(3) + suitSymbol + " ".repeat(3) + suitSymbol + " ".repeat(width - 8);
                }
            case 11, 12, 13:
                if (width < MAX_WIDTH) {
                    return " ".repeat(width);
                } else {
                    return " ".repeat(3) + suitSymbol + " " + suitSymbol + " " + suitSymbol + " ".repeat(3);
                }
            default:
                return " ".repeat(width);
        }
    }

    public static String center2(int rank, String suitSymbol, int width) {
        switch (rank) {
            case 7, 8:
                if (width < 6) {
                    return " ".repeat(width);
                } else {
                    return " ".repeat(5) + suitSymbol + " ".repeat(width - 6);
                }
            case 9:
                if (width < 4) {
                    return " ".repeat(width);
                } else if (width < 8) {
                    return " ".repeat(3) + suitSymbol + " ".repeat(width - 4);
                } else {
                    return " ".repeat(3) + suitSymbol + " ".repeat(3) + suitSymbol + " ".repeat(width - 8);
                }
            case 10:
                if (width < 4) {
                    return " ".repeat(width);
                } else if (width < 6) {
                    return " ".repeat(3) + suitSymbol + " ".repeat(width - 4);
                } else if (width < 8) {
                    return " ".repeat(3) + suitSymbol + " " + suitSymbol + " ".repeat(width - 6);
                } else {
                    return " ".repeat(3) + suitSymbol + " " + suitSymbol + " " + suitSymbol + " ".repeat(width - 8);
                }
            case 11, 12, 13:
                if (width < MAX_WIDTH) {
                    return " ".repeat(width);
                } else {
                    return " ".repeat(3) + suitSymbol + " " + suitSymbol + " " + suitSymbol + " ".repeat(3);
                }
            default:
                return " ".repeat(width);
        }
    }

    public static String center3(int rank, String suitSymbol, int width) {
        switch (rank) {
            case 1, 3, 5, 9:
                if (width < 6) {
                    return " ".repeat(width);
                } else {
                    return " ".repeat(5) + suitSymbol + " ".repeat(width - 6);
                }
            case 6, 7, 8:
                if (width < 4) {
                    return " ".repeat(width);
                } else if (width < 8) {
                    return " ".repeat(3) + suitSymbol + " ".repeat(width - 4);
                } else {
                    return " ".repeat(3) + suitSymbol + " ".repeat(3) + suitSymbol + " ".repeat(width - 8);
                }
            case 11, 12, 13:
                if (width < MAX_WIDTH) {
                    return " ".repeat(width);
                } else {
                    return " ".repeat(3) + suitSymbol + " " + suitSymbol + " " + suitSymbol + " ".repeat(3);
                }
            default:
                return " ".repeat(width);
        }
    }

    public static String center4(int rank, String suitSymbol, int width) {
        switch (rank) {
            case 8:
                if (width < 6) {
                    return " ".repeat(width);
                } else {
                    return " ".repeat(5) + suitSymbol + " ".repeat(width - 6);
                }
            case 9:
                if (width < 4) {
                    return " ".repeat(width);
                } else if (width < 8) {
                    return " ".repeat(3) + suitSymbol + " ".repeat(width - 4);
                } else {
                    return " ".repeat(3) + suitSymbol + " ".repeat(3) + suitSymbol + " ".repeat(width - 8);
                }
            case 10:
                if (width < 4) {
                    return " ".repeat(width);
                } else if (width < 6) {
                    return " ".repeat(3) + suitSymbol + " ".repeat(width - 4);
                } else if (width < 8) {
                    return " ".repeat(3) + suitSymbol + " " + suitSymbol + " ".repeat(width - 6);
                } else {
                    return " ".repeat(3) + suitSymbol + " " + suitSymbol + " " + suitSymbol + " ".repeat(width - 8);
                }
            case 11, 12, 13:
                if (width < MAX_WIDTH) {
                    return " ".repeat(width);
                } else {
                    return " ".repeat(3) + suitSymbol + " " + suitSymbol + " " + suitSymbol + " ".repeat(3);
                }
            default:
                return " ".repeat(width);
        }
    }

    public static String center5(int rank, String suitSymbol, int width) {
        switch (rank) {
            case 2, 3:
                if (width < 6) {
                    return " ".repeat(width);
                } else {
                    return " ".repeat(5) + suitSymbol + " ".repeat(width - 6);
                }
            case 4, 5, 6, 7, 8, 9, 10:
                if (width < 4) {
                    return " ".repeat(width);
                } else if (width < 8) {
                    return " ".repeat(3) + suitSymbol + " ".repeat(width - 4);
                } else {
                    return " ".repeat(3) + suitSymbol + " ".repeat(3) + suitSymbol + " ".repeat(width - 8);
                }
            case 11, 12, 13:
                if (width < MAX_WIDTH) {
                    return " ".repeat(width);
                } else {
                    return " ".repeat(3) + suitSymbol + " " + suitSymbol + " " + suitSymbol + " ".repeat(3);
                }
            default:
                return " ".repeat(width);
        }
    }

    public static String last(String rankDisplay, int width) {
        if (width == MAX_WIDTH) {
            String rankString = "%-2s".formatted(rankDisplay);
            return " ".repeat(width - rankString.length()) + rankString;
        } else {
            return " ".repeat(width);
        }
    }

    // カードの色変更 -> replaceで置換
    public static String colorSuit(String text, Card card) {
        String symbol = card.getSuitSymbol();
        int suit = card.getSuit();

        if (suit == 0 || suit == 1) { // ハート、ダイヤ
            return text.replace(symbol, RED + symbol + BLACK);
        }

        return text; // スペード、クラブ
    }
}
