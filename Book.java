import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;
import java.util.Scanner;

class Book {
    private String name;
    private String isbn;
    private String date;
    private double price;
    private String description;

    public Book(String name, String isbn, String date, double price, String description) {
        this.name = name;
        this.isbn = isbn;
        this.date = date;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", ISBN: " + isbn + ", Date: " + date + ", Price: " + price + ", Author: " + description;
    }
}

class BookCollection {
    private ArrayList<Book> books;

    public BookCollection() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void sortByName() {
        Collections.sort(books, Comparator.comparing(Book::getName));
    }

    public void sortByISBN() {
        Collections.sort(books, Comparator.comparing(Book::getIsbn));
    }

    public void sortByDate() {
        Collections.sort(books, Comparator.comparing(Book::getDate));
    }

    public void sortByPrice() {
        Collections.sort(books, Comparator.comparingDouble(Book::getPrice));
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.println(book.getName());
                writer.println(book.getIsbn());
                writer.println(book.getDate());
                writer.println(book.getPrice());
                writer.println(book.getDescription());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            books.clear();
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                String isbn = scanner.nextLine();
                String date = scanner.nextLine();
                double price = Double.parseDouble(scanner.nextLine());
                String description = scanner.nextLine();
                Book book = new Book(name, isbn, date, price, description);
                books.add(book);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class BookCollectionApp extends JFrame {
    private JTextField nameField, isbnField, dateField, priceField, descriptionField;
    private JTextArea displayArea;
    private BookCollection bookCollection;

    public BookCollectionApp() {
        bookCollection = new BookCollection();

        setTitle("Book Collection");
        setSize(900, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        getContentPane().setBackground(new Color(255, 229, 180));

        JLabel nameLabel = new JLabel("Name:");
        add(nameLabel);
        nameField = new JTextField(10);
        add(nameField);

        JLabel isbnLabel = new JLabel("ISBN:");
        add(isbnLabel);
        isbnField = new JTextField(10);
        add(isbnField);

        JLabel dateLabel = new JLabel("Date:");
        add(dateLabel);
        dateField = new JTextField(10);
        add(dateField);

        JLabel priceLabel = new JLabel("Price:");
        add(priceLabel);
        priceField = new JTextField(10);
        add(priceField);

        JLabel descriptionLabel = new JLabel("Author:");
        add(descriptionLabel);
        descriptionField = new JTextField(10);
        add(descriptionField);

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String isbn = isbnField.getText();
                String date = dateField.getText();
                double price = Double.parseDouble(priceField.getText());
                String description = descriptionField.getText();

                Book book = new Book(name, isbn, date, price, description);
                bookCollection.addBook(book);
                displayBooks();
                clearFields();
            }
        });
        add(addButton);

        JButton removeButton = new JButton("Remove Book");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String isbn = JOptionPane.showInputDialog("Enter the ISBN of the book to remove:");
                Book bookToRemove = null;
                for (Book book : bookCollection.getBooks()) {
                    if (book.getIsbn().equals(isbn)) {
                        bookToRemove = book;
                        break;
                    }
                }
                if (bookToRemove != null) {
                    bookCollection.removeBook(bookToRemove);
                    displayBooks();
                } else {
                    JOptionPane.showMessageDialog(null, "Book with ISBN " + isbn + " not found.");
                }
            }
        });
        add(removeButton);

        JButton sortByNameButton = new JButton("Sort by Name");
        sortByNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookCollection.sortByName();
                displayBooks();
            }
        });
        add(sortByNameButton);

        JButton sortByISBNButton = new JButton("Sort by ISBN");
        sortByISBNButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookCollection.sortByISBN();
                displayBooks();
            }
        });
        add(sortByISBNButton);

        JButton sortByDateButton = new JButton("Sort by Date");
        sortByDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookCollection.sortByDate();
                displayBooks();
            }
        });
        add(sortByDateButton);

        JButton sortByPriceButton = new JButton("Sort by Price");
        sortByPriceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookCollection.sortByPrice();
                displayBooks();
            }
        });
        add(sortByPriceButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename = JOptionPane.showInputDialog("Enter the filename to save:");
                bookCollection.saveToFile(filename);
                JOptionPane.showMessageDialog(null, "Book collection saved successfully.");
            }
        });
        add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename = JOptionPane.showInputDialog("Enter the filename to load:");
                bookCollection.loadFromFile(filename);
                displayBooks();
                JOptionPane.showMessageDialog(null, "Book collection loaded successfully.");
            }
        });
        add(loadButton);

        displayArea = new JTextArea(40, 80);
        add(displayArea);

        setVisible(true);
    }

    private void displayBooks() {
        displayArea.setText("");
        for (Book book : bookCollection.getBooks()) {
            displayArea.append(book.toString() + "\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
        isbnField.setText("");
        dateField.setText("");
        priceField.setText("");
        descriptionField.setText("");
    }
}
