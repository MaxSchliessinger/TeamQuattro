# TeamQuattro


Notizen:

-3 For schleifen xy inline + blockweise
-jeweils rücktransformierte rgb werte
	-> Vergleichen ob werte mit Original übereinstimmen


Probleme:
- Ungerade blockgröße vermeiden (vielfaches von .. ?!)
- 3 Bilder einlesen
- 3 Bilder Rücktransformation
	- Reverse Trans


Test:
- Signalenergien speichern
- Nur bestimmte Bildabmessungen verwenden (gerade?!)
- Zusammensetzen der Bilder nach Encode


Gelöst:
- Encode in jeweilige Anzahl der Blockmenge
- 3 Bilder bei Decode 
- Nicht quadratische Bilder -> NullPointerEx


