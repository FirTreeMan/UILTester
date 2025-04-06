package swing.problems;

import javax.swing.*;
import java.awt.*;

public class ProblemUI extends JPanel implements IdHolder {
    public static final int SIZE = 100;

    private final JLabel nameLabel, lossLabel;
    private final TestCase testCase;
    private final String problemName;
    private final int id;

    public ProblemUI(String problemName, int id) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEtchedBorder());
//        setBackground(new Color(220, 220, 220));

        this.problemName = problemName;
        this.id = id;

        nameLabel = new JLabel(problemName, JLabel.CENTER);
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        lossLabel = new JLabel("-", JLabel.CENTER);
        lossLabel.setFont(new Font("Serif", Font.BOLD, 15));
        lossLabel.setEnabled(false);
        lossLabel.setForeground(TestCaseStatus.WRONG_ANSWER.getBorder());
        testCase = new TestCase(id);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(5, 1, 1, 1);

        gbc.gridy = 0;
        gbc.weightx = 1;
        add(nameLabel, gbc);

        gbc.insets.set(1, 1, 1, 1);

        gbc.weighty = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 4;
        add(testCase, gbc);

        gbc.weighty = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(lossLabel, gbc);
    }

    public String getProblemName() {
        return problemName;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setLoss(int loss) {
        if (loss == 0) return;
        lossLabel.setText("-" + loss);
        lossLabel.setEnabled(true);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(SIZE, SIZE);
    }
}
