package no.nav.sbl.demo.camel.sb;

import no.nav.sbl.demo.camel.NavRouteBuilder;
import org.apache.camel.builder.RouteBuilder;

public class RapporterOpprettetSB extends NavRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("seda:hendelser")
                .filter(type("henvendelseOpprettet"))
                .log("Rapporterer opprettet mot SB: " + body())
                .to("activemq:queue:KoMotSb");
    }
}
