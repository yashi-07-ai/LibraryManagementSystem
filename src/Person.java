import org.jetbrains.annotations.NotNull;

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
    public List<Book> borrowedBooks;
    private static final int maxBooks = 2;

    public RegularUser(String name) {
        super(name);
        this.borrowedBooks = new ArrayList<>();
    }

    public int getMaxBooks(){
        return maxBooks;
    }

    public List<Book> getIssuedBooks(){
        return borrowedBooks;
    }


    public boolean canBorrow(){
        return this.borrowedBooks.size() < maxBooks;
    }

    public void addBooks(Book book){
        borrowedBooks.add(book);
    }

    public void returnBook(Book book){
        borrowedBooks.remove(book);
    }

//    public synchronized void borrowBook(@NotNull Library library, String isbn, RequestType request) {
//        library.addBookRequest(new BookRequest(isbn, user, request));
//    }

//    public void returnBook(Library library, String isbn) {
//        for(Book book : borrowedBooks){
//            if(book.getIsbn().equals(isbn)){
//                borrowedBooks.remove(book);
//                book.returnBook();
//                System.out.println(getName() + " returned the book: " + book.getTitle());
//            }
//        }
//        System.out.println("Book not in borrowed books.");
//    }

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

class UserThread extends Thread{
    private final Library library;
    private final RegularUser user;
    private final String bookIsbn;
    private RequestType requestType;

    public UserThread(Library library, RegularUser user, String isbn, RequestType request) {
        this.library = library;
        this.user = user;
        this.bookIsbn = isbn;
        this.requestType = request;
    }

    @Override
    public void run() {
        library.addBookRequest(new BookRequest(this.bookIsbn, this.user, this.requestType));
    }
}




