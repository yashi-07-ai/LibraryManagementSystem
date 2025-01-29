import java.io.*;

enum RequestType{
    BORROW, RETURN
}

@BookInfo(createdBy = "LibraryAdmin", lastModified = "2025-01-28")
public class Book implements Serializable{
    @NotNull(message = "Title cannot be null")
    private final String title;

    @NotNull(message = "Author cannot be null")
    private final String author;

    @NotNull(message = "ISBN cannot be null")
    private final String isbn;

    private boolean isAvailable;
    private final File bookFile;
    transient public int id = 1;

    public Book(@NotNull(message = "Fields cannot be null") String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;

        // Create a file for storing the book's content
        this.bookFile = new File(title.replaceAll(" ", "_") + ".txt");
        try {
            if (bookFile.createNewFile()) {
                System.out.println("File created for book: " + title);
            }
        } catch (IOException e) {
            System.err.println("Error creating file for book: " + title);
            e.printStackTrace();
        }
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Borrow and return books
    public void borrowBook() {
        isAvailable = false;
    }

    public void returnBook() {
        isAvailable = true;
    }

    // Write content to the book file
    public void writeContent(String newContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookFile, true))) {
            writer.write(newContent);
            writer.newLine();
            System.out.println("Content added to the book: " + title);
        } catch (IOException e) {
            System.err.println("Error writing to the book file: " + title);
            e.printStackTrace();
        }
    }

    // Read content from the book file
    public void readContent() {
        System.out.println("Reading content of the book: " + title);
        try (BufferedReader reader = new BufferedReader(new FileReader(bookFile))) {
            String line;
            int pageNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println("Page " + pageNumber++ + ": " + line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the book file: " + title);
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Author: %s, ISBN: %s, Available: %s",
                title, author, isbn, isAvailable ? "Yes" : "No");
    }
}

// BookRequest class
class BookRequest {
    private final String isbn;
    private final Person user;
    private RequestType requestType;

    public BookRequest(@NotNull String isbn, Person user, RequestType request) {
        this.isbn = isbn;
        this.user = user;
        this.requestType = request;
    }

    public String getIsbn() {
        return isbn;
    }

    public Person getUser() {
        return user;
    }

    public RequestType getRequestType(){
        return requestType;
    }

}
