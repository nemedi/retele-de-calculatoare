# 09 – Retea de noduri

## 1. Descriere generala

Se va implementa o aplicatie distribuita formata din mai multe noduri interconectate.

Fiecare nod:

* este atat client, cat si server
* are configurata o lista de noduri din proximitate la care incearca sa se conecteze
* mentine o singura conexiune activa catre un alt nod (la pornire se incearca pe rand nodurile din lista pana la reusita)

Fiecare nod expune o serie de servicii care pot fi pornite sau oprite prin executarea unor comenzi.

---

## 2. Cerinte functionale

### 2.1 Conectarea intre noduri

* La pornire, un nod:

  * citeste lista nodurilor din proximitate (configurare)
  * incearca pe rand sa se conecteze la fiecare
  * mentine o singura conexiune deschisa (prima reusita)

---

### 2.2 Gestionarea serviciilor locale

* Fiecare nod are o lista de servicii disponibile.

* Pentru fiecare serviciu exista:

  * nume
  * comanda de pornire
  * comanda de oprire

* Aplicatia client citeste la pornire lista serviciilor si comenzile asociate.

---

### 2.3 Controlul serviciilor

Din aplicatia client se pot realiza urmatoarele operatii:

* Interogarea starii serviciilor de pe fiecare nod.
* Pornirea unui serviciu pe un nod (local sau la distanta).
* Oprirea unui serviciu pe un nod (local sau la distanta).

---

### 2.4 Executia comenzilor la distanta

* Cand un nod A solicita executia unei comenzi pe un nod B:

  * nodul B executa comanda asociata serviciului
  * nodul B confirma rezultatul executiei (ex. exit code, status text)
  * nodul A afiseaza rezultatul primit

---

## 3. Cerinte tehnice minime

* Fiecare nod trebuie sa functioneze simultan ca:

  * server (accepta cereri)
  * client (se conecteaza la alt nod)

* Protocol clar pentru:

  * conectare intre noduri
  * listare servicii
  * interogare status serviciu
  * pornire/oprire serviciu
  * confirmare rezultat executie

* Executia comenzilor poate fi:

  * reala (executie proces local)
  * sau simulata (comenzi predefinite), dar comportamentul trebuie sa fie clar

* Tratarea deconectarilor:

  * daca nodul la care este conectat se deconecteaza, nodul trebuie sa incerce reconectarea la alt nod din lista

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornirea a cel putin doua noduri (in containere separate sau procese diferite).
2. Conectarea automata a unui nod la altul din lista de proximitate.
3. Listarea serviciilor disponibile.
4. Interogarea starii unui serviciu.
5. Pornirea unui serviciu la distanta si confirmarea rezultatului.
6. Oprirea unui serviciu la distanta.
7. Simularea deconectarii unui nod si reconectarea la alt nod din lista (daca exista).

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Conectare automata la un nod din lista de proximitate
* 20% – Listare si interogare corecta a serviciilor
* 20% – Pornire/oprire serviciu la distanta cu confirmare rezultat

### 5.2 Arhitectura nod hibrid (client + server) – 25%

* 15% – Nod functional simultan ca server si client
* 10% – Gestionare corecta a reconectarii la deconectare

### 5.3 Robustete si validari – 15%

* 5% – Server concurent stabil
* 5% – Tratarea comenzilor invalide sau serviciilor inexistente
* 5% – Gestionarea erorilor de executie fara crash

---

## 6. Clarificari

* Lista nodurilor si lista serviciilor pot fi citite din fisiere de configurare.
* Persistenta nu este obligatorie.
* UI poate fi consola sau interfata grafica.
* Nu este necesara implementarea unui algoritm complex de routing; este suficienta conexiunea la un singur nod activ.
