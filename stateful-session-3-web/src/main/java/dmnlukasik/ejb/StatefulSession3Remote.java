package dmnlukasik.ejb;

import javax.ejb.Remote;

@Remote
public interface StatefulSession3Remote {
    String greeting(String name);
}
