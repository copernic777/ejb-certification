package dmnlukasik.ejb;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class LogInterceptor {

    public LogInterceptor() {
        System.out.println("LogInterceptor - Constructor");
    }

    @AroundInvoke
    public Object logMethodEntryExit(InvocationContext invocationContext) throws
            Exception {
        log(invocationContext, " LogInterceptor - Entering method: ");
        Object theResult = invocationContext.proceed();
        log(invocationContext, " LogInterceptor - Exiting method: ");
        return theResult;
    }

    private void log(InvocationContext invocationContext, String message) {
        System.out.println(message + invocationContext.getMethod().getName());
    }
}