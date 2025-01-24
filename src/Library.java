import java.util.*;

public class Library {
    private List<Book> books;
    private Queue<BookRequest> bookRequestQueue;
    private Map<Book, Person> issuedBooks;  // to keep a track of issued books

    public Library() {
        this.books = new ArrayList<>();
        this.bookRequestQueue = new LinkedList<>();
        this.issuedBooks = new HashMap<>();
    }

    // Add book
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    // Search books
    public void searchBook(String keyword) {
        System.out.println("Search results for: " + keyword);
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(book);
            }
        }
    }

    // Get book by ISBN
    public Book getBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        System.out.println("No book found with ISBN: " + isbn);
        return null;
    }

    // Process the book request queue
    public void processBookRequests() {
        while (!bookRequestQueue.isEmpty()) {
            BookRequest request = bookRequestQueue.poll();
            Book book = getBookByISBN(request.getIsbn());
            if (book != null && book.isAvailable()) {
                book.borrowBook();
                issuedBooks.put(book, request.getUser());
                System.out.println("Book issued: " + book.getTitle() + " to " + request.getUser().getName());
            } else {
                System.out.println("Book request for ISBN " + request.getIsbn() + " could not be fulfilled.");
            }
        }
    }

    public void returnBook(Book book, Person user){
        if (issuedBooks.containsKey(book) && issuedBooks.get(book).equals(user)) {
            issuedBooks.remove(book);
            book.returnBook();
            System.out.println("Book returned: " + book.getTitle() + " by " + user.getName());
        } else {
            System.out.println("This book was not issued to " + user.getName());
        }
    }

    // view book borrowed and their users
    public void viewIssuedBooks() {
        if (issuedBooks.isEmpty()) {
            System.out.println("No books are currently issued.");
        } else {
            System.out.println("Issued Books:");
            for (Map.Entry<Book, Person> entry : issuedBooks.entrySet()) {
                System.out.println("Book: " + entry.getKey().getTitle() + ", Issued to: " + entry.getValue().getName());
            }
        }
    }

    // Add a book request to the queue
    public void addBookRequest(BookRequest request) {
        bookRequestQueue.offer(request);
        System.out.println("Book request added to the queue for: " + request.getIsbn());
    }

    // Sort books
    public void sortBooksByTitle() {
        System.out.println("Books sorted by title:");
        books.stream()
                        .sorted(Comparator.comparing(Book::getTitle))     // sorts the book
                                .forEach(System.out::println);            // prints the sorted books
    }

    public void sortBooksByAuthor() {
        System.out.println("Books sorted by author:");
        books.stream().sorted(Comparator.comparing(Book::getAuthor))
                        .forEach(System.out::println);
    }

    // Filter books by availability using Streams
    public void filterAvailableBooks() {
        System.out.println("Available books:");
        books.stream()
                .filter(Book::isAvailable)
                .forEach(System.out::println);
    }
}



