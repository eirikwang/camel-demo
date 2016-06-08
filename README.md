#Apache Camel


##Hva er det?
Apache camel er et lettvekts integrasjonsprodukt. Den bygger på "Enterprise Integration Patterns" beskrevet i boken med samme navn av Gregor Hohpe og Bobby Woolf.

http://www.enterpriseintegrationpatterns.com/

Dette definer et sett mønster en kan bruke i koden.
Eksempler på dette:
* Melding
* Dead letter queue
* public/subscribe channel
* Message filter
* Splitter
* for en god oversikt se: http://camel.apache.org/enterprise-integration-patterns.html

Camel handler om integrasjon, og for å få til det, må en ha mulighet til å snakke over ulike kanaler og mekanismer.
Det støttes veldig mange komponenter rett ut av boksen. Eksempler på dette er:
* JMS
* CXF
* REST
* Filer
* FTP
* LDAP
* For en utfyllende liste se http://camel.apache.org/components.html

## Ruter
Et sentralt begrep i camel er Ruter.

En rute må lese fra et sted, og gjøre noe videre med det som mottas.
Si en vil lytte etter filer som legges på en katalog, for å lese hver enkelt linje i denne og lagre de i en database ville en gjort noe slik:
```
from("file:///data/app/dropbox?delete=true").split(body().tokenize("\n")).to("direct:lagreIDb");
```

Ruter kan kobler sammen via interne "køer" via "direct" som vist over. Dette gjør det veldig enkelt å frikoble ulike deler av koden.

#Feilhåndtering

Det er flere måter å håndtere feil på

Global feilkø/dead letter queue. Denne tar alle feil som ikke blir håndtert andre steder.
Feilhåndetering pr. rute. En kan sette opp en feilhåndterer pr. rute.
try/catch - Camel har også støtte for å wrappe deler av en rute inn i en try/catch

#Retry
Camel har veldig god støtte for å kjøre oppgaver på nytt ved feil.


##Testing

Camel har meget god støtte for å teste ruter, og det er enkelt å sette opp.

http://camel.apache.org/testing.html

En kan teste en enkelt rute, eller et sett med ruter.

##Dokumentasjon
Det finnes fantastisk dokumentasjon på http://camel.apache.org
Det er lett å navigere, og har godt eksempler. Den blir også oppdatert for hver release.


##Visualisering

Det finnes verktøy som gjør at en kan visualisere camel router.

Et som jeg har testet er hawtio (http://hawt.io/plugins/camel/).

Fuse er et JBoss produkt som er bygget på toppen av apache camel. Fuse har bla visualisering av ruter.