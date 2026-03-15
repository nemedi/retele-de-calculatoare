# 08 – Licitatie

## 1. Descriere generala

Se va implementa o aplicatie distribuita de tip client-server pentru organizarea de licitatii.

Server-ul gestioneaza:

* lista clientilor conectati (identificati prin nume unic)
* lista produselor puse in vanzare
* pentru fiecare produs:

  * proprietarul
  * pretul minim de pornire
  * pretul maxim curent
  * lista ofertantilor

Durata unei licitatii este predefinita de server si este aceeasi pentru toate produsele.

---

## 2. Cerinte functionale

### 2.1 Conectare si identificare

* Clientul se conecteaza la server si se identifica printr-un nume.
* Numele trebuie sa fie unic.

Daca numele este deja folosit:

* server-ul refuza conexiunea.

Daca este unic:

* server-ul accepta conexiunea
* server-ul trimite lista produselor in curs de licitare, cu:

  * nume produs
  * proprietar
  * pret minim
  * pret maxim curent

---

### 2.2 Publicarea unui produs

* Un client conectat poate pune in vanzare un produs:

  * nume produs (unic in lista produselor)
  * pret minim de pornire
* Server-ul:

  * adauga produsul
  * seteaza pretul maxim curent la pretul minim
  * notifica toti clientii despre noul produs disponibil

---

### 2.3 Plasarea unei oferte

* Un client poate face o oferta pentru un produs aflat in curs de licitare.
* O oferta este valida doar daca:

  * produsul este inca activ
  * suma oferita este mai mare decat pretul maxim curent

Daca oferta este valida:

* server-ul actualizeaza pretul maxim
* server-ul notifica:

  * proprietarul produsului
  * toti clientii care au ofertat anterior pentru acel produs

Daca oferta este invalida:

* server-ul refuza cererea

---

### 2.4 Expirarea licitatiei

* Durata unei licitatii este predefinita pe server.
* La expirarea duratei:

  * produsul este marcat ca indisponibil pentru ofertare
  * server-ul notifica toti clientii pentru a actualiza starea produsului

---

## 3. Cerinte tehnice minime

* Server concurent
* Gestionarea timpului pentru fiecare produs (expirare automata)
* Protocol clar pentru:

  * conectare
  * publicare produs
  * ofertare
  * notificari la oferta
  * notificari la expirare
* Validare corecta a ofertelor (mai mare decat pretul curent)
* Tratarea deconectarilor:

  * daca un client se deconecteaza, produsele sale pot ramane active (comportament ales si documentat)

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin trei clienti.
3. Publicarea unui produs.
4. Notificarea tuturor clientilor despre produsul nou.
5. Plasarea mai multor oferte succesive si actualizarea pretului maxim.
6. Refuzul unei oferte mai mici decat pretul curent.
7. Expirarea automata a licitatiei si notificarea tuturor.
8. Incercare de ofertare dupa expirare si refuzul corect.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 15% – Conectare cu nume unic + primire lista produse
* 15% – Publicare produs nou + notificare catre toti clientii
* 20% – Ofertare valida + actualizare pret maxim + notificari corecte
* 10% – Refuz ofertare invalida (pret prea mic sau produs expirat)

### 5.2 Gestionarea timpului si starii – 25%

* 15% – Expirare automata corecta a licitatiei
* 10% – Blocarea ofertelor dupa expirare

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea deconectarilor fara crash
* 5% – Validari (nume produs duplicat, sume invalide etc.)

---

## 6. Clarificari

* Persistenta in baza de date nu este obligatorie.
* UI poate fi consola sau interfata grafica.
* Durata licitatiei poate fi configurabila (ex. 30 secunde) pentru a putea fi demonstrata in video.
* Nu este necesar un sistem real de plati; se simuleaza doar logica de ofertare.
