package swing;

import scoring.MetaInfoPanel;
import scoring.ProblemData;
import scoring.ProblemDataHolder;
import swing.problems.ProblemDisplayPane;
import swing.problems.ProblemUI;
import swing.problems.ProblemUIPanel;
import swing.problems.TestCaseStatus;
import tester.CompileRunner;

import javax.swing.*;
import java.io.IOException;

public class BaseFrame extends JFrame {
    private final MetaInfoPanel metaInfoPanel;
    private final ProblemUIPanel problemPanel;
    private final ProblemDisplayPane problemDisplay;
    private final CompileRunner compileRunner;
    private final ProblemDataHolder problemDataHolder;

    public BaseFrame(String path) throws IOException {
        super("UIL Computer Science Tester - Active Test");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        metaInfoPanel = new MetaInfoPanel(this);
        problemPanel = new ProblemUIPanel(this, path);
        problemDisplay = new ProblemDisplayPane(this, path);
        compileRunner = new CompileRunner(path);

        problemDataHolder = new ProblemDataHolder(this, problemPanel.getProblems());

        metaInfoPanel.start();

        problemDisplay.setId(0);
        add(metaInfoPanel);
        add(problemPanel);
        add(problemDisplay);
    }

    public TestCaseStatus compileAndRun(String problemName, String filePath, boolean sample) throws IOException, InterruptedException {
        compileRunner.clean();
        return compileRunner.compileAndRun(problemName, filePath, sample);
    }

    public MetaInfoPanel getMetaInfo() {
        return metaInfoPanel;
    }

    public ProblemDataHolder getProblemData() {
        return problemDataHolder;
    }

    public ProblemUI getProblemUI(int id) {
        return problemPanel.getProblems()[id];
    }

    public void setId(int id) throws IOException {
        problemDisplay.setId(id);
    }

    public void lock() {
        metaInfoPanel.lock();
        problemDisplay.lock();
        JOptionPane.showMessageDialog(this,
                "The contest's 2-hour time window has elapsed. Scores have been finalized and programs may no longer be submitted.",
                "Contest Over",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void createAndShowGUI() {
        pack();
        setVisible(true);
    }
}

