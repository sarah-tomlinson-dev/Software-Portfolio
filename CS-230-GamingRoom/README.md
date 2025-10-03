# 🎮 CS-230: Gaming Room App

## 📖 Project Overview
This project was developed for *The Gaming Room*, a client that wanted to expand their Android-based game **Draw It or Lose It** into a scalable, cross-platform web application. The goal was to design a system that could handle unique game/team/player instances, maintain performance, and prioritize user experience and security.

---

## 🛠 What I Did
- Designed and documented the system architecture, including UML diagrams and requirements analysis.  
- Implemented core features with **design patterns**:  
  - **Singleton** – ensures only one instance of the game service exists in memory.  
  - **Iterator** – enforces uniqueness of game, team, and player names.  
- Created a secure, efficient system structure that emphasized user-friendly error handling and constraints.  
- Recommended **Windows as the primary platform** for user familiarity and performance, while ensuring cross-platform capability through a web-based architecture.  

---

## 💡 Key Skills Demonstrated
- Software Engineering & Documentation  
- Design Patterns (Singleton, Iterator)  
- UML Diagrams & Requirements Analysis  
- User-Centered and Secure Design Principles  
- Maven Project Structure & Config Management  

---

![UML Diagram](docs/uml.png)

---

## 📂 Repo Contents

- [docs/CS230_Software_Design_Document.docx](./docs/CS230_Software_Design_Document.docx) — Full software design document for The Gaming Room project  
- [docs/uml.png](./docs/uml.png) — UML diagram of the system architecture  
- [src/](./src) — Source code for the Gaming Room web application  
  - [auth/](./src/auth) — Authentication and authorization logic  
  - [controller/](./src/controller) — REST controllers for game management  
  - [dao/](./src/dao) — Data access objects for persistence and game state  
  - [healthcheck/](./src/healthcheck) — System health check endpoints  
  - [representations/](./src/representations) — Data models and representations  
  - [GameAuthApplication.java](./src/GameAuthApplication.java) — Main entry point for the application  
  - [GameAuthConfiguration.java](./src/GameAuthConfiguration.java) — Configuration setup  
- [config.yml](./config.yml) — Application configuration file  
- [pom.xml](./pom.xml) — Maven project configuration  

---

## 🚀 How to Run
1. Build the project with Maven:
   ```bash
   mvn clean package
java -jar target/*-SNAPSHOT.jar server config.yml

curl http://localhost:8081/healthcheck
