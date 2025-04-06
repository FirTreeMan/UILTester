package tester;

import swing.problems.TestCaseStatus;

import java.io.File;
import java.io.IOException;

public class CompileRunner {
    private final SingleCompiler compiler;
    private final TestRunner runner;
    private final File testFolder;

    public CompileRunner(String path) throws IOException {
        compiler = new SingleCompiler();
        runner = new TestRunner(path);
        testFolder = new File(System.getProperty("user.dir") + "/testing/");
    }

    public TestCaseStatus compileAndRun(String problemName, String filePath, boolean sample) throws IOException, InterruptedException {
        if (!compiler.compile(filePath)) return TestCaseStatus.COMPILE_ERROR;
        return runner.run(problemName, sample);
    }

    public void clean() {
        for (File file: testFolder.listFiles())
            if (!file.isDirectory())
                file.delete();
    }
}
