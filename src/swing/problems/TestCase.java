package swing.problems;

import javax.swing.*;
import java.awt.*;

public class TestCase extends JPanel implements IdHolder {
    public static final int SIZE = 50;

    private final int id;
    private final JLabel label;

    public TestCase(int id) {
        this.id = id;
        label = new JLabel("", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 30));
        setStatus(TestCaseStatus.NOT_ATTEMPTED);
        add(label);
    }

    public void setStatus(TestCaseStatus status) {
        setBackground(status.getBg());
        setBorder(BorderFactory.createLineBorder(status.getBorder()));
        setToolTipText(status.getHoverText());
        label.setText(String.valueOf(status.getCh()));
        label.setForeground(status.getBorder());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIZE, SIZE);
    }
}
