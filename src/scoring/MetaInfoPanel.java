package scoring;

import swing.BaseFrame;

import javax.swing.*;

public class MetaInfoPanel extends JPanel {
    public static final long SECOND_MILLIS = 1000;
    public static final long MINUTE_MILLIS = SECOND_MILLIS * 60;
    public static final long HOUR_MILLIS = MINUTE_MILLIS * 60;
    public static final long DURATION = HOUR_MILLIS * 2;

    private BaseFrame frame;
    private long startTime;
    private int score;

    private JLabel timeLabel, scoreLabel;
    private Timer timer;

    public MetaInfoPanel(BaseFrame frame) {
        this.frame = frame;
        startTime = -1;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        timeLabel = new JLabel("2:00:00");
        scoreLabel = new JLabel("000/720");
        timer = new Timer(250, _ -> {
            if (update()) {
                frame.lock();
                timer.stop();
            }
        });

        add(timeLabel);
        add(Box.createHorizontalGlue());
        add(scoreLabel);
    }

    public Timer getTimer() {
        return timer;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        timer.start();
    }

    public boolean update() {
        return update(-1);
    }

    public boolean update(int score) {
        long timeDiff = DURATION - Math.min(System.currentTimeMillis() - startTime, DURATION);
        long hours = timeDiff / HOUR_MILLIS;
        long minutes = timeDiff % HOUR_MILLIS / MINUTE_MILLIS;
        long seconds = timeDiff % MINUTE_MILLIS / SECOND_MILLIS;

        timeLabel.setText("%01d:%02d:%02d".formatted(hours, minutes, seconds));

        if (score >= 0) {
            this.score = score;
            scoreLabel.setText("%03d/720".formatted(score));
        }
        return timeDiff <= 0;
    }

    public void lock() {
        timeLabel.setText("CONTEST OVER");
        String scoreText = scoreLabel.getText();
        scoreLabel.setText("%.1f%%".formatted((double) score / 720));
        add(Box.createHorizontalGlue());
        add(new JLabel("FINAL SCORE: %s".formatted(scoreText)));
    }
}
