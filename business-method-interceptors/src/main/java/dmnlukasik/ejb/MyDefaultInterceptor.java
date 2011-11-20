package dmnlukasik.ejb;

import javax.interceptor.InvocationContext;

public class MyDefaultInterceptor {

    public Object aroundInvoke(InvocationContext ic) throws Exception {
        System.out.println("MyDefaultInterceptor intercepting: " +
                ic.getTarget().getClass().getSimpleName() + "." + ic.getMethod().getName());
        return ic.proceed();
    }
}