package scoring;

import swing.BaseFrame;
import swing.problems.ProblemUI;

import java.io.IOException;

public class ProblemDataHolder {
    private final BaseFrame frame;
    private final ProblemData[] problemData = new ProblemData[12];
    private int totalScore;

    public ProblemDataHolder(BaseFrame frame, ProblemUI[] problems) {
        this.frame = frame;
        totalScore = 0;
        for (int i = 0; i < 12; i++) {
            problemData[i] = new ProblemData(frame, problems[i]);
        }
    }

    public ProblemData[] getProblemData() {return problemData;}

    public int getTotalScore() {return totalScore;}

    public void submit(int id, String filePath) throws IOException, InterruptedException {
        ProblemData pd = problemData[id];
        if (pd.submit(filePath) && pd.hasPassed()) {
            totalScore += pd.getScore();
        }

        frame.getMetaInfo().update(totalScore);
    }
}
