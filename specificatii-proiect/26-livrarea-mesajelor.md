# 26 – Livrarea mesajelor

## 1. Descriere generala

Se va implementa un sistem distribuit de livrare a mesajelor intre domenii, inspirat de functionarea unui sistem de e-mail (dar mult simplificat).

Arhitectura:

* Client:

  * compune mesaje si le trimite unui server pentru livrare
  * mesajul are unul sau mai multi destinatari de forma `user@domeniu`

* Server:

  * are configurata o lista de alte servere catre care poate trimite mesaje
  * pentru fiecare domeniu cunoscut, are configurat server-ul destinatie
  * accepta si mesaje de la alte servere pentru domeniul pe care il gestioneaza
  * salveaza mesajele primite in directoare locale aferente fiecarui utilizator

Scopul este ca mesajele sa ajunga la server-ul responsabil de domeniul destinatarului si sa fie salvate la utilizator.

---

## 2. Cerinte functionale

### 2.1 Compunerea si trimiterea mesajelor (client -> server)

* Clientul compune un mesaj care contine:

  * emitent (optional)
  * lista destinatari (una sau mai multe adrese de e-mail)
  * subiect
  * continut

* Clientul trimite mesajul server-ului pentru livrare.

---

### 2.2 Rutarea mesajelor pe domenii (server -> server)

* La primirea unui mesaj, server-ul:

  * analizeaza lista de destinatari
  * pentru fiecare destinatar:

    * extrage domeniul
    * verifica daca are un server configurat pentru acel domeniu
    * daca da: trimite mesajul catre server-ul corespunzator domeniului
    * daca nu: marcheaza acea livrare ca imposibila

* Server-ul confirma trimiterea pentru fiecare adresa pentru care a putut livra.

* Server-ul notifica imposibilitatea pentru fiecare adresa pentru care nu are server destinatie configurat.

Nota: confirmarea poate fi per destinatar (recomandat) sau agregata.

---

### 2.3 Primirea mesajelor de la alte servere (server destinatar)

* Server-ul responsabil de un domeniu:

  * accepta mesaje de la alte servere pentru acel domeniu
  * salveaza mesajele primite intr-un subdirector aferent adresei de e-mail (sau username-ului)

Exemplu:

* mesaj pentru `ana@exemplu.ro`
* se salveaza in `mailboxes/ana/` (sau echivalent)

---

## 3. Cerinte tehnice minime

* Minim doua servere (domenii diferite)
* Server concurent
* Configurare server:

  * domeniul gestionat
  * mapping domeniu -> server destinatie (pentru domenii externe)
* Protocol clar pentru:

  * trimitere mesaj client -> server
  * trimitere mesaj server -> server (livrare)
  * raspunsuri de confirmare/eroare per destinatar
* Persistenta mesajelor:

  * salvare pe disc in structura de directoare
* Tratarea deconectarilor si a serverelor indisponibile:

  * daca server destinatie nu raspunde, se raporteaza eroare pentru acel domeniu/destinatar (fara crash)

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire a cel putin doua servere (in Docker), fiecare cu domeniul sau.
2. Client trimite un mesaj catre doi destinatari:

   * unul pe domeniul local server-ului
   * unul pe domeniul celuilalt server
3. Server-ul ruteaza corect mesajul catre server-ul corespunzator domeniului extern.
4. Mesajele sunt salvate corect in directoarele destinatarilor.
5. Client trimite un mesaj catre un domeniu necunoscut si primeste eroare pentru acel destinatar.
6. Demonstratie server destinatie indisponibil (oprit) si raportare eroare.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Trimitere mesaj de la client cu mai multi destinatari
* 20% – Rutare corecta pe domenii catre servere destinatie
* 20% – Salvare corecta a mesajelor pe serverul destinatar

### 5.2 Arhitectura distribuita si confirmari – 25%

* 15% – Mapping domeniu -> server si livrare server -> server
* 10% – Confirmari/erori corecte per destinatar (nu doar generic)

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea domeniilor necunoscute si a serverelor indisponibile
* 5% – Persistenta pe disc (structura clara de mailboxes)

---

## 6. Clarificari

* Nu se cere implementarea SMTP/IMAP real.
* Formatul mesajului poate fi JSON sau text structurat, dar trebuie documentat.
* Nu se cere criptare, autentificare sau atasamente.
* UI poate fi consola.

