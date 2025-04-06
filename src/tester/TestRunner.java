package tester;

import swing.problems.TestCaseStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

public class TestRunner {
    public static int TIME_LIMIT = 4;

    private final String path;
    private final String testPath;

    public TestRunner(String path) {
        this.path = path;
        testPath = System.getProperty("user.dir") + "/testing/";
    }

    public TestCaseStatus run(String program, boolean sample) throws IOException, InterruptedException {
        String ioSourceBase = path + program.toLowerCase() + (sample ? "-sample" : "");
        String inputSource = ioSourceBase + ".dat";
        String outputSource = ioSourceBase + ".out";
        String ioPathBase = testPath + program.toLowerCase();
        Path inputPath = Path.of(ioPathBase + ".dat");
        Path outputPath = Path.of(ioPathBase + ".out");

        Files.copy(Path.of(inputSource), inputPath, StandardCopyOption.REPLACE_EXISTING);
        Files.createFile(outputPath);
        File outputFile = outputPath.toFile();

        ProcessBuilder pb = new ProcessBuilder("java", program);
        pb.redirectError();
        pb.redirectOutput(outputFile);
        pb.directory(new File(testPath));
        Process p = pb.start();
        InputStreamConsumer consumer = new InputStreamConsumer(p.getInputStream());
        consumer.start();

        boolean tle = !p.waitFor(TIME_LIMIT, TimeUnit.SECONDS);

        consumer.join();

        if (tle) return TestCaseStatus.TIME_LIMIT_EXCEEDED;
        if (!(new File(testPath + "/" + program)).isFile()) return TestCaseStatus.WRONG_CLASS_NAME;
        if (p.exitValue() != 0) return TestCaseStatus.RUNTIME_ERROR;

        BufferedReader source = new BufferedReader(new FileReader(outputSource));
        BufferedReader toTest = new BufferedReader(new FileReader(outputFile));

        while (true) {
            String sourceLine = source.readLine();
            String toTestLine = toTest.readLine();

            if (sourceLine == null) break;
            if (!sourceLine.equals(toTestLine)) return TestCaseStatus.WRONG_ANSWER;
        }

        return TestCaseStatus.PASSED;
    }

    public static class InputStreamConsumer extends Thread {
        private final InputStream is;
        private IOException exp;
        private StringBuilder output;

        public InputStreamConsumer(InputStream is) {
            this.is = is;
            exp = null;
        }

        @Override
        public void run() {
            int in;
            output = new StringBuilder(64);
            try {
                while ((in = is.read()) != -1) {
                    output.append((char) in);
                }
            } catch (IOException e) {
                e.printStackTrace();
                exp = e;
            }
        }

        public StringBuilder getOutput() {return output;}

        public IOException getException() {return exp;}
    }
}
