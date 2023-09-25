package step.learning.oop;

public class Book extends Literature implements Copyable, Printable, Multiple {
    private String author;

    public Book(String author, String title) {
        this.setAuthor(author);
        super.setTitle(title);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getCard() {
        return String.format("Book: %s '%s'", getAuthor(), getTitle());
    }

    @Override
    public int getCount() {
        return 1;
    }
}
