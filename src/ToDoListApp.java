public class ToDoListApp {
    public static void main(String[] args) {
        TaskModel model = new TaskModel();
        TaskViewModel viewModel = new TaskViewModel(model);
        TaskView view = new TaskView(viewModel);
        view.show();
    }
}