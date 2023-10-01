package step.learning.ioc;

import com.google.inject.AbstractModule;
import step.learning.ioc.services.HashService;
import step.learning.ioc.services.MD5HashService;
import step.learning.ioc.services.SHA256HashService;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HashService.class).to(SHA256HashService.class);
    }
}
