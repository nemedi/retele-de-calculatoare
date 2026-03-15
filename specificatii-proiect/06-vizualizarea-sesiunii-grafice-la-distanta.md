# 06 – Vizualizarea sesiunii grafice la distanta

## 1. Descriere generala

Se va implementa o aplicatie distribuita de tip client-server pentru vizualizarea la distanta a sesiunii grafice (desktop/screen) a unui utilizator.

Server-ul gestioneaza lista utilizatorilor conectati (identificati prin nume unic). Un client poate selecta un alt utilizator si va primi periodic imagini (capturi) ale sesiunii grafice a acestuia, astfel incat sa poata urmari activitatea de la distanta.

Server-ul intermediaza selectia si transferul imaginilor.

---

## 2. Cerinte functionale

### 2.1 Conectare si identificare

* Clientul se conecteaza la server si se identifica printr-un nume.
* Numele trebuie sa fie unic la nivelul server-ului.

Daca numele este unic:

* server-ul accepta conexiunea
* server-ul notifica toti clientii conectati pentru a adauga noul utilizator in lista

Daca numele nu este unic:

* server-ul refuza conectarea
* server-ul notifica utilizatorul nou ca numele este deja folosit

---

### 2.2 Lista utilizatorilor conectati

* La conectare, un utilizator nou primeste lista tuturor utilizatorilor deja conectati.

---

### 2.3 Selectarea unui utilizator si primirea imaginii

* Un utilizator conectat poate selecta, prin intermediul server-ului, un alt utilizator existent.
* Dupa selectare, clientul solicitant primeste imaginea sesiunii grafice a utilizatorului selectat (la momentul curent).

---

### 2.4 Actualizari periodice (stream de imagini)

* Clientul primeste actualizari ale imaginii sesiunii grafice la un interval prestabilit (ex. la 200 ms, 500 ms, 1 secunda).
* Actualizarea trebuie sa permita urmarirea cursiva a activitatii (nu este nevoie de video real-time, dar trebuie sa se vada schimbari).

---

### 2.5 Deconectare

* Cand un client inchide aplicatia:

  * server-ul este notificat
  * server-ul notifica toti clientii conectati pentru eliminarea utilizatorului din lista

---

## 3. Cerinte tehnice minime

* Server concurent
* Transfer de date binare (imagini)
* Protocol clar pentru:

  * connect/deny (cu motiv)
  * listare utilizatori
  * selectare utilizator tinta
  * transfer imagine (cadru) + metadate (ex. format, dimensiune, timestamp)
  * notificari join/leave
* Rata de actualizare configurabila (in client sau server, dar documentata)
* Tratarea deconectarilor neasteptate:

  * server-ul elimina utilizatorul din lista si notifica restul clientilor

Nota: este acceptat ca un client sa fie atat emitator (trimite capturi ale propriului ecran cand este cerut) cat si receptor (vizualizeaza ecranul altuia).

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti (cu nume diferite).
3. Incercare de conectare cu nume duplicat si refuzul corect.
4. Primirea listei de utilizatori la conectare.
5. Selectarea unui utilizator si afisarea imaginii initiale.
6. Actualizari periodice (se demonstreaza schimbari: miscare fereastra, scris intr-un editor etc.).
7. Deconectarea unui client si actualizarea listelor la ceilalti.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Identificare cu nume unic (accept/refuz) + notificari join/leave
* 15% – Lista utilizatorilor corecta la conectare si dupa modificari
* 25% – Selectare utilizator + primire si afisare imagine (cadru) corect

### 5.2 Actualizari si transfer imagini – 25%

* 15% – Actualizari periodice la interval prestabilit (vizibil cursiv)
* 10% – Transfer binar robust (cadre complete, fara corupere, metadate corecte)

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea deconectarilor neasteptate fara crash/blocaj
* 5% – Tratarea erorilor (utilizator tinta inexistent, selectari invalide etc.)

---

## 6. Clarificari

* UI poate fi consola sau interfata grafica.
* Este acceptat ca afisarea sa fie simpla (ex. o fereastra care reimprospateaza imaginea).
* Nu este necesara compresie avansata; se pot folosi formate standard (PNG/JPEG) si capturi periodice.
* Daca se foloseste o librarie de captura/afisare, aceasta trebuie mentionata in README.

