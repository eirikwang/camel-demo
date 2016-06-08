package no.nav.sbl.demo.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;


public class GlobalFeilhandterer extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:feil")
                .log(LoggingLevel.ERROR, "FEILET MELDING: " + body());
    }
}
