package com.zuehlke.blockchain;

import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.model.fileset.FileSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JavaClassGeneratorMojoTest {

    public static final String TEST_GENERATED_FOLDER = "src/test/generated";
    private MavenProject mavenProject;
    private File executionDirectory;

    @Before
    public void setUp() throws Exception {
        mavenProject = mock(MavenProject.class);
        executionDirectory = new File(Paths.get(".").toUri());
        when(mavenProject.getBasedir()).thenReturn(executionDirectory);
    }

    @Test
    public void basicExecution() throws Exception {
        JavaClassGeneratorMojo javaClassGenerator = new JavaClassGeneratorMojo("model", TEST_GENERATED_FOLDER, new FileSet(), mavenProject);

        javaClassGenerator.execute();

        File fileAfter = new File(executionDirectory, TEST_GENERATED_FOLDER + "/model/Greeter.java");
        assertTrue(fileAfter.exists());

    }

    @After
    public void removeTestFiles() throws IOException {
        File file = new File(new File(Paths.get(".").toUri()), TEST_GENERATED_FOLDER);
        Files.walk(file.toPath())
                .map(Path::toFile)
                .sorted((o1, o2) -> -o1.compareTo(o2)) //reversed order
                .forEach(File::delete);
    }


}