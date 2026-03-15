# 01 – Partajarea fisierelor

## 1. Descriere generala

Se va implementa o aplicatie distribuita de tip client-server pentru partajarea fisierelor intre mai multi clienti conectati simultan.

Server-ul nu stocheaza permanent fisierele, ci intermediaza comunicarea dintre clienti si mentine evidenta fisierelor publicate de acestia.

Aplicatia trebuie sa permita:

* autentificarea clientilor
* publicarea si retragerea fisierelor
* notificarea clientilor la modificari
* descarcarea fisierelor prin intermediul server-ului

---

## 2. Cerinte functionale

### 2.1 Autentificare

* Clientul se autentifica pe server folosind un cont (username; optional si parola).
* Dupa autentificare, clientul trimite server-ului lista fisierelor pe care le publica.
* Server-ul raspunde cu lista tuturor fisierelor publicate de ceilalti clienti autentificati.

### 2.2 Notificari la conectare / deconectare

* Cand un client se autentifica:

  * server-ul notifica toti clientii deja conectati
  * notificarea contine numele clientului si lista fisierelor publicate

* Cand un client isi incheie sesiunea:

  * server-ul confirma inchiderea sesiunii
  * server-ul notifica toti clientii conectati pentru a elimina acel client si fisierele sale din lista

### 2.3 Descarcarea unui fisier

* Un client poate solicita descarcarea unui fisier publicat de alt client.
* Server-ul:

  * identifica detinatorul fisierului
  * solicita acestuia citirea continutului
  * primeste continutul fisierului
  * livreaza continutul clientului solicitant
* Clientul salveaza fisierul in sistemul sau de fisiere.

Nota: transferul se face prin server (nu direct client–client).

### 2.4 Monitorizarea directorului gazda

Fiecare client va avea un director local expus.

* Directorul trebuie monitorizat pentru modificari.

* La adaugarea unui fisier nou:

  * clientul notifica server-ul
  * server-ul notifica ceilalti clienti

* La stergerea unui fisier:

  * clientul notifica server-ul
  * server-ul notifica ceilalti clienti

---

## 3. Cerinte tehnice minime

* Server concurent (mai multi clienti simultan)
* Gestionarea corecta a conexiunilor inchise brusc
* Protocol clar pentru:

  * autentificare
  * publicare fisiere
  * notificare
  * solicitare download
  * transfer continut
* Transfer corect pentru fisiere binare si text (nu doar string simplu)

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti.
3. Publicarea initiala a fisierelor.
4. Adaugarea unui fisier nou in directorul unui client si propagarea notificarii.
5. Stergerea unui fisier si propagarea notificarii.
6. Descarcarea unui fisier de la alt client.
7. Deconectarea unui client si actualizarea listelor.

---

## 5. Ghid de evaluare (orientativ)

Evaluarea se face pe baza indeplinirii cerintelor functionale si a comportamentului corect in scenarii multi-client.

### 5.1 Functionalitate de baza – 60%

* 15% – Autentificare + publicare lista initiala de fisiere + primire lista agregata de la server
* 15% – Notificare la conectare: ceilalti clienti primesc client nou + lista lui de fisiere
* 10% – Deconectare corecta: confirmare pentru client + notificare catre ceilalti pentru eliminare
* 20% – Download prin server: cerere -> detinator -> server -> solicitant, cu salvare locala corecta

### 5.2 Monitorizare director – 25%

* 15% – Detectare adaugare fisier + notificare prin server + actualizare lista la ceilalti clienti
* 10% – Detectare stergere fisier + notificare prin server + actualizare lista la ceilalti clienti

### 5.3 Robustete si corectitudine – 15%

* 5% – Server concurent (cel putin 2 clienti simultan fara blocaje)
* 5% – Tratarea deconectarilor neasteptate (client inchis fortat) fara crash
* 5% – Transfer corect si pentru fisiere binare (ex. imagine, arhiva), nu doar text

---

## 6. Clarificari

* Nu este necesara persistenta fisierelor pe server.
* Nu este necesara autentificare reala cu baza de date (un username unic este suficient).
* UI poate fi consola sau interfata grafica.
* Se puncteaza corectitudinea sincronizarii si a notificarilor, nu aspectul vizual.
