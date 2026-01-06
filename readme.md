# SmartmeterGui
## Einfache GUI für [Smartmeter](https://github.com/andreaskielkopf/Smartmeter)
Viele Smartmeter haben eine IR-Schnittstelle, über die je Abrechnungseinheit ein IR-Impuls ausgestrahlt wird. Wenn man die Pulse pro Minute mitzählt, erhält man ein direktes Maß für den Stromverbrauch in dieser Zeit. Das erledigt ein Lua-Projekt mit einem D1-Mini.

Der D1-Mini hat jedoch wenig Speicher und kann keine gute http-GUI dafür darstellen. Deswegen speichert er die gemessenen Daten nur lokal (reicht für je 1-2 Monate).

SmartmeterGui kann genutzt werden um diese Daten auszulesen und darzustellen.

## SmartmeterGui installieren
Das einzige was sie brauchen ist:
* java 25 (openjdk 25)
* SmartmeterGui.jar

Das Programm funktioniert unter Linux ebenso, wie unter Windows
### Herunterladen
#### wget
download nach `/usr/local/bin`
```
sudo wget https://github.com/andreaskielkopf/SmartmeterGui/raw/master/jar/SmartmeterGui.jar -O /usr/local/bin/SmartmeterGui.jar; 
```
Prüfsumme anzeigen:
```
sha256sum /usr/local/bin/SmartmeterGui.jar;
```
Rechte setzen:
```
sudo chmod -c a+x /usr/local/bin/SmartmeterGui.jar
```
oder alles auf einmal:
```
sudo wget https://github.com/andreaskielkopf/SmartmeterGui/raw/master/jar/SmartmeterGui.jar -O /usr/local/bin/SmartmeterGui.jar; sha256sum /usr/local/bin/SmartmeterGui.jar; sudo chmod -c a+x /usr/local/bin/SmartmeterGui.jar
```
#### Browser
[SmartmeterGui.jar](https://github.com/andreaskielkopf/SmartmeterGui/raw/master/jar/SmartmeterGui.jar)

Datei speichern im Downloadordner. Dann verschieben, wohin du willst.

#### Linux test
Gehe mit `cd` dahin wo du deine Daten vom Smartmeter zwischenspeichern möchtest.
```
java --version
java -jar /usr/local/bin/SmartmeterGui.jar
```
#### Windows test
Gehe mit `cd` dahin wo die Datei SmartmeterGui.jar liegt. Hier werden dann auch deine Daten vom Smartmeter zwischengespeichert.
```
java --version
java -jar SmartmeterGui.jar
```

## Benutzung
Das Programm ist im Moment unvollständig, aber nutzbar.

Reihenfolge:

* IP des Smartmeter eingeben
* [ping] muss nach dem Drücken grün werden
* [test] muss nach dem Drücken grün werden
* [load] = bisherige daten aus dem Dateisystem laden
* [smartmeter auslesen] = erfasste Messwerte vom Smartmeter dazuladen
* [save] = Daten zwischenspeichern

---

Der zweite Reiter dient zum Betrachten der Daten
* Tag [von] auswählen
* Scrollbalken nach unten bewegen bis die Daten sichtbar werden