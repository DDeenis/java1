package step.learning.ioc.random;

public interface RandomService {
    String randomHex(int charLength);
    void seed(String iv);
}
