package duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 * This represents a storage place where all the tasks are stored.
 */
public class Storage {

    private File savedTextTasks;
    private String filePath;

    /**
     * This is a constructor for Storage which creates a text file to use as a Hard Disk
     * storage based on the file path provided.
     * @param filePath file path for creation of text file.
     */
    public Storage(String filePath) {
        savedTextTasks = new File(filePath);
        this.filePath = filePath;

        if (!savedTextTasks.exists()) {
            try {
                savedTextTasks.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }

    /**
     * This method takes the input String and rewrites the entire text file.
     * @param content String that is written onto the text file.
     */
    public void writeToStorage(String content) {
        try {
            FileWriter fw = new FileWriter(filePath);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * This method makes sense of the String in the Hard Disk text file by reading every
     * line of the file, detecting the information regarding each task stored, creating
     * new task objects using this information and adding them into the List of Task objects
     * that Duke bot is currently using for storage.
     * @return an ArrayList of Task objects.
     */
    public List<Task> readFromStorage() {
        List<Task> taskList = new ArrayList<>();
        try {
            Scanner textFileContent = new Scanner(savedTextTasks);

            while (textFileContent.hasNext()) {
                String eachLine = textFileContent.nextLine();
                char taskType = eachLine.split("\\[", 2)[1].charAt(0);
                char taskDone = eachLine.split("]\\[", 2)[1].charAt(0);
                String taskDescription = eachLine.split("] ", 2)[1].trim();
                taskList.add(createTask(taskType, taskDone, taskDescription));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error finding file: " + e.getMessage());
        }
        return taskList;
    }

    private static Task createTask(char taskType, char taskDone, String taskDescription) {
        Task createdTask = new Task("Created");
        boolean isDone = (taskDone == 'X');

        switch (taskType) {
        case 'T':
            Task taskToDo = new ToDo(taskDescription);
            if (isDone) {
                taskToDo.markAsDone();
            }
            createdTask = taskToDo;
            break;
        case 'D':
            Task taskDeadline = Parser.parseDeadlineFromFile(taskDescription);
            if (isDone) {
                taskDeadline.markAsDone();
            }
            createdTask = taskDeadline;
            break;
        case 'E':
            Task taskEvent = Parser.parseEventFromFile(taskDescription);
            if (isDone) {
                taskEvent.markAsDone();
            }
            createdTask = taskEvent;
            break;
        }

        return createdTask;
    }
}
