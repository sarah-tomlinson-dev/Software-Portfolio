# 📚 CS-300: Advising App – Course Planning System

## 📖 Project Overview
This project was developed for the **Computer Science department at ABCU** to support academic advising.  
The application loads courses and their prerequisites from a CSV input file, then allows users to:

- Print the full list of courses in **alphanumeric order**  
- Search for a **specific course** and view its prerequisites  
- Exit the program  

The goal was to design an efficient advising tool by comparing multiple data structures and implementing the best choice for performance and scalability.

---

## 🛠 Implementation Details
- Explored and compared:  
  - **Vector** (sequential storage)  
  - **Hash Table** (fast lookups, no ordering)  
  - **Binary Search Tree (BST)** (efficient lookups + ordered output)  
- Final implementation uses a **Binary Search Tree (BST)** for scalability and ordered traversal.  
- Developed pseudocode and runtime analysis before writing the implementation.  
- Program written in **C++**, emphasizing modularity, readability, and error handling.  

---

## 📂 Repo Contents

- [data/CS300_Advising_Input.csv](./data/CS300_Advising_Input.csv) — Input dataset of courses and prerequisites  
- [src/CS-300-AdvisingApp.cpp](./src/CS-300-AdvisingApp.cpp) — Final C++ implementation of the advising system  
- [docs/CS-300-Pseudocode.pdf](./docs/CS-300-Pseudocode.pdf) — Pseudocode design and runtime analysis  

---

## 🚀 How to Run
1. Navigate into the repo directory.  
2. Compile the program:  
   ```bash
   g++ src/CS-300-AdvisingApp.cpp -o AdvisingApp
./AdvisingApp data/CS300_Advising_Input.csv
