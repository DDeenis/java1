package step.learning.oop;

public class Hologram extends Literature {
    private int version;

    public Hologram(String title, int version) {
        super.setTitle(title);
        this.setVersion(version);
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String getCard() {
        return String.format("Hologram: '%s' v%d", getTitle(), getVersion());
    }
}
