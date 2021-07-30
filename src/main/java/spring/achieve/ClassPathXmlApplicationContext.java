package spring.achieve;

import static org.hamcrest.CoreMatchers.nullValue;
 
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
 
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
 
public class ClassPathXmlApplicationContext implements ApplicationContext {
 
	private String fileName;
 
	public ClassPathXmlApplicationContext(String fileName) {
		this.fileName = fileName;
 
	}
 
	@Override
	public Object getBean(String beanId) {
		//使用dom4j读取xml文件
		//单一，慢慢扩展,多个bean的时候，并且只有value，现在只有属性注入，并且异常没有解决
		SAXReader reader = new SAXReader();
		Document document = null;
		Object object = nullValue();
		String currentPath = this.getClass().getResource("").getPath();
	/*	问题1 如何读取到的resources下文件
	 * String currentPath1 = this.getClass().getResource("/").getPath();
	 * currentPath1 返回/E:/java/CX/test/target/test-classes/
	 * currentPath返回 /E:/java/CX/test/target/classes/spring/achieve/  
	 * 而resources下的文件是E:\java\CX\test\target\classes
	   */
		try {
		    //拿到resources下的文件
			document = reader.read(new File(currentPath+fileName));
			System.out.println(currentPath+fileName);
			//拿到文件下的元素
            Element root =document.getRootElement();
            //放到迭代器中
            Iterator iterable = root.elementIterator();
            while(iterable.hasNext()){
                //拿到第一组元素
            	Element element = (Element) iterable.next();
            	//拿到第一组元素的第一个元素
            	Iterator proiterable = element.elementIterator();//selectsinglenode不好使，这样很麻烦
            	while(proiterable.hasNext()){
            		Element proelement = (Element) proiterable.next();
            		//通过名称得到对应的属性
            		String propertyName = proelement.attributeValue("name");
            		String propertyValue = proelement.attributeValue("value");
            		String url = element.attributeValue("class");
                    //拿到元素名字给首字母设置为大写
            		String setMethod = "set"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
            		System.out.println(setMethod);
                	try {
                	    //类加载，使用newInstance()方法的时候，就必须保证：1、这个类已经加载；2、这个类已经连接了。
                        //拿到了对应对象的包的路径并加载
    					object = Class.forName(url).newInstance();
    					Method[] methods  =object.getClass().getMethods();
    					for(Method method:methods){
    						if (setMethod.equals(method.getName())) {
    							System.out.println(method.getName());
    							try {
    							    //使用invoke方法要比别的方法多做一步，就是构建一个Method对象，
                                    // 这个对象替代的是现在程序要调用方法的替代品。
                                    //而且除了参数以外，invoke还会多要一个对象，因为方法调用需要对象，
                                    // 所以invoke要想调用的目标方法，就需要目标方法的需要的对象。
									method.invoke(object, propertyValue);
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
    						
    					}
    					
    					
    				} catch (InstantiationException e) {
    					e.printStackTrace();
    				} catch (IllegalAccessException e) {
    					e.printStackTrace();
    				} catch (ClassNotFoundException e) {
    					e.printStackTrace();
    				}
            	}
            	
            	
            
            	
            }
            
           
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	
		
		
		
 
		return null;
	}
 
}
