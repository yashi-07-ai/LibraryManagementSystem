import java.util.*;
import java.util.stream.Collectors;

// Main class
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        RegularUser user1 = new RegularUser("XYZ");
        RegularUser user2 = new RegularUser("yashi");
        Admin admin1 = new Admin("Jane Smith");

        user1.performAction();
        admin1.performAction();
        System.out.println("/------------------------------------------------/");


        // Admin adding books
        admin1.addBook(library, new Book("The Great Gatsby", "F. Scott Fitzgerald", "12345"));
        admin1.addBook(library, new Book("1984", "George Orwell", "54321"));
        admin1.addBook(library, new Book("Harry Potter", "J.K. Rowling", "45670"));
        admin1.addBook(library, new Book("Metamorphosis", "Kafka", "23456"));
        System.out.println("/------------------------------------------------/");


        // Regular user requesting a book
        user1.borrowBook(library, "12345");
        user2.borrowBook(library, "12345"); // this will not be assigned to user2 as already issued by user1
        user2.borrowBook(library, "54321");
        System.out.println("/------------------------------------------------/");

        // searching book
        library.searchBook("great");
        System.out.println("/------------------------------------------------/");

        library.searchBook("great gatsby");
        System.out.println("/------------------------------------------------/");

        // Process book requests
        library.processBookRequests();
        System.out.println("/------------------------------------------------/");

        library.viewIssuedBooks();
        System.out.println("/------------------------------------------------/");


        // Display available books
        library.filterAvailableBooks();
        System.out.println("/------------------------------------------------/");


        // Sort and display books by title
        library.sortBooksByTitle();
        System.out.println("/------------------------------------------------/");


        // Sort and display books by author
        library.sortBooksByAuthor();
        System.out.println("/------------------------------------------------/");


    }
}
