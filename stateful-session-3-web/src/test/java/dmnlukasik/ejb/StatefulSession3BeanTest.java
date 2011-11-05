package dmnlukasik.ejb;

import dmnlukasik.ejb.StatefulSession3Remote;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import static org.junit.Assert.assertTrue;

public class StatefulSession3BeanTest {

    private static EJBContainer ejbContainer;
    private static Context context;

    @BeforeClass
    public static void beforeClass() {
        ejbContainer = EJBContainer.createEJBContainer();
        context = ejbContainer.getContext();
    }

    @AfterClass
    public static void afterClass() {
        ejbContainer.close();
    }

    @Test
    public void shouldGreet() throws NamingException {
        StatefulSession3Remote bean = (StatefulSession3Remote) context.lookup("java:global/classes/StatefulSession3Bean");

        assertTrue(bean.greeting("Damian").contains("Damian"));
    }
}
