# Optimized Feature Selection

Das Projekt wurde im Rahmen der Veranstaltung "Selbstorganisierende und adaptive Systeme"
an der Universität Duisburg-Essen entwickelt. Es stellt einen Webservice für das finden
von optimalen Konfigurationen von Feature Modellen, basierend auf dem 
[Regulator Algorithmus](http://link.springer.com/article/10.1007/s00500-015-1624-6) bereit.

Auf der folgenden Seite kann der Service ohne Installation online getestet werden: [Demo](http://docs.optimizedfeatureselection.apiary.io/)

Um den Service lokal zu starten benötigen Sie die JAR-Datei aus dem Ordner "bin". Diese können Sie mit dem Befehl "java -jar optfeatureselection-0.0.1-SNAPSHOT.jar" ausführen. Der Service ist danach unter "localhost:8081/optimize" über POST erreichbar. Eine Beispielanfrage befindet sich ebenfalls in dieser [Demo](http://docs.optimizedfeatureselection.apiary.io/).
