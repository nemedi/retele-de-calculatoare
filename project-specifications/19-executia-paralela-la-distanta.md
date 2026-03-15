# 19 – Executia paralela la distanta

## 1. Descriere generala

Se va implementa un sistem distribuit de executie paralela in care fiecare nod poate actiona atat ca server, cat si ca client, formand un cluster distribuit.

Fiecare nod:

* incearca sa se conecteze pe rand la o lista de server-e, oprindu-se la primul la care reuseste
* la randul sau accepta conexiuni de la alti clienti
* mentine informatii despre gradul sau de incarcare

Un client poate solicita executia in paralel a unei metode (dintr-o clasa) pe un server cu grad minim de incarcare.

---

## 2. Cerinte functionale

### 2.1 Conectare in cluster

* Clientii incearca sa se conecteze pe rand la o lista de server-e.
* Dupa conectare:

  * trimit portul pe care asculta pentru a primi procesari.
* Server-ul notifica restul clientilor conectati despre noul client (adresa + port).
* Notificarea se propaga si in amonte (upstream).

---

### 2.2 Evidenta gradului de incarcare

* Fiecare server mentine un grad de incarcare (ex. numar de fire active).
* Cand primeste o procesare noua:

  * isi actualizeaza gradul de incarcare
  * notifica clientii conectati
  * notifica server-ul la care este conectat
* Notificarea se propaga mai departe in cluster.

---

### 2.3 Solicitare executie paralela

* Un client poate solicita executia unei metode:

  * pe un numar specific de fire de executie
  * cu argumente
* Server-ul cu cel mai mic grad de incarcare trebuie ales pentru executie (criteriu documentat).

---

### 2.4 Transferul clasei

* Daca metoda apartine unei clase care nu exista pe server-ul destinatie:

  * clientul trimite continutul binar al clasei server-ului
* Server-ul incarca clasa si o foloseste pentru executie.

---

### 2.5 Executia si livrarea rezultatului

* Server-ul lanseaza metoda pe numarul de fire specificat.
* La finalizarea fiecarui fir:

  * trimite rezultatul catre clientul solicitant
  * isi actualizeaza gradul de incarcare
  * notifica cluster-ul despre noua stare

---

### 2.6 Deconectare

* Cand un client se deconecteaza:

  * server-ul la care este conectat notifica clientii conectati la el
  * acestia propaga notificarea mai departe

---

## 3. Cerinte tehnice minime

* Nod hibrid (client + server)
* Evidenta locala pentru:

  * clienti conectati (adresa + port)
  * grad incarcare
* Protocol clar pentru:

  * conectare si anunt port
  * solicitare executie
  * transfer clasa (daca lipseste)
  * trimitere rezultat
  * notificare incarcare
* Executie paralela reala (fire de executie reale, nu simulare secventiala)
* Sincronizare corecta pentru actualizarea gradului de incarcare
* Tratarea deconectarilor fara blocaje

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire a cel putin 3 noduri in Docker sau procese separate.
2. Conectare in cluster si propagare informatii despre noduri.
3. Solicitare executie paralela cu un anumit numar de fire.
4. Demonstratie ca este ales server-ul cu grad minim de incarcare.
5. Transfer clasa catre server (daca nu exista deja).
6. Trimitere rezultate partiale (pe fir) catre client.
7. Actualizare grad de incarcare si propagare in cluster.
8. Deconectare nod si actualizare corecta a cluster-ului.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Conectare cluster + propagare informatii noduri
* 20% – Executie paralela reala cu fire multiple
* 20% – Selectie server cu grad minim de incarcare + livrare rezultate

### 5.2 Transfer si coordonare – 25%

* 15% – Transfer clasa binara si incarcare dinamica
* 10% – Actualizare si propagare corecta grad de incarcare

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea deconectarilor
* 5% – Validari (clasa invalida, metoda inexistenta, parametri incorecti etc.)

---

## 6. Clarificari

* Limbajul ales trebuie sa permita incarcare dinamica a codului (ex. Java, C#, etc.).
* Metoda executata poate fi simpla (ex. calcul matematic), dar trebuie sa permita rulare paralela.
* Nu este necesar mecanism avansat de fault tolerance.
* UI poate fi consola.

