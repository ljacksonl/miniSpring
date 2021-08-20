package spring.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 切面类
 * @author Administer
 *
 */
public class MyInterceptorForXml {
    //前置通知
    public void doAccessCheck(){
        System.out.println("前置通知");
    }

    //后置通知
    public void doAfterReturning(){
        System.out.println("后置通知:");
    }

    //例外通知
    public void doAfterThrowing(){
        System.out.println("例外通知");
    }

    //最终通知
    public void doAfter(){
        System.out.println("最终通知");
    }

    //环绕通知（特别适合做权限系统）
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("环绕通知进入方法");
        Object object=pjp.proceed();
        System.out.println("环绕通知退出方法");
        return object;
    }
}
