package dmnlukasik.ejb;

import javax.ejb.Local;

@Local
public interface StatefulSession2Local {
    String greeting(String name);
}
