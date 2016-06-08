package no.nav.sbl.demo.camel;

import no.nav.sbl.demo.camel.henvendelse.HentHenvendelse;
import no.nav.sbl.demo.camel.inn.InnHenvendelseRouteBuilder;
import no.nav.sbl.demo.camel.joark.JoarkHandterer;
import no.nav.sbl.demo.camel.sb.RapporterOpprettetSB;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.main.Main;

public class MainApp {

    public static void main(String... args) throws Exception {
        startMQ();
        Main main = new Main();
        main.getOrCreateCamelContext().setUseMDCLogging(true);
        main.getOrCreateCamelContext().setUseBreadcrumb(true);
        main.getOrCreateCamelContext().addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
        main.addRouteBuilder(new InnHenvendelseRouteBuilder());
        main.addRouteBuilder(new HentHenvendelse());
        main.addRouteBuilder(new JoarkHandterer());
        //main.addRouteBuilder(new RapporterOpprettetSB());
        main.addRouteBuilder(new EksempelDataOppretter());
        main.run(args);
    }

    private static void startMQ() throws Exception {
        final BrokerService broker = new BrokerService();
        broker.getSystemUsage().getTempUsage().setLimit(100 * 1024 * 1024);
        broker.getSystemUsage().getStoreUsage().setLimit(100 * 1024 * 1024);
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }

}

