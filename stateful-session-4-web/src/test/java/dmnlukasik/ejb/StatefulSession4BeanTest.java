package dmnlukasik.ejb;

import dmnlukasik.ejb.StatefulSession4Bean;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import static org.junit.Assert.assertTrue;

public class StatefulSession4BeanTest {

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
        StatefulSession4Bean bean = (StatefulSession4Bean) context.lookup("java:global/classes/StatefulSession4Bean");

        assertTrue(bean.greeting("Damian").contains("Damian"));
    }
}
