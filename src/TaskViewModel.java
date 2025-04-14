import java.beans.*;
import java.util.*;

public class TaskViewModel {
    private final TaskModel model;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public static final String TASKS_UPDATED = "tasksUpdated";

    public TaskViewModel(TaskModel model) {
        this.model = model;
        model.loadTasks();
    }

    public List<TaskModel.Task> getTasks() {
        return model.getTasks();
    }

    public void addTask(String text) {
        model.addTask(new TaskModel.Task(text, false));
        propertyChangeSupport.firePropertyChange(TASKS_UPDATED, null, model.getTasks());
    }

    public void markTaskCompleted(int index) {
        model.markTaskCompleted(index);
        propertyChangeSupport.firePropertyChange(TASKS_UPDATED, null, model.getTasks());
    }

    public void removeCompletedTasks() {
        model.removeCompletedTasks();
        propertyChangeSupport.firePropertyChange(TASKS_UPDATED, null, model.getTasks());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}