import java.util.*;

public class Library {
    private final List<Book> books;
    private final Queue<BookRequest> bookRequestQueue;
    private final Map<Book, RegularUser> issuedBooks;  // to keep a track of issued books

    public Library() {
        this.books = new ArrayList<>();
        this.bookRequestQueue = new LinkedList<>();
        this.issuedBooks = new HashMap<>();
    }

    // Add book
    public synchronized void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    // Add/Edit Book Content (Admins Only)
    public void addOrEditBookContent(String isbn, String content, Admin admin) {
        Book book = getBookByISBN(isbn);
        if (book != null) {
            System.out.println(admin.getName() + " is editing the content of the book: " + book.getTitle());
            book.writeContent(content);
        } else {
            System.out.println("No book found with ISBN: " + isbn);
        }
    }

    // Read Book Content (Accessible by Any User)
    public void readBookContent(String isbn, Person user) {
        try{
            Book book = getBookByISBN(isbn);
            if (book != null) {
                if(issuedBooks.containsKey(book) && issuedBooks.get(book).equals(user)){
                    System.out.println(user.getName() + " is reading the content of the book: " + book.getTitle());
                    book.readContent();
                }
                else {
                    throw new LibraryExceptions(user.getName() + " cannot read this book.");
                }



            } else {
                System.out.println("No book found with ISBN: " + isbn);
            }
        }catch(LibraryExceptions e){
            System.err.println("Operation Error: " + e.getMessage());
        }

    }

    public synchronized void addBookRequest(BookRequest request) {
        bookRequestQueue.add(request);
        Book book = getBookByISBN(request.getIsbn());
        String bookTitle = book.getTitle();
        System.out.println(request.getUser().getName() + " submitted a request to " +
                (request.getRequestType() == RequestType.BORROW ? "borrow" : "return") +
                " the book: " + bookTitle);
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

    // Process book requests (borrowing and returning)
    public void processBookRequests() {
        boolean stopProcessing = false;
        while (!stopProcessing) {
            BookRequest request;

            synchronized (this) {
                request = bookRequestQueue.poll();
            }

            if (request != null) {
                Person user = request.getUser();
                String isbn = request.getIsbn();
                Book book = getBookByISBN(isbn);
                String bookTitle = book.getTitle();

                if(user instanceof RegularUser regularUser){
                    try{
                        RequestType requestType = request.getRequestType();

                        if (requestType == RequestType.BORROW) {
                            boolean success = borrowBook(bookTitle, regularUser);
                            if (success) {
                                System.out.println(user.getName() + " successfully borrowed the book: " + bookTitle);
                                issuedBooks.put(book, regularUser);
                            } else {
                                throw new LibraryExceptions(user.getName() + " could not borrow the book: " + bookTitle);
                            }
                        } else if (requestType == RequestType.RETURN) {
                            boolean success = returnBook(bookTitle, regularUser);

                            if (success) {
                                System.out.println(user.getName() + " successfully returned the book: " + bookTitle);
                                issuedBooks.remove(book, user);
                            } else {
                                throw new LibraryExceptions(user.getName() + " could not return the book: " + bookTitle);
                            }
                        }

                    }catch (LibraryExceptions e) {
                        System.err.println("Operation Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Unexpected Error: " + e.getMessage());
                        e.printStackTrace();
                    }

//                    // Simulate processing delay
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            } else {
                stopProcessing = true;
            }
        }
        System.out.println("Processing complete. All requests have been handled.");
    }


    private synchronized boolean borrowBook(String bookTitle, RegularUser user) throws LibraryExceptions
    {
        if(!user.canBorrow()){
            throw new LibraryExceptions(user.getName() + " has already reached the maximum limit of borrowed books.");
        }

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(bookTitle) && book.isAvailable()) {
                user.addBooks(book);
                book.borrowBook();
                System.out.println("Books with " + user.getName() + " : " + user.getIssuedBooks().size());
                return true;
            }
        }

        return false;
    }

    // Return a book (thread-safe)
    @NotNull(message = "Book returned cannot be null")
    private synchronized boolean returnBook(String bookTitle, RegularUser user) throws LibraryExceptions
    {
        boolean found = false;

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                if (book.isAvailable()) {
                    throw new LibraryExceptions(user.getName() + " is trying to return a book that was not borrowed.");
                }
                book.returnBook();
                user.returnBook(book);
                return true;
            }
        }

        if (!found) {
            throw new LibraryExceptions("The book " + bookTitle + " does not exist in the library.");
        }

        return false;
    }

//    public void returnBook(Book book, Person user){
//        if (issuedBooks.containsKey(book) && issuedBooks.get(book).equals(user)) {
//            issuedBooks.remove(book);
//            book.returnBook();
//            System.out.println("Book returned: " + book.getTitle() + " by " + user.getName());
//        } else {
//            System.out.println("This book was not issued to " + user.getName());
//        }
//    }

    // view book borrowed and their users
    public void viewIssuedBooks() {
        if (issuedBooks.isEmpty()) {
            System.out.println("No books are currently issued.");
        } else {
            System.out.println("Issued Books:");
            for (Map.Entry<Book, RegularUser> entry : issuedBooks.entrySet()) {
                System.out.println("Book: " + entry.getKey().getTitle() + ", Issued to: " + entry.getValue().getName());
            }
        }
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

class LibraryExceptions extends Exception{
    public LibraryExceptions(String message) {
        super(message);
    }
}

