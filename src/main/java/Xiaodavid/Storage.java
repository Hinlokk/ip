package Xiaodavid;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Handles saving and loading of tasks from a file.
 */
public class Storage {
    private String filePath;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Creates a Storage object with the given file path.
     *
     * @param filePath path of the save file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the save file.
     * If the file does not exist, a new empty file is created.
     *
     * @return list of tasks loaded from the file
     * @throws IOException if there is an error accessing the file
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            return tasks;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            switch (type) {
                case "T": {
                    Task todo = new Todo(description);
                    if (isDone) todo.markAsDone();
                    tasks.add(todo);
                    break;
                }
                case "D": {
                    LocalDate by = LocalDate.parse(parts[3], DATE_FORMAT);
                    Task deadline = new Deadline(description, by);
                    if (isDone) deadline.markAsDone();
                    tasks.add(deadline);
                    break;
                }
                case "E": {
                    LocalDate from = LocalDate.parse(parts[3], DATE_FORMAT);
                    LocalDate to = LocalDate.parse(parts[4], DATE_FORMAT);
                    Task event = new Event(description, from, to);
                    if (isDone) event.markAsDone();
                    tasks.add(event);
                    break;
                }
            }
        }
        br.close();
        return tasks;
    }

    /**
     * Saves tasks to the save file.
     *
     * @param tasks list of tasks to save
     * @throws IOException if there is an error writing to the file
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (Task t : tasks) {
            bw.write(t.toSaveFormat());
            bw.newLine();
        }
        bw.close();
    }
}
