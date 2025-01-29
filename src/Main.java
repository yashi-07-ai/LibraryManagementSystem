import java.io.*;

// Main class
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        // Create users
        RegularUser user1 = new RegularUser("Alice");
        RegularUser user2 = new RegularUser("Bob");
        RegularUser user3 = new RegularUser("Charlie");

        Admin admin1 = new Admin("Jane Smith");

        user1.performAction();
        admin1.performAction();
        System.out.println("/------------------------------------------------/");


        // Admin adding books
        admin1.addBook(library, new Book("The Great Gatsby", "F. Scott Fitzgerald", "12345"));
        admin1.addBook(library, new Book("1984", "George Orwell", "54321"));
        admin1.addBook(library, new Book("Harry Potter", "J.K. Rowling", "45670"));
        admin1.addBook(library, new Book("Metamorphosis", "Kafka", "23456"));
        admin1.addBook(library, new Book("To Kill a Mocking Bird", "Harper Lee", "34567"));
        System.out.println("/------------------------------------------------/");

        // custom annotation @bookInfo
        if (Book.class.isAnnotationPresent(BookInfo.class)) {
            BookInfo bookInfo = Book.class.getAnnotation(BookInfo.class);
            System.out.println("Book Created By: " + bookInfo.createdBy());
            System.out.println("Last Modified: " + bookInfo.lastModified());
        }

        // custom notnull annotations
        try {
            Book book = new Book(null, "John Doe", "12345"); // Title is null
            NotNullValidator.validate(book); // Validate the object
        } catch (Exception e) {
            System.err.println("Validation failed: " + e.getMessage());
        }


        // Create threads for each user
        Thread userThread1 = new UserThread(library, user1, "12345", RequestType.BORROW);
        Thread userThread2 = new UserThread(library, user2, "23456", RequestType.BORROW);
        Thread userThread3 = new UserThread(library, user3, "12345", RequestType.BORROW); // Same book as user 1
        Thread userThread4 = new UserThread(library, user1, "34567", RequestType.BORROW);
        Thread userThread5 = new UserThread(library, user1, "45670", RequestType.BORROW);



        // Start threads
        userThread1.start();
        userThread2.start();
        userThread3.start();
        userThread4.start();
        userThread5.start();

        // managing the process queue
        Thread libraryProcessor = new Thread(library::processBookRequests);
        libraryProcessor.start();


        System.out.println("/------------------------------------------------/");

        // Wait for user threads to finish
        try {
            userThread1.join();
            userThread2.join();
            userThread3.join();
            userThread4.join();
            userThread5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Let the library process remaining requests
        try {
            Thread.sleep(5000); // Wait 5 seconds for processing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //reading a file
        library.readBookContent("12345", user1);
        library.readBookContent("12345", user3);

        System.out.println("All user requests have been processed.");

        // Regular user requesting a book
//        user1.borrowBook(library, "12345");
//        user2.borrowBook(library, "12345"); // this will not be assigned to user2 as already issued by user1
//        user2.borrowBook(library, "54321");
        System.out.println("/------------------------------------------------/");

        // serialization and deserialization
        Book object = new Book("Silent Patient", "Alicia", "11111");

        try{
            FileOutputStream file = new FileOutputStream("serialize.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(object);

            out.close();
            file.close();

            System.out.println("Object has been serialized");
            System.out.println("Object before deserialization : " );
            SerialExample.printData(object);
        }catch (IOException e){
            System.out.println("IOExcpetion is caught!");
        }

        object = null;

        try{
            FileInputStream file = new FileInputStream("serialize.txt");
            ObjectInputStream in = new ObjectInputStream(file);

            object = (Book)in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized");
            SerialExample.printData(object);

        }catch(IOException ex){
            System.out.println("IOExcpetion is caught!");
        }catch (ClassNotFoundException ex){
            System.out.println("ClassNotFoundException is caught!");
        }
        // end of serialization and deserialization

        // writing into a file (admin only)
        // library.addOrEditBookContent("12345", "Title : The Great gatsby", admin1);
        // library.addOrEditBookContent("12345", "Chapter 1: Introduction", admin1);

        System.out.println("/------------------------------------------------/");


        // searching book
        library.searchBook("great");
        System.out.println("/------------------------------------------------/");

        library.searchBook("great gatsby");
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
