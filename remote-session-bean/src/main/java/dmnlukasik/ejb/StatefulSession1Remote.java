package dmnlukasik.ejb;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface StatefulSession1Remote {

    String greeting(final String inName);

    void processList(List<String> inList);
}
