# 04 – Rezervarea de resurse

## 1. Descriere generala

Se va implementa o aplicatie distribuita de tip client-server pentru rezervarea unor resurse pe intervale de timp.

Server-ul gestioneaza:

* o lista de resurse
* pentru fiecare resursa, lista rezervarilor existente (intervale de timp)

Clientii se pot conecta, pot vizualiza resursele si rezervarile, pot bloca temporar o resursa pentru completarea unei rezervari si pot crea, modifica sau sterge rezervari proprii.

---

## 2. Cerinte functionale

### 2.1 Conectare si lista resurse

* Clientul se autentifica printr-un nume.
* Server-ul raspunde cu:

  * lista resurselor
  * pentru fiecare resursa: lista rezervarilor existente (interval start–end + utilizator)

---

### 2.2 Blocarea temporara a unei resurse

* Un client poate solicita blocarea unei resurse pentru un interval de timp.
* Server-ul verifica:

  * daca intervalul nu se suprapune cu o rezervare existenta
  * daca nu este deja blocat pentru acel interval

Daca blocarea este posibila:

* server-ul marcheaza resursa ca fiind blocata pentru acel interval de catre clientul respectiv
* server-ul notifica toti clientii autentificati

Daca nu este posibila:

* server-ul refuza cererea

Nota: blocarea este un pas intermediar inainte de finalizarea rezervarii.

---

### 2.3 Anularea blocarii

* Clientul care a blocat resursa poate anula solicitarea.
* Server-ul:

  * elimina blocarea
  * notifica toti clientii ca intervalul este din nou disponibil

---

### 2.4 Finalizarea unei rezervari

* Clientul care a blocat resursa poate confirma rezervarea.
* Server-ul:

  * transforma blocarea in rezervare efectiva
  * adauga rezervarea in lista resursei
  * notifica toti clientii autentificati

---

### 2.5 Modificarea unei rezervari

* Un client poate modifica datele de inceput si sfarsit pentru o rezervare realizata de el.
* Server-ul verifica:

  * suprapuneri cu alte rezervari
* Daca modificarea este valida:

  * actualizeaza rezervarea
  * notifica toti clientii

---

### 2.6 Stergerea unei rezervari

* Un client poate sterge o rezervare realizata de el.
* Server-ul:

  * elimina rezervarea
  * notifica toti clientii autentificati

---

## 3. Cerinte tehnice minime

* Server concurent
* Model clar pentru intervale de timp (ex. timestamp, data+ora)
* Verificare corecta a suprapunerii intervalelor
* Protocol clar pentru:

  * listare resurse
  * blocare
  * anulare blocare
  * finalizare rezervare
  * modificare
  * stergere
  * notificari
* Tratarea deconectarilor:

  * blocarile active trebuie eliberate automat daca un client se deconecteaza brusc

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti.
3. Vizualizarea aceleiasi liste de resurse.
4. Blocarea unei resurse de catre un client.
5. Refuzul blocarii pentru alt client pe acelasi interval.
6. Finalizarea rezervarii si notificarea tuturor.
7. Modificarea unei rezervari.
8. Stergerea unei rezervari.
9. Deconectarea fortata a unui client cu blocare activa si eliberarea automata a intervalului.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 15% – Conectare + listare corecta resurse si rezervari
* 15% – Blocare corecta cu verificare suprapunere
* 15% – Finalizare rezervare si propagare corecta
* 15% – Modificare si stergere rezervari proprii

### 5.2 Sincronizare si consistenta – 25%

* 15% – Notificari corecte catre toti clientii la orice schimbare
* 10% – Blocari exclusiviste fara conditii de cursa (race conditions)

### 5.3 Robustete si validari – 15%

* 5% – Server concurent stabil
* 5% – Validari corecte ale intervalelor (start < end, fara suprapuneri)
* 5% – Eliberare automata a blocarilor la deconectare neasteptata

---

## 6. Clarificari

* Persistenta in baza de date nu este obligatorie (rezervarile pot fi tinute in memorie).
* Formatul datei si orei este la alegere, dar trebuie documentat in README.
* UI poate fi consola sau interfata grafica.
