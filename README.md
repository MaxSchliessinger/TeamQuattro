# TeamQuattro


Notizen:
- Signalenergie des gesamten Transformierten Bildes



Probleme:


Test:
- Richtiger RCT für Rücktransformation
- Signalenergien für Decode speichern
- Nur bestimmte Bildabmessungen verwenden (gerade?!)
- Ungerade blockgröße vermeiden (vielfaches von .. ?!)



Gelöst:
- Zusammensetzen der Bilder nach Encode
- Encode in jeweilige Anzahl der Blockmenge
- 3 Bilder bei Decode 
- Nicht quadratische Bilder -> NullPointerEx
- 3 Bilder einlesen
- 3 Bilder Rücktransformation
	- Reverse Trans
- jeweils rücktransformierte rgb werte
	-> Vergleichen ob werte mit Original übereinstimmen

