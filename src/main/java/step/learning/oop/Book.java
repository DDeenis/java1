package step.learning.oop;

public class Book extends Literature {
    private String author;

    public Book(String author, String title) {
        setAuthor(author);
        setTitle(title);
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
}
