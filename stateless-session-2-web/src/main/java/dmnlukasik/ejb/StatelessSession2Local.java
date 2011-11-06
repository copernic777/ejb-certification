package dmnlukasik.ejb;

import javax.ejb.Local;

@Local
public interface StatelessSession2Local {
    String greeting(String name);
}
