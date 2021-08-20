package spring;

import spring.bean.Person;
import spring.context.LjsAnnotationClassPathXmlApplicationContext;
import spring.context.LjsClassPathXmlApplicationContext;

public class SpringTest {
    public static void main(String[] args) {
//        LjsClassPathXmlApplicationContext ljsClassPathXmlApplicationContext = new LjsClassPathXmlApplicationContext("spring.xml");
//        Person person = (Person) ljsClassPathXmlApplicationContext.getBean("person");
//        System.out.println(person.getName());

        LjsAnnotationClassPathXmlApplicationContext xqAnnotationClassPathXmlApplicationContext = new LjsAnnotationClassPathXmlApplicationContext("spring.xml");
        Person person = (Person) xqAnnotationClassPathXmlApplicationContext.getBean("person");
        System.out.println(person.getName());
    }
}
