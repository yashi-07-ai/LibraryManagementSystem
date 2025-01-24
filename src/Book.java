public class Book {
    private final String title;
    private final String author;
    private final String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
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

    @Override
    public String toString() {
        return String.format("Title: %s, Author: %s, ISBN: %s, Available: %s",
                title, author, isbn, isAvailable ? "Yes" : "No");
    }
}

// BookRequest class
class BookRequest {
    public final String isbn;
    public final Person user;

    public BookRequest(String isbn, Person user) {
        this.isbn = isbn;
        this.user = user;
    }

    public String getIsbn() {
        return isbn;
    }

    public Person getUser() {
        return user;
    }

}
