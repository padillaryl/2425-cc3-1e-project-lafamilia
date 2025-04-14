import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TaskView {
    private JFrame frame;
    private TitleBar titleBar;
    private Footer footer;
    private ListPanel listPanel;

    public TaskView() {
        frame = new JFrame();
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleBar = new TitleBar();
        footer = new Footer();
        listPanel = new ListPanel();

        frame.add(titleBar.getPanel(), BorderLayout.NORTH);
        frame.add(footer.getPanel(), BorderLayout.SOUTH);
        frame.add(listPanel.getPanel(), BorderLayout.CENTER);
    }

    public void show() {
        frame.setVisible(true);
    }

    public JButton getAddTaskButton() {
        return footer.getNewTask();
    }

    public JButton getClearButton() {
        return footer.getClear();
    }

    public ListPanel getListPanel() {
        return listPanel;
    }

    // Inner view components
    static class TitleBar {
        private JPanel panel;
        private Color lightColor = new Color(252, 221, 176);

        public TitleBar() {
            panel = new JPanel();
            panel.setPreferredSize(new Dimension(400, 80));
            panel.setBackground(lightColor);

            JLabel titleText = new JLabel("Hampter List");
            titleText.setPreferredSize(new Dimension(200, 60));
            titleText.setFont(new Font("Sans-serif", Font.BOLD, 20));
            titleText.setHorizontalAlignment(JLabel.CENTER);
            panel.add(titleText);
        }

        public JPanel getPanel() {
            return panel;
        }
    }

    static class Footer {
        private JPanel panel;
        private JButton addTask;
        private JButton clear;
        private Color orange = new Color(233, 133, 128);
        private Color lightColor = new Color(252, 221, 176);
        private Border emptyBorder = BorderFactory.createEmptyBorder();

        public Footer() {
            panel = new JPanel();
            panel.setPreferredSize(new Dimension(400, 60));
            panel.setBackground(lightColor);

            addTask = new JButton("Add Task");
            addTask.setBorder(emptyBorder);
            addTask.setFont(new Font("Sans-serif", Font.ITALIC, 20));
            addTask.setVerticalAlignment(JButton.BOTTOM);
            addTask.setBackground(orange);
            panel.add(addTask);

            panel.add(Box.createHorizontalStrut(20));

            clear = new JButton("Clear finished tasks");
            clear.setFont(new Font("Sans-serif", Font.ITALIC, 20));
            clear.setBorder(emptyBorder);
            clear.setBackground(orange);
            panel.add(clear);
        }

        public JPanel getPanel() {
            return panel;
        }

        public JButton getNewTask() {
            return addTask;
        }

        public JButton getClear() {
            return clear;
        }
    }

    static class ListPanel {
        private JPanel panel;
        private Color lightColor = new Color(252, 221, 176);

        public ListPanel() {
            panel = new JPanel();
            GridLayout layout = new GridLayout(10, 1);
            layout.setVgap(5);
            panel.setLayout(layout);
            panel.setPreferredSize(new Dimension(400, 560));
            panel.setBackground(lightColor);
        }

        public JPanel getPanel() {
            return panel;
        }

        public void addTaskComponent(TaskComponent task) {
            panel.add(task.getPanel());
        }

        public void removeCompletedTasks() {
            Component[] components = panel.getComponents();
            for (Component c : components) {
                if (c instanceof TaskComponent.TaskPanel) {
                    TaskComponent.TaskPanel taskPanel = (TaskComponent.TaskPanel) c;
                    if (taskPanel.isCompleted()) {
                        panel.remove(taskPanel);
                    }
                }
            }
        }

        public void updateNumbers() {
            Component[] components = panel.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i] instanceof TaskComponent.TaskPanel) {
                    TaskComponent.TaskPanel taskPanel = (TaskComponent.TaskPanel) components[i];
                    taskPanel.setIndex(i + 1);
                }
            }
        }
    }

    static class TaskComponent {
        private TaskPanel panel;
        private Color pink = new Color(255, 161, 161);
        private Color green = new Color(188, 226, 158);
        private Color doneColor = new Color(233, 119, 119);

        public TaskComponent(String text, boolean completed) {
            panel = new TaskPanel(text, completed);
        }

        public JPanel getPanel() {
            return panel;
        }

        public JButton getDoneButton() {
            return panel.getDoneButton();
        }

        public boolean isCompleted() {
            return panel.isCompleted();
        }

        public void setCompleted(boolean completed) {
            panel.setCompleted(completed);
        }

        public String getText() {
            return panel.getText();
        }

        static class TaskPanel extends JPanel {
            private JLabel index;
            private JTextField taskName;
            private JButton done;
            private boolean completed;

            public TaskPanel(String text, boolean completed) {
                this.setPreferredSize(new Dimension(400, 20));
                this.setLayout(new BorderLayout());
                this.completed = completed;

                index = new JLabel("");
                index.setPreferredSize(new Dimension(20, 20));
                index.setHorizontalAlignment(JLabel.CENTER);
                this.add(index, BorderLayout.WEST);

                taskName = new JTextField(text);
                taskName.setBorder(BorderFactory.createEmptyBorder());
                this.add(taskName, BorderLayout.CENTER);

                done = new JButton("Done");
                done.setPreferredSize(new Dimension(80, 20));
                done.setBorder(BorderFactory.createEmptyBorder());
                done.setFocusPainted(false);
                this.add(done, BorderLayout.EAST);

                updateAppearance();
            }

            public void setIndex(int num) {
                this.index.setText(num + "");
                this.revalidate();
            }

            public JButton getDoneButton() {
                return done;
            }

            public boolean isCompleted() {
                return completed;
            }

            public void setCompleted(boolean completed) {
                this.completed = completed;
                updateAppearance();
            }

            public String getText() {
                return taskName.getText();
            }

            private void updateAppearance() {
                Color bgColor = completed ? green : pink;
                this.setBackground(bgColor);
                taskName.setBackground(bgColor);
                done.setBackground(doneColor);
            }
        }
    }
}