#!/bin/bash
if ! getent group vmail > /dev/null
then
	groupadd -g 5000 vmail
	useradd -g vmail -u 5000 vmail -d /var/mail
	/var/mail/config/generate-users.sh
fi
service postfix start
dovecot -F