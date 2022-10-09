package com.example.subscriptionapi.utill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static void appendStringToEndOfFirstLine(String string, String fileLocation, String separator) {

        checkIfFileExists(fileLocation);


        LOGGER.info("Trying to write string - {} to file {}", string, fileLocation);

        try (FileWriter fileWriter = new FileWriter(fileLocation, true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append(string);
            bufferedWriter.append(separator);
            bufferedWriter.close();
            LOGGER.info("Successfully written string - {} to file - {}", string, fileLocation);
        } catch (IOException e) {
            LOGGER.error("Error occurred with writing string in file - {}", fileLocation, e);
        }
    }

    public static void writeInFirstLineStringFromList(List<String> strings, String fileLocation, String separator) {

        checkIfFileExists(fileLocation);

        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strings) {
            stringBuilder.append(str);
            stringBuilder.append(separator);
        }

        try (FileWriter fileWriter = new FileWriter(fileLocation)) {
            fileWriter.write(stringBuilder.toString());
            LOGGER.info("Successfully written strings - {} to file - {}", strings, fileLocation);
        } catch (IOException e) {
            LOGGER.error("Error occurred with writing strings in file - {}", fileLocation, e);
        }
    }


    public static List<String> getFileFirstLineAsListOfStrings(String fileLocation, String separator) {

        List<String> stringsList = Collections.emptyList();

        checkIfFileExists(fileLocation);

        try (FileReader fileReader = new FileReader(fileLocation)) {
            LOGGER.info("Trying to read data from file {}", fileLocation);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            if (line != null) {
                stringsList = new ArrayList<>(List.of(line.split(separator)));
            }

            bufferedReader.close();
            LOGGER.info("Successfully read file - {}. Amount of strings read - {}. Strings - {}", fileLocation, stringsList.size(), stringsList);
            return stringsList;

        } catch (IOException e) {
            LOGGER.error("Error occurred with reading strings from file - {}", fileLocation, e);
        }


        return stringsList;
    }

    public static void clearFile(String fileLocation) {
        checkIfFileExists(fileLocation);

        try {
            PrintWriter writer = new PrintWriter(fileLocation);
            writer.print("");
            writer.close();
        } catch (IOException exception) {
            LOGGER.error("Error occurred while clearing file with file location - {}", fileLocation);
        }

    }

    private static void checkIfFileExists(String fileLocation) {
        File file = new File(fileLocation);
        if (!file.exists()) {
            throw new IllegalArgumentException("File with location - " + fileLocation + "doesn't exits");
        }
    }
}