import java.awt.event.*;

public class TaskController {
    private TaskModel model;
    private TaskView view;

    public TaskController(TaskModel model, TaskView view) {
        this.model = model;
        this.view = view;

        // Load saved tasks
        model.loadTasks();
        initializeTasksFromModel();

        // Set up event listeners
        setupListeners();
    }

    private void initializeTasksFromModel() {
        for (TaskModel.TaskData taskData : model.getTasks()) {
            TaskView.TaskComponent task = new TaskView.TaskComponent(taskData.getText(), taskData.isCompleted());
            view.getListPanel().addTaskComponent(task);
            setupTaskListeners(task);
        }
        view.getListPanel().updateNumbers();
    }

    private void setupListeners() {
        // Add task button listener
        view.getAddTaskButton().addActionListener(e -> {
            TaskView.TaskComponent task = new TaskView.TaskComponent("Write your task..", false);
            view.getListPanel().addTaskComponent(task);
            view.getListPanel().updateNumbers();
            model.addTask(task.getText(), false);
            model.saveTasks();
            setupTaskListeners(task);
        });

        // Clear completed tasks button listener
        view.getClearButton().addActionListener(e -> {
            view.getListPanel().removeCompletedTasks();
            view.getListPanel().updateNumbers();
            model.removeCompletedTasks();
            model.saveTasks();
        });
    }

    private void setupTaskListeners(TaskView.TaskComponent task) {
        task.getDoneButton().addActionListener(e -> {
            task.setCompleted(true);
            view.getListPanel().updateNumbers();
            model.saveTasks();
        });
    }
}