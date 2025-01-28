enum RequestType{
    BORROW, RETURN
}

@BookInfo(createdBy = "LibraryAdmin", lastModified = "2025-01-28")
public class Book {
    @NotNull(message = "Title cannot be null")
    private final String title;

    @NotNull(message = "Author cannot be null")
    private final String author;

    @NotNull(message = "ISBN cannot be null")
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
