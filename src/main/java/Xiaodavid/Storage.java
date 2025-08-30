package Xiaodavid;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Storage {
    private String filePath;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // If file doesn't exist yet, create directories (if any) + empty file
        if (!file.exists()) {
            File parentDir = file.getParentFile();
            if (parentDir != null) {
                parentDir.mkdirs();
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

    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null) {
            parentDir.mkdirs();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (Task t : tasks) {
            bw.write(t.toSaveFormat()); // relies on Deadline/Event/Todo formatting their dates correctly
            bw.newLine();
        }
        bw.close();
    }
}
