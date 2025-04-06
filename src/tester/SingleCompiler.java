package tester;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class SingleCompiler {
    private final JavaCompiler compiler;
    private final String outputDir;
    private final StandardJavaFileManager fileManager;

    public SingleCompiler() throws IOException {
        compiler = ToolProvider.getSystemJavaCompiler();
        outputDir = System.getProperty("user.dir") + "/testing/";
        fileManager = compiler.getStandardFileManager(null, null, null);
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singleton(new File(outputDir)));
    }

    public boolean compile(String path) {
        Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjectsFromStrings(Collections.singleton(path));
        return compiler.getTask(null, fileManager, null, null, null, fileObjects).call();
    }

    public String getOutputDir() {return outputDir;}
}
