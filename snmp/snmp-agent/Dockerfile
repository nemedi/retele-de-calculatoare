FROM ubuntu:22.04

RUN apt-get update && \
    apt-get install -y snmp snmpd && \
    apt-get clean

COPY snmpd.conf /etc/snmp/snmpd.conf

EXPOSE 161/udp

CMD ["snmpd", "-f", "-Lo"]