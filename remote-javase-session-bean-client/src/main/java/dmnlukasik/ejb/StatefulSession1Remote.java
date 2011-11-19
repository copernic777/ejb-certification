package dmnlukasik.ejb;

import java.util.List;

public interface StatefulSession1Remote {

    String greeting(final String inName);

    void processList(List<String> inList);
}
