# 05 – Subscrierea si notificarea pentru canale de stiri

## 1. Descriere generala

Se va implementa o aplicatie distribuita de tip client-server pentru gestionarea unor canale de stiri.

Server-ul mentine o lista de canale (nume + descriere). Clientii se pot conecta, pot vedea lista canalelor, pot crea/sterge canale (doar cele proprii), se pot abona/dezabona la canale si pot primi stiri publicate pe canalele la care sunt abonati.

Server-ul filtreaza continutul stirilor si blocheaza livrarea stirilor care contin cuvinte interzise.

---

## 2. Cerinte functionale

### 2.1 Conectare si lista de canale

* Clientul se conecteaza la server.
* Server-ul trimite lista canalelor existente, fiecare canal avand:

  * nume (unic)
  * descriere

---

### 2.2 Publicarea unui canal

* Un client poate publica (crea) un canal nou specificand:

  * nume canal (unic)
  * descriere
* Daca numele este unic:

  * server-ul adauga canalul
  * server-ul notifica toti clientii conectati ca a aparut un canal nou
* Daca numele exista deja:

  * server-ul refuza cererea

---

### 2.3 Stergerea unui canal

* Un client poate sterge un canal publicat de el.
* Server-ul:

  * elimina canalul
  * elimina toate subscrierile asociate
  * notifica toti clientii conectati ca acel canal a fost sters

---

### 2.4 Subscriere si renuntare la subscriere

* Un client poate subscrie la un canal.
* Un client poate renunta la subscrierea la un canal.
* Server-ul mentine lista abonatilor pentru fiecare canal.

---

### 2.5 Publicarea de stiri pe canal

* Clientul care a publicat un canal poate trimite stiri pe acel canal.
* La publicarea unei stiri:

  * server-ul filtreaza continutul
  * daca stirea este acceptata, server-ul o transmite tuturor clientilor abonati la canal
  * daca stirea este blocata, clientii abonati NU sunt notificati cu acea stire

---

### 2.6 Filtrarea continutului (cuvinte interzise)

* Server-ul are o lista de cuvinte predefinite care nu sunt permise.
* Daca o stire contine unul sau mai multe cuvinte interzise:

  * stirea este blocata
  * stirea nu este livrata abonatilor

Nota: nu este necesar ca server-ul sa notifice abonatii ca o stire a fost blocata; este suficient sa nu o livreze.

---

## 3. Cerinte tehnice minime

* Server concurent
* Stocarea server-side a:

  * canalelor (nume, descriere, proprietar)
  * subscrierilor (canal -> lista clienti)
  * listei de cuvinte interzise
* Protocol clar pentru:

  * listare canale
  * creare canal
  * stergere canal
  * subscribe / unsubscribe
  * publicare stire
  * notificari catre abonati
* Tratarea deconectarilor:

  * la deconectarea unui client, server-ul il elimina automat din toate subscrierile
  * daca proprietarul unui canal se deconecteaza, canalul poate ramane (optional) sau poate fi sters (trebuie ales un comportament si documentat)

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti.
3. Crearea unui canal de catre un client si notificarea catre ceilalti.
4. Abonarea celuilalt client la canal.
5. Publicarea unei stiri permise si primirea ei de catre abonati.
6. Publicarea unei stiri care contine cuvant interzis si faptul ca abonatii NU primesc stirea.
7. Dezabonarea unui client si verificarea ca nu mai primeste stiri.
8. Stergerea canalului si notificarea catre toti clientii.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 15% – Conectare + listare corecta canale
* 15% – Creare canal cu nume unic + notificare catre toti clientii
* 15% – Subscribe / unsubscribe corect
* 15% – Publicare stire de catre proprietar si livrare corecta catre abonati

### 5.2 Filtrare si reguli – 25%

* 15% – Filtrare corecta a cuvintelor interzise (blocare livrare)
* 10% – Restrictie corecta: doar proprietarul poate publica pe canalul sau / sterge canalul

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea erorilor (canal inexistent, subscribe dublu, cereri invalide)
* 5% – Curatare corecta a subscrierilor la deconectare si/sau la stergerea canalului

---

## 6. Clarificari

* UI poate fi consola sau interfata grafica.
* Lista de cuvinte interzise poate fi hardcodata sau incarcata dintr-un fisier de configurare.
* Persistenta in baza de date nu este obligatorie (canalele pot fi tinute in memorie).

