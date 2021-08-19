package spring.context;

import com.sun.javafx.collections.MappingChange;
import org.apache.commons.beanutils.ConvertUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import spring.bean.BeanDefinition;
import spring.bean.PropertyDefinition;
import sun.java2d.pipe.SpanIterator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class LjsClassPathXmlApplicationContext {
    //用来存储所有的beans
    private List<BeanDefinition> beanDefines = new ArrayList<>();
    private List<PropertyDefinition> propertyDefinitions = new ArrayList<>();
    //用来存储实例化后的bean
    private Map<String, Object> sigletons = new HashMap<>();

    /**
     * 构造方法，用来模拟spring的行为
     *
     * @param fileName
     */
    public LjsClassPathXmlApplicationContext(String fileName) {
        //1.读取spring的配置文件
        this.readXml(fileName);
        //2.实例化bean
        this.instanceBeans();
        //3.实现对依赖对象的注入功能
        this.injectObject();
    }

    /**
     * 根据文件名读取xml的配置文件
     *
     * @param fileName
     */

    private void readXml(String fileName) {
        // TODO Auto-generated method stub
        //创建一个读取器
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            //获取要读取的配置文件的路径
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            //读取文件内容
             document = saxReader.read(xmlPath);
            //获取xml中的根元素 beans
            Element rootElement = document.getRootElement();
            for (Iterator iterator = rootElement.elementIterator();iterator.hasNext();){
                //bean
                Element element = (Element) iterator.next();
                //获取bean的id属性值
                String id = element.attributeValue("id");
                //获取bean的class属性值
                String clazz = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(id, clazz);
//                beanDefines.add(beanDefinition);
                ////获取bean的Property属性
                for (Iterator iterator1 = element.elementIterator();iterator1.hasNext();){
                    Element element1 = (Element) iterator1.next();
                    String name = element1.attributeValue("name");
                    String value = element1.attributeValue("value");
                    PropertyDefinition propertyDefinition = new PropertyDefinition(name, value);
//                    propertyDefinitions.add(propertyDefinition);
                    beanDefinition.getPropertyDefinitions().add(propertyDefinition);
                }
                beanDefines.add(beanDefinition);

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

    /**
     * 完成实例化bean
     */
    private void instanceBeans() {
        if (beanDefines != null && beanDefines.size() > 0) {
            //对每个bean进行实例化
            for (BeanDefinition beanDefine : beanDefines) {

                //bean的class属性存在的时候才进行实例化，否则不进行实例化
                if (beanDefine.getClassName() != null && !beanDefine.getClassName().equals("")) {
                    //实例化的关键操作
                    try {
                        sigletons.put(beanDefine.getId(), Class.forName(beanDefine.getClassName()).newInstance());
                        System.out.println("id为：" + beanDefine.getId() + "的bean实例化成功");

                    } catch (Exception e) {
                        System.out.println("bean实例化失败");
                        e.printStackTrace();
                    }
                }


            }
        }
    }

    /**
     * 为bean对象的属性注入值
     */
    private void injectObject(){
        //遍历配置文件中定义的所有的bean
        for (BeanDefinition beanDefine : beanDefines) {
            //找到要注入的bean(类)
            Object bean = sigletons.get(beanDefine.getId());
            if (bean != null){
                try {
                    //内省
                    //通过类Introspector的getBeanInfo方法获取对象的BeanInfo 信息
                    BeanInfo info = Introspector.getBeanInfo(bean.getClass());
                    //通过BeanInfo来获取属性的描述器(PropertyDescriptor),通过这个属性描述器就可以获取某个属性对应的getter/setter方法,然后我们就可以通过反射机制来调用这些方法。
                    //获得 bean所有的属性描述
                    PropertyDescriptor[] props = info.getPropertyDescriptors();

                    //遍历Property标签
                    for (PropertyDefinition propertyDefinition : beanDefine.getPropertyDefinitions()) {
                        //遍历要注入bean通过属性描述器得到的所有属性以及行为
                        //其实就是把List<PropertyDefinition>的值放到person类中 使用内省set进去
                        for (PropertyDescriptor prop : props) {
                            //用户定义的bean属性与java内省后的bean属性名称相同时
                            if (prop.getName().equals(propertyDefinition.getName())){

//                                System.out.println(prop.getName());
                                Method setter = prop.getWriteMethod();//获得属性的setter方法
                                if (setter!=null){
                                    Object value = null;//用来存储引用的值
//                                    setter.invoke(, )
                                    //ConvertUtil依赖两个jar包，一个是common-beanutils,而common-beanutils又依赖common-logging
                                    //ConvertUtil将任意类型转化为需要的类型
                                    value = ConvertUtils.convert(propertyDefinition.getValue(), prop.getPropertyType());
                                    setter.setAccessible(true);//打开权限 保证setter方法可以访问私有

                                    try {
                                        setter.invoke(bean, value);//把引用对象注入到属性
//                                        System.out.println(bean.toString());
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                    }
                } catch (IntrospectionException e) {


                }
            }
        }
    }

    /**
     * 通过bean名称来获取bean对象
     * @param beanName
     * @return
     * @date 2021
     */
    public Object getBean(String beanName){
        return sigletons.get(beanName);
    }

}
