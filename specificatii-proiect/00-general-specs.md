# Cerinte generale – Proiect Retele de Calculatoare

## 1. Scopul proiectului

Proiectul consta intr-o aplicatie distribuita de tip client-server, implementata:

* pe socket-uri sau prin apel de metode la distanta (RMI/gRPC/WCF etc.)
* intr-un limbaj la alegere (C#, Java, C/C++, Python, Go etc.)

Proiectul poate fi realizat individual sau in echipa de maximum 3 persoane.

Fiecare student (sau echipa) va primi aleator o tema din lista de proiecte si trebuie sa implementeze cerintele specifice acelei teme.

Proiectul va fi prezentat pe calculator in ultima activitate de seminar.

## 2. Functionalitate (valabil pentru toate proiectele)

Indiferent de tema primita, implementarea trebuie sa demonstreze clar urmatoarele:

* existenta unui server care accepta conexiuni de la unul sau mai multi clienti
* existenta unuia sau mai multor clienti care trimit cereri si primesc raspunsuri
* protocol clar (formatul mesajelor) si tratarea situatiilor de eroare (date invalide, conexiune inchisa, timeouts etc.)

## 3. Organizare si prezentare

* proiect individual sau in echipa de maximum 3 persoane
* fiecare student trebuie sa aiba un repository Git (public sau privat, cu acces acordat cadrului didactic)
* in cazul echipelor:

  * prezentarea proiectului trebuie facuta de intreaga echipa care a realizat proiectul
  * fiecare membru trebuie sa aiba o contributie identificabila:

    * in timpul prezentarii (explica o parte clara din proiect)
    * in istoricul de commit-uri (commit-uri reale, relevante, asociate fiecarui membru)
* codul trebuie sa fie suficient de clar incat studentul sa poata explica implementarea la prezentare

## 4. Cerinte obligatorii de livrare

### 4.1 Repository Git

Repository-ul trebuie sa contina:

* codul sursa complet (client + server, si orice fisiere auxiliare)
* un fisier README.md cu instructiuni clare
* fisierele necesare pentru rulare in Docker
* un fisier docker-compose.yml la radacina (sau indicat explicit in README)
* link-ul catre video-ul demonstrativ

Structura recomandata (nu obligatorie, dar utila):

```text
/server
/client
/docker-compose.yml
/README.md
```

### 4.2 Docker (obligatoriu)

* serverul trebuie sa ruleze (cel putin) intr-un container Docker
* pornirea se face folosind docker compose
* evaluarea se va face pornind proiectul exact dupa instructiunile din README

Comanda tinta (sau echivalent, daca este justificat in README):

```bash
docker compose up --build
```

Daca solutia necesita setari (porturi, variabile de mediu), acestea trebuie documentate in README si incluse in docker-compose (sau intr-un fisier .env).

### 4.3 Video demonstrativ (obligatoriu)

* video-ul trebuie sa aiba durata maxima 5–7 minute
* video-ul trebuie sa contina obligatoriu:

  * face cam (fata studentului / a studentilor)
  * demo functional (pornire server in Docker, pornire clienti, scenarii relevante)
  * o scurta explicatie (ce face proiectul, cum functioneaza comunicarea, ce cerinte sunt acoperite)

Video-ul poate fi incarcat pe YouTube (public/nepublic) sau pe Drive/alt serviciu similar.
Link-ul trebuie pus in README.

## 5. Cerinte de stabilitate si comportament

* aplicatia nu trebuie sa se blocheze sau sa se inchida necontrolat in scenarii normale de utilizare
* la deconectari (client inchis, server inchis, conexiune intrerupta), aplicatia trebuie sa trateze situatia fara crash si fara a ramane blocata
* erorile trebuie tratate controlat (mesaje clare, inchidere curata a resurselor, reconectare daca este cazul sau iesire controlata)

## 6. Integritate academica

* codul trebuie sa fie realizat de student / echipa
* este permisa utilizarea bibliotecilor externe
* este permisa utilizarea uneltelor AI pentru asistenta
* la prezentare, studentul trebuie sa poata explica functionalitatea si deciziile principale (protocol, arhitectura, tratarea erorilor etc.)
