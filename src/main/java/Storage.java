import java.io.*;
import java.util.ArrayList;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // if file doesn't exist, create folder & file
        if (!file.exists()) {
            file.getParentFile().mkdirs(); // create ./data folder if missing
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

            Task task;
            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    task = new Deadline(description, parts[3]);
                    break;
                case "E":
                    task = new Event(description, parts[3]);
                    break;
                default:
                    continue;
            }
            if (isDone) task.markAsDone();
            tasks.add(task);
        }
        br.close();
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (Task task : tasks) {
            fw.write(task.toFileFormat() + System.lineSeparator());
        }
        fw.close();
    }
}
