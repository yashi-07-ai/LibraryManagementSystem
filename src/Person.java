import java.util.*;

// Base class for Person
abstract class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void performAction();
}

class RegularUser extends Person {
    private List<Book> borrowedBooks;

    public RegularUser(String name) {
        super(name);
        this.borrowedBooks = new ArrayList<>();
    }

    public void borrowBook(Library library, String isbn) {
        library.addBookRequest(new BookRequest(isbn, this));
    }

    public void returnBook(Library library, String isbn) {
        for(Book book : borrowedBooks){
            if(book.getIsbn().equals(isbn)){
                borrowedBooks.remove(book);
                book.returnBook();
                System.out.println(getName() + " returned the book: " + book.getTitle());
            }
        }
        System.out.println("Book not in borrowed books.");
    }

    @Override
    public void performAction() {
        System.out.println(getName() + " is a regular user and can borrow/return books.");
    }
}

// Admin class
class Admin extends Person {
    public Admin(String name) {
        super(name);
    }

    public void addBook(Library library, Book book) {
        library.addBook(book);
    }

    @Override
    public void performAction() {
        System.out.println(getName() + " is an admin and can manage books.");
    }
}




