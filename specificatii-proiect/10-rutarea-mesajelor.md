# 10 – Rutarea mesajelor

## 1. Descriere generala

Se va implementa un sistem distribuit de rutare a mesajelor intre mai multe servere.

Fiecare server:

* gestioneaza o lista de destinatari locali (identificati prin nume unic)
* poate primi mesaje de la clienti
* poate interoga alte servere pentru a determina unde trebuie rutat un mesaj
* poate primi mesaje de la alte servere

Clientii trimit mesaje catre un destinatar. Daca destinatarul nu este local, server-ul trebuie sa determine catre ce alt server sa ruteze mesajul.

---

## 2. Cerinte functionale

### 2.1 Gestionarea destinatarilor locali

* Fiecare server mentine o lista de destinatari locali (nume unic).
* Pentru fiecare destinatar local, mesajele sunt salvate intr-un director local cu numele destinatarului.
* Numele fisierului trebuie generat pe baza momentului salvarii (ex. timestamp).

---

### 2.2 Trimiterea unui mesaj de la client

* Clientul se conecteaza la server si trimite un mesaj care contine:

  * destinatar
  * emitent
  * subiect
  * text mesaj

* Server-ul confirma clientului acceptarea mesajului (indiferent daca este local sau rutat mai departe).

---

### 2.3 Livrare locala

Daca destinatarul exista in lista gestionata de server:

* server-ul salveaza mesajul in directorul local al destinatarului.

---

### 2.4 Rutare catre alte servere

Daca destinatarul nu exista local:

* server-ul interogheaza lista de alte servere configurate
* transmite o cerere pentru a verifica daca destinatarul exista la acel server
* daca un server confirma existenta destinatarului:

  * mesajul este rutat catre acel server
* daca niciun server nu confirma:

  * mesajul este considerat nelivrabil (dar clientul primeste confirmarea de acceptare initiala)

---

### 2.5 Interogari intre servere

* Server-ul poate primi, pe aceeasi conexiune, cereri de la alte servere pentru:

  * verificarea existentei unui destinatar
  * livrarea unui mesaj rutat

* Daca destinatarul cautat nu exista local:

  * server-ul poate proceda similar rutarii unui mesaj (interogand mai departe alte servere configurate)

---

## 3. Cerinte tehnice minime

* Minim doua servere care pot comunica intre ele.

* Server concurent.

* Protocol clar pentru:

  * trimitere mesaj de la client
  * verificare destinatar intre servere
  * rutare mesaj
  * confirmari

* Evitarea buclelor infinite de rutare (ex. prin:

  * limita de hop-uri
  * lista servere deja interogate
  * TTL)

* Salvarea mesajelor in fisiere locale corect structurate.

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornirea a cel putin doua servere (in Docker).
2. Configurarea fiecarui server cu o lista de servere vecine.
3. Trimiterea unui mesaj catre un destinatar local si salvarea corecta.
4. Trimiterea unui mesaj catre un destinatar existent pe alt server si rutarea corecta.
5. Trimiterea unui mesaj catre un destinatar inexistent si tratarea corecta.
6. Demonstratie ca nu apar bucle infinite in rutare.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Trimitere mesaj de la client si confirmare corecta
* 20% – Livrare locala corecta (salvare in director destinatar)
* 20% – Rutare corecta catre alt server si livrare finala

### 5.2 Comunicare intre servere – 25%

* 15% – Interogare corecta a serverelor pentru destinatar
* 10% – Evitarea buclelor de rutare (TTL / lista servere vizitate etc.)

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea destinatarilor inexistenti fara crash
* 5% – Tratarea erorilor de comunicare intre servere

---

## 6. Clarificari

* Persistenta se face prin salvarea mesajelor in fisiere pe disc.
* Formatul mesajului poate fi text sau JSON, dar trebuie documentat.
* Nu este necesara autentificare avansata.
* UI poate fi consola.

