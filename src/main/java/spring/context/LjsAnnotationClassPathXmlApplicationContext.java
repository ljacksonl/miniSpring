package spring.context;


import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import spring.annotation.YhdResource;
import spring.bean.BeanDefinition;
import spring.bean.PropertyDefinition;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * 加载 xml 配置文件，遍历其中的标签
 * 获取标签中的 id 和 class 属性，加载 class 属性对应的类，并创建 bean
 * 遍历标签中的标签，获取属性值，并将属性值填充到 bean 中
 * 将 bean 注册到 bean 容器中
 **/
public class LjsAnnotationClassPathXmlApplicationContext {
    private List<BeanDefinition> beanDefines = new ArrayList<BeanDefinition>();//用来存储所有的beans
    private Map<String, Object> sigletons = new HashMap<String, Object>();//用来存储实例化后的bean

    /**
     * 构造方法，用来模拟spring的行为
     *
     * @param fileName
     */
    public LjsAnnotationClassPathXmlApplicationContext(String fileName) {
        try {
            //1.read xml
            this.readXml(fileName);
            //2.实例化bean
            this.instanceBeans();
            //3.注解方式
            this.annotationInject();
        } catch (Exception e) {

        }

    }

    /**
     * 根据文件名读取xml的配置文件
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
            //获取xml中的根元素
            Element rootElement = document.getRootElement();
            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                String id = element.attributeValue("id");//获取bean的id属性值
                String clazz = element.attributeValue("class");//获取bean的class属性值
                BeanDefinition beanDefinition = new BeanDefinition(id, clazz);
                //获取bean的Property属性
                for (Iterator subElementIterator = element.elementIterator(); subElementIterator.hasNext(); ) {
                    Element subElement = (Element) subElementIterator.next();
                    String propertyName = subElement.attributeValue("name");
                    String propertyValue = subElement.attributeValue("value");
                    PropertyDefinition propertyDefinition = new PropertyDefinition(propertyName, propertyValue);
                    beanDefinition.getPropertyDefinitions().add(propertyDefinition);
                }
                beanDefines.add(beanDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实例化bean
     */
    private void instanceBeans() {
        if (beanDefines != null && beanDefines.size() > 0) {
            //对每个bean进行实例化
            for (BeanDefinition beanDefinition : beanDefines) {
                try {
                    //bean的class属性存在的时候才进行实例化，否则不进行实例化
                    if (beanDefinition.getClassName() != null && !beanDefinition.getClassName().equals("")) {
                        //实例化的关键操作
                        sigletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
                        System.out.println("id为：" + beanDefinition.getId() + "的bean实例化成功");
                    }
                } catch (Exception e) {
                    System.out.println("bean实例化失败");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 注解方式注入
     * <p>
     * Administer
     */
    private void annotationInject() throws IllegalAccessException {
        //遍历所有的bean
        for (String beanName : sigletons.keySet()) {
            Object bean = sigletons.get(beanName);//获取需要注入的bean
            if (bean != null) {
                //再对字段进行处理，即对字段上标识有注解
                Field[] fields = bean.getClass().getDeclaredFields();//取得声明的所有字段
                for (Field field : fields) {
                    //判断字段上是否存在注解,若存在
                    if (field.isAnnotationPresent(YhdResource.class)) {
                        YhdResource resource = field.getAnnotation(YhdResource.class);//取得字段上的注解
                        //字段上存在注解，并且字段上注解的name属性不为空
                        System.out.println(JSONObject.toJSONString(field));
                        System.out.println(JSONObject.toJSONString(resource));
                        field.setAccessible(true);
                        field.set(bean, resource.value());
                    }
                }


            }
        }
    }


    public Object getBean(String beanName) {
        return sigletons.get(beanName);
    }
}
