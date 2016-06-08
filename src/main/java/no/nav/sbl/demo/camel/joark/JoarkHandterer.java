package no.nav.sbl.demo.camel.joark;

import no.nav.sbl.demo.camel.NavRouteBuilder;
import no.nav.sbl.demo.camel.henvendelse.Henvendelse;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.TypeConversionException;
import org.apache.camel.support.TypeConverterSupport;

import java.util.Arrays;

import static org.apache.camel.language.spel.SpelExpression.spel;

public class JoarkHandterer extends NavRouteBuilder {
    @Override
    public void configure() throws Exception {
        getContext().getTypeConverterRegistry().addTypeConverter(Henvendelse.class, JoarkMelding.class, new JoarkMeldingTransformer());
        from("seda:hendelser")
                .filter(body().in(type("henvendelseAvsluttet")))
                .setProperty("hendelse", body())

                .setHeader("henvendelseId", spel("${in.body.id}"))
                .enrich("direct:hentFraHenvendelse")
                .setHeader("henvendelse", body())
                .setBody(body().convertTo(JoarkMelding.class))
                .log(LoggingLevel.INFO, "no.nav.sbl", "senderTilJoark: " + body())
                .to("direct:opprettJoark")
                .transform(spel("${in.body.filer}"))
                .split(body())
                .log(LoggingLevel.INFO, "no.nav.sbl", "sender fil til joark: " + body())
                .to("direct:leggTilFilTilJournalpost")
        ;
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
