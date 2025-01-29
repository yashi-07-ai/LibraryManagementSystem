public class SerialExample {
    public static void printData(Book object1)
    {
        System.out.println("Book Title = " + object1.getTitle());
        System.out.println("Author = " + object1.getAuthor());
        System.out.println("ISBN = " + object1.getIsbn());
        System.out.println("ID = " + object1.id);
    }
}