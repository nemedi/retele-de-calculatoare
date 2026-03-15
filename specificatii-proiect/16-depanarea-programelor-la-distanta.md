# 16 – Depanarea programelor la distanta

## 1. Descriere generala

Se va implementa un sistem distribuit de depanare (debugging) la distanta pentru un limbaj simplificat de instructiuni aritmetice.

Server-ul:

* executa mai multe programe in paralel
* fiecare program este o secventa de instructiuni de forma:

  * atribuire rezultat intr-o variabila
  * expresii cu:

    * variabile
    * constante numerice
    * operatori aritmetici (+, -, *, /)
    * paranteze

Clientii se pot conecta pentru a:

* atasa la un program
* seta sau elimina puncte de oprire (breakpoints)
* evalua variabile
* modifica valori ale variabilelor
* continua executia

Server-ul accepta un singur client care poate intrerupe executia unui program la un moment dat.

---

## 2. Cerinte functionale

### 2.1 Executia programelor

* Server-ul lanseaza in executie mai multe programe in paralel.
* Fiecare program este executat linie cu linie.
* Fiecare atribuire constituie o instructiune ce poate fi oprita prin breakpoint.

---

### 2.2 Atasare si gestionare breakpoints

* Clientii se pot conecta la server.
* Clientii pot:

  * atasa la un program
  * adauga puncte de oprire (identificate prin numele programului + linie)
  * elimina puncte de oprire
  * detasa de la depanare

Restrictie:

* Pe durata executiei unui program, clientul care-l depaneaza nu mai poate adauga sau elimina puncte de oprire.

---

### 2.3 Oprire la breakpoint

* Cand executia ajunge la o linie marcata cu breakpoint:

  * server-ul opreste executia programului respectiv
  * asteapta comenzi de la clientul atasat

Clientul poate:

* evalua valoarea unei variabile dupa nume
* seta valoarea unei variabile
* continua executia pana la urmatorul breakpoint

---

### 2.4 Finalizarea executiei

* La incheierea executiei programului depanat:

  * server-ul notifica clientul care monitoriza programul

---

## 3. Cerinte tehnice minime

* Server concurent (mai multe programe executate simultan)

* Sincronizare corecta intre:

  * firul de executie al programului
  * firul de comunicare cu clientul depanator

* Evidenta pentru fiecare program:

  * lista de breakpoints
  * starea executiei (running / paused / finished)
  * contextul variabilelor

* Protocol clar pentru:

  * atasare la program
  * adaugare/eliminare breakpoint
  * evaluare variabila
  * setare variabila
  * continue
  * notificare finalizare

* Restrictie:

  * un singur client poate controla (intr-adevar opri executia) unui program la un moment dat

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Lansarea a cel putin doua programe in paralel.
3. Conectarea unui client si atasarea la un program.
4. Adaugarea unui breakpoint inainte de executie.
5. Oprirea la breakpoint.
6. Evaluarea unei variabile.
7. Modificarea valorii unei variabile si continuarea executiei.
8. Finalizarea programului si notificarea clientului.
9. Incercarea unui al doilea client de a prelua controlul asupra aceluiasi program si refuzul corect.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Executie corecta a programelor (evaluare expresii si atribuiri)
* 20% – Implementare corecta breakpoints (oprire si reluare)
* 20% – Evaluare si setare variabile in timpul pauzei

### 5.2 Control si sincronizare – 25%

* 15% – Sincronizare corecta intre executie si client depanator
* 10% – Restrictie corecta: un singur client poate controla un program

### 5.3 Robustete si validari – 15%

* 5% – Server concurent stabil (mai multe programe simultan)
* 5% – Tratarea comenzilor invalide (variabila inexistenta, linie invalida etc.)
* 5% – Tratarea deconectarilor fara a lasa programul blocat

---

## 6. Clarificari

* Limbajul programului poate fi simplificat (doar atribuiri si expresii aritmetice).
* Nu este necesara implementarea unui parser complet; se poate folosi o abordare simpla (ex. evaluare controlata).
* Programele pot fi citite din fisiere text.
* UI poate fi consola.

