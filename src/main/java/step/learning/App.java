package step.learning;

import com.google.inject.Guice;
import com.google.inject.Injector;
import step.learning.ioc.ConfigModule;
import step.learning.ioc.IocDemo;
import step.learning.oop.OOPDemo;

import javax.inject.Inject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //new BasicsDemo().run();
        //new FilesDemo().run();
        //new OOPDemo().run4();
        Injector injector = Guice.createInjector(new ConfigModule());
        IocDemo iocDemo = injector.getInstance(IocDemo.class);
        iocDemo.run();
    }
}
