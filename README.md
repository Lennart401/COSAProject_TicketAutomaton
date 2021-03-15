# TicketAutomaton
Exam task for the module "Component-oriented software architecture" at Leuphana University of Lüneburg.

## Task setting
Implement a componentbus for the project "TicketAutomaton" using OSGi (Equinox/Felix/etc.). The components "DocumentSystem", "PrintingSystem", "MessagingSystem", "RouteSystem" and "PricingSystem" shall be connected using an event delegation mechanism. The TicketAutomaton must be accessable using a command line interface.

## Components
- [DocumentSystem](https://github.com/Lennart401/COSAProject/tree/main/DocumentSystem_Automaton)
- [PrintingSystem](https://github.com/Lennart401/COSAProject/tree/main/PrintingSystem_Automaton)
- [MessagingSystem](https://github.com/Lennart401/COSAProject/tree/main/MessagingSystem_Automaton)
- [RouteSystem](https://github.com/Lennart401/COSAProject/tree/main/RouteSystem)
- [PricingSystem](https://github.com/Lennart401/COSAProject/tree/main/PricingSystem)
- [TicketAutomaton](https://github.com/Lennart401/COSAProject/tree/main/TicketAutomaton)

## Required librarys
- OSGi (org.osgi.core, Version 7.0.0)
- Apache Felix (Version 7.0.0)
- Apache Felix EventAdmin (Version 1.6.2)
- LOG4J2 (log4j-api version 2.13.3 and log4j-core version 2.13.3)
- JUnit 5.4 (for unit tests only)

## Setting up the project.
1. Clone the repository.
2. Setup a project in your IDE of choice.
3. Add each folder from the repository as an OSGi module to your project.
4. Install/setup the neccessary libraries mentioned above.

## Running the project
Apache Felix was used as OSGi framework. When launching the framework, the libraries (as bundles) "Apache Felix EventAdmin" and "LOG4J" must be started first, after which all components except for TicketAutomaton may be started. At last, TicketAutomaton may be started which will immediately trigger the CLI to start. 

If manually starting all bundles: as soon as the TicketAutomaton launches, both the Apache GoGo Console and the CLI will become active which might lead to some unwanted results and/or crashes with inputs and commands being mixed up.

### Setting up start levels (optional)
To avoid manually starting all the bundles, it is easiest to assign start levels to each bundle. The library bundles (EventAdmin und LOG4J) should be given start level 1, all components except TicketAutomaton should get start level 2, TicketAutomaton and the Framework should start at level 3.

Furthermore, not starting the OSGi console will prevent mixed inputs and commands as describes above and make the CLI work as intended.
