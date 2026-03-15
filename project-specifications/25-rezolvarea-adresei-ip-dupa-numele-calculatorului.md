# 25 – Rezolvarea adresei IP dupa numele calculatorului

## 1. Descriere generala

Se va implementa un sistem distribuit de rezolvare a adresei IP dupa numele unui calculator, similar unui sistem DNS simplificat.

Arhitectura:

* Client:

  * mentine un fisier local cu asocieri nume -> adresa IP (initial vid)
  * poate interoga pentru a afla adresa IP asociata unui nume
  * poate contacta un server configurat pentru rezolvare

* Server:

  * mentine un fisier local cu asocieri nume -> adresa IP pentru anumite domenii
  * poate fi configurat sa contacteze alte servere pentru domenii pe care nu le gestioneaza
  * memoreaza rezultatele (pozitive si negative)

Rezolvarea poate fi recursiva intre servere.

---

## 2. Cerinte functionale

### 2.1 Fisier local pe client

* Clientul mentine un fisier cu:

  * nume -> adresa IP
  * eventual rezultate negative (nume sau domenii inexistente)

* La primirea unei interogari:

  * verifica fisierul local
  * daca exista intrare (pozitiva sau negativa), raspunde imediat

---

### 2.2 Interogarea server-ului

* Daca clientul nu are informatia local:

  * contacteaza server-ul configurat pentru rezolvare
  * asteapta raspuns

* Dupa primirea raspunsului:

  * actualizeaza fisierul local
  * raspunde solicitantului

---

### 2.3 Fisier local pe server

* Server-ul mentine un fisier cu:

  * asocieri nume -> adresa IP pentru anumite domenii
* Server-ul poate avea configurata o lista de alte servere responsabile pentru anumite domenii.

---

### 2.4 Rezolvare pe server

La primirea unei cereri:

1. Server-ul verifica fisierul sau local.
2. Daca exista intrare:

   * raspunde clientului.
3. Daca nu exista:

   * verifica daca are configurat un server responsabil pentru domeniul respectiv.
   * daca da:

     * contacteaza acel server
     * obtine raspuns
     * isi actualizeaza fisierul local
     * trimite raspunsul clientului
   * daca nu:

     * raspunde negativ.

---

### 2.5 Rezultate negative

* Raspunsurile negative trebuie memorate:

  * pe client
  * pe server
* Daca un domeniu este negativ:

  * orice interogare ulterioara pentru acel domeniu este respinsa fara alte interogari suplimentare.

---

## 3. Cerinte tehnice minime

* Server concurent
* Persistenta prin fisiere text pentru:

  * cache client
  * baza server
* Protocol clar pentru:

  * interogare nume
  * raspuns pozitiv
  * raspuns negativ
* Tratarea ciclurilor potentiale intre servere (documentare strategie simpla, ex. limitare hop-uri)
* Tratarea deconectarilor sau server indisponibil
* Validare nume (format simplu: ex. host.domeniu)

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server principal in Docker.
2. Pornire server secundar responsabil pentru alt domeniu.
3. Pornire client.
4. Interogare pentru un nume rezolvat local pe server principal.
5. Interogare pentru un nume rezolvat prin server secundar (forward).
6. Interogare repetata pentru acelasi nume si rezolvare din cache.
7. Interogare pentru un domeniu necunoscut si memorare rezultat negativ.
8. Reinterogare pentru acelasi domeniu si raspuns imediat din cache negativ.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Rezolvare corecta din fisier local
* 20% – Forward corect catre server responsabil pentru domeniu
* 20% – Cache corect (pozitiv si negativ)

### 5.2 Arhitectura distribuita – 25%

* 15% – Comunicatie corecta intre servere
* 10% – Gestionare limitare recursivitate sau cicluri

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea serverelor indisponibile
* 5% – Validari si tratarea cererilor invalide

---

## 6. Clarificari

* Formatul fisierelor poate fi simplu (ex. nume=IP pe linie).
* Nu este necesara compatibilitate cu DNS real.
* Persistenta poate fi realizata prin rescriere fisier la fiecare update.
* UI poate fi consola.

