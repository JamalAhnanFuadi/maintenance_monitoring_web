package id.tsi.mmw.util.helper;

import id.tsi.mmw.util.log.AppLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

public class FileHelper {
    private static AppLogger log = new AppLogger(FileHelper.class);
    private static final String PATH_DELIMITER = "/";
    public static final int DEFAULT_BUFFER_SIZE = 8192;

    private FileHelper() {
        //Empty constructor
    }

    /**
     * This method copies the data from an input stream to a file.
     *
     * @param inputStream The input stream that contains the data to be copied to the file.
     * @param file        The file that the data will be copied to.
     * @return Returns a boolean indicating whether the copying was successful or not. Returns true if the copying was successful,
     * and false otherwise.
     */
    public static boolean copyInputStreamToFile(InputStream inputStream, File file){

        boolean result = false;

        // Wrap the FileOutputStream in a try-with-resources block to ensure that it is closed properly, even if an exception occurs.
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];

            // Read data from the input stream and write it to the output stream until there is no more data to read.
            while ((read = inputStream.read(bytes)) != -1) {
                // Write the data from the byte array to the output stream. Specify the starting index of the data to write and the number
                // of bytes to write.
                outputStream.write(bytes, 0, read);
            }

            // Set the result variable to true to indicate that the copying was successful.
            result = true;
        } catch (IOException e) {
            // handle the IOException
            log.error("copyInputStreamToFile", e);
        } catch (Exception e) {
            // handle any other unexpected exceptions
            log.error("copyInputStreamToFile", e);
        }
        return result;
    }

    /**
     * This method creates a directory at the specified destination path.
     *
     * @param destinationPath The path where the directory will be created.
     *                        This method takes a string parameter called destinationPath which represents the path where the directory will be created.
     */
    public static void createDirectory(String destinationPath) {
        String methodName = "createDirectory";

        File destinationFile = new File(destinationPath);

        try {
            // Check if the destination file already exists. If it doesn't, create a new directory at that path.
            // If it does, log a debug message indicating that the folder already exists and skipping creation.
            if (!destinationFile.exists()) {
                Files.createDirectories(destinationFile.toPath());
            } else {
                log.debug(methodName, "Folder already exists, skipping creation");
            }
        } catch (SecurityException e) {
            // If a security exception occurs while trying to create the directory, log a debug message with the exception details.
            log.error(methodName, e);
        } catch (IOException e) {
            // If an IOException occurs while trying to create the directory, log a debug message with the exception message.
            log.error(methodName, e);
        }
    }

    /**
     * This method deletes a directory and all its contents recursively.
     *
     * @param filePath The path of the directory to be deleted.
     */
    public static void deleteDirectory(String filePath) {
        final String methodName = "deleteDirectory";

        try {
            File file = new File(filePath);

            // Get the parent directory path from the File object.
            String directoryPath = file.getParent();

            // Create a new File object representing the parent directory.
            File directory = new File(directoryPath);

            // Check if the parent directory is a directory.
            if (directory.isDirectory()) {
                // Get all the files and directories in the parent directory.
                String[] files = directory.list();

                // If there are files and directories in the parent directory,
                if (files != null) {

                    // Iterate over each file and directory.
                    for (String fileStr : files) {

                        // Create a new File object representing the current file or directory.
                        File childFile = new File(directory, fileStr);

                        // If the current file or directory is a directory,
                        if (childFile.isDirectory()) {
                            // Recursively call the deleteDirectory method to delete it and its contents.
                            deleteDirectory(childFile.getAbsolutePath());
                        } else {
                            // If the current file or directory is a file, delete it.
                            childFile.delete();
                        }
                    }
                }
                // Delete the parent directory when all files and directories have been deleted.
                directory.delete();
            }
        } catch (SecurityException e) {
            // If a security exception occurs while trying to create the directory, log a debug message with the exception details.
            log.error(methodName, e);
        } catch (Exception e) {
            // If an IOException occurs while trying to create the directory, log a debug message with the exception message.
            log.error(methodName, e);
        }
    }

    public static void cleanDirectory(String filePath) {
        File file = new File(filePath);

        // Get the parent directory path from the File object.
        String directoryPath = file.getParent();

        // Create a new File object representing the parent directory.
        File directory = new File(directoryPath);

        // Check if the parent directory is a directory.
        if (directory.isDirectory()) {
            // Get all the files and directories in the parent directory.
            String[] files = directory.list();
            // If there are files and directories in the parent directory,
            if (files != null) {

                // Iterate over each file and directory.
                for (String fileStr : files) {

                    // Create a new File object representing the current file or directory.
                    File childFile = new File(directory, fileStr);

                    if (childFile.isDirectory()) {
                        cleanDirectory(childFile.getAbsolutePath());
                    } else {
                        childFile.delete();
                    }
                }
            }
        }
        file.delete();
    }

    /**
     * Reads the contents of a file from the classpath resources and returns it as a string.
     *
     * @param filename the name of the file to read from the resources folder
     * @return the contents of the file as a string
     * @throws IllegalArgumentException if the file is not found in the resources folder
     */
    public static String readFileFromResources(String filename) {
        final String methodName = "readFileFromResources";
        // Create a StringBuilder to store the contents of the file
        StringBuilder content = new StringBuilder();

        // Get the class loader to access the resources
        // The class loader is used to load classes and resources from the classpath
        ClassLoader classLoader = FileHelper.class.getClassLoader();

        // Read the file as an InputStream from the resources folder
        // The getResourceAsStream method returns an InputStream for the specified resource
        try (InputStream inputStream = classLoader.getResourceAsStream(filename)) {

            // Ensure the inputStream is not null
            // If the file is not found, getResourceAsStream returns null
            if (inputStream == null) {
                // Throw an exception if the file is not found
                throw new IllegalArgumentException("File not found in resources: " + filename);
            }

            // Construct a Scanner using UTF-8 encoding
            // The Scanner is used to read the file line by line
            try (Scanner scanner = new Scanner(inputStream, String.valueOf(StandardCharsets.UTF_8))) {
                // Read the file content line by line
                while (scanner.hasNextLine()) {
                    // Append each line to the StringBuilder with a line separator
                    content.append(scanner.nextLine()).append(System.lineSeparator());
                }
            }
        } catch (Exception ex) {
            // Handle any exceptions that occur during file reading
            log.error(methodName, ex);
        }

        // Return the contents of the file as a string
        return content.toString();
    }
}

