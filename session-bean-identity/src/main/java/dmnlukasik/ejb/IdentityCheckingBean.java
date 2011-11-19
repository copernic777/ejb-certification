package dmnlukasik.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class IdentityCheckingBean {

    @EJB
    private StatelessSessionBean mStateless1;
    @EJB
    private StatelessSessionBean mStateless2;
    @EJB
    private StatefulSessionBean mStateful1;
    @EJB
    private StatefulSessionBean mStateful2;
    @EJB
    private SingletonSessionBean mSingleton1;
    @EJB
    private SingletonSessionBean mSingleton2;
    @EJB
    private StatelessSessionBeanLocalBusiness mStatelessLocalBusiness;

    public void checkBeanIdentities() {
        compareStatelessBeanWithStatelessBean();
        compareStatelessBeanWithOtherKindsOfBeans();
        compareStatefulBeanWithStatefulBean();
        compareStatefulBeanWithOtherKindsOfBeans();
        compareSingletonBeanWithSingletonBean();
    }

    private void compareStatelessBeanWithStatelessBean() {
        System.out.println("\n***** Compare stateless bean with stateless bean:");
        doIdentityCheck(mStateless1, mStateless1, "Stateless1 equals stateless1");
        doIdentityCheck(mStateless1, mStateless2, "Stateless1 equals stateless2");
        doIdentityCheck(mStateless2, mStateless1, "Stateless2 equals stateless1");
        doIdentityCheck(mStateless1, mStatelessLocalBusiness, "Stateless1 equals stateless local business:");
    }

    private void compareStatelessBeanWithOtherKindsOfBeans() {
        System.out.println("\n***** Compare stateless bean with other kinds of beans:");
        doIdentityCheck(mStateless1, mStateful1, "Stateless1 equals stateful1");
        doIdentityCheck(mStateless1, mSingleton1, "Stateless1 equals singleton1");
    }

    private void compareStatefulBeanWithStatefulBean() {
        System.out.println("\n***** Compare stateful bean with stateful bean:");
        doIdentityCheck(mStateful1, mStateful1, "Stateful1 equals stateful1");
        doIdentityCheck(mStateful1, mStateful2, "Stateful1 equals stateful2");
        doIdentityCheck(mStateful2, mStateful1, "Stateful2 equals stateful1");
    }

    private void compareStatefulBeanWithOtherKindsOfBeans() {
        System.out.println("\n***** Compare stateless bean with other kinds of beans:");
        doIdentityCheck(mStateful1, mSingleton1, "Stateful1 equals singleton1");
    }

    private void compareSingletonBeanWithSingletonBean() {
        System.out.println("\n***** Compare singleton bean with singleton bean:");
        doIdentityCheck(mSingleton1, mSingleton1, "Singleton1 equals singleton1");
        doIdentityCheck(mSingleton1, mSingleton2, "Singleton1 equals singleton2");
        doIdentityCheck(mSingleton2, mSingleton1, "Singleton2 equals singleton1");
    }

    public void checkBeanHashCodes() {
        hashCodesOfStatelessBeans();
        hashCodesOfStatefulBeans();
        hashCodesOfSingletonBeans();
    }

    private void hashCodesOfStatelessBeans() {
        System.out.println("\n***** Stateless bean hash codes:");
        doBeanHashCode(mStateless1, "Stateless1");
        doBeanHashCode(mStateless2, "Stateless2");
        doBeanHashCode(mStatelessLocalBusiness, "Stateless local business");
    }

    private void hashCodesOfStatefulBeans() {
        System.out.println("\n***** Stateful bean hash codes:");
        doBeanHashCode(mStateful1, "Stateful1");
        doBeanHashCode(mStateful2, "Stateful2");
    }

    private void hashCodesOfSingletonBeans() {
        System.out.println("\n***** Singleton bean hash codes:");
        doBeanHashCode(mSingleton1, "Singleton1");
        doBeanHashCode(mSingleton2, "Singleton2");
    }

    private void doIdentityCheck(Object inBean1, Object inBean2, String inMessage) {
        boolean theIdentityFlag;
        theIdentityFlag = inBean1.equals(inBean2);
        System.out.println(" " + inMessage + " " + theIdentityFlag);
    }

    private void doBeanHashCode(Object inBean, String inMessage) {
        int theHashCode = inBean.hashCode();
        System.out.println(" " + inMessage + " hash code: " + theHashCode);
    }
}