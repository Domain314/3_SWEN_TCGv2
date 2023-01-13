# TradingCardGame314 v2

## How to run

### 0. Prerequisites (if you want to use "docker compose up", skip this step)
    - start empty postgres docker container on port 5432

### 1. Run application (3 ways to get it running)
    -  Run .jar in out/artifacts/TCGv2/TCGv2.jar
    -  start in IDE
    -  `$ docker compose up`

### 2. Test Server
    - Use curl script (either "short testing script" or "original curl script") OR
    - Write curl commands manually to "localhost:10002/xxxxx" where xxxxxx is the route

### 3. Giving me a great evaluation :+1:

### 4. Profit.

----------------------------------------------


## Technical steps:

### Functionality:

    - Server spins up and defines Routes.
    - Each Route ("/users", "/deck",..) has a Service, which splits Requests by their method-type and passes them to the right Controller
    - Controller make all kind of checks (like authentication) and prepares data, which will be sent to a Repository
    - A Repository makes the final sql requests to the postgres db and sends the response back to the Controller.
    - Controller passes Data further back to the Service.
    - Service passes response further back to the Router => Client Socket


### Features:

Some neat features:

0. User login and Player Data are sperated in database. If a player will want to delete his account, he will only delete his login-data from the DB, but not his cards, elo ranking, player-name,..
1. More Elements! in util.Constants.java you can find the element-matrix, which decides how high the multiplicator will be, when element x attacks element y.
2. Card Description. Altough no card has a meaningful description, they COULD have. Space is reserved in classes and db.
3. Elo score like a pro! in util.Constants.java you can find the calculation for the elo change after a battle.
4. Win/lose ratio, when getting player stats
5. Random Package generation. If the package creation request has no body, a random package with random Cards will be generated.
6. Random Deck creation. If you add "?random=true" to localhost:10002/deck?random=true, the deck card will be choosen randomly from the stack.

During the project I learned a lot about inheritance, http communication, dockerization, unit tests and Java overall. This was my first Java Project and I loved to write in Java.
I hope to work more with Java in the future. :)

I also learned a lot about dockerization and decided to turn my java server into a docker container too, add it together with the postgres db container and make my first docker compose file!
This led to my first docker hub repository, which is public, so that everybody with the docker-compose file can download my server and spin it up!

    + Persitency in the docker compose file, with volumes
    + docker compose health check

----------------------------------------------


## Tests chosen:

I wanted to test the building blocks of the application and tried to diverse the test-context.
Some tests are testing base classes, some tests are testing the DB and some are testing the game (/business) logic.
Especially challanging was to write tests for the game logic, as a lot of calucaltions are dependent on random numbers. For example the drawn card for a battle is random, thus it's impossible to know how many rounds a game will have. Except when all cards are the same of each deck. Which was the solution, and after i figured it out, the tests were done in minutes. :)

This was the first time I made unit tests and at first I have completly underrated them. But with time and after some youtube videos on the topic testing (in particular: https://www.youtube.com/watch?v=Jv2uxzhPFl4 ) it has opend my eyes and I will write way more tests in the future!

LAST MINUTE EDIT: A few new tests (Controller Tests) were added, but are quite irrelevant, because they are getting checked, by the curl script. My IntelliJ told me, that even before the Controller tests, the tests got 100% Coverage of the base classes. I guess that's enough. :D

----------------------------------------------


## Time spent:

```
h - DD.MM.YYYY - Info
----------------------------------------------
4 - 16.09.2022 - Base structure for game logic
8 - 17.09.2022 - Base structure for game logic
8 - 18.09.2022 - Base structure for game logic.
^-- First confrontation with Java, thus learned a lot about Java syntax: class - abstract class - interface, polymorphism - inheritance, data structures in Java --^

8 - 05.01.2023 - integretaion of http Server, setup of postgres-db in a docker container
8 - 06.01.2023 - building up routes, get/post/put requests, writing first test for this project
8 - 07.01.2023 - last routes, integrating game-logic, bugfixes development
7 - 08.01.2023 - finalizing development, last tests, minor bugfixes, cleaning up
2 - 09.01.2023 - BONUS: writing docker compose for simultaneous startup of db and java server (which is in a container too)
2 - 12.01.2023 - writing documentation, prepare zip for upload
2 - 13.01.2023 - docker compose bugfix, few more tests
----------------------------------------------
57 h
```
----------------------------------------------


## The git history may not be complete, as I moved it once to a new repostiory.

    - github: https://github.com/Domain314/SWEN_TCGv2
    - docker: https://hub.docker.com/repository/docker/domain314/tcgv2


