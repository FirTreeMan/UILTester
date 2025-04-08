package swing.problems;

import org.json.JSONArray;
import org.json.JSONObject;
import swing.BaseFrame;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ProblemDisplayPane extends JScrollPane {
    private final BaseFrame frame;
    private final String path;
    private final String imgPath;
    private final String[] problemNames = new String[12];
    private String name, programName, inputFile, body, input, output, sampleInput, sampleOutput, note;
    private final JLabel nameLabel, programNameLabel, inputFileLabel, bodyLabel, inputLabel, outputLabel, sampleInputLabel, sampleOutputLabel, noteLabel;
    private final JPanel headerPanel = new JPanel(), infoPanel = new JPanel(), samplePanel = new JPanel(), uploadPanel = new JPanel(), sampleTestPanel = new JPanel();
    private final JLabel testOutputLabel, testResultLabel;
    private int id = -1;

    public ProblemDisplayPane(BaseFrame frame, String path) throws IOException {
        this.frame = frame;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 2));
//        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        this.path = path;
        this.imgPath = path + "img/";

        JSONArray problemJson = new JSONArray(Files.readString(Paths.get(path + "names.json")));
        for (int i = 0; i < 12; i++)
            problemNames[i] = problemJson.getString(i);

        nameLabel = new JLabel("", JLabel.CENTER);
        programNameLabel = new JLabel("", JLabel.LEFT);
        inputFileLabel = new JLabel("", JLabel.RIGHT);
        bodyLabel = new JLabel();
        inputLabel = new JLabel();
        outputLabel = new JLabel();
        sampleInputLabel = new JLabel();
        sampleOutputLabel = new JLabel();
        noteLabel = new JLabel();

        testResultLabel = new JLabel();
        testOutputLabel = new JLabel();

        nameLabel.setFont(new Font("Serif", Font.BOLD, 25));
        programNameLabel.setFont(new Font("Serif", Font.BOLD, 18));
        inputFileLabel.setFont(new Font("Serif", Font.BOLD, 18));
        inputLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        bodyLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        inputLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        outputLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        sampleInputLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        sampleOutputLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        testResultLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        testOutputLabel.setFont(new Font("Serif", Font.BOLD, 26));

        makeHeaderPanel();
        makeInfoPanel();
        makeSamplePanel();
        makeUploadPanel();
        makeSampleTestPanel();

        panel.add(headerPanel);
        panel.add(filler());
        panel.add(infoPanel);
        panel.add(filler());
        panel.add(samplePanel);
        panel.add(filler());
        panel.add(uploadPanel);
        panel.add(filler());
        panel.add(sampleTestPanel);

        setViewportView(panel);
    }

    public void setId(int id) throws IOException {
        if (this.id == id) return;

        this.id = id;
        JSONObject data = new JSONObject(Files.readString(Paths.get(path + problemNames[id] + ".json")));

        name = problemNames[id];
        programName = problemNames[id] + ".java";
        inputFile = (data.has("No Input") && data.getInt("No Input") == 1) ? "None" : problemNames[id].toLowerCase() + ".dat";
        body = data.getString("Body").replace("\n", "<br>").replace("\t", "&#9;");
        input = data.getString("Input").replace("\n", "<br>");
        output = data.getString("Output").replace("\n", "<br>");
        sampleInput = Files.readString(Paths.get(path + problemNames[id].toLowerCase() + "-sample.dat")).replace("\n", "<br>");
        sampleOutput = Files.readString(Paths.get(path + problemNames[id].toLowerCase() + "-sample.out")).replace("\n", "<br>");
        note = (data.has("Note")) ? data.getString("Note") : "";

        int imgTrack = 0;
        while (body.contains("<img/>")) {
            String imgSrc = new File(imgPath + problemNames[id].toLowerCase() + (imgTrack == 0 ? "" : imgTrack) + ".png").toURI().toURL().toExternalForm();
            body = body.replaceFirst("<img/>", "<img src=\"" + imgSrc + "\">");
            imgTrack++;
        }
        while (input.contains("<img/>")) {
            String imgSrc = new File(imgPath + problemNames[id].toLowerCase() + (imgTrack == 0 ? "" : imgTrack) + ".png").toURI().toURL().toExternalForm();
            input = input.replaceFirst("<img/>", "<img src=\"" + imgSrc + "\">");
            imgTrack++;
        }
        while (output.contains("<img/>")) {
            String imgSrc = new File(imgPath + problemNames[id].toLowerCase() + (imgTrack == 0 ? "" : imgTrack) + ".png").toURI().toURL().toExternalForm();
            output = output.replaceFirst("<img/>", "<img src=\"" + imgSrc + "\">");
            imgTrack++;
        }

        nameLabel.setText((id + 1) + ". " + name);
        programNameLabel.setText("Program Name: " + programName);
        inputFileLabel.setText("Input File: " + inputFile);
        bodyLabel.setText("<html>" + body + "</html>");
        inputLabel.setText("<html><b>Input: </b>" + input + "</html>");
        outputLabel.setText("<html><b>Output: </b>" + output + "</html>");
        sampleInputLabel.setText("<html><pre>" + sampleInput + "</pre></html>");
        sampleOutputLabel.setText("<html><pre>" + sampleOutput + "</pre></html>");
        noteLabel.setText(note.isEmpty() ? "" : "<html>Note: " + note + "</html>");

        sampleTestPanel.setVisible(false);
    }

    public void lock() {
        uploadPanel.setVisible(false);
    }

    private void makeHeaderPanel() {
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.PAGE_AXIS));

        JPanel headerSubPanel = new JPanel();
        headerSubPanel.setLayout(new BoxLayout(headerSubPanel, BoxLayout.LINE_AXIS));
        headerSubPanel.add(programNameLabel);
        headerSubPanel.add(Box.createHorizontalGlue());
        headerSubPanel.add(inputFileLabel);

        headerPanel.add(nameLabel);
        headerPanel.add(headerSubPanel);
    }

    private void makeInfoPanel() {
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.setAlignmentX(CENTER_ALIGNMENT);

        infoPanel.add(bodyLabel);
        infoPanel.add(filler());
        infoPanel.add(inputLabel);
        infoPanel.add(filler());
        infoPanel.add(outputLabel);
    }

    private void makeSamplePanel() {
        samplePanel.setLayout(new BoxLayout(samplePanel, BoxLayout.PAGE_AXIS));
        samplePanel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel sampleInputTitleLabel = new JLabel("Sample Input:"), sampleOutputTitleLabel = new JLabel("Sample Output:");
        sampleInputTitleLabel.setFont(new Font("Serif", Font.BOLD, 14));
        sampleOutputTitleLabel.setFont(new Font("Serif", Font.BOLD, 14));

        samplePanel.add(sampleInputTitleLabel);
        samplePanel.add(sampleInputLabel);
        samplePanel.add(filler());
        samplePanel.add(sampleOutputTitleLabel);
        samplePanel.add(sampleOutputLabel);
    }

    private void makeUploadPanel() {
        uploadPanel.setLayout(new BoxLayout(uploadPanel, BoxLayout.PAGE_AXIS));
        uploadPanel.setAlignmentX(CENTER_ALIGNMENT);

        JButton uploadButton = new JButton("Upload file");
        JLabel fileNameLabel = new JLabel("No file uploaded");
        fileNameLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        JButton testButton = new JButton("Test");
        JButton submitButton = new JButton("Submit!");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().equals(name + ".java");
            }

            @Override
            public String getDescription() {
                return "Matches UIL submission name (" + name + ".java).";
            }
        });

        uploadButton.addActionListener(_ -> {
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                fileNameLabel.setText(fileChooser.getSelectedFile().getPath());
            }
        });

        testButton.addActionListener(_ -> {
            try {
                TestCaseStatus status = frame.compileAndRun(problemNames[id], fileChooser.getSelectedFile().getPath(), true);

                Scanner sc = new Scanner(new File(System.getProperty("user.dir") + "/testing/" + problemNames[id].toLowerCase() + ".out"));
                StringBuilder toWrite = new StringBuilder();
                while (sc.hasNextLine())
                    toWrite.append(sc.nextLine()).append("<br>");
                testResultLabel.setText("<html><pre>" + toWrite + "</pre></html>");

                testOutputLabel.setText(status.getHoverText());
                testOutputLabel.setForeground(status.getBorder());

                sampleTestPanel.setBackground(status.getTertiary());
                sampleTestPanel.setVisible(true);
            }
            catch (IOException | InterruptedException e) {throw new RuntimeException(e);}
        });

        submitButton.addActionListener(_ -> {
            try {
                frame.getProblemData().submit(id, fileChooser.getSelectedFile().getPath());
//                TestCase targeted = frame.getProblemUI(id).getTestCase();
//                targeted.setStatus(TestCaseStatus.PROCESSING);
//
//                TestCaseStatus newStatus = frame.compileAndRun(problemNames[id], fileChooser.getSelectedFile().getPath(), false);
//                targeted.setStatus(newStatus);
            } catch (IOException | InterruptedException e) {throw new RuntimeException(e);}
        });

        uploadPanel.add(uploadButton);
        uploadPanel.add(fileNameLabel);
        uploadPanel.add(testButton);
        uploadPanel.add(submitButton);
    }

    private void makeSampleTestPanel() {
        sampleTestPanel.setLayout(new BoxLayout(sampleTestPanel, BoxLayout.PAGE_AXIS));
        sampleTestPanel.setAlignmentX(CENTER_ALIGNMENT);
        sampleTestPanel.setBorder(BorderFactory.createTitledBorder("Sample Testing"));
        sampleTestPanel.setOpaque(true);

        testResultLabel.setBorder(BorderFactory.createTitledBorder("Your output"));
        testOutputLabel.setBorder(BorderFactory.createTitledBorder("Verdict"));

        sampleTestPanel.add(testResultLabel);
        sampleTestPanel.add(testOutputLabel);
    }

    public static Component filler() {
        return Box.createRigidArea(new Dimension(0, 5));
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(150, -1);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(1500, 100);
    }
}
