# Programming in Java – Assignment 5 (CO5)
**Student:** M Dhinesh Karthi  
**Register No:** 192525358  
**GitHub:** https://github.com/dhineshkarthi868-cmyk/java

## Tasks
- Task A1/B1: `Task01_PassStore/PassStore.java` – delimited pass file, escaping, damaged-row handling, find and route listing.
- Task A2/B1: `Task02_BufferedIO/BufferedCounter.java` – byte-at-a-time versus buffered line counting.
- Task A3: `Task03_GenericDAO/` – generic DAO contract, Student/Course/Marks models and JDBC implementations.
- Task A4: `Task04_Serialization/` – Hostel/Room/Resident object graph and transient sensitive/live fields.
- Task A5: `Task05_Transactions/WalletTransfer.java` – JDBC transaction and unsafe/safe login comparison.
- B2: adversarial inputs and damaged file cases are described in the report; use only a local test database.
- B3/B4: `Task06_BusPass/` – batch loader, schema, metadata report, migration validator and benchmark generator.

## Compile
JDK 17+:
```bash
mkdir -p out
javac -d out $(find . -name '*.java')
```
On Windows PowerShell:
```powershell
$files = Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
```

## MySQL setup
Install MySQL 8 and MySQL Connector/J. Run:
- `Task03_GenericDAO/schema.sql`
- `Task05_Transactions/schema.sql`
- `Task06_BusPass/BusPassSchema.sql`

Set environment variables:
- `DB_URL=jdbc:mysql://localhost:3306/java_assignment5`
- `DB_USER=your_mysql_user`
- `DB_PASSWORD=your_mysql_password`

For JDBC classes, include the Connector/J JAR in the classpath, e.g.:
```bash
java -cp "out:mysql-connector-j.jar" BatchBenchmark 1000
```
The Java-only file and serialization demos can run without MySQL.

## Important
Benchmark timings must be collected on the student's computer and entered into the report. The source code compiles, but database tests require a configured local MySQL server and Connector/J. Screenshots must be captured from actual program runs; do not use mock screenshots as execution evidence.
