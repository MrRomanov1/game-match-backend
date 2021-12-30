## GameMatch
Backend repository of an application, which allows user to match a game to his/her preferences.
### Requirements
- Spring Framework
- SpringBoot
### Dependencies
There are a number of third-party dependencies used in the project. Browse the Maven pom.xml file for details of libraries and versions used.
### Building the project
To build this project locally you will need:
- Java JDK 11 or higher
- Maven 3.1.1 or higher
- PostgreSQL 11 or higher
- Git 
  Clone the project and use Maven to build the server
  ```sh
  $ mvn clean install
  ```
### Live preview
https://game-match.piotr-romanczak.pl/
### Features
- [x] user can see most popular games on the platform
- [x] user can see coming soon games
- [x] user can see single game record page
- [x] user can see different lists of games by preferences
- [x] user can chose his/her preferences when it comes to matching a game
- [x] user can see a list of games matched to his/her preferences
### Future plans
- [ ] user can register/login
- [ ] user can be ranked
- [ ] user can rate every single game
- [ ] user can hide disliked games
- [ ] user can view history of previous matches
- [ ] user can add games to favourites and improve their future match
- [ ] user can edit or add new games to database which will have to be approved by higher ranked user
- [ ] users can match with other users to play with each other
- [ ] users can create their own lists of games
- [ ] users can communicate through platform