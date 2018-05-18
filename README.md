# TeamQuattro


Notizen:




Probleme:


Test:
-jeweils rücktransformierte rgb werte
	-> Vergleichen ob werte mit Original übereinstimmen
- Signalenergien speichern
- Nur bestimmte Bildabmessungen verwenden (gerade?!)

- Ungerade blockgröße vermeiden (vielfaches von .. ?!)
- 3 Bilder einlesen
- 3 Bilder Rücktransformation
	- Reverse Trans


Gelöst:
- Zusammensetzen der Bilder nach Encode
- Encode in jeweilige Anzahl der Blockmenge
- 3 Bilder bei Decode 
- Nicht quadratische Bilder -> NullPointerEx


