import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskManagerGUI {
    private TaskManager taskManager;
    private JFrame frame;
    private JTextField descriptionField;
    private JTextField dueDateField;
    private JTextArea taskListArea;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TaskManagerGUI() {
        taskManager = new TaskManager();
        frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Due Date (yyyy-MM-dd):"));
        dueDateField = new JTextField();
        inputPanel.add(dueDateField);

        JButton addButton = new JButton("Add Task");
        inputPanel.add(addButton);
        JButton saveButton = new JButton("Save Tasks");
        inputPanel.add(saveButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        taskListArea = new JTextArea();
        taskListArea.setEditable(false);
        frame.add(new JScrollPane(taskListArea), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTasks();
            }
        });

        frame.setVisible(true);
    }

    private void addTask() {
        String description = descriptionField.getText();
        String dueDateText = dueDateField.getText();
        try {
            Date dueDate = dateFormat.parse(dueDateText);
            Task task = new Task(description, dueDate);
            taskManager.addTask(task);
            updateTaskList();
            descriptionField.setText("");
            dueDateField.setText("");
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveTasks() {
        try {
            taskManager.saveTasksToFile("tasks.txt");
            JOptionPane.showMessageDialog(frame, "Tasks saved to tasks.txt", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving tasks: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTaskList() {
        taskListArea.setText("");
        for (Task task : taskManager.getTasks()) {
            taskListArea.append(task.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskManagerGUI::new);
    }
}
