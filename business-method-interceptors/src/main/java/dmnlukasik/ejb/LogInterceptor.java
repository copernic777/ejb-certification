package dmnlukasik.ejb;

import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class LogInterceptor extends LogInterceptorSuperclass {

    public LogInterceptor() {
        System.out.println("LogInterceptor - Constructor");
    }

    @AroundInvoke
    public Object logMethodEntryExit(InvocationContext ic) throws
            Exception {
        log(ic, " LogInterceptor - Entering method: ");
        Object theResult = ic.proceed();
        log(ic, " LogInterceptor - Exiting method: ");
        return theResult;
    }

    private void log(InvocationContext ic, String message) {
        System.out.println(message + ic.getMethod().getName());
    }

    @AroundTimeout
    public Object logTimeout(InvocationContext ic)
            throws Exception {
        log(ic, " LogInterceptor - Entering timeout: ");
        Object theResult = ic.proceed();
        log(ic, " LogInterceptor - Exiting timeout: ");
        return theResult;
    }
}