package no.nav.sbl.demo.camel.sb;

import no.nav.sbl.demo.camel.NavRouteBuilder;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsMessageType;
import org.apache.camel.model.ModelCamelContext;

import static no.nav.sbl.demo.camel.inn.InnHenvendelseRouteBuilder.HENDELSER;

public class RapporterOpprettetSB extends NavRouteBuilder {
    @Override
    protected ModelCamelContext createContainer() {
        ModelCamelContext container = super.createContainer();
        container.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
        return container;
    }

    @Override
    public void configure() throws Exception {
        from(HENDELSER)
                .filter(PredicateBuilder.in(type("henvendelseOpprettet"), type("henvendelseAvsluttet")))
                .log("Rapporterer opprettet mot SB: " + body())
                .to("activemq:queue:KoMotSb?jmsMessageType=Text");
    }
}
