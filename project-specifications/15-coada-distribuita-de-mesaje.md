# 15 – Coada distribuita de mesaje

## 1. Descriere generala

Se va implementa un sistem distribuit in care fiecare participant este atat client, cat si server.

Fiecare nod:

* are o lista de adrese ale unor servere la care incearca sa se conecteze pe rand, pana reuseste la primul
* accepta la randul lui conexiuni de la alti clienti (este si server)
* poate subscrie/dezabona chei pentru procesarea mesajelor
* poate primi mesaje binare (job-uri) identificate printr-o cheie si le livreaza consumatorilor abonati

Ideea principala:

* producatorul trimite un mesaj cu o cheie
* nodul care accepta mesajul identifica consumatorii abonati la cheia respectiva si livreaza mesajul catre ei
* consumatorul proceseaza mesajul executand o comanda asociata cheii

---

## 2. Cerinte functionale

### 2.1 Conectare in retea (client + server)

* Fiecare client are o lista de servere.
* La pornire, clientul incearca sa se conecteze pe rand la servere pana reuseste la primul.
* Fiecare client este si server si asteapta:

  * cereri de conectare
  * cereri de procesare

---

### 2.2 Anuntarea portului de contact (callback)

* Cand un client se conecteaza la un server, ii comunica acestuia portul pe care poate fi contactat inapoi.
* Cand un server accepta o conexiune noua de la un client:

  * informeaza server-ul la care este conectat ca si client (upstream)
  * informeaza ceilalti clienti conectati la el despre punctul unde poate fi contactat inapoi noul client

---

### 2.3 Subscriere / renuntare la subscriere pe chei

* Clientul poate subscrie sau renunta la subscriere pentru procesarea mesajelor identificate printr-o cheie.
* Cand un server primeste o cerere de subscriere/dezabonare:

  * contacteaza server-ul upstream
  * contacteaza toti clientii conectati la el
  * ii informeaza cine a facut operatia si pentru ce cheie

---

### 2.4 Acceptare mesaje binare (producere)

* Clientii accepta mesaje binare identificate printr-o cheie, produse de aplicatia clientului.
* Un client poate trimite un astfel de mesaj catre nodul la care este conectat.

---

### 2.5 Livrare catre consumatori (distribuire)

* Cand un client accepta un mesaj (in rol de „server local” pentru producator sau in rol de nod intermediar):

  * identifica din datele stocate local ce clienti asteapta sa consume mesajul (abonati la cheie)
  * se conecteaza la acestia si le trimite mesajul spre procesare

---

### 2.6 Procesarea mesajului (consum)

* La primirea unui mesaj pentru procesare, un server (nod consumator) executa o comanda aferenta cheii mesajului,
  avand ca argument datele mesajului primit spre procesare.
* Rezultatul procesarii poate fi afisat local; optional se poate trimite inapoi un status/ack.

---

## 3. Cerinte tehnice minime

* Fiecare nod ruleaza simultan ca server si client
* Stocare locala a:

  * conexiunilor cunoscute (clienti + porturi callback)
  * subscrierilor (cheie -> lista consumatori)
* Protocol clar pentru:

  * handshake + port callback
  * subscribe/unsubscribe + propagare
  * trimitere mesaj binar (cheie + payload)
  * livrare catre consumatori
* Tratarea deconectarilor:

  * daca un client se deconecteaza, trebuie eliminat din listele de consumatori si anuntat (direct sau prin timeout)
* Control minim pentru evitare flood:

  * limita de marime a mesajului sau timeout la procesare (documentat)

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire a cel putin 3 noduri (in Docker sau procese separate).
2. Conectare in lant (un nod se conecteaza la primul server disponibil).
3. Subscriere la o cheie de catre un nod consumator si propagarea informatiei.
4. Trimiterea unui mesaj binar cu acea cheie de catre un producator.
5. Livrarea mesajului catre consumator(i) si executia comenzii asociate cheii.
6. Dezabonare si demonstratie ca mesajele ulterioare nu mai sunt livrate acelui consumator.
7. Deconectarea unui consumator si curatarea listelor astfel incat livrarea ulterioara sa nu blocheze sistemul.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Nod hibrid (client+server) + conectare la primul server disponibil
* 20% – Subscriere/dezabonare pe chei + propagare corecta a informatiei
* 20% – Trimitere mesaj binar + livrare catre consumatorii abonati + procesare

### 5.2 Topologie si propagare – 25%

* 15% – Anuntare port callback + distribuire catre ceilalti (upstream + peers)
* 10% – Evidenta locala corecta cheie -> consumatori si livrare catre destinatiile corecte

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea deconectarilor fara blocaje (timeout/curatare)
* 5% – Validari (cheie lipsa, payload prea mare, cereri invalide etc.)

---

## 6. Clarificari

* Comanda asociata cheii poate fi reala (executie proces) sau simulata (switch-case), dar trebuie demonstrata procesarea diferita pe chei diferite.
* Sistemul nu trebuie sa garanteze livrare exact-once; best-effort este suficient, dar trebuie sa fie stabil.
* UI poate fi consola.

