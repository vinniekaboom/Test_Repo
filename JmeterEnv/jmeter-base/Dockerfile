FROM ubuntu
LABEL Description="Base image for jmeter"

ARG JMVERSION=4.0

RUN apt-get clean && \
    apt-get update && \
    apt-get -qy install \
    wget \
    default-jre-headless \
    telnet \
    iputils-ping \
    unzip \
    git

RUN   mkdir /jmeter \
    && cd /jmeter/ \
    && wget https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-$JMVERSION.tgz \
    && tar -xzf apache-jmeter-$JMVERSION.tgz \
    && rm apache-jmeter-$JMVERSION.tgz \
    && mkdir /jmeter-plugins \
    && cd /jmeter-plugins/ \
    && wget https://jmeter-plugins.org/downloads/file/JMeterPlugins-ExtrasLibs-1.4.0.zip \
    && unzip -o JMeterPlugins-ExtrasLibs-1.4.0.zip -d /jmeter/apache-jmeter-$JMVERSION/

ENV JMETER_HOME /jmeter/apache-jmeter-$JMVERSION/

ENV PATH $JMETER_HOME/bin:$PATH

RUN mkdir /root/.ssh