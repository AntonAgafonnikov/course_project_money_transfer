package ru.netology.course_project_money_transfer.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterLogsInFile {

    private String message;

    public WriterLogsInFile() {
    }

    public WriterLogsInFile(String message) {
        this.message = message;
    }

    public static void writeLogsInFile(String msg) {
        File file = new File("logs.txt");
        try {
            if (file.createNewFile()){
                System.out.println("File is created!");
            }
            else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt", true))) {
            writer.write(msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
