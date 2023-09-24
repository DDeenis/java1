package step.learning.oop;

public class OOPDemo {
    public void run() {
        Book book = new Book("A. Mogus", "Art of impostor");
        System.out.println(book.getCard());
        Library library = new Library() ;
        library.add( book ) ;
        library.printAllCards() ;
    }
}
