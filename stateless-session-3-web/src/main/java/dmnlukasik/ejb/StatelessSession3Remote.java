package dmnlukasik.ejb;

import javax.ejb.Remote;

@Remote
public interface StatelessSession3Remote {
    String greeting(String name);
}
