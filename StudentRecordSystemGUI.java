/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentrecordsystemgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentRecordSystemGUI extends JFrame {
    private JTextField nameField, matrixField, facultyField, matrixNumberField;
    private JTextArea recordArea;
    private JComboBox<String> sortComboBox;
    private JComboBox<String> studentComboBox; // Changed type to String
    private JButton addButton, removeButton, sortButton, viewButton, saveButton, loadButton;
    private List<Student> studentList;

    public StudentRecordSystemGUI() {
        studentList = new ArrayList<>();

        setTitle("Student Record System");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        nameField = new JTextField(15);
        matrixField = new JTextField(10);
        facultyField = new JTextField(10);
        recordArea = new JTextArea(15, 30);
        sortComboBox = new JComboBox<>(new String[]{"Name", "Faculty"});
        addButton = new JButton("Add Student");
        removeButton = new JButton("Remove Student");
        sortButton = new JButton("Sort Students");
        viewButton = new JButton("View Record");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        studentComboBox = new JComboBox<>(); // Initialize the combo box
        matrixNumberField = new JTextField(10);

        // Create panels
        JPanel inputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel recordPanel = new JPanel();

        // Set layouts
        setLayout(new BorderLayout());
        inputPanel.setLayout(new GridLayout(3, 2));
        recordPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(1, 7)); // Increase the grid layout columns to accommodate the new combo box

        // Add components to panels
        inputPanel.add(new JLabel("Name: "));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Matrix Number: "));
        inputPanel.add(matrixField);
        inputPanel.add(new JLabel("Faculty: "));
        inputPanel.add(facultyField);

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(new JLabel("Matrix Number: "));
        buttonPanel.add(matrixNumberField);
        buttonPanel.add(sortButton);
        buttonPanel.add(sortComboBox);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        recordPanel.add(new JScrollPane(recordArea), BorderLayout.CENTER);
        recordPanel.add(viewButton, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(recordPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeStudent();
            }
        });

        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortStudents();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewRecord();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveRecords();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadRecords();
            }
        });
    }

    private void addStudent() {
        String name = nameField.getText();
        String matrixNumber = matrixField.getText();
        String faculty = facultyField.getText();

        if (!name.isEmpty() && !matrixNumber.isEmpty() && !faculty.isEmpty()) {
            Student student = new Student(name, matrixNumber, faculty);
            studentList.add(student);
            clearFields();
            recordArea.append("Student added: " + student.toString() + "\n");

            // Update the combo box with the updated student list
            updateStudentComboBox();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeStudent() {
        String matrixNumber = matrixNumberField.getText(); // Get the matrix number from the text field
        for (Student student : studentList) {
            if (student.getMatrixNumber().equals(matrixNumber)) {
                studentList.remove(student);
                clearFields();
                recordArea.setText("");
                recordArea.append("Student removed: " + student.toString() + "\n");

                // Update the combo box with the updated student list
                updateStudentComboBox();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void sortStudents() {
        String selectedSort = (String) sortComboBox.getSelectedItem();
        if (selectedSort.equals("Name")) {
            Collections.sort(studentList, Comparator.comparing(Student::getName));
        } else if (selectedSort.equals("Faculty")) {
            Collections.sort(studentList, Comparator.comparing(Student::getFaculty));
        }
        viewRecord();
    }

    private void viewRecord() {
        recordArea.setText("");
        for (Student student : studentList) {
            recordArea.append(student.toString() + "\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
        matrixField.setText("");
        facultyField.setText("");
        matrixNumberField.setText("");
    }

    private void saveRecords() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("save_file"))) {
            outputStream.writeObject(studentList);
            JOptionPane.showMessageDialog(this, "Records saved successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving records.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRecords() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("save_file"))) {
            studentList = (List<Student>) inputStream.readObject();
            viewRecord();
            updateStudentComboBox();
            JOptionPane.showMessageDialog(this, "Records loaded successfully.", "Load", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading records.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudentComboBox() {
        studentComboBox.removeAllItems();
        for (Student student : studentList) {
            studentComboBox.addItem(String.valueOf(student));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentRecordSystemGUI().setVisible(true);
            }
        });
    }

    private class Student implements Serializable {
        private String name;
        private String matrixNumber;
        private String faculty;

        public Student(String name, String matrixNumber, String faculty) {
            this.name = name;
            this.matrixNumber = matrixNumber;
            this.faculty = faculty;
        }

        public String getName() {
            return name;
        }

        public String getMatrixNumber() {
            return matrixNumber;
        }

        public String getFaculty() {
            return faculty;
        }

        public String toString() {
            return "Name: " + name + ", Matrix Number: " + matrixNumber + ", Faculty: " + faculty;
        }
    }
}

