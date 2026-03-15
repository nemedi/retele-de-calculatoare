# 24 – Agent pentru controlul unor aplicatii la distanta

## 1. Descriere generala

Se va implementa un sistem client-server pentru controlul unor aplicatii la distanta.

Server-ul:

* are configurata o lista de aplicatii
* pentru fiecare aplicatie sunt definite operatii disponibile:

  * start
  * stop
  * restart
  * status
* fiecare operatie are asociata o comanda care va fi executata pe masina server-ului

Clientul:

* se conecteaza la server
* poate interoga lista aplicatiilor si comenzile disponibile
* poate solicita executia unei comenzi pentru o aplicatie
* afiseaza rezultatul executiei

Exemplu: controlul unui server de baze de date sau al unui serviciu de sistem.

---

## 2. Cerinte functionale

### 2.1 Configurarea aplicatiilor pe server

* Server-ul are o configuratie (fisier) care contine:

  * lista aplicatiilor
  * pentru fiecare aplicatie:

    * nume unic
    * comenzile asociate operatiilor (start/stop/restart/status)

* Lista aplicatiilor nu trebuie sa se modifice la runtime (nu este necesara adaugare dinamica).

---

### 2.2 Interogarea listei de aplicatii

* Clientul se conecteaza la server.
* Clientul poate solicita:

  * lista aplicatiilor
  * operatiile disponibile pentru fiecare aplicatie

Server-ul raspunde cu informatiile configurate.

---

### 2.3 Executia comenzilor

* Clientul poate solicita executia unei operatii pentru o aplicatie.
* Server-ul:

  * executa comanda asociata aplicatiei si operatiei
  * captureaza rezultatul (exit code si/sau output)
  * trimite rezultatul catre client

---

### 2.4 Tratarea erorilor

Server-ul trebuie sa trateze:

* aplicatie inexistenta
* operatie inexistenta pentru aplicatia respectiva
* eroare la executia comenzii
* comanda care nu poate fi lansata

---

## 3. Cerinte tehnice minime

* Server concurent (mai multi clienti pot trimite cereri simultan)

* Executia comenzilor in procese separate (nu blocarea firului principal)

* Capturarea exit code-ului si optional stdout/stderr

* Protocol clar pentru:

  * solicitare lista aplicatii
  * solicitare executie comanda
  * raspuns rezultat

* Validari stricte pentru a preveni executia arbitrara de comenzi (doar cele configurate)

* Tratarea deconectarilor fara a lasa procese zombie

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Configurarea a cel putin doua aplicatii cu operatii diferite.
3. Conectare client si solicitare lista aplicatii.
4. Executie operatie status pentru o aplicatie.
5. Executie operatie start sau restart (simulata sau reala).
6. Tratarea unei cereri invalide (aplicatie inexistenta).
7. Executie simultana de comenzi din doi clienti diferiti.
8. Demonstratie capturare exit code sau output.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Interogare corecta lista aplicatii si operatii
* 20% – Executie corecta comenzi configurate
* 20% – Returnare rezultat (exit code / output)

### 5.2 Control si securizare – 25%

* 15% – Restrictie la comenzile configurate (fara executie arbitrara)
* 10% – Executie in proces separat si gestionare corecta a acestuia

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea erorilor (aplicatie/operatie inexistenta)
* 5% – Tratarea deconectarilor si evitarea proceselor zombie

---

## 6. Clarificari

* Aplicatiile pot fi:

  * servicii reale ale sistemului
  * sau aplicatii simulate (ex. script-uri simple care scriu in fisier)
* Nu este necesar un mecanism avansat de autentificare, dar comportamentul trebuie documentat.
* UI poate fi consola.
* Comenzile trebuie definite intr-un fisier de configurare clar documentat in README.

