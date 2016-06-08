package no.nav.sbl.demo.camel.inn;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.spi.DataFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * A Camel Java DSL Router
 */
public class InnHenvendelseRouteBuilder extends RouteBuilder {

    @Override
    protected ModelCamelContext createContainer() {
        ModelCamelContext container = super.createContainer();
        container.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
        return container;
    }

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() throws JAXBException {
        DataFormat df = new JaxbDataFormat(JAXBContext.newInstance(HenvendelseHendelse.class));
        from("activemq:queue:innkommendeMelding")
                .setProperty("camel.contextId", header("callId"))
                .log("mottok: " + body())
                .unmarshal(df)
                .to("seda:hendelser");
    }

}
