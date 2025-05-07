package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class TestResultsManager {

    private static final String CURRENT_RESULTS_PATH = "reports/Current_Test_Results";
    private static final String ARCHIVED_RESULTS_PATH = "reports/Archived_Test_Results";

    public static void manageTestResults() {
       
    	File currentResultsDir = new File(CURRENT_RESULTS_PATH);
        File archivedResultsDir = new File(ARCHIVED_RESULTS_PATH);
        // Ensure the Archived_Test_Results directory exists
        if (!archivedResultsDir.exists()) {
            archivedResultsDir.mkdirs();
        }

        // Move files to Archived_Test_Results
        for (File file : currentResultsDir.listFiles()) {
            if (file.isFile()) {
                try {
                    Files.move(file.toPath(),
                               Path.of(archivedResultsDir.getPath(), file.getName()),
                               StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.err.println("Failed to move file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }

        // Clean Current_Test_Results folder
        for (File file : currentResultsDir.listFiles()) {
            
        	if (file.isFile()) {
                file.delete();
            }
        }
    }
}
