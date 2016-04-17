# spring-http-game-server
A simple SPRING Restful service. 


# Usage

## Compile
```bash
./gradlew build
```

## Run
```bash
java -jar build/libs/gs-rest-service-0.1.0.jar
```

## View In Browser

Clients can find their ip relative to the server
```bash
http://localhost:8080/find
```

Conenct players to the game server
```bash
http://localhost:8080/connect
```

List players connected to the game server
```bash
http://localhost:8080/players
```

Return the player details for client making the request
```bash
http://localhost:8080/player
```

Return the requested player details
```bash
http://localhost:8080/player/{playerIP}
```

Send the move through a GET request
```bash
http://localhost:8080/sendmove/{playerMove}
```

Send the move through a POST request
```bash
http://localhost:8080/sendmove
```

