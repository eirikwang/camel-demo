package no.nav.sbl.demo.camel;

import org.apache.camel.builder.RouteBuilder;

import static no.nav.sbl.demo.camel.inn.InnHenvendelseRouteBuilder.HENDELSER;

public class MotattLogger extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(HENDELSER)
                .log("Mottok: " + body());
    }
}
