package dmnlukasik.ejb;

import javax.annotation.PostConstruct;
import javax.interceptor.InvocationContext;

public class MyDefaultInterceptor {

    public Object aroundInvoke(InvocationContext ic) throws Exception {
        System.out.println("MyDefaultInterceptor intercepting: " +
                ic.getTarget().getClass().getSimpleName() + "." + ic.getMethod().getName());
        return ic.proceed();
    }

    @PostConstruct
    public void postConstruct(InvocationContext ic)
            throws Exception {
        System.out.println(" MyDefaultInterceptor.postConstruct");
        /**
         * Important!
         * Must call proceed, in order for the other interceptor methods
         * in the interceptor chain to become invoked.
         */
        ic.proceed();
    }
}