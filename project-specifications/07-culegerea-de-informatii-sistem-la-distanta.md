### 07-culegerea-de-informatii-sistem-la-distanta.md

# 07 – Culegerea de informatii sistem la distanta

## 1. Descriere generala

Se va implementa o aplicatie distribuita de tip client-server pentru executia la distanta a unor interogari de tip Windows Management Instrumentation (WMI) si colectarea rezultatelor de la mai multi clienti.

Clientii se conecteaza la server si se inregistreaza cu:

* numele masinii
* adresa IP

Un client (operator) poate compune o interogare WMI si poate solicita executia ei pe toti clientii sau pe un subset selectat. Server-ul coordoneaza executia si colecteaza rezultatele de la fiecare client selectat, apoi le intoarce agregat catre clientul operator.

---

## 2. Cerinte functionale

### 2.1 Conectare si inregistrare client

* Clientul se conecteaza la server si transmite:

  * nume masina
  * adresa IP
* Server-ul:

  * adauga clientul in lista clientilor existenti
  * trimite clientului nou lista tuturor clientilor deja existenti
  * notifica toti clientii existenti pentru a adauga noul client

---

### 2.2 Compunere interogare si selectare tinte

* Un client poate compune o interogare WMI (string) si selecteaza:

  * toti clientii
  * sau un set de clienti (dupa nume masina / IP / id intern)

---

### 2.3 Executia la distanta si colectarea rezultatelor

* Clientul operator trimite cererea catre server (interogare + lista tinte).
* Server-ul notifica pe rand fiecare client selectat:

  * transmite comanda/interogarea
  * asteapta rezultatul de la clientul respectiv
* Server-ul colecteaza rezultatele si raspunde clientului operator cu:

  * rezultatul fiecarui client tinta (inclusiv erori, daca apar)

---

### 2.4 Deconectare

* Cand o aplicatie client se inchide:

  * notifica server-ul
  * server-ul solicita clientilor ramasi sa il elimine din lista

---

## 3. Cerinte tehnice minime

* Server concurent
* Protocol clar pentru:

  * inregistrare client
  * listare clienti
  * cerere executie WMI (query + tinte)
  * rezultat executie per client tinta
  * notificari join/leave
* Tratarea timeouts:

  * daca un client tinta nu raspunde intr-un timp rezonabil, server-ul marcheaza acel rezultat ca eroare/timeout si continua
* Tratarea deconectarilor neasteptate:

  * server-ul elimina clientul si notifica restul

Nota: daca implementarea nu ruleaza pe Windows sau nu se doreste integrare WMI reala, se accepta un mecanism echivalent de “comenzi de sistem” (ex. un set limitat de comenzi permise) care simuleaza comportamentul WMI, cu conditia sa fie mentionat explicit in README. Cerinta principala este coordonarea executiei distribuite si colectarea rezultatelor.

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin trei clienti (2 tinte + 1 operator).
3. Primirea listei de clienti la conectare si actualizare la join/leave.
4. Trimiterea unei interogari catre toti clientii si afisarea rezultatelor pe fiecare.
5. Trimiterea unei interogari catre un subset de clienti.
6. Simulare/afisare a unui caz de eroare (client care nu raspunde / query invalid).
7. Deconectarea unui client si actualizarea listei la ceilalti.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Inregistrare client + distribuire lista clienti (catre noul client si catre cei existenti)
* 20% – Cerere executie (query + selectie tinte) transmisa corect prin server
* 20% – Colectare rezultate per client tinta si raspuns agregat catre operator

### 5.2 Coordonare si tratarea cazurilor reale – 25%

* 15% – Executie secventiala/coordonnata pe tinte, fara blocare globala (server-ul continua)
* 10% – Timeouts si raportare corecta a esecurilor per client (fara a opri tot procesul)

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea deconectarilor neasteptate si actualizarea listelor
* 5% – Validari (tinte inexistente, query goala/invalida, cereri malformate)

---

## 6. Clarificari

* Clientul “operator” poate fi aceeasi aplicatie ca si clientii tinta (doar rol diferit in UI).
* Daca se foloseste WMI real:

  * trebuie documentat cum ruleaza (drepturi, dependinte, ce interogari sunt demonstrate).
* Daca se simuleaza WMI:

  * trebuie documentat setul de comenzi permise si formatul rezultatelor.
