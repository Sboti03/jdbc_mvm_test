package hu.webvalto.ui.colors;

public class WriteOut {
    public static void write(String text, Color color) {
        StringBuilder sb = new StringBuilder();
        switch (color) {
            case RED:
                sb.append(Colors.ANSI_RED);
                break;
            case GREEN:
                sb.append(Colors.ANSI_GREEN);
                break;
            case YELLOW:
                sb.append(Colors.ANSI_YELLOW);
                break;
            case BLUE:
                sb.append(Colors.ANSI_BLUE);
                break;
            case PURPLE:
                sb.append(Colors.ANSI_PURPLE);
                break;
            case CYAN:
                sb.append(Colors.ANSI_CYAN);
                break;
            case WHITE:
                sb.append(Colors.ANSI_WHITE);
                break;
        }
        sb.append(text);
        sb.append(Colors.ANSI_RESET);
        System.out.print(sb);
    }

    public static void writeLine(String text, Color color) {
        write(text + "\n", color);
    }

}
