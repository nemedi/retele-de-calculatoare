# 22 – Comunicatia la distanta prin intermediul tunelurilor

## 1. Descriere generala

Se va implementa un mecanism de tunelare a comunicatiei pentru a traversa o regiune de retea in care este deschis un singur port.

Arhitectura include:

* Server de tunelare local

  * asculta pe mai multe porturi configurabile
  * primeste cereri de la clienti locali
  * redirectioneaza pachetele catre server-ul de tunelare de la distanta pe un singur port accesibil
  * adauga in pachet informatia despre portul destinatie dorit

* Server de tunelare la distanta

  * asculta pe singurul port accesibil
  * extrage din pachet portul destinatie
  * retrimite pachetul local catre server-ul care ofera serviciul dorit

* Doua servicii distincte la distanta

  * fiecare oferit de un server diferit
  * fiecare cu propriul client

Scopul este demonstratia conectarii la un serviciu aflat in spatele unei restrictii de port, folosind tunelul.

---

## 2. Cerinte functionale

### 2.1 Server de tunelare local

* Asculta pe o lista configurabila de porturi.
* Pentru fiecare conexiune primita:

  * incapsuleaza datele
  * adauga informatia despre portul destinatie dorit
  * trimite pachetul catre server-ul de tunelare de la distanta pe singurul port deschis.

---

### 2.2 Server de tunelare la distanta

* Asculta pe un singur port.
* Pentru fiecare pachet primit:

  * extrage portul destinatie
  * redirectioneaza local pachetul catre server-ul serviciului corespunzator.

---

### 2.3 Servicii la distanta

Se vor implementa cel putin doua servicii diferite. Exemple:

* Serviciu 1: server care trimite periodic timpul catre clienti.
* Serviciu 2: server de tip broadcast mesaje text (talk).

Fiecare serviciu trebuie:

* sa fie accesibil doar prin tunel
* sa demonstreze functionare independenta.

---

### 2.4 Flux complet de comunicatie

Fluxul trebuie sa fie:

Client serviciu
-> Server tunel local
-> Server tunel distanta (singurul port deschis)
-> Server serviciu destinatie

Raspunsul trebuie sa urmeze traseul invers.

---

## 3. Cerinte tehnice minime

* Server de tunelare local concurent
* Server de tunelare la distanta concurent
* Incapsulare clara a mesajului:

  * header care contine port destinatie
  * payload
* Protocol clar pentru:

  * incapsulare
  * decapsulare
  * forward
  * raspuns
* Tratarea erorilor:

  * port destinatie invalid
  * serviciu indisponibil
  * pachet corupt
* Fara a folosi solutii existente de tip SSH tunnel; implementare proprie

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server tunel local in Docker.
2. Pornire server tunel la distanta.
3. Pornire a doua servicii diferite la distanta.
4. Conectare client serviciu prin tunel.
5. Demonstratie functionare serviciu 1 prin tunel.
6. Demonstratie functionare serviciu 2 prin tunel.
7. Demonstratie refuz conexiune daca se incearca acces direct (fara tunel).
8. Tratarea unei erori (port destinatie invalid).

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Implementare server tunel local
* 20% – Implementare server tunel la distanta
* 20% – Flux complet client -> tunel -> serviciu -> raspuns

### 5.2 Incapsulare si multiplexare – 25%

* 15% – Incapsulare corecta (header + payload)
* 10% – Gestionare corecta a mai multor porturi si servicii

### 5.3 Robustete si comportament corect – 15%

* 5% – Servere concurente stabile
* 5% – Tratarea erorilor (port invalid, serviciu indisponibil)
* 5% – Fara blocaje sau bucle infinite

---

## 6. Clarificari

* Serviciile pot fi simple (ex. timp curent, echo, chat text).
* Nu este necesara criptare a tunelului.
* Implementarea trebuie sa fie proprie, fara utilizarea unui proxy/tunel deja existent.
* UI poate fi consola.

