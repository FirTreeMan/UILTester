package swing.problems;

import java.awt.*;

public enum TestCaseStatus {
    NOT_ATTEMPTED(' ', "Not attempted.", Color.LIGHT_GRAY, Color.GRAY, Color.WHITE),
    WRONG_ANSWER('X', "Wrong answer.", new Color(245, 61, 61), new Color(138, 1, 3), new Color(245, 213, 213)),
    COMPILE_ERROR('?', "Compile-time error.", new Color(245, 61, 61), new Color(138, 1, 3), new Color(245, 213, 213)),
    RUNTIME_ERROR('!', "Runtime error.", new Color(245, 61, 61), new Color(138, 1, 3), new Color(245, 213, 213)),
    TIME_LIMIT_EXCEEDED('%', "Time limit exceeded.", new Color(245, 61, 61), new Color(138, 1, 3), new Color(245, 213, 213)),
    WRONG_CLASS_NAME('%', "Class name does not match problem name.", new Color(245, 61, 61), new Color(138, 1, 3), new Color(245, 213, 213)),
    PROCESSING('&', "Processing...", new Color(217, 210, 2), new Color(143, 138, 1), new Color(245, 244, 213)),
    PASSED('+', "Passed.", new Color(29, 214, 4), new Color(0, 133, 0), new Color(213, 243, 245)),
    ;

    private final char ch;
    private final String hoverText;
    private final Color bg, border, tertiary;

    TestCaseStatus(char ch, String hoverText, Color bg, Color border, Color tertiary) {
        this.ch = ch;
        this.hoverText = hoverText;
        this.bg = bg;
        this.border = border;
        this.tertiary = tertiary;
    }

    public char getCh() {
        return ch;
    }

    public String getHoverText() {
        return hoverText;
    }

    public Color getBg() {
        return bg;
    }

    public Color getBorder() {
        return border;
    }

    public Color getTertiary() {
        return tertiary;
    }
}
