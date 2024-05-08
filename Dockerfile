FROM payara/micro:6.2024.4-jdk21
#COPY target/vcai-0.1-SNAPSHOT.war $DEPLOY_DIR
COPY target/${final.name}.war $DEPLOY_DIR
