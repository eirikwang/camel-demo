package no.nav.sbl.demo.camel;

import no.nav.sbl.demo.camel.inn.HenvendelseHendelse;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

public abstract class NavRouteBuilder extends RouteBuilder {
    protected Predicate type(String type) {
        return exchange -> type.equals(exchange.getIn().getBody(HenvendelseHendelse.class).type);
    }

}
