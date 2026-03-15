# 23 – Chat cu camere virtuale

## 1. Descriere generala

Se va implementa un sistem de chat bazat pe camere virtuale folosind multicast pentru livrarea mesajelor intre clienti.

Server-ul:

* mentine o lista de camere virtuale
* fiecare camera are asociata o adresa de multicast
* raspunde la cereri de tip broadcast pentru a furniza lista camerelor si datele necesare conectarii

Clientii:

* la pornire, trimit un pachet de broadcast pentru a obtine lista camerelor
* se pot alatura unei camere (grup multicast) sau pot parasi camera curenta
* pot trimite mesaje catre camera curenta (multicast)
* asculta mesajele primite pe adresa de multicast a camerei

Server-ul poate adauga/sterge camere si notifica clientii prin broadcast.

---

## 2. Cerinte functionale

### 2.1 Gestionarea camerelor pe server

* Server-ul mentine o lista de camere.

* Fiecare camera are:

  * nume (unic)
  * adresa de multicast asociata
  * port pentru mesaje (predefinit sau per camera, dar documentat)

* In aplicatia server se pot adauga sau sterge camere virtuale.

---

### 2.2 Descoperirea camerelor (broadcast)

* La pornire, clientul trimite un pachet pe adresa de broadcast a subretelei catre portul server-ului.
* Server-ul raspunde cu lista camerelor virtuale si adresele de multicast aferente.

---

### 2.3 Alaturare / parasire camera

* Clientul se poate alatura grupului multicast al unei camere.
* Clientul poate parasi camera curenta.
* La un moment dat, un client este in cel mult o camera (un singur grup multicast activ).

---

### 2.4 Trimitere si receptie mesaje (multicast)

* Clientul poate trimite mesaje text catre adresa de multicast a camerei curente pe portul predefinit.
* Fiecare client asculta mesajele pe adresa de multicast a camerei in care se afla si afiseaza mesajele primite.

---

### 2.5 Notificarea modificarilor de camere

* La stergerea unei camere de catre server, sau la adaugarea uneia noi:

  * server-ul trimite un mesaj de tip broadcast catre subretea pe portul predefinit
  * clientii isi actualizeaza lista camerelor

---

## 3. Cerinte tehnice minime

* Implementare corecta broadcast pentru descoperire (UDP broadcast)
* Implementare corecta multicast pentru chat (UDP multicast)
* Protocol clar pentru:

  * cerere lista camere (broadcast)
  * raspuns lista camere
  * notificare schimbari camere (broadcast)
  * format mesaj chat (multicast)
* Tratarea cazurilor:

  * camera stearsa in timp ce clientul este in ea (clientul trebuie sa iasa / sa fie informat si sa nu mai trimita)
  * camera inexistenta selectata
* Concurenta pe server pentru administrare (cel putin pentru raspunsuri la broadcast)

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Pornirea a cel putin doi clienti.
3. Descoperirea camerelor prin broadcast si afisarea listei.
4. Alaturarea ambilor clienti la aceeasi camera.
5. Trimitere si receptie mesaje prin multicast.
6. Mutarea unui client intr-o alta camera si verificare ca primeste doar mesajele camerei curente.
7. Adaugarea unei camere pe server si actualizarea listei la clienti prin broadcast.
8. Stergerea unei camere si comportamentul corect al clientilor.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Descoperire camere prin broadcast (cerere + raspuns)
* 20% – Join/leave camera (un singur grup activ)
* 20% – Chat functional prin multicast (trimitere + receptie)

### 5.2 Gestionarea camerelor si notificari – 25%

* 15% – Adaugare/stergere camere pe server
* 10% – Notificare broadcast pentru schimbari si actualizare lista la clienti

### 5.3 Robustete si validari – 15%

* 5% – Tratarea camerelor inexistente / sterse
* 5% – Stabilitate (fara crash) la pachete pierdute / ordine diferita
* 5% – Format mesaje si validari (mesaj gol, camera nealeasa etc.)

---

## 6. Clarificari

* Multicast poate necesita configurare specifica pe OS; aceasta trebuie mentionata in README daca este cazul.
* UI poate fi consola.
* Nu este necesara persistenta listelor de camere intre restarturi.
* Mesajele pot include optional numele expeditorului.

