package swing.problems;

import org.json.JSONArray;
import swing.BaseFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProblemUIPanel extends JPanel implements MouseListener {
    private final BaseFrame frame;
    private final ProblemUI[] problems = new ProblemUI[12];

    public ProblemUIPanel(BaseFrame frame, String path) throws IOException {
        this.frame = frame;

        setLayout(new GridLayout(2, 6, 3, 3));
        setBorder(BorderFactory.createTitledBorder("Problems"));

        JSONArray jsonArr = new JSONArray(Files.readString(Paths.get(path + "names.json")));
        for (int i = 0; i < 12; i++) {
            problems[i] = new ProblemUI(jsonArr.getString(i), i);
            problems[i].addMouseListener(this);
            problems[i].getTestCase().addMouseListener(this);
            add(problems[i]);
        }

//        problems[2].getTestCase().setStatus(TestCaseStatus.PASSED);
//        problems[5].setLoss(15);
//        problems[9].getTestCase().setStatus(TestCaseStatus.PROCESSING);
    }

    public ProblemUI[] getProblems() {
        return problems;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        IdHolder idHolder = (IdHolder) e.getSource();
        try {
            frame.setId(idHolder.getId());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
