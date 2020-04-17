# System do zarządzania zasobami

## Opis systemu
System pozwala na skupienie w jednym miejscu wszsytkich zasobów do kursów studenckich. Takie zasoby student może przeglądać
swobodnie za pomocą prostej wyszkiwarki. Zasobammi mogą być pliki ale i liniki do zasobów na innych serwerach(np. Wikipedia).

Administrator może tworzyć użytkowników(studentów lub pracowników), tworzyć kursy i klasy(każdy kurs
może mieć wiele klas) oraz przypisywać studentów do klas.

Prowadzący kurs może dodać do kursu zasoby i pogrupować je dla ułatwienia dostępu.

Student może przejrzeć zasoby związane z kursami i klasam do których jest zapisany.

### Kurs a klasa

Kurs to ogólny przedmiot prowadzony dla studentów. Klasa to prowadzone jedynie w jednym semestrze zajęcia z pewnego
kursu.

Osoby zapisne na klasę pewnego kursu mają dostęp do wszyskich zaosbów przpisanych do ich klasy ale i również do zasobów przypisanych bezpośrednio do kursu. Osoby zapisane do jednej klasy pewnego kursu nie mają dostępu do zasobów przypisanych do innej klasy tego samego kursu

## Budowanie bazy
 
Baza danych PostgreSQL może zostać zbudoana ze skryptu w pliku script.sql

## Uruchamianie

Po uruchomieniu na porcie 8080 powinna uruchomić się aplikacja. Zakładając,
że skrypt do budowania bazy został wykonany powinno dać się zalogować użytkownikiem:
 - login: admin@pw.edu.pl
 - hasło: haslo123456
