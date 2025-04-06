import swing.BaseFrame;
import swing.SelectorFrame;

import javax.swing.*;
import java.io.IOException;
import java.util.function.BiConsumer;

public class Main {
    public static void main(String[] args) {
        try {UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");}
        catch (Exception ignored) {}

        BiConsumer<String, Integer> contestMaker = (level, year) -> {
            try {startContest(level, year);}
            catch (IOException e) {throw new RuntimeException(e);}
        };

        SelectorFrame selectorFrame = new SelectorFrame(contestMaker);
        SwingUtilities.invokeLater(selectorFrame::createAndShowGUI);
    }

    static void startContest(String level, int year) throws IOException {
        BaseFrame baseFrame = new BaseFrame("data/" + level + "/" + year + "/");
        SwingUtilities.invokeLater(baseFrame::createAndShowGUI);
    }
}