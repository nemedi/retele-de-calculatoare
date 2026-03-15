FROM ubuntu:22.04
ENV DEBIAN_FRONTEND=noninteractive

# Install mail server packages
RUN apt-get update && \
    apt-get install -y mc postfix dovecot-imapd dovecot-pop3d mailutils && \
    apt-get clean

# Configure Postfix basic settings
RUN postconf -e "myhostname=mail.example.com" && \
    postconf -e "mydomain=example.com" && \
    postconf -e "myorigin=/etc/mailname" && \
    postconf -e "inet_interfaces=all" && \
    postconf -e "inet_protocols=ipv4" && \
    postconf -e "mydestination=\$myhostname, localhost.\$mydomain, localhost, \$mydomain" && \
    postconf -e "home_mailbox=Maildir/"

# Configure Dovecot to use Maildir
RUN sed -i 's|#mail_location =.*|mail_location = maildir:~/Maildir|' /etc/dovecot/conf.d/10-mail.conf

# Create mail user
RUN useradd -m mailuser && \
    echo "mailuser:password" | chpasswd

# Expose SMTP, IMAP, POP3 ports
EXPOSE 25 143 110

# Start services
CMD service postfix start && dovecot -F