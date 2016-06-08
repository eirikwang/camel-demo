package no.nav.sbl.demo.camel.joark;

import no.nav.sbl.demo.camel.NavRouteBuilder;
import no.nav.sbl.demo.camel.henvendelse.Henvendelse;
import no.nav.sbl.demo.camel.inn.InnHenvendelseRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.TypeConversionException;
import org.apache.camel.support.TypeConverterSupport;

import java.util.Arrays;

import static no.nav.sbl.demo.camel.inn.InnHenvendelseRouteBuilder.HENDELSER;
import static org.apache.camel.language.spel.SpelExpression.spel;

public class JoarkHandterer extends NavRouteBuilder {
    @Override
    public void configure() throws Exception {
        getContext().getTypeConverterRegistry().addTypeConverter(JoarkMelding.class,Henvendelse.class, new JoarkMeldingTransformer());
        from(HENDELSER)
                .filter(body().in(type("henvendelseAvsluttet")))
                .setProperty("hendelse", body())

                .setHeader("henvendelseId", spel("#{request.body.id}"))
                .enrich("direct:hentFraHenvendelse")
                .setHeader("henvendelse", body())
                .convertBodyTo(JoarkMelding.class)
                .to("direct:opprettJoark")
                .log("body er: " + body())
                .transform(spel("#{request.body.dokumenter}"))
                .split(body())
                .to("direct:leggTilFilTilJournalpost").end()
                .to("direct:avsluttJoark")

        ;
        from("direct:opprettJoark")
                .log(LoggingLevel.INFO, "no.nav.sbl", "senderTilJoark: " + body());
        from("direct:avsluttJoark")
                .log(LoggingLevel.INFO, "no.nav.sbl", "avslutterJoark: " + body());
        from("direct:leggTilFilTilJournalpost")
                .log(LoggingLevel.INFO, "no.nav.sbl", "sender fil til joark: " + body());

    }

    private class JoarkMeldingTransformer extends TypeConverterSupport {
        @SuppressWarnings("unchecked")
        @Override
        public <T> T convertTo(Class<T> type, Exchange exchange, Object value) throws TypeConversionException {
            JoarkMelding melding = new JoarkMelding();
            melding.id = ((Henvendelse) value).id;
            melding.dokumenter = Arrays.asList("fil 1", "fil2", "fil3");
            return (T) melding;
        }
    }
}
