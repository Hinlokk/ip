import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Handles saving and loading tasks from a file.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the save file.
     * If file or folder does not exist, creates them and returns an empty list.
     */
    public ArrayList<Task> load() throws DukeException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        try {
            // Create folder if missing
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Create file if missing
            if (!file.exists()) {
                file.createNewFile();
                return tasks;
            }

            // Read tasks
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                int isDone = Integer.parseInt(parts[1]);
                String description = parts[2];

                Task t;
                switch (type) {
                    case "T":
                        t = new ToDo(description);
                        break;
                    case "D":
                        t = new Deadline(description, parts[3]);
                        break;
                    case "E":
                        t = new Event(description, parts[3]);
                        break;
                    default:
                        throw new DukeException("Corrupted data file: unknown task type");
                }

                if (isDone == 1) {
                    t.markAsDone();
                }
                tasks.add(t);
            }
            sc.close();
        } catch (IOException e) {
            throw new DukeException("Error loading save file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves all tasks into the save file.
     */
    public void save(ArrayList<Task> tasks) throws DukeException {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (Task t : tasks) {
                fw.write(t.toSaveFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new DukeException("Error saving tasks: " + e.getMessage());
        }
    }
}