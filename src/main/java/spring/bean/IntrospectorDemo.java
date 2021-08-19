package spring.bean;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class IntrospectorDemo {
    String name;
    String age;

    public IntrospectorDemo(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) throws Exception {
        IntrospectorDemo demo = new IntrospectorDemo("123","456");
//        demo.setName("WinterLau");
        Class<? extends IntrospectorDemo> clazz = demo.getClass();
        Field[] fields = clazz.getDeclaredFields();//获取其声明的字段

        for (Field field:fields) {
            String key = field.getName();//System.out.println(key);   //输出name   //     age
            //创建一个属性描述器
            PropertyDescriptor descriptor = new PropertyDescriptor(key,clazz);  //给一个属性，获取值
            Method method = descriptor.getReadMethod();     //相当于为上面声明的字段设置get方法
            Method method2= descriptor.getWriteMethod();    //为上面声明的字段设置set方法（又称内省）
            Object ob= method.invoke(demo);
            System.out.println( key +":"+ ob);
            Object ob1 = method2.invoke(demo, new String[]{"123456789"});
            System.out.println(key + ":" + ob1);

        }

        //如果不想把父类的属性也列出来的话，
        //那getBeanInfo的第二个参数填写父类的信息
       /*  BeanInfo bi = Introspector.getBeanInfo(clazz, Object.class);
        PropertyDescriptor[] props = bi.getPropertyDescriptors();
        for (PropertyDescriptor prop : props) {
            Method setter = prop.getWriteMethod();
            setter.invoke(demo, new String[]{"123456789"});
        }
        for (int i = 0; i < props.length; i++) {
            System.out.println(props[i].getName() + "=" +
                    props[i].getReadMethod().invoke(demo, null));
        }
        */

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

} 
