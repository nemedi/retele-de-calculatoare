# 27 – Proxy pentru intermedierea comunicatiei

## 1. Descriere generala

Se va implementa un server proxy care intermediaza comunicatia intre clienti si alte servere.

Arhitectura:

* Client:

  * se conecteaza la proxy
  * trimite cereri adresate altor servere (server destinatie specificat in cerere)

* Proxy:

  * primeste cereri de la clienti
  * ataseaza un identificator unic fiecarei cereri
  * mentine asocierea:

    * id_cerere -> client (sau lista clienti)
  * trimite cererea mai departe catre server-ul destinatie, incluzand identificatorul
  * la primirea raspunsului, recupereaza identificatorul si livreaza raspunsul clientului corect

* Server destinatie:

  * proceseaza cererea
  * raspunde proxy-ului incluzand identificatorul

Proxy-ul poate raspunde si la cereri adresate lui direct.

---

## 2. Cerinte functionale

### 2.1 Trimiterea cererilor prin proxy

* Clientul trimite o cerere catre proxy care contine:

  * adresa server destinatie
  * operatia dorita
  * datele necesare

* Proxy-ul:

  * genereaza un identificator unic pentru cerere
  * memoreaza asocierea id -> client
  * transmite cererea catre server-ul destinatie, incluzand id-ul

---

### 2.2 Primirea raspunsului

* Server-ul destinatie:

  * raspunde proxy-ului incluzand identificatorul cererii.

* Proxy-ul:

  * identifica clientul asociat id-ului
  * transmite raspunsul catre clientul corespunzator
  * elimina asocierea id -> client (dupa finalizare)

---

### 2.3 Cereri adresate proxy-ului

* Proxy-ul trebuie sa poata raspunde si la cereri adresate lui direct.
* Exemplu:

  * citirea sau scrierea unui fisier dintr-un director expus pe masina proxy.

---

### 2.4 Concurenta si cereri multiple

* Proxy-ul trebuie sa poata gestiona:

  * mai multi clienti simultan
  * mai multe cereri in paralel
  * raspunsuri care pot veni in ordine diferita fata de cereri

---

## 3. Cerinte tehnice minime

* Server proxy concurent
* Evidenta server-side:

  * mapare id_cerere -> client (sau conexiune)
* Generare identificator unic (incremental sau UUID)
* Protocol clar pentru:

  * cerere client -> proxy
  * forward proxy -> server destinatie
  * raspuns server destinatie -> proxy
  * livrare proxy -> client
* Tratarea erorilor:

  * server destinatie indisponibil
  * raspuns fara id valid
  * cerere invalida
* Curatarea maparilor in caz de deconectare client

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire proxy in Docker.
2. Pornire a cel putin unui server destinatie.
3. Conectarea a cel putin doi clienti la proxy.
4. Trimiterea simultana de cereri diferite catre acelasi server destinatie.
5. Demonstratie ca raspunsurile sunt corelate corect cu clientii pe baza identificatorului.
6. Demonstratie cerere adresata direct proxy-ului (ex. citire fisier local).
7. Demonstratie server destinatie indisponibil si tratarea erorii.
8. Demonstratie raspunsuri care vin in ordine diferita fata de cereri.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Generare si gestionare corecta identificator cerere
* 20% – Forward corect proxy -> server destinatie
* 20% – Corelare corecta raspuns -> client

### 5.2 Concurenta si consistenta – 25%

* 15% – Gestionare corecta a mai multor cereri simultane
* 10% – Corectitudine la raspunsuri out-of-order

### 5.3 Robustete si comportament corect – 15%

* 5% – Server proxy concurent stabil
* 5% – Tratarea serverelor destinatie indisponibile
* 5% – Curatare mapari la deconectari si erori

---

## 6. Clarificari

* Exemplul minim de server destinatie poate fi un server simplu care:

  * citeste/scrie fisiere dintr-un director expus
  * sau raspunde cu un mesaj predefinit.
* Protocolul poate fi text sau binar, dar trebuie documentat.
* Nu este necesara criptare.
* UI poate fi consola.
