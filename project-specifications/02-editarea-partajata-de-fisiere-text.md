# 02 – Editarea partajata de fisiere text

## 1. Descriere generala

Se va implementa o aplicatie distribuita de tip client-server pentru editarea partajata a fisierelor text.

Server-ul gestioneaza un director gazda care contine fisiere text. Clientii se pot conecta, pot vizualiza fisiere, pot prelua un fisier in editare si pot salva modificari.

La un moment dat, un fisier poate fi in editare de un singur client.

---

## 2. Cerinte functionale

### 2.1 Conectare si lista fisiere

* Clientul se autentifica printr-un nume.
* Server-ul raspunde cu:

  * lista fisierelor text disponibile
  * pentru fiecare fisier: starea (liber / in editare) si, daca este cazul, numele utilizatorului care il editeaza

---

### 2.2 Vizualizarea unui fisier

* Orice client autentificat poate solicita vizualizarea continutului unui fisier.
* Server-ul trimite ultima versiune salvata pe disc.
* Clientul afiseaza continutul in mod read-only (daca fisierul este in editare de altcineva).

---

### 2.3 Preluarea unui fisier in editare

* Un client poate solicita preluarea in editare a unui fisier disponibil.

* Daca fisierul nu este deja in editare:

  * server-ul marcheaza fisierul ca fiind in editare de catre acel client
  * server-ul trimite continutul fisierului
  * server-ul notifica toti ceilalti clienti ca fisierul este in editare

* Daca fisierul este deja in editare:

  * server-ul refuza cererea

---

### 2.4 Salvarea modificarilor

* Clientul care are fisierul in editare poate trimite o versiune noua catre server.
* Server-ul:

  * actualizeaza fisierul pe disc
  * notifica toti clientii care au fisierul in vizualizare
  * transmite noul continut acestora pentru actualizare

---

### 2.5 Renuntarea la editare

* Clientul poate renunta la editarea fisierului.
* Server-ul:

  * marcheaza fisierul ca fiind disponibil
  * notifica toti clientii autentificati ca fisierul este din nou liber

---

### 2.6 Adaugarea si stergerea fisierelor pe server

* Daca un fisier este adaugat in directorul server-ului:

  * server-ul notifica toti clientii autentificati pentru a-l adauga in lista

* Daca un fisier este sters:

  * server-ul notifica toti clientii pentru a-l elimina din lista

---

## 3. Cerinte tehnice minime

* Server concurent
* Sincronizare corecta pentru acces exclusiv la editare
* Gestionarea corecta a deconectarilor:

  * daca un client care editeaza un fisier se deconecteaza, fisierul trebuie eliberat automat
* Actualizare corecta a fisierelor pe disc
* Protocol clar pentru:

  * conectare
  * vizualizare
  * preluare editare
  * salvare
  * notificare
  * renuntare

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti.
3. Vizualizarea aceluiasi fisier de catre doi clienti.
4. Preluarea fisierului in editare de catre un client.
5. Refuzul editarii pentru al doilea client.
6. Salvarea unei versiuni noi si actualizarea automata la ceilalti clienti.
7. Renuntarea la editare si posibilitatea preluarii de catre alt client.
8. Deconectarea fortata a clientului care editeaza si eliberarea automata a fisierului.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 15% – Conectare + primire lista fisiere cu stare corecta
* 15% – Preluare corecta in editare (exclusivitate respectata)
* 15% – Salvare si actualizare corecta a fisierului pe disc
* 15% – Notificari corecte catre ceilalti clienti

### 5.2 Control acces si sincronizare – 25%

* 15% – Blocare corecta la editare (fara doua editari simultane)
* 10% – Eliberare automata a fisierului la deconectare neasteptata

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea erorilor (fisier inexistent, cereri invalide etc.)
* 5% – Actualizare corecta si consistenta a starii intre clienti

---

## 6. Clarificari

* Nu este necesar un mecanism de versionare avansata.
* Nu este necesar diff incremental; se poate trimite intreg continutul fisierului.
* UI poate fi consola sau interfata grafica.
* Persistenta este obligatorie la nivel de fisier pe disc (nu doar in memorie).

