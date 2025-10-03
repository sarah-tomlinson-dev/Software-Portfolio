# 📚 CS-300: Advising App – Course Planning System

## 📖 Project Overview
This project was developed for the **Computer Science department at ABCU** to support academic advising. The application loads courses and their prerequisites from a data file, allows students to view the full course list in alphanumeric order, and supports searching for a specific course with its prerequisites.

The goal was to design and implement an efficient advising tool by evaluating different data structures and applying the most effective solution for scalability and performance.

---

## 🛠 Implementation Details
- Explored and compared multiple data structures:  
  - **Vector** (sequential storage)  
  - **Hash Table** (constant-time lookups)  
  - **Binary Search Tree (BST)** (ordered storage and efficient traversal)  
- Implemented the final solution using a **Binary Search Tree** for its balance of search efficiency and ordered output.  
- Developed pseudocode and runtime analysis prior to implementation.  
- Program written in **C++** with focus on modularity, readability, and error handling.  

---

## 📂 Repo Contents

- [CS-300-AdvisingApp.cpp](./CS-300-AdvisingApp.cpp) — Final C++ implementation of the advising application  
- [CS-300-Pseudocode.pdf](./CS-300-Pseudocode.pdf) — Pseudocode design and runtime analysis

---

## 🚀 How to Run
1. Compile the program:
   ```bash
   g++ -o advising CS-300-AdvisingApp.cpp
./advising CS300_Advising_Input.csv
