# 17 – Sistem de fisiere distribuit

## 1. Descriere generala

Se va implementa un sistem distribuit de sincronizare a fisierelor intre un server si mai multi clienti.

Server-ul:

* expune un anumit director de pe masina sa.

Clientii:

* mentin un director local sincronizat cu directorul expus de server.
* orice modificare pe server sau pe un client trebuie propagata catre toti ceilalti clienti.

Sincronizarea trebuie sa includa:

* creare fisier
* modificare continut
* stergere fisier
* redenumire (poate fi tratata ca stergere + creare)

---

## 2. Cerinte functionale

### 2.1 Conectare si sincronizare initiala

* La conectare, clientul:

  * solicita lista directoarelor si fisierelor expuse de server
  * sincronizeaza directorul local astfel incat sa reflecte exact continutul server-ului

Sincronizarea poate implica:

* creare fisiere lipsa
* stergere fisiere in plus
* actualizare continut pentru fisiere modificate

---

### 2.2 Modificari pe client

* Cand un client efectueaza modificari asupra directorului local:

  * modificarile sunt detectate (monitorizare director)
  * modificarile sunt transmise server-ului
  * server-ul aplica aceleasi modificari asupra directorului sau
  * server-ul notifica toti clientii conectati pentru a-si sincroniza directoarele

---

### 2.3 Modificari pe server

* Daca se modifica direct directorul server-ului (ex. manual sau prin alt client):

  * server-ul detecteaza modificarea
  * notifica toti clientii conectati
  * clientii isi sincronizeaza directoarele locale

---

### 2.4 Tipuri de operatii suportate

Trebuie gestionate cel putin:

* creare fisier
* modificare continut fisier
* stergere fisier

Optional:

* directoare (creare/stergere)
* detectare conflict (nu este obligatoriu mecanism complex)

---

## 3. Cerinte tehnice minime

* Server concurent

* Monitorizare director (watcher) pe client si/sau server

* Transfer corect al fisierelor (inclusiv binare)

* Protocol clar pentru:

  * sincronizare initiala
  * notificare modificare
  * transfer fisier
  * stergere fisier

* Evitarea buclelor infinite de sincronizare:

  * o modificare propagata nu trebuie sa fie retrimisa inapoi ca modificare noua

* Tratarea deconectarilor:

  * la reconectare, clientul trebuie sa refaca sincronizarea completa

---

## 4. Scenarii minime care trebuie demonstrate in video

1. Pornire server in Docker.
2. Conectarea a cel putin doi clienti.
3. Sincronizare initiala corecta.
4. Creare fisier pe un client si propagare catre server si celalalt client.
5. Modificare fisier pe server si propagare catre clienti.
6. Stergere fisier pe un client si sincronizare corecta la toti.
7. Deconectare si reconectare a unui client cu resynchronizare corecta.

---

## 5. Ghid de evaluare (orientativ)

### 5.1 Functionalitate de baza – 60%

* 20% – Sincronizare initiala corecta
* 20% – Propagare creare/modificare fisier
* 20% – Propagare stergere fisier

### 5.2 Consistenta si control bucle – 25%

* 15% – Evitare bucle infinite de sincronizare
* 10% – Reconectare cu resynchronizare corecta

### 5.3 Robustete si validari – 15%

* 5% – Server concurent stabil
* 5% – Transfer corect fisiere binare
* 5% – Tratarea erorilor (fisier lipsa, conflicte simple, deconectari)

---

## 6. Clarificari

* Persistenta este implicita (fisiere reale pe disc).
* Nu este necesar un algoritm avansat de rezolvare conflicte (ultimul update castiga este acceptabil).
* UI poate fi consola.
* Structura directoarelor poate fi simpla (nu este necesar suport complex pentru ierarhii adanci).

