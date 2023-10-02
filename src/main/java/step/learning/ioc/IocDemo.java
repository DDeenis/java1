package step.learning.ioc;

import com.google.common.util.concurrent.Service;
import step.learning.ioc.random.RandomService;
import step.learning.ioc.services.HashService;

import com.google.inject.Inject;

import javax.inject.Named;

public class IocDemo {
    private final HashService digestHashService;
    private final HashService dsaHashService;
    private final RandomService randomService;

    @Inject
    public IocDemo(
            @Named("Digest-Hash") HashService digestHashService,
            @Named("DSA-Hash") HashService dsaHashService,
            RandomService randomService
    ) {
        this.digestHashService = digestHashService;
        this.dsaHashService = dsaHashService;
        this.randomService = randomService;
    }

    public void run() {
        System.out.println("IoC Demo");
        System.out.println(digestHashService.hash("IoC Demo"));
        System.out.println(dsaHashService.hash("IoC Demo"));
        System.out.println(randomService.randomHex(6));
    }
}
