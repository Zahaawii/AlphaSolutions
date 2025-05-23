# Alpha Solutions – Projektkalkulationsværktøj  
*KEA Datamatiker – Eksamensprojekt, Forår 2025*

Dette system er udviklet som en del af eksamensprojektet på KEA 2. semester. Formålet er at understøtte Alpha Solutions’ behov for bedre overblik og styring af projekter, medarbejdere, tid og ressourcer.

---

## ⚡ TL;DR – Kom hurtigt i gang

1. **Klon projektet**
   ```bash
   git clone https://github.com/Zahaawii/AlphaSolution.git
   cd AlphaSolution
   
2. **Opsæt MySQL-database**
   - Opret database: `alphasolutions`
   - Kør SQL-scripts fra `src/main/resources/sql/`

3. **Tilføj `application.properties` i `src/main/resources/`**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/alphasolutions
   spring.datasource.username=dinBruger
   spring.datasource.password=ditPassword
   spring.profiles.active=dev
   
4. **Kør applikationen**
   - Åbn projektet i IntelliJ IDEA (Ultimate anbefales)
   - Navigér til `AlphaSolutionsApplication.java` og klik på "Run"

5. **Login testbruger**
   - Brugernavn: `ajen`
   - Adgangskode: `Password123!`
---

## 🎯 Problemstilling og Formål

Alpha Solutions opererer i flere lande med over 80 medarbejdere og har identificeret et behov for et internt værktøj, der gør det muligt at:

- Nedbryde projekter i subprojekter, tasks og subtasks
- Tilknytte medarbejdere og kompetencer til specifikke opgaver
- Estimere og følge op på timer og deadlines
- Få visuel indsigt i fremdrift og belastning

Målet er et simpelt, brugervenligt system, der kan anvendes af både projektledere og medarbejdere.

---

## 🧱 Teknologier og Arkitektur

| Teknologi       | Version     | Funktion                  |
|-----------------|-------------|---------------------------|
| Java            | 21          | Backend                   |
| Spring Boot     | 3.x         | Webramme + MVC            |
| JDBC Template   | N/A         | Database access           |
| MySQL           | 8.x         | Primær database           |
| H2              | In-memory   | Testdatabase              |
| Thymeleaf       | N/A         | HTML templating           |
| GitHub Actions  | YAML-based  | CI/CD workflow            |
| Azure           | App Services| Hosting + MySQL cloud     |

### 🧱 Arkitektur
Applikationen er struktureret efter MVC-principper:
``` 
src/
├── controller/ // Web endpoints
├── service/ // Forretningslogik
├── repository/ // Data access (JDBC)
├── model/ // Domæneklasser
├── rowmapper/ // JDBC row mappers
├── templates/ // Thymeleaf HTML-filer
├── static/css/ // Frontend styling
└── resources/sql/ // DB scripts
``` 

Se `docs/`-folderen for yderligere diagrammer og dokumentation.

---

## 🚀 Deployment og test

| Miljø        | URL                                         |
|--------------|---------------------------------------------|
| Azure Prod   | https://g1ssolutions.azurewebsites.net/     |
| Login        | `ajen` / `Password123` *(testkonto)*        |

### CI/CD
- GitHub Actions automatiserer build, test og deployment.
- Deployment sker til Azure via `main` branch.

---

## 📦 Funktioner

- Login med rollebaseret adgang (Admin, Projektleder, Medarbejder)
- CRUD for projekter, subprojekter, tasks og subtasks
- Filtrering af medarbejdere ud fra kompetencer
- Tildeling af medarbejdere til projekter
- Beregning af fremdrift og estimeret belastning
- Gantt diagram (fremtidig feature)

---

## 🧪 Test og kvalitetssikring

- Unit- og integrationstests (JUnit, H2)
- Testdækning af controllers og services
- Static code inspection med Qodana (CI-step)
- Testdatabase og scripts medfølger i `test/resources/sql`

---

## 📄 Dokumentation og Bilag

- **Klassediagram** – `docs/klassediagram.png`
- **UML Package diagram** – `docs/architecture.png`
- **ER-model** – `docs/er-model.png`
- **SQL scripts** – `resources/sql/`
- **Rapport** – `docs/rapport/`

---

## 👥 Udviklingsteam

- 👥 Zahaa Al-khakani
- 👥 Simon Pedersen
- 👥 Victor Krogh Jensen
- 👥 Hannibal Ussing-Widholm

> Projektgruppe – KEA, Datamatikeruddannelsen, E24C – Forår 2025  
> Produktet er udviklet med fokus på læring, best practices og overdragelig dokumentation.

---

## 📚 License

Dette projekt er udelukkende udviklet til undervisningsbrug og må ikke anvendes kommercielt uden tilladelse fra udviklingsteamet og KEA.

