# google-calendar-api
applicazione backend per un sistema di gestione del calendario simile a Google Calendar.

# funzionalità di base
● Il sistema deve supportare più utenti: ogni utente può vedere solo i propri calendari.

● Deve essere possibile creare più calendari per lo stesso utente (ad esempio, il
calendario privato e quello di lavoro).

● Deve essere possibile creare eventi che abbiano le seguenti caratteristiche:
    - Sono associati ad uno e un solo calendario
    - Hanno una data, un’ora di inizio e una di fine, un titolo.
    - Possono durare più giorni

● Deve essere presente una funzione per creare eventi che si ripetono: ad es. tutti i giorni
fino ad una certa data, o in determinati giorni della settimana tutte le settimane, ecc.
(prendere ispirazione da Google Calendar)

● Deve essere possibile vedere i propri eventi:
    - Del giorno corrente o di un giorno qualsiasi
    - Della settimana corrente o di una settimana qualsiasi
    - Del mese corrente o di un mese qualsiasi

# struttura del database
![image](https://github.com/CoffeeCode15/google-calendar-api/assets/138596346/770c8340-e21a-4802-99bd-c9703f410dc9)

# relazioni
- Ogni utente può vedere solo i propri calendari, e può creare più di un calendario. Quindi c'è una relazione Utente-Calendario di 1:N
- Ogni evento può essere associato a un solo calendario. Il calendario può avere più eventi. Relazione Calendario-Evento di 1:N
