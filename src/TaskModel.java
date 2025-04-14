import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskModel {
    private static final String DATA_FILE = "tasks.json";
    private List<TaskData> tasks;

    public TaskModel() {
        tasks = new ArrayList<>();
    }

    public void addTask(String text, boolean completed) {
        tasks.add(new TaskData(text, completed));
    }

    public void removeCompletedTasks() {
        tasks.removeIf(TaskData::isCompleted);
    }

    public List<TaskData> getTasks() {
        return new ArrayList<>(tasks);
    }

    @SuppressWarnings("unchecked")
    public void saveTasks() {
        JSONArray tasksJson = new JSONArray();
        for (TaskData task : tasks) {
            JSONObject taskJson = new JSONObject();
            taskJson.put("text", task.getText());
            taskJson.put("completed", task.isCompleted());
            tasksJson.add(taskJson);
        }

        try (FileWriter file = new FileWriter(DATA_FILE)) {
            file.write(tasksJson.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTasks() {
        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(DATA_FILE);
            JSONArray tasksJson = (JSONArray) parser.parse(reader);

            tasks.clear();
            for (Object obj : tasksJson) {
                JSONObject taskJson = (JSONObject) obj;
                String text = (String) taskJson.get("text");
                boolean completed = (Boolean) taskJson.get("completed");
                tasks.add(new TaskData(text, completed));
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class TaskData {
        private final String text;
        private final boolean completed;

        public TaskData(String text, boolean completed) {
            this.text = text;
            this.completed = completed;
        }

        public String getText() {
            return text;
        }

        public boolean isCompleted() {
            return completed;
        }
    }
}