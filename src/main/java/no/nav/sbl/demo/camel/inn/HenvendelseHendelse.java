package no.nav.sbl.demo.camel.inn;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HenvendelseHendelse {
    public String id, type, melding;

    @Override
    public String toString() {
        return "HenvendelseHendelse{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", melding='" + melding + '\'' +
                '}';
    }
}
