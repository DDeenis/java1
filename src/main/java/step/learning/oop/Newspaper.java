package step.learning.oop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Newspaper extends Literature implements Periodic, Printable {
    private Date date;
    public static final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat cardDateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public Newspaper(String title, String date) throws ParseException {
        super.setTitle(title);
        this.setDate(sqlDateFormat.parse(date));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getCard() {
        return String.format("Newspaper: '%s' from %s", getTitle(), cardDateFormat.format(getDate()));
    }

    @Override
    public String getPeriod() {
        return "daily";
    }
}
