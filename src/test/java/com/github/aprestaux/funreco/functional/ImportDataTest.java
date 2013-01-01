package com.github.aprestaux.funreco.functional;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ImportDataTest {
    public static final String dataDir = "src/test/java/com/github/aprestaux/funreco/functional";

    @Test
    public void importProfiles() throws IOException, InterruptedException {
        execAndAssert("/usr/local/bin/groovy src/main/groovy/ImportData.groovy --profiles --dataDir " + dataDir);
    }

    @Test
    public void importFriends() throws IOException, InterruptedException {
        execAndAssert("/usr/local/bin/groovy src/main/groovy/ImportData.groovy --friends --dataDir " + dataDir);
    }

    @Test
    public void importActions() throws IOException, InterruptedException {
        execAndAssert("/usr/local/bin/groovy src/main/groovy/ImportData.groovy --actions --dataDir " + dataDir);
    }

    private void execAndAssert(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);

        process.waitFor();

        System.out.println(IOUtils.toString(process.getInputStream()));
        System.out.println(IOUtils.toString(process.getErrorStream()));

        assertThat(process.exitValue()).isEqualTo(0);
    }
}
