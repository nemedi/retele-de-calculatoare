# 20 – Actualizarea aplicatiilor la distanta

## 1. Descriere generala

Se va implementa un sistem distribuit pentru distribuirea si actualizarea aplicatiilor executabile.

Server-ul:

* are o lista de aplicatii executabile disponibile
* lista nu se modifica structural in timpul rularii (nu se adauga sau sterg aplicatii, doar se pot suprascrie cu versiuni noi)
* mentine evidenta aplicatiilor descarcate de fiecare client

Clientii:

* se conecteaza la server
* pot solicita lista aplicatiilor disponibile
* pot descarca aplicatii
* primesc automat versiuni noi atunci cand acestea sunt publicate pe server

---

## 2. Cerinte functionale

### 2.1 Lista aplicatiilor

* La conectare, clientul poate solicita lista aplicatiilor disponibile pe server.
* Server-ul raspunde cu:

  * nume aplicatie
  * optional: versiune sau hash

---

### 2.2 Descarcarea unei aplicatii

* Clientul poate solicita descarcarea unei aplicatii.
* Server-ul:

  * trimite fisierul executabil (transfer binar)
  * inregistreaza faptul ca acel client a descarcat aplicatia

---

### 2.3 Publicarea unei noi versiuni

* Pe server se poate publica o noua versiune a unei aplicatii prin suprascrierea celei existente.
* Cand o aplicatie este actualizata:

  * server-ul trimite automat noua versiune tuturor clientilor care au descarcat aplicatia respectiva

---

### 2.4 Comportament pe client la actualizare

* Daca aplicatia nu ruleaza:

  * clientul suprascrie direct versiunea veche.
* Daca aplicatia ruleaza:

  * clientul salveaza versiunea noua
  * incearca periodic sa o suprascrie pe cea veche pana reuseste

---

## 3. Cerinte tehnice minime

* Server concurent
* Evidenta server-side:

  * aplicatii disponibile
  * client -> lista aplicatii descarcate
* Transfer binar corect (executabile reale sau simulate)
* Protocol clar pentru:

  * solicitare lista aplicatii
  * descarcare aplicatie
  * notificare actualizare
  * transfer automat versiune noua
* Mecanism de retry pe client pentru suprascriere in cazul fisierului blocat
* Tratarea deconectarilor:

  * la reconectare, clientul trebuie sa poata verifica daca exista versiuni mai noi

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti.
3. Solicitarea listei de aplicatii.
4. Descarcarea unei aplicatii de catre un client.
5. Publicarea unei noi versiuni pe server.
6. Trimiterea automata a noii versiuni catre clientii care au descarcat aplicatia.
7. Demonstratie situatie in care aplicatia ruleaza si actualizarea este aplicata ulterior (retry).
8. Reconectare client si verificare actualizari.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Lista aplicatii si descarcare corecta
* 20% – Evidenta corecta a clientilor care au descarcat aplicatii
* 20% – Notificare si trimitere automata a noilor versiuni

### 5.2 Transfer si control versiuni – 25%

* 15% – Transfer binar corect si complet
* 10% – Mecanism corect de retry cand fisierul este blocat

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea deconectarilor si verificare versiuni la reconectare
* 5% – Validari (aplicatie inexistenta, cereri invalide etc.)

---

## 6. Clarificari

* Aplicatiile pot fi executabile reale sau fisiere simulate (ex. fisiere binare simple), dar trebuie tratate ca fisiere binare.
* Nu este necesara verificare criptografica avansata a versiunilor; hash simplu sau numar versiune este suficient.
* UI poate fi consola.
* Nu este necesar mecanism de rollback automat.
