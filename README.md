
### How to run instructions

*1. Commands to set java home* 

$env:JAVA_HOME = "C:\path\to\jdk-17"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
java -version



*2. Run tests:*

.\mvnw.cmd test
.\mvnw.cmd clean verify


*3. To start the app in dev mode:*

.\mvnw.cmd quarkus:dev



### Note
The quarkus app uses h2 in memory database in dev mode, so dependency postgres is not required in testing phase


### Theory solutions
Refer to QUESTIONS.md and CASE_STUDY.md

