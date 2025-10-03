# 📦 CS-210: Inventory System

## 📖 Project Overview
This project was developed as part of CS-210 *Programming Languages* and focuses on building an **Inventory Management System** in C++.  
The program reads product data from external files, processes inventory transactions, and outputs frequency and usage statistics in a clean, user-friendly format.  

The project emphasizes **modularity** (separating logic across multiple files), **file I/O operations**, and applying fundamental **object-oriented programming** principles.

---

## 🛠 What I Did
- Designed and implemented a modular C++ program split across multiple source/header files.
- Integrated **file input/output** to read product lists and frequency data for inventory tracking.
- Created robust functions for:
  - Displaying product frequency reports.  
  - Managing inventory records.  
  - Providing a user-friendly console interface.  
- Documented the program design, implementation, and testing process in a comprehensive report.

---

## 💡 Key Skills Demonstrated
- C++ Programming (OOP, modular design, header files).  
- File Input/Output (text and data file handling).  
- Data validation and structured error handling.  
- Software design documentation and testing practices.

---

## 📂 Project Structure
- [`src/Main.cpp`](src/Main.cpp) – Main program entry point.  
- [`src/Products.cpp`](src/Products.cpp) – Inventory logic implementation.  
- [`src/Products.h`](src/Products.h) – Header file for inventory class definitions.  
- [`data/ProductList.txt`](data/ProductList.txt) – Input file containing available products.  
- [`data/frequency.dat`](data/frequency.dat) – Frequency data output file.  
- [`docs/CS-210-Project_Documentation.pdf`](docs/CS-210-Project_Documentation.pdf) – Project documentation detailing design decisions and testing.  

---

## 🚀 How to Run
1. Compile the program:  
   ```bash
   g++ src/Main.cpp src/Products.cpp -o InventorySystem

2. Run the executable:   
./InventorySystem

3. The program will read from ProductList.txt and generate frequency data into frequency.dat.
