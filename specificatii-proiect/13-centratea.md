# 13 – Centratea

## 1. Descriere generala

Se va implementa un joc distribuit de tip client-server similar jocului „Bulls and Cows”.

Server-ul:

* genereaza un numar format din 4 cifre distincte
* mentine evidenta numarului de incercari pentru fiecare client

Clientii se conecteaza la server si incearca sa ghiceasca numarul.

Pentru fiecare incercare, server-ul raspunde cu:

* numar de centrate (cifre corecte pe pozitia corecta)
* numar de necentrate (cifre corecte pe pozitie gresita)

Cand un client ghiceste numarul, jocul se reia automat cu un alt numar.

---

## 2. Cerinte functionale

### 2.1 Generarea numarului

* Server-ul genereaza la start un numar de 4 cifre diferite (0–9).
* Cifrele nu trebuie sa se repete.

---

### 2.2 Conectare si identificare

* Clientii se conecteaza la server si se identifica printr-un nume unic.
* Daca numele este deja utilizat, server-ul refuza conexiunea.

---

### 2.3 Propunerea unei solutii

* Clientul trimite un numar de 4 cifre diferite.
* Server-ul verifica validitatea:

  * exact 4 cifre
  * fara cifre repetate
  * doar cifre 0–9

Daca valid:

* server-ul raspunde cu:

  * numar centrate
  * numar necentrate

Daca invalid:

* server-ul refuza incercarea

---

### 2.4 Castig si reluarea jocului

* Cand un client ghiceste numarul (4 centrate):

  * server-ul notifica toti clientii:

    * numele castigatorului
    * numarul de incercari
  * server-ul genereaza un nou numar
  * jocul se reia

---

## 3. Cerinte tehnice minime

* Server concurent (mai multi clienti pot trimite simultan incercari)

* Evidenta pentru fiecare client:

  * numar incercari in runda curenta

* Sincronizare corecta la schimbarea rundei (dupa castig)

* Protocol clar pentru:

  * conectare
  * trimitere incercare
  * raspuns (centrate/necentrate)
  * notificare castig
  * reset joc

* Tratarea deconectarilor:

  * deconectarea unui client nu afecteaza jocul global

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti.
3. Trimiterea unor incercari invalide si refuzul corect.
4. Trimiterea unor incercari valide si raspuns cu centrate/necentrate.
5. Castigarea jocului de catre un client.
6. Notificarea tuturor clientilor despre castigator.
7. Generarea unui nou numar si reluarea jocului.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 15% – Generare corecta numar (4 cifre distincte)
* 20% – Calcul corect centrate si necentrate
* 15% – Validare corecta a incercarilor
* 10% – Detectare corecta a castigului si reset joc

### 5.2 Concurenta si consistenta – 25%

* 15% – Gestionare corecta a incercarilor simultane
* 10% – Evidenta corecta a numarului de incercari per client

### 5.3 Robustete si validari – 15%

* 5% – Server concurent stabil
* 5% – Tratarea deconectarilor fara crash
* 5% – Tratarea cererilor invalide fara a afecta alti clienti

---

## 6. Clarificari

* UI poate fi consola sau interfata grafica.
* Persistenta scorurilor intre runde nu este obligatorie.
* Nu este necesara o limita de timp pentru ghicire.
* Formatul raspunsului trebuie documentat in README.

