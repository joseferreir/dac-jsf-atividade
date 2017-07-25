FROM payara/server-full
MAINTAINER Ricardo Jose <joseifpb@gmail.com>
ENV DOMAIN domain1
ENV DEPLOY ${PAYARA_PATH}/glassfish/domains/${DOMAIN}/autodeploy/
ENTRYPOINT $PAYARA_PATH/bin/asadmin start-domain --verbose ${DOMAIN}
ADD target/dac-jsf-pratica.war  ${DEPLOY}
