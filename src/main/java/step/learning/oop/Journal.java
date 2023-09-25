package step.learning.oop;

public class Journal extends Literature implements Copyable, Periodic, Printable, Multiple {
    private int number;

    public Journal(String title, int number) {
        super.setTitle(title);
        this.setNumber(number);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String getCard() {
        return String.format("Journal: '%s' №%d", getTitle(), getNumber());
    }

    @Override
    public String getPeriod() {
        return "monthly";
    }

    @Override
    public int getCount() {
        return 1;
    }
}
