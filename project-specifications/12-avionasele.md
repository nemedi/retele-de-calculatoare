# 12 – Avionasele

## 1. Descriere generala

Se va implementa un joc distribuit de tip client-server inspirat din jocul „Avioane”.

Server-ul:

* are o lista de fisiere de configurare
* fiecare fisier descrie o distributie a trei avioane pe o matrice 10 x 10
* alege aleator o configuratie curenta la inceputul jocului

Clientii se conecteaza la server si pot „trage” indicand coordonatele (linie, coloana). Server-ul raspunde in functie de rezultat.

Un avion este doborat doar daca este lovit in cap.

---

## 2. Cerinte functionale

### 2.1 Configuratia jocului

* Server-ul citeste o lista de fisiere de configurare.
* Fiecare configuratie este o matrice 10 x 10.
* Server-ul alege aleator o configuratie la start si dupa fiecare joc finalizat.

Configuratia foloseste:

* cifre pentru parti ale avionului
* litere pentru capetele avioanelor

---

### 2.2 Conectare si identificare

* Clientii se conecteaza la server si se identifica printr-un nume unic.
* Daca numele este deja folosit, server-ul refuza conexiunea.

---

### 2.3 Tragerea

* Clientul trimite o coordonata (linie, coloana).
* Server-ul verifica pozitia si raspunde:

  * `0` – niciun avion atins
  * `1` – parte a unui avion atinsa (dar nu cap)
  * `X` – cap de avion lovit (avion doborat)

---

### 2.4 Finalizarea jocului

* Cand un client doboara toate cele trei avioane:

  * server-ul notifica toti clientii cu numele castigatorului
  * server-ul alege o alta configuratie
  * jocul se reia

---

## 3. Cerinte tehnice minime

* Server concurent (mai multi clienti pot trage simultan)

* Sincronizare corecta pentru acces la configuratia curenta (fara conditii de cursa)

* Evidenta pentru fiecare client:

  * cate avioane a doborat

* Protocol clar pentru:

  * conectare
  * tragere
  * raspuns rezultat
  * notificare castig
  * reset joc

* Tratarea deconectarilor:

  * deconectarea unui client nu afecteaza jocul global

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti.
3. Demonstratie tragere valida si raspuns 0.
4. Demonstratie lovire parte avion si raspuns 1.
5. Demonstratie lovire cap si raspuns X.
6. Finalizarea jocului (doborarea tuturor avioanelor) si notificarea tuturor clientilor.
7. Resetarea automata a jocului cu o noua configuratie.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 15% – Incarcare corecta configuratii + selectie aleatoare
* 20% – Tratare corecta a tragerilor (0 / 1 / X)
* 15% – Evidenta corecta a avioanelor doborate per client
* 10% – Detectare corecta a finalului de joc

### 5.2 Sincronizare si consistenta – 25%

* 15% – Gestionare corecta a tragerilor simultane (fara coruperea starii jocului)
* 10% – Reset corect al jocului si notificare tuturor clientilor

### 5.3 Robustete si validari – 15%

* 5% – Server concurent stabil
* 5% – Tratarea coordonatelor invalide (in afara matricii)
* 5% – Tratarea deconectarilor fara crash

---

## 6. Clarificari

* Nu este necesara afisarea grafica a matricii; este suficienta afisare text.
* Persistenta scorului intre runde nu este obligatorie.
* Configuratiile pot fi fisiere text simple.
* Formatul fisierului trebuie documentat in README.

