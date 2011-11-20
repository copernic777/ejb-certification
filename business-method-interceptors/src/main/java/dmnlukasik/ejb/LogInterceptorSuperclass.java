package dmnlukasik.ejb;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class LogInterceptorSuperclass {

    @AroundInvoke
    public Object logSuper(InvocationContext ic) throws Exception {
        System.out.println(" LogInterceptorSuperclass intercepting: " +
                ic.getTarget().getClass().getSimpleName() +
                "." + ic.getMethod().getName());
        return ic.proceed();
    }
}