package no.nav.sbl.demo.camel.henvendelse;

import no.nav.sbl.demo.camel.inn.HenvendelseHendelse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Henvendelse {
    public String id, status, melding;
    public List<HenvendelseHendelse> hendelser;

    @Override
    public String toString() {
        return "Henvendelse{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", melding='" + melding + '\'' +
                ", hendelser=" + hendelser +
                '}';
    }
}
