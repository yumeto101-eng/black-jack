
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainBlackJack {

        // 起動前のコードページを保存
        private static String originalCodePage;

        public static void main(String[] args) throws Exception {
                try {
                        setupConsole();

                        Scanner scanner = new Scanner(
                                        System.in, StandardCharsets.UTF_8);

                        GameMaster master = new GameMaster(scanner);
                        master.start();

                } finally {
                        restoreConsole();
                }
        }

        // Windows環境か判定
        private static boolean isWindows() {
                return System.getProperty("os.name")
                                .toLowerCase().contains("windows");
        }

        // chcp.com のパスを取得
        private static String getChcpPath() {
                String windowsDir = System.getenv()
                                .getOrDefault("SystemRoot", "C:\\Windows");

                return windowsDir + "\\System32\\chcp.com";
        }

        // 起動前のコードページを取得
        private static String getCurrentCodePage() throws Exception {
                Process process = new ProcessBuilder(getChcpPath())
                                .redirectErrorStream(true)
                                .start();

                String output = new String(
                                process.getInputStream().readAllBytes(),
                                StandardCharsets.ISO_8859_1);

                if (process.waitFor() != 0) {
                        throw new IllegalStateException(
                                        "Failed to read console code page.");
                }

                Matcher matcher = Pattern
                                .compile("(\\d+)\\s*$")
                                .matcher(output);

                if (!matcher.find()) {
                        throw new IllegalStateException(
                                        "Cannot detect console code page.");
                }

                return matcher.group(1);
        }

        // コンソールのコードページを変更
        private static void changeCodePage(String codePage)
                        throws Exception {

                Process process = new ProcessBuilder(
                                getChcpPath(), codePage)
                                .inheritIO()
                                .start();

                if (process.waitFor() != 0) {
                        throw new IllegalStateException(
                                        "Failed to change console code page.");
                }
        }

        // UTF-8に変更
        private static void setupConsole() throws Exception {
                if (isWindows()) {
                        // 変更前の文字コードを保存
                        originalCodePage = getCurrentCodePage();

                        changeCodePage("65001");
                }

                System.setOut(new PrintStream(
                                new FileOutputStream(FileDescriptor.out),
                                true,
                                StandardCharsets.UTF_8));

                System.setErr(new PrintStream(
                                new FileOutputStream(FileDescriptor.err),
                                true,
                                StandardCharsets.UTF_8));
        }

        // 起動前のコードページに戻す
        private static void restoreConsole() throws Exception {
                if (isWindows() && originalCodePage != null) {
                        changeCodePage(originalCodePage);
                }
        }
}
