package dmnlukasik.ejb;

public class StatelessSession2Bean extends CommonStatelessSessionBean {

    public String greeting(final String name) {
        System.out.println("*** StatelessSession2Bean.greeting");
        printSecurityInfo();

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException ignored) {
        }

        return assembleGreeting(name, "StatelessSession2Bean");
    }

    public String superusersOnly() {
        System.out.println("*** StatelessSession2Bean.superusersOnly");
        printSecurityInfo();
        return "Bingo!";
    }
}