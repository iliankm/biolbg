biolbg
======
mvn clean install -D build=%your_build_config_file%

%your_build_config_file% is a .properties file for build configuration - must exist in biol-parent folder.
In biol-parent folder there is 2 examples: default.properties and openshift.properties. 

The application is deployable on: Glassfish 3.1, JBOSS 7.1, WildFly 8.1.0.CR1, OPENSHIFT PaaS. 
Currently it is hosted on OPENSHIFT PaaS with WildFly 8 and PostgreSQL cartridges.

DB setup:
In biol-jpa/db there is a PostgreSQL script for initial db setup.
Register the db to the app server and place the jndi name in build config file: e.g. appDatasource=jdbc/biol-1.2

Filesystem setup:
In the build config file imagesPath=/path/to/images must be configured.
In the build config file logPath=/path/to/appserver/log/folder/logfile.log must be configured.
