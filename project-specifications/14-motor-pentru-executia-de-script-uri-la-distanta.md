# 14 – Motor pentru executia de script-uri la distanta

## 1. Descriere generala

Se va implementa o aplicatie distribuita de tip client-server pentru executia la distanta a unor script-uri si a unor comenzi compuse (pipeline).

Clientii se conecteaza la server si publica o lista de script-uri. Server-ul mentine:

* lista clientilor conectati
* asocierea script -> client pe care se gaseste script-ul
* lista de comenzi compuse publicate la nivelul server-ului

O comanda compusa este o secventa de script-uri care ruleaza in „conducta” (pipeline): iesirea unui script devine intrarea urmatorului.

Executia unei comenzi compuse se face pornind de la un fisier de intrare primit de server; rezultatul final este un fisier trimis inapoi clientului solicitant.

---

## 2. Cerinte functionale

### 2.1 Publicarea script-urilor

* Clientii se conecteaza la server si publica o lista de script-uri.
* Fiecare script este identificat printr-un nume unic la nivelul server-ului.
* Server-ul tine lista clientilor si asocierea:

  * nume_script -> client_detinator

Restrictie:

* pe durata unei sesiuni cu server-ul, un client nu isi poate modifica lista de script-uri disponibile.

---

### 2.2 Publicarea unei comenzi compuse

* Un client poate publica o comanda compusa identificata prin nume unic la nivelul server-ului.

* O comanda compusa contine o secventa de nume de script-uri (ex. [S1, S2, S3]).

* Semantica executiei:

  * server-ul primeste un fisier de intrare
  * ruleaza S1 cu fisierul de intrare ca argument
  * iesirea lui S1 devine intrarea lui S2
  * etc.
  * iesirea ultimului script devine fisierul rezultat

* Clientii pot suprascrie comenzi existente publicand o comanda cu acelasi nume.

---

### 2.3 Stergerea unei comenzi

* Clientii pot solicita stergerea unei comenzi compuse pe baza numelui acesteia.
* Server-ul elimina comanda din lista.

---

### 2.4 Executia unei comenzi compuse

* Server-ul primeste un fisier pentru executia unei comenzi compuse.
* Comanda de executat este identificata prin numele fisierului de intrare, care trebuie sa coincida cu numele comenzii.

Exemplu:

* comanda compusa se numeste `resize`

* clientul trimite un fisier cu numele `resize`

* server-ul identifica comanda `resize` si o executa

* Server-ul coordoneaza executia script-urilor, contactand clientii detinatori ai fiecarui script din pipeline:

  * trimite datele de intrare (fisier/bytes)
  * primeste iesirea (fisier/bytes)
  * transmite iesirea mai departe la urmatorul script

* La final, fisierul rezultat este trimis clientului care a initiat executia.

---

## 3. Cerinte tehnice minime

* Server concurent
* Stocarea server-side a:

  * maparii script -> client
  * definitiilor comenzilor compuse (nume -> lista script-uri)
* Protocol clar pentru:

  * publicare lista script-uri
  * publicare comanda compusa
  * stergere comanda
  * upload fisier pentru executie
  * transfer input/output intre server si clienti
* Transfer binar corect (fisier ca bytes)
* Tratarea erorilor:

  * script inexistent / client detinator offline
  * comanda inexistenta
  * un pas din pipeline esueaza
* Tratarea deconectarilor:

  * la deconectarea unui client, server-ul elimina script-urile detinute de acesta si poate invalida comenzile care le folosesc (comportament ales si documentat)

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti care publica script-uri diferite.
3. Publicarea unei comenzi compuse care foloseste script-uri de pe ambii clienti.
4. Suprascrierea unei comenzi compuse cu acelasi nume.
5. Trimiterea unui fisier de intrare pentru executie si primirea fisierului rezultat.
6. Demonstratie de eroare controlata (ex. script lipsa / client detinator deconectat).
7. Stergerea unei comenzi si refuzul executiei dupa stergere.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Publicare lista script-uri si mapare corecta script -> client
* 20% – Publicare comanda compusa + suprascriere corecta
* 20% – Executie pipeline corecta (transfer input/output intre pasi) + rezultat trimis solicitantului

### 5.2 Transfer fisiere si tratarea erorilor – 25%

* 15% – Transfer binar corect (fisiere arbitrare, nu doar text)
* 10% – Tratarea erorilor (comanda lipsa, script lipsa, pas esuat) fara blocaj/crash

### 5.3 Robustete si comportament corect – 15%

* 5% – Server concurent stabil
* 5% – Tratarea deconectarilor (curatare mapari)
* 5% – Protocol clar si validari (nume duplicate, cereri invalide etc.)

---

## 6. Clarificari

* Script-urile pot fi script-uri reale (ex. Python, Bash) sau functii simulate, dar trebuie demonstrata executia in lant.
* Formatul input/output pentru script poate fi:

  * bytes (fisier binar)
  * sau text
    dar trebuie sa fie consecvent si documentat.
* UI poate fi consola sau interfata grafica.

