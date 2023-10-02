package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import step.learning.ioc.random.RandomService;
import step.learning.ioc.random.RandomServiceV1;
import step.learning.ioc.services.HashService;
import step.learning.ioc.services.MD5HashService;
import step.learning.ioc.services.SHA256HashService;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HashService.class).annotatedWith(Names.named("Digest-Hash")).to(SHA256HashService.class);
        bind(HashService.class).annotatedWith(Names.named("DSA-Hash")).to(MD5HashService.class);
    }

    private RandomService randomService;
    @Provides
    private RandomService injectRandomService() {
        if(randomService == null) {
            randomService = new RandomServiceV1();
            randomService.seed("0");
        }
        return randomService;
    }
}
