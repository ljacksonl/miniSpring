package spring.ioc;

import org.junit.BeforeClass;
import org.junit.Test;


public class SpringTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Test
    public void testInstanceSping() {
        YhdClassPathXmlApplicationContext ctx=new YhdClassPathXmlApplicationContext("beans.xml");
        PersonService personService=(PersonService)ctx.getBean("personService");
    }

}
