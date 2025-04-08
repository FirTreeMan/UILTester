package swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class SelectorFrame extends JFrame implements ActionListener {
    public static final String[] levels = {"District", "Region", "State"};
    public static final ArrayList<Integer> districtYears = new ArrayList<>(), regionYears = new ArrayList<>(), stateYears = new ArrayList<>();
    public static final ArrayList[] years = {districtYears, regionYears, stateYears};

    private final JComboBox<String> levelList = new JComboBox<>(levels);
    private final JComboBox<Integer> yearList = new JComboBox<>();
    private final DefaultComboBoxModel[] yearModels;

    public SelectorFrame(BiConsumer<String, Integer> contestMaker) {
        super("UIL Computer Science Tester");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        makeLevelYears();
        yearModels = new DefaultComboBoxModel[]{
                new DefaultComboBoxModel<>(years[0].toArray(Integer[]::new)),
                new DefaultComboBoxModel<>(years[1].toArray(Integer[]::new)),
                new DefaultComboBoxModel<>(years[2].toArray(Integer[]::new))
        };

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

    private void makeLevelYears() {
        String pathBase = "data/";
        districtYears.addAll(findYearsForLevel(new File(pathBase + "district").listFiles()));
        regionYears.addAll(findYearsForLevel(new File(pathBase + "region").listFiles()));
        stateYears.addAll(findYearsForLevel(new File(pathBase + "state").listFiles()));
    }

    private List<Integer> findYearsForLevel(File[] files) {
        ArrayList<Integer> yearsFound = new ArrayList<>(files.length);
        for (File file: files) {
            // file should be a directory with contest year as name
            if (file.isDirectory())
                yearsFound.add(Integer.parseInt(file.getName()));
        }
        return yearsFound;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        setYears(cb.getSelectedIndex());
    }
}
