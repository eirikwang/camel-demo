package no.nav.sbl.demo.camel;

import no.nav.sbl.demo.camel.henvendelse.Henvendelse;
import no.nav.sbl.demo.camel.inn.HenvendelseHendelse;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;

import javax.xml.bind.JAXBContext;
import java.util.Arrays;
import java.util.List;

public class EksempelDataOppretter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        DataFormat df = new JaxbDataFormat(JAXBContext.newInstance(HenvendelseHendelse.class));
        from("timer:exsempeldata?fixedRate=true&period=5000")
                .setBody(method(this, "lagHenvendelse"))
                .marshal(df)
                .convertBodyTo(String.class)
                .to("activemq:queue:innkommendeMelding");
    }
    private int id;
    private List<String > type = Arrays.asList("henvendelseOpprettet","henvendelseAvsluttet", "henvendelseLest");
    public HenvendelseHendelse lagHenvendelse() {
        HenvendelseHendelse henvendelse = new HenvendelseHendelse();
        henvendelse.id = "" + id++;
        henvendelse.type = type.get(id%type.size());
        return henvendelse;
    }
}
