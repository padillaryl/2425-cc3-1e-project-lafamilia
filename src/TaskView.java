import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.beans.*;
import java.util.List;

public class TaskView {
    private final TaskViewModel viewModel;
    private JFrame frame;
    private JPanel taskListPanel;  // Renamed from listPanel to avoid confusion

    public TaskView(TaskViewModel viewModel) {
        this.viewModel = viewModel;
        initializeUI();
        setupBindings();
    }

    private void initializeUI() {
        frame = new JFrame("Hampter List");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title Bar
        JPanel titleBar = new JPanel();
        titleBar.setPreferredSize(new Dimension(400, 80));
        titleBar.setBackground(new Color(252, 221, 176));
        JLabel titleText = new JLabel("Hampter List");
        titleText.setFont(new Font("Sans-serif", Font.BOLD, 20));
        titleBar.add(titleText);
        frame.add(titleBar, BorderLayout.NORTH);

        // Task List Panel
        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        taskListPanel.setBackground(new Color(252, 221, 176));

        JScrollPane scrollPane = new JScrollPane(taskListPanel);
        scrollPane.setPreferredSize(new Dimension(400, 560));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setPreferredSize(new Dimension(400, 60));
        footer.setBackground(new Color(252, 221, 176));

        JButton addTask = new JButton("Add Task");
        addTask.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        addTask.setBackground(new Color(233, 133, 128));
        addTask.addActionListener(e -> {
            String taskText = JOptionPane.showInputDialog(frame, "Enter task:");
            if (taskText != null && !taskText.trim().isEmpty()) {
                viewModel.addTask(taskText);
            }
        });

        JButton clear = new JButton("Clear finished tasks");
        clear.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        clear.setBackground(new Color(233, 133, 128));
        clear.addActionListener(e -> viewModel.removeCompletedTasks());

        footer.add(addTask);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(clear);
        frame.add(footer, BorderLayout.SOUTH);
    }

    private void setupBindings() {
        viewModel.addPropertyChangeListener(evt -> {
            if (TaskViewModel.TASKS_UPDATED.equals(evt.getPropertyName())) {
                updateTaskList();
            }
        });
        updateTaskList();
    }

    private void updateTaskList() {
        taskListPanel.removeAll();
        List<TaskModel.Task> tasks = viewModel.getTasks();

        for (int i = 0; i < tasks.size(); i++) {
            TaskModel.Task task = tasks.get(i);
            JPanel taskPanel = createTaskPanel(task, i);
            taskListPanel.add(taskPanel);
            taskListPanel.add(Box.createVerticalStrut(5));
        }

        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    private JPanel createTaskPanel(TaskModel.Task task, int index) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(380, 30));
        panel.setMaximumSize(new Dimension(380, 30));
        panel.setBackground(task.isCompleted() ? new Color(188, 226, 158) : new Color(255, 161, 161));

        JLabel indexLabel = new JLabel((index + 1) + "");
        indexLabel.setPreferredSize(new Dimension(30, 30));
        indexLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(indexLabel, BorderLayout.WEST);

        JTextField taskField = new JTextField(task.getText());
        taskField.setBorder(BorderFactory.createEmptyBorder());
        taskField.setBackground(task.isCompleted() ? new Color(188, 226, 158) : new Color(255, 161, 161));
        taskField.setEditable(false);
        panel.add(taskField, BorderLayout.CENTER);

        JButton doneButton = new JButton(task.isCompleted() ? "✓" : "Done");
        doneButton.setPreferredSize(new Dimension(80, 30));
        doneButton.setBorder(BorderFactory.createEmptyBorder());
        doneButton.setBackground(new Color(233, 119, 119));
        doneButton.setEnabled(!task.isCompleted());
        doneButton.addActionListener(e -> viewModel.markTaskCompleted(index));
        panel.add(doneButton, BorderLayout.EAST);

        return panel;
    }

    public void show() {
        frame.setVisible(true);
    }
}