package step.learning.oop;

public class OOPDemo {
    public void run() {
        Library library = new Library() ;
        try {
            library.add(new Book("A. Mogus", "Art of impostor"));
            library.add(new Newspaper("Daily Mail", "2023-02-28"));
            library.add(new Journal("Quantum Mechanics", 1));
            library.add(new Hologram("Holograms isn't real", 616));
        }
        catch (Exception ex) {
            System.err.printf("Literature creation error: %s%n", ex.getMessage());
        }
        library.printAllCards();
        System.out.println("----------- COPYABLE -----------");
        library.printCopyable();
        System.out.println("----------- NON COPYABLE -----------");
        library.printNonCopyable();
        System.out.println("----------- PERIODIC -----------");
        library.printPeriodic();
        System.out.println("----------- NON PERIODIC -----------");
        library.printNonPeriodic();
        System.out.println("----------- PRINTABLE -----------");
        library.printPrintable();
        System.out.println("----------- NON PRINTABLE -----------");
        library.printNonPrintable();
        System.out.println("----------- MULTIPLE -----------");
        library.printMultiple();
        System.out.println("----------- NON MULTIPLE -----------");
        library.printNonMultiple();
    }
}
