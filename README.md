# gryttr-web-service

gryttr-web-service is a RESTful web service built with Spring Boot and MySQL.

## Setup

Clone the project
```
git clone git@github.com:uyth/gryttr-web-service.git
```

Go to the root and enter mysql
```
cd gryttr-web-service
mysql -u root -p
```

Setup the database:
```
mysql > CREATE USER 'gryttr'@'localhost' IDENTIFIED BY 'gryttr';
mysql > CREATE DATABASE gryttrdb;
mysql > GRANT ALL PRIVILEGES ON gryttrdb.* TO 'gryttr'@'localhost';
mysql > USE gryttrdb;
mysql > SOURCE db/db.sql;
mysql > exit;
```

### Run the Application

*Alternative 1*

Run the command
```
./gradlew run
```

*Alternative 2*

Run the project from your IDEA.

The application should be available at [http://localhost:8080/]().

## API

The web service provides APIs for collections and boulders.

### Boulders

#### Get a list of all boulders: `GET /api/boulders/`

Example response:
```
[
    {
        "id":1,
        "name":"First boulder",
        "grade":"5+",
        "latitude":0.0,
        "longitude":0.0,
        "collections_id":1
    },
    {
        "id":2,
        "name":"Second boulder",
        "grade":"6C+",
        "latitude":12.0,
        "longitude":12.0,
        "collections_id":1
    },
    ...
]
```

#### Create a new boulder: `POST /api/boulders/`
Example payload:
```
{
    "name": "Boulder name",
    "grade": "5+",
    "latitude": 0.0,
    "longitude": 0.0,
    "collections_id": 2
}
```

Example response:
```
{
    "id": 30,
    "name": "Boulder name",
    "grade": "5+",
    "latitude": 0.0,
    "longitude": 0.0,
    "collections_id": 2
}
```

#### Get a specific boulder by ID: `GET /api/boulders/{id}`

Example response:
```
{
    "id": 30,
    "name": "Boulder name",
    "grade": "5+",
    "latitude": 0.0,
    "longitude": 0.0,
    "collections_id": 2
}
```
### Collections

#### Get a list of all collections: `GET /api/collections`

Example response:
```
[
    {
        "id": 1,
        "name": "Skullerud",
        "boulders": [
            {
                "id": 1,
                "name": "Første bulder",
                "grade": "5+",
                "latitude": 0.0,
                "longitude": 0.0,
                "collections_id": 1
            },
            ...
        ]
    },
    {
        "id": 2,
        "name": "Bøler",
        "boulders": [...]
    },
    ...
]
```

#### Create a new collection: `POST /api/collections`

Example payload:
```
{
    "name": "New collection"
}
```

Example response:
```
{
    "id": 31,
    "name": "New collection",
    "boulders": []
}
```


#### Get a specific collection by ID: `GET /api/collections/{id}`

Example response:
```
{
    "id": 30,
    "name": "Collection",
    "boulders": [...]
}
```


##### Get a specific collection by ID and sorted boulders
Current available sorting methods are `name` and `grade`.

`GET /api/collections/{id}?sort=name`

`GET /api/collections/{id}?sort=grade`

##### Get a specific collection by ID and filtered boulders
Available filtering methods are `mingrade` and `maxgrade`.

Grade are on the [Fontainbleau format](https://en.wikipedia.org/wiki/Grade_(bouldering)#Fontainebleau_grades), eg. 7A+, starting from 3.

`GET /api/collections/{id}?maxgrade={grade}`

`GET /api/collections/{id}?mingrade={grade}`

`GET /api/collections/{id}?mingrade={grade1}&maxgrade={grade2}`

