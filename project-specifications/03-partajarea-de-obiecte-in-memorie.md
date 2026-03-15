# 03 – Partajarea de obiecte in memorie

## 1. Descriere generala

Se va implementa o aplicatie distribuita de tip client-server pentru partajarea de obiecte tinute in memorie la clienti.

Server-ul actioneaza ca registru (index) si intermediar de transfer:

* mentine un dictionar cheie -> client detinator
* furnizeaza lista de chei publicate
* intermediaza cererile de regasire (server-ul cere obiectul de la detinator si il livreaza solicitantului)

Obiectele pot fi orice structura serializabila (ex: JSON, binar, protobuf etc.), dar cheia trebuie sa fie unica la nivelul server-ului.

---

## 2. Cerinte functionale

### 2.1 Conectare si lista de chei

* Clientul se conecteaza la server.
* Server-ul trimite lista tuturor cheilor existente (obiecte publicate de clientii conectati).

---

### 2.2 Cautare / regasire obiect dupa cheie

* Un client poate solicita un obiect dupa cheie.

* Server-ul:

  * verifica daca cheia exista in dictionarul sau
  * identifica clientul detinator al cheii
  * solicita detinatorului transferul continutului obiectului
  * primeste obiectul
  * livreaza obiectul clientului solicitant

* Daca cheia nu exista:

  * server-ul raspunde cu eroare (cheie inexistenta)

---

### 2.3 Publicarea unui obiect

* Un client poate publica un obiect trimitand:

  * cheia
  * continutul obiectului (sau o referinta serializata completa)
* Server-ul:

  * verifica unicitatea cheii
  * daca cheia este unica:

    * inregistreaza asocierea cheie -> client detinator
    * notifica toti clientii conectati cu privire la noua cheie publicata
  * daca cheia exista deja:

    * refuza publicarea

Nota: server-ul poate alege sa nu pastreze efectiv continutul obiectului, ci doar asocierea cheie -> client; continutul ramane la clientul detinator.

---

### 2.4 Stergerea unui obiect (cheie) publicat

* Un client poate sterge o cheie pe care a publicat-o anterior.
* Server-ul:

  * verifica faptul ca solicitantul este detinatorul cheii
  * sterge cheia din dictionar
  * notifica toti clientii conectati pentru a elimina cheia din lista

---

## 3. Cerinte tehnice minime

* Server concurent
* Dictionar server-side pentru maparea cheilor la clienti
* Protocol clar pentru:

  * conectare si listare chei
  * publicare cheie + obiect
  * regasire dupa cheie
  * stergere cheie
  * notificari (cheie noua / cheie stearsa)
* Serializare corecta a obiectelor (text sau binar), inclusiv pentru obiecte mai mari (nu doar mesaje scurte)
* Tratarea deconectarilor:

  * la deconectarea unui client, server-ul trebuie sa elimine automat toate cheile publicate de acel client si sa notifice restul clientilor

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti.
3. Publicarea a cel putin doua obiecte de catre un client (chei diferite).
4. Notificarea celuilalt client pentru aparitia cheilor noi.
5. Regasirea unui obiect de catre celalalt client (transfer prin server).
6. Stergerea unei chei de catre detinator si notificarea celorlalti.
7. Deconectarea fortata a unui client si eliminarea automata a cheilor sale din lista.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 15% – Conectare + primire lista curenta de chei
* 20% – Regasire obiect dupa cheie (server -> detinator -> server -> solicitant)
* 15% – Publicare obiect cu verificare de unicitate a cheii
* 10% – Stergere cheie de catre detinator

### 5.2 Notificari si consistenta intre clienti – 25%

* 15% – Notificare corecta la publicarea unei chei noi
* 10% – Notificare corecta la stergerea unei chei

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea cheilor inexistente / cererilor invalide
* 5% – Curatare automata a cheilor la deconectarea unui client (inclusiv deconectare brusca)

---

## 6. Clarificari

* Formatul obiectelor este la alegere (JSON, binar etc.), dar trebuie documentat in README-ul proiectului.
* Server-ul nu este obligat sa persiste obiectele; obiectele pot exista doar in memorie la clienti.
* UI poate fi consola sau interfata grafica.
