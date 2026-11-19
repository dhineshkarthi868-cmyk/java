# Java Programming – Assignment 3 (C03)

Seven independent Java implementations based on the supplied SIMATS assignment screenshots.

## Requirements
- JDK 8 or later
- Terminal / Command Prompt

## Compile and run
Each question is a separate program. Open the relevant folder and run:
```bash
javac FileName.java
java ClassName
```
For Q7, optionally provide the thread-pool size:
```bash
javac ResultProcessor.java
java ResultProcessor 1
java ResultProcessor 2
java ResultProcessor 4
java ResultProcessor 8
```

## Programs
1. `Q1_ATMExceptionHandling/ATMExceptionHandling.java` – exception hierarchy, ordered catch blocks, finally.
2. `Q2_UserDefinedExceptions/ExaminationPortal.java` – checked custom exceptions, propagation and exception chaining.
3. `Q3_TryWithResources/SalesReportGenerator.java` – try-with-resources, partial failure handling and report output.
4. `Q4_ThreadLifecycle/ScannerStations.java` – Thread subclass, start, sleep, join and isAlive.
5. `Q5_RaceConditions/SeatBooking.java` – synchronized check-and-book critical section; expected maximum is 10 seats.
6. `Q6_DeadlockAndCommunication/WalletAndTokenCounter.java` – consistent lock ordering and bounded producer-consumer buffer.
7. `Q7_FaultTolerantProcessor/ResultProcessor.java` – ExecutorService, Callable-style submitted tasks, Future, failure summary and pool-size parameter.

## GitHub
Repository link supplied by the student:
https://github.com/dhineshkarthi868-cmyk/java/tree/main/assignment3

Copy these folders into that repository's `assignment3` directory and commit them. The archive does not claim that files have been pushed to GitHub.
