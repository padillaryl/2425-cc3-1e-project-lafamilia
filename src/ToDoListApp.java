public class ToDoListApp {
    public static void main(String[] args) {
        // Create model, view and controller
        TaskModel model = new TaskModel();
        TaskView view = new TaskView();
        TaskController controller = new TaskController(model, view);

        // Display the GUI
        view.show();
    }
}