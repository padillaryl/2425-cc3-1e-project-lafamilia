import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.util.*;

public class TaskModel {
    private static final String DATA_FILE = "tasks.json";
    private List<Task> tasks = new ArrayList<>();

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public void removeCompletedTasks() {
        tasks.removeIf(Task::isCompleted);
        saveTasks();
    }

    public void markTaskCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).setCompleted(true);
            saveTasks();
        }
    }

    @SuppressWarnings("unchecked")
    public void saveTasks() {
        JSONArray tasksJson = new JSONArray();
        for (Task task : tasks) {
            JSONObject taskJson = new JSONObject();
            taskJson.put("text", task.getText());
            taskJson.put("completed", task.isCompleted());
            tasksJson.add(taskJson);
        }

        try (FileWriter file = new FileWriter(DATA_FILE)) {
            file.write(tasksJson.toJSONString());
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
                tasks.add(new Task(text, completed));
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Task {
        private String text;
        private boolean completed;

        public Task(String text, boolean completed) {
            this.text = text;
            this.completed = completed;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}