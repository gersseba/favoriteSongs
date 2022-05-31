# Favorite Songs
Favorite Song management application based on Java 11, Spring boot and REST.

### Technologies used
- Java 11
- Spring boot 2.6.5
- Docker 19.03.12
- Mockito core 4.4.0
- Junit 4.13.2
- H2 database 2.1.210
- Spring boot data jpa 2.6.6
- Lombok 1.18.22
- Maven 3.6.3

### Build application using Maven
- Execute
````
    mvn clean install
````
this will successfully build the favoritesongs application executable jar for the docker run.

- Execute
````
    mvn test
````
to run all unit and integration tests.

### Configuration & Installation
- Make sure docker daemon is running on your system. To verify, execute on the command line
```
    docker version 
```
this displays the details like version, OS/Arch etc.

- Navigate to the directory where Dockerfile is located and build docker image
```
    docker build -t favoritesongs .
````
the image is successfully built with the message
-"Successfully tagged favoritesongs:latest"
- to run the docker image execute
````
    docker run -p 8080:8080 favoritesongs
````
now the application is listening on port 8080

- to stop the application execute
````
    docker ps
````
this returns the relevant container id, and then execute
````
    docker stop container_id
````
the application is stopped and is no more available on port 8080

### API end points

````
1. POST- http://localhost:8080/favoritesongs/registerUser   
````
with request body in raw json format like
````
{
    "firstName":"Curt",
    "lastName":"Johns",
    "email":"Curt.Johns@yahoo.com",
    "password":"C123456YUCD"
}
````
must create a user record with username as email id.

````
2. POST- http://localhost:8080/favoritesongs/operations/login
````
with Authorization header values:
````
Username: {user_name} 
password: {password}
````
returns user record corresponding to the username.
````
3. DELETE- http://localhost:8080/favoritesongs/operations/deleteUser
````
with Authorization header values:
````
Username: {user_name} 
password: {password}
````
deletes user record corresponding to the username.

````
4. POST- http://localhost:8080/favoritesongs/addSong
````
with request body in raw json format like
````
{
    "title":"Out of time",
    "artist":"Weekend",
    "genre":"Pop",
    "releaseDate":"2022-04-31"
}
````
and with Authorization header values:
````
Username: {user_name} 
password: {password}
````
should add Song to the User record corresponding to the username.
````
5. GET- http://localhost:8080/favoritesongs/viewAllSongs
````
with Authorization header values:
````
Username: {user_name} 
password: {password}
````
should return all songs present in the User record corresponding to the username.

````
6. GET- http://localhost:8080/favoritesongs/viewSong/{song_title}
````
with Authorization header values:
````
Username: {user_name} 
password: {password}
````
returns song if it is present in the user record corresponding to the username.
In case the song is not found, HTTP status code 404 is returned.

### Author
Anand Chouksey

### Planned feature
- Maven build support using dockerfile.
- Use securityFilterChain rather than deprecated WebSecurityConfigurerAdapter.
