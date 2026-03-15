# 21 – Distribuirea procesarii

## 1. Descriere generala

Se va implementa un sistem distribuit de procesare in care:

* Exista un server central care:

  * asculta pachete pe adresa de loopback a subretelei pe un anumit port
  * mentine o lista cu toti clientii activi

* Clientii:

  * la pornire, se inregistreaza la server
  * deschid un port pe care asteapta cereri de procesare
  * pot trimite task-uri pentru a fi executate in cluster
  * pot executa task-uri primite de la server

Server-ul distribuie task-urile catre clienti in mod round-robin.

---

## 2. Cerinte functionale

### 2.1 Inregistrarea clientilor

* La pornire, clientul:

  * trimite un pachet pe adresa de loopback si portul server-ului
  * comunica portul pe care asculta pentru procesare

* Server-ul adauga clientul in lista clientilor activi.

* Inainte de inchidere, clientul:

  * trimite un mesaj pentru a fi eliminat din lista clientilor activi.

---

### 2.2 Trimiterea unui task pentru procesare

* Un client poate trimite server-ului o cerere de procesare:

  * cod binar al task-ului (ex. executabil sau clasa)
  * argumentele de apel

* Server-ul:

  * alege urmatorul client de procesare (round-robin)
  * trimite codul binar si argumentele pe portul de procesare al acelui client

---

### 2.3 Executia task-ului

* Clientul de procesare:

  * lanseaza in executie task-ul primit local
  * cu argumentele primite
  * dupa finalizare, obtine exit code-ul procesului

* Clientul de procesare trimite exit code-ul inapoi server-ului.

* Server-ul trimite exit code-ul catre clientul care a solicitat executia task-ului.

---

### 2.4 Eliminarea clientilor

* Daca un client se inchide corect:

  * este eliminat din lista server-ului.
* Daca un client cade:

  * server-ul trebuie sa detecteze situatia (timeout sau eroare la trimitere)
  * sa il elimine din lista pentru procesari viitoare.

---

## 3. Cerinte tehnice minime

* Server concurent
* Evidenta server-side:

  * lista clienti activi (adresa + port procesare)
  * index round-robin
* Transfer binar corect pentru task
* Executie reala a task-ului ca proces separat
* Protocol clar pentru:

  * inregistrare
  * deregistrare
  * trimitere task
  * trimitere rezultat
* Tratarea erorilor:

  * niciun client activ
  * client de procesare indisponibil
  * task invalid

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Pornirea a cel putin doi clienti procesatori.
3. Inregistrarea corecta a clientilor.
4. Trimiterea unui task si distribuirea lui catre primul client.
5. Trimiterea unui al doilea task si distribuirea round-robin catre al doilea client.
6. Returnarea exit code-ului catre clientul solicitant.
7. Inchiderea unui client si eliminarea lui din lista.
8. Trimiterea unui nou task dupa eliminare si distribuirea corecta.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Inregistrare si eliminare corecta clienti
* 20% – Distribuire round-robin corecta
* 20% – Executie reala task + returnare exit code

### 5.2 Transfer si izolare executie – 25%

* 15% – Transfer binar corect al task-ului
* 10% – Executie in proces separat (nu in thread al server-ului)

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea clientilor cazuti
* 5% – Validari (task invalid, lipsa clienti activi etc.)

---

## 6. Clarificari

* Task-ul poate fi:

  * un executabil simplu
  * o clasa compilata
  * sau un script
    dar trebuie executat efectiv, nu simulat.
* Exit code este suficient ca rezultat (nu este necesara capturarea stdout).
* Nu este necesara persistenta listei de clienti intre restarturi.
* UI poate fi consola.

