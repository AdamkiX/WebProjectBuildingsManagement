# Uruchamianie

## Uruchamianie kontenerów

### Przed uruchomieniem
Żeby API zbudowało się w kontenerze to plik `mvnw` musi mieć ustawiony koniec linii jako `LF`. W IntelliJ prawy doly róg kliknąć `CRLF` i zmienić na `LF - unix and macOs (\n)`.

## Uruchamianie kontenerów
Żeby uruchomić wszystkie kontenery na raz i je zbudować należy użyć polecenia:
```bash
docker compose up -d
```
lub można je uruchamiać pojedyńczo:
```bash
docker compose up -d db
docker compose up -d spring-boot-api
```

## Przebudowywanie kontenerów
Żeby wprowadzone zmiany zaszły w kontenerze to trzeba przebudowac kontener.
Żeby przebudować kontener z API należy użyć polecenia:
```bash
docker compose up -d --build spring-boot-api
```

# Konfiguracje
## Baza danych
- Baza jest dostęnpna pod adresem `localhost:13306`. 
- Plik `db/init.sql` jest uruchamiany przy pierwszym starcie kontenera. W nim moża wpisać DDL, który ma tworzyć struktórę bazy danych.
- Wszystkie dane z bazy danych są przechowywane w folderze `db/mysql_data`.
## API
- API jest dostępne pod adresem `localhost:8080`.

# Backendowe info
## Logowanie się
Aby przetestować logowanie się:
- skorzystać z postmana 
- wybrać metodę POST
- wpisać adres `localhost:8080/auth/signin`
- następnie w Body wpisać:
```
{
    "username": "<username>",
    "password": "<password>"
}
```
- i wcisnąć Send. Powinien wygenerować się token, który umożliwia przeglądanie podstron dla typu użytkownika na, który się zalogowano

## Testowanie podstron (WIP)
DISCLAIMER: Jeżeli coś nie działa, to znaczy, że:
- nie jest się zalogowanym,
- nie przekopiowano Tokena do Auth Type Bearer Token (POSTMAN),
- użytkownik nie ma uprawnień do endpointu,
- bug z mojej strony

Aby przetestować podstrony:
- wpisać adres `/tenants`, `/managers` lub `/users` (na tą podstronę można się dostać zarówno z tenanta jak i managera - będzie to zmieniane)
- są 4 metody na każdą podstronę  GET, POST, PUT i DELETE
### /tenants
- getowanie najemcy po id_tenant: `GET /tenants/{id}`
- update danych najemcy po id_tenant: `PUT /tenants/{id}`
- dodawanie najemcy: `POST /tenants`
- usuwanie najemcy po id_tenant: `DELETE /tenants/{id}`
### /managers
- getowanie wszystkich zarządców `GET /managers`
- update danych zarządcy po id_manager: `PUT /managers/{id}`
- dodawanie zarządcy: `POST /managers`
- usuwanie zarządcy po id_manager: `DELETE /managers/{id}`
### /users
- getowanie użytkowników: `GET /users`
- update danych użytkownika po id_user: `PUT /users/{id}`
- dodawanie użytkownika: `POST /users`
- usuwanie użytkownika po id_user: `DELETE /users/{id}`
  
 
Dane JSON, do updatowania / dodawania tenanta, managera:
```
{
    "name": "<name>",
    "surname": "<surname>"
    "birthdate: "<birthdate>"
    "phone: "<phone>"
    "residence: "<residence>"
}
```
Dane JSON, do updatowania / dodawania użytkownika:
```
{
    "name": "<name>",
    "surname": "<surname>"
    "birthdate: "<birthdate>"
    "phone: "<phone>"
    "residence: "<residence>"
}
```
PÓKI CO JEST TO WIP WIĘC METODY NA PEWNO SIĘ ZMIENIĄ

DODAWANIE UŻYTKOWNIKA JAKO MANAGER
```
{ 
  "tenant": {    
    "name": "fffff",
    "surname": "Dffasdfasf",
    "birthdate": "1987-05-14",
    "phone": 12748520,
    "residence": "Abby Rd. 1, London" 
    }, 

  "user": { 
    "username": "susamogus"
    } 
}
```