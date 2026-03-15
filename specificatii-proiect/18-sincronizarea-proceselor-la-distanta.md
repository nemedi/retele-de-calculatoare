# 18 – Sincronizarea proceselor la distanta

## 1. Descriere generala

Se va implementa un sistem distribuit pentru sincronizarea unor procese care ruleaza in aplicatiile client, utilizand semafoare gestionate de un server central.

Server-ul:

* gestioneaza o lista de semafoare identificate prin nume unic
* asigura acces exclusiv la un semafor pentru un singur client la un moment dat
* mentine o lista de clienti care asteapta eliberarea unui semafor

Clientii:

* se conecteaza la server si mentin conexiunea deschisa pe durata executiei procesului
* pot solicita si elibera acces exclusiv la un semafor

---

## 2. Cerinte functionale

### 2.1 Conectare

* Clientii se conecteaza la server.
* Conexiunea este mentinuta deschisa pe durata executiei procesului client.

---

### 2.2 Solicitarea accesului exclusiv

* Un client poate solicita acces exclusiv la un semafor identificat printr-un nume unic.

Daca:

* niciun alt client nu detine accesul la acel semafor:

  * server-ul acorda accesul
  * server-ul retine ca acel client detine semaforul

* un alt client detine deja accesul:

  * server-ul refuza temporar accesul
  * adauga clientul intr-o lista de asteptare pentru acel semafor

---

### 2.3 Eliberarea semaforului

* Clientul care detine accesul exclusiv poate elibera semaforul.
* Server-ul:

  * acorda accesul urmatorului client din lista de asteptare (daca exista)
  * notifica clientul respectiv ca a primit accesul

---

### 2.4 Gestionarea deconectarilor

* Daca un client care detine un semafor se deconecteaza:

  * server-ul trebuie sa elibereze automat semaforul
  * acorda accesul urmatorului client din lista de asteptare

* Daca un client aflat in lista de asteptare se deconecteaza:

  * este eliminat din lista

---

## 3. Cerinte tehnice minime

* Server concurent
* Evidenta server-side pentru fiecare semafor:

  * client detinator (daca exista)
  * lista de asteptare (FIFO recomandat)
* Protocol clar pentru:

  * request semafor
  * grant
  * release
  * notificari
* Sincronizare corecta (fara conditii de cursa la acordarea semaforului)
* Tratarea timeouts sau deconectari fara blocaje permanente

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti.
3. Client A solicita un semafor si il obtine.
4. Client B solicita acelasi semafor si este pus in asteptare.
5. Client A elibereaza semaforul si Client B il primeste automat.
6. Demonstratie de deconectare fortata a unui client care detine semaforul si acordarea automata a accesului urmatorului.
7. Demonstratie cu doua semafoare diferite gestionate simultan.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Solicitare si acordare corecta a accesului exclusiv
* 20% – Implementare corecta lista de asteptare
* 20% – Eliberare si transfer automat al accesului

### 5.2 Sincronizare si consistenta – 25%

* 15% – Fara conditii de cursa la acces concurent
* 10% – Gestionare corecta a mai multor semafoare simultan

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Eliberare automata la deconectare
* 5% – Tratarea cererilor invalide (semafor inexistent etc.)

---

## 6. Clarificari

* Semafoarele pot fi create dinamic la prima cerere sau pot fi predefinite.
* Nu este necesar suport pentru semafoare cu mai mult de un permis (exclusiv este suficient).
* UI poate fi consola.
* Nu este necesara persistenta semafoarelor intre restarturi.

