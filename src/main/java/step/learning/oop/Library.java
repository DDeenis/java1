package step.learning.oop;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Literature> literature;

    public Library() {
        literature = new ArrayList<>();
    }

    public void add(Literature item) {
        literature.add(item);
    }

    public void printAllCards() {
        for(Literature item : literature) {
            System.out.println(item.getCard());
        }
    }
}
