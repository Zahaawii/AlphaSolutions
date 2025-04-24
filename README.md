# 📊 Projektkalkulationsværktøj – Eksamensprojekt (KEA Datamatiker 2. Semester F2025)

Dette projekt er udviklet som del af eksamensopgaven i 2. semester for datamatikeruddannelsen på KEA. Opgaven går ud på at udvikle et **projektkalkulationsværktøj** for virksomheden **Alpha Solutions**.

## 🧭 Overblik

Formålet med projektet er at skabe et system, der opfylder kravene fremlagt af kunden under kickoff den 23/04 og efterfølgende i slides præsenteret af Alpha Solutions. Projektet skal demonstrere vores evner inden for systemudvikling med sporbarhed gennem hele processen – fra analyse til deployment.

## 📝 Krav til rapport og projekt

- Sporbarhed mellem:  
  - Virksomhedsanalyse  
  - Kravspecificering  
  - UML-diagrammer  
  - Kildekode  
  - Database  
  - Test cases  

- Diagrammer og kodeudsnit skal suppleres med reflektion og argumentation for valg og fravalg undervejs i projektet.

## 📈 IT & Forretningsforståelse

- Feasibility Study: En analyse i opstartsfasen, som underbygger beslutningen om at igangsætte projektet.

## 🔄 Systemudvikling

- Udvikling efter **agile metoder (Scrum)**:
  - Der arbejdes i 3 sprints
  - Hvert sprint indeholder:
    - Sprint Planning
    - Sprint Review
    - Sprint Backlog
  - Product Backlog og Sprint Backlogs dokumenteres
  - GitHub Projects bruges til Scrum-overblik

## 🛠 Teknologistak

Systemet er udviklet med følgende teknologier:  
(*Angiv versionsnumre og IDE, f.eks.* `IntelliJ 2025.1`)

- Java (Spring Boot)
- JDBC
- MySQL
- Thymeleaf (HTML/CSS)
- GitHub / GitHub Actions
- Maven

## 🧩 Kravspecifikation

- Beskrivelse af Alpha Solutions’ vision og forretningsværdi
- User stories med:
  - Korte beskrivelser
  - Acceptkriterier
  - Organisering i Sprint Backlogs

### Ikke-funktionelle krav:

- Anvendelse af MySQL-database
- Webapplikation via Spring Boot
- Brugervenligt UI med hensyntagen til heuristikker og designprincipper (The Golden Rules)

## 🧠 Domænemodel og Database

- Domænemodel over problemdomænet
- ER-diagram over databasen
- SQL scripts til:
  - Oprettelse af databasen
  - Indsættelse af testdata

### Designovervejelser:

- Normalisering (forklar hvis 3NF ikke er opnået)
- Begrundelser for brug af:
  - Fremmednøgler
  - Constraints
  - Unikke indeks
  - Automatiske ID’er

## 🌐 Navigationsflow

- Aktivitetsdiagram over navigation mellem skærmbilleder
- Screenshots af applikationen (bilag)

## 🧱 Softwaredesign

- UML Package Diagram eller oversigt over pakkestruktur
- Klassediagram (læsbart og forståeligt for udviklere)
- Refleksion over:
  - Designvalg
  - Brug af design patterns
  - Principper for god arkitektur

## 📌 Særlige forhold

Dokumentation af tekniske valg i applikationen, fx:

- Sessionshåndtering
- Exception håndtering
- Brugerinput-validering
- Login-sikkerhed og roller
- Brugertyper og rettigheder

## 💡 Udvalgte kodeeksempler

For at fremhæve kompleks og vigtig kode vises udvalgte kodeeksempler i rapporten, fx fra:

- Sikkerhedshåndtering
- Inputvalidering
- Testbar logik

## 📊 Status på implementering

Et afsnit i rapporten beskriver projektets aktuelle status – hvad er implementeret, hvad er under udvikling og hvad er eventuelt udeladt eller udskudt.

---

> ✨ *Denne README fungerer både som intern dokumentation og som præsentation af projektet for vejledere og censor. Husk at linke til relaterede filer som f.eks. CONTRIBUTING.md og evt. testdata/scripts, hvis de findes i projektet.*
