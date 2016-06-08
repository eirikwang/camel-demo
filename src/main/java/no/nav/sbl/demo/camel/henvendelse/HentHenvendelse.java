package no.nav.sbl.demo.camel.henvendelse;

import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;

public class HentHenvendelse extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        from("direct:hentHevendelse")
                .setBody(header("henvendelseId"))
                .setBody(method(this, "lagHenvendelse"));

    }
    public Henvendelse lagHenvendelse(String henvendelseId) {
        Henvendelse henvendelse = new Henvendelse();
        henvendelse.id = henvendelseId;
        henvendelse.melding = "Hei på deg";
        return henvendelse;
    }
}
