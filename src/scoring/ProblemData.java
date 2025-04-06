package scoring;

import swing.BaseFrame;
import swing.problems.ProblemUI;
import swing.problems.TestCase;
import swing.problems.TestCaseStatus;

import java.io.IOException;

public class ProblemData {
    public static final int INITIAL_SCORE = 60;
    public static final int INCORRECT_PENALTY = 5;

    private final BaseFrame frame;
    private final ProblemUI problemUI;
    private int score;
    private boolean passed;

    public ProblemData(BaseFrame frame, ProblemUI problemUI) {
        this.frame = frame;
        this.problemUI = problemUI;
        score = INITIAL_SCORE;
    }

    public int getScore() {return score;}

    public boolean hasPassed() {return passed;}

    public boolean submit(String filePath) throws IOException, InterruptedException {
        if (passed || score <= 0) return false;

        TestCase targeted = problemUI.getTestCase();
        targeted.setStatus(TestCaseStatus.PROCESSING);

        TestCaseStatus status = frame.compileAndRun(problemUI.getProblemName(), filePath, false);
        if (status == TestCaseStatus.PASSED) passed = true;
        else score -= INCORRECT_PENALTY;

        targeted.setStatus(status);
        problemUI.setLoss(INITIAL_SCORE - score);

        return true;
    }
}
