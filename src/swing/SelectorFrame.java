package swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.function.BiConsumer;

public class SelectorFrame extends JFrame implements ActionListener {
    public static final String[] levels = {"District", "Region", "State"};
    public static final Integer[] districtYears = {2024};
    public static final Integer[] regionYears = {2024};
    public static final Integer[] stateYears = {2024};
    public static final Integer[][] years = {districtYears, regionYears, stateYears};

    private final JComboBox<String> levelList = new JComboBox<>(levels);
    private final JComboBox<Integer> yearList = new JComboBox<>();
    private final DefaultComboBoxModel[] yearModels = {
            new DefaultComboBoxModel<>(years[0]),
            new DefaultComboBoxModel<>(years[1]),
            new DefaultComboBoxModel<>(years[2])
    };

    public SelectorFrame(BiConsumer<String, Integer> contestMaker) {
        super("UIL Computer Science Tester");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        levelList.addActionListener(this);
        setYears(0);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(150, 60, 200, 60));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        panel.add(levelList);
        panel.add(Box.createHorizontalStrut(50));
        panel.add(yearList);

        JButton startButton = new JButton("Start Practice Contest (2 Hours)");
        startButton.addActionListener(_ -> contestMaker.accept(
                (String) levelList.getSelectedItem(),
                (Integer) yearList.getSelectedItem()));

        p.add(panel);
        p.add(startButton);

        add(p);
    }

    public void createAndShowGUI() {
        pack();
        setVisible(true);
    }

    private void setYears(int x) {
        yearList.setModel(yearModels[x]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        setYears(cb.getSelectedIndex());
    }
}
