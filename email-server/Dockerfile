FROM ubuntu:22.04
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update && apt-get install -y \
	mc \
    postfix \
    dovecot-core \
    dovecot-imapd \
    dovecot-pop3d \
    dovecot-lmtpd \
    dovecot-mysql \
    dovecot-sieve \
    mailutils \
    && apt-get clean
RUN mkdir -p /var/mail/vhosts
RUN mkdir /var/mail/config
COPY users.txt /var/mail/config/users.txt
COPY generate-users.sh /var/mail/config/generate-users.sh
COPY start.sh /var/mail/start.sh

RUN chmod +x /var/mail/config/generate-users.sh
RUN chmod +x /var/mail/start.sh

# Configure postfix
RUN postconf -e "myhostname=mail.example.com"
RUN postconf -e "virtual_mailbox_domains=example.com"
RUN postconf -e "virtual_mailbox_base=/var/mail/vhosts"
RUN postconf -e "virtual_mailbox_maps=hash:/etc/postfix/vmailbox"
RUN postconf -e "virtual_minimum_uid=5000"
RUN postconf -e "virtual_uid_maps=static:5000"
RUN postconf -e "virtual_gid_maps=static:5000"

EXPOSE 25 143 110 587 993 995

CMD ["/var/mail/start.sh"]