package com.example.bitcoingenesis.utill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static void appendStringToEndOfFirstLine(String string, String fileLocation, String separator) {

        if(checkIfFileExists(fileLocation)) {

            logger.info("Trying to write string - {} to file {}", string, fileLocation);

            try (FileWriter fileWriter = new FileWriter(fileLocation, true)) {
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.append(string);
                bufferedWriter.append(separator);
                bufferedWriter.close();
                logger.info("Successfully written string - {} to file - {}", string, fileLocation);
            } catch (IOException e) {
                logger.error("Error occurred with writing string in file - {}", fileLocation, e);
            }
        }
    }

    public static void writeInFirstLineStringFromList(List<String> strings, String fileLocation, String separator) {

        if(checkIfFileExists(fileLocation)) {

            StringBuilder stringBuilder = new StringBuilder();
            for (String str : strings) {
                stringBuilder.append(str);
                stringBuilder.append(separator);
            }

            try (FileWriter fileWriter = new FileWriter(fileLocation)) {
                fileWriter.write(stringBuilder.toString());
                logger.info("Successfully written strings - {} to file - {}", strings, fileLocation);
            } catch (IOException e) {
                logger.error("Error occurred with writing strings in file - {}", fileLocation, e);
            }
        }

    }

    public static List<String> getFileFirstLineAsListOfStrings(String fileLocation, String separator) {

        List<String> stringsList = Collections.emptyList();

        if(checkIfFileExists(fileLocation)) {

            try (FileReader fileReader = new FileReader(fileLocation)) {
                logger.info("Trying to read data from file {}", fileLocation);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();

                if (line != null) {
                    stringsList = new ArrayList<>(List.of(line.split(separator)));
                }

                bufferedReader.close();
                logger.info("Successfully read file - {}. Amount of strings read - {}. Strings - {}", fileLocation, stringsList.size(), stringsList);
                return stringsList;

            } catch (IOException e) {
                logger.error("Error occurred with reading strings from file - {}", fileLocation, e);
            }

        }

        return stringsList;
    }

    private static boolean checkIfFileExists(String fileLocation) {
        File file = new File(fileLocation);
        boolean exists = file.exists();
        logger.info("Does file with file location - {} exists - {}", fileLocation, exists);
        return exists;
    }
}


