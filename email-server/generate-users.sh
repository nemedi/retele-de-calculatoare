#!/bin/bash
USER_FILE=/var/mail/config/users.txt
PASSWD_FILE=/etc/dovecot/users
rm -f $PASSWD_FILE
while IFS=: read -r email password
do
    HASH=$(doveadm pw -s SHA512-CRYPT -p "$password")
    echo "$email:$HASH:5000:5000::/var/mail/vhosts/${email%%@*}/${email##*@}/::" >> $PASSWD_FILE
done < $USER_FILE