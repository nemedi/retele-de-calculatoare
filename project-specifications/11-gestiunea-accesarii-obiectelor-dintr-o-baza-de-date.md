# 11 – Gestiunea accesarii obiectelor dintr-o baza de date

## 1. Descriere generala

Se va implementa o aplicatie distribuita de tip client-server pentru accesarea si modificarea unor obiecte stocate intr-o baza de date.

Server-ul:

* gestioneaza o colectie de obiecte identificate prin cheie unica
* permite selectarea, actualizarea si stergerea obiectelor
* notifica clientii atunci cand un obiect pe care l-au selectat anterior este modificat

Schimbarile trebuie salvate atat in memoria server-ului, cat si in baza de date.

---

## 2. Cerinte functionale

### 2.1 Conectare

* Clientul se conecteaza la server.
* Server-ul permite operatii asupra obiectelor existente in baza de date.

---

### 2.2 Selectarea obiectelor

* Clientul poate interoga server-ul pentru a selecta o lista de obiecte pe baza valorii cheii (sau a unui interval/criteriu simplu asupra cheii).
* Server-ul:

  * returneaza lista obiectelor corespunzatoare
  * memoreaza pentru fiecare client lista cheilor obiectelor selectate

---

### 2.3 Actualizarea unui obiect

* Clientul poate actualiza un obiect dupa cheie.
* Server-ul:

  * verifica existenta obiectului
  * actualizeaza obiectul in memorie
  * actualizeaza obiectul in baza de date
  * notifica toti clientii care selectasera anterior acel obiect

---

### 2.4 Stergerea unui obiect

* Clientul poate sterge un obiect dupa cheie.
* Server-ul:

  * elimina obiectul din memorie
  * elimina obiectul din baza de date
  * notifica toti clientii care selectasera anterior acel obiect

---

## 3. Cerinte tehnice minime

* Server concurent

* Utilizarea unei baze de date reale (ex. SQLite, MySQL, PostgreSQL etc.)

* Sincronizare corecta intre:

  * memoria server-ului
  * baza de date

* Server-ul trebuie sa mentina pentru fiecare client:

  * lista cheilor selectate (pentru a sti cui sa trimita notificari)

* Protocol clar pentru:

  * select
  * update
  * delete
  * notificari

* Tratarea deconectarilor:

  * la deconectare, server-ul elimina asocierea client -> lista obiecte selectate

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker (inclusiv baza de date).
2. Conectarea a cel putin doi clienti.
3. Selectarea aceluiasi obiect de catre ambii clienti.
4. Actualizarea obiectului de catre un client.
5. Notificarea automata a celuilalt client cu noua versiune.
6. Stergerea obiectului si notificarea clientilor care il selectasera.
7. Demonstrarea persistentei (oprire si repornire server cu datele pastrate in baza de date).

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Selectare corecta a obiectelor din baza de date
* 20% – Update corect (memorie + baza de date)
* 20% – Delete corect (memorie + baza de date)

### 5.2 Notificari si consistenta – 25%

* 15% – Notificare corecta a clientilor care au selectat anterior obiectul modificat
* 10% – Mentinerea corecta a asocierii client -> lista obiecte selectate

### 5.3 Robustete si persistenta – 15%

* 5% – Server concurent stabil
* 5% – Tratarea obiectelor inexistente si a cererilor invalide
* 5% – Persistenta reala a datelor in baza de date (datele raman dupa restart)

---

## 6. Clarificari

* Schema bazei de date poate fi simpla (ex. tabel cu cheie si campuri suplimentare).
* Nu este necesar ORM; se pot folosi query-uri directe.
* Formatul obiectelor este la alegere (JSON, campuri structurate etc.), dar trebuie documentat.
* UI poate fi consola sau interfata grafica.

