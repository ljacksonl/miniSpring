import org.junit.Test;
 
import spring.achieve.ApplicationContext;
import spring.achieve.ClassPathXmlApplicationContext;
import spring.achieve.Person;
 
public class TestMySpring {
	@Test
	public void testGetBean(){
		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
		Person person = (Person)context.getBean("pro");
		
	}
 
}
