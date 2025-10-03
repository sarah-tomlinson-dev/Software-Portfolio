#include "Products.h"

#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <stdexcept>

using namespace std;

	//function to open and read file, creating the map
	map<string, int> Products::OpenAndReadFile(string file) {
		ifstream productFile;
		productFile.open(file);

		if (!productFile.is_open()) {
			cout << "Error Opening File 'ProductList.txt'" << endl;
		}

		map<string, int> productMap; // declare map
		string currWord;
		while (!productFile.eof()) { // loop through file and create map
			productFile >> currWord;
			if (!productFile.fail()) {
				++productMap[currWord];
			}
			productFile >> currWord;
		}

		productFile.close(); // close file
		return productMap; // return map
	}

	// function to output product counts to data file
	void Products::WriteDataFile(string file, map<string, int> productMap) {
		ofstream outputFile;
		outputFile.open(file);

		if (!outputFile.is_open()) {
			cout << "Error Opening File";
		}

		map<string, int> ::iterator i; // declare iterator

		// loop through map writing to output data file
		for (i = productMap.begin(); i != productMap.end(); i++) {
			outputFile << i->first << " " << i->second << endl;
		}
		outputFile.close();
	}

	// function to output a character n number of times for menu formatting
	string Products::nCharString(size_t n, char c) {
		string resultString;
		for (int i = 0; i < n; i++) { // creates string of length 'n'
			resultString = resultString + c;
		}
		return resultString;
	}

	// function to print menu
	void Products::PrintMenu() {
		cout << endl;
		cout << nCharString(12, ' ') << "MAIN MENU" << endl;
		cout << "1. Get Count of Specific Item" << endl;
		cout << "2. Print List of All Items (ex. Peas 4)" << endl;
		cout << "3. Print List of All Items (ex. Peas ****)" << endl;
		cout << "4. Exit Program" << endl;
	}

	//function to get input from user and output selected option
	void Products::MainMenu(map<string, int> productMap) {
		int userChoice = 0;
		string userWord;
		map<string, int> ::iterator i;

		while (userChoice != 4) {
			PrintMenu(); // prints menu to user before asking for input
			cout << endl << "Enter Menu Choice as 1, 2, 3, or 4: ";
			cin.exceptions(ios_base::failbit);
			try { // exception handling
				cin >> userChoice;
				if (userChoice < 1 || userChoice > 4) {
					throw runtime_error("That is not a valid Menu Option.\n");
				}
			}
			catch (ios_base::failure const& ex) {
				cout << "WARNING: INVALID INPUT: " << ex.what() << endl;
				cin.clear(); // clear cin to be used again
				cin.ignore(260, '\n'); // ignore line
				userChoice = 0; // reset user choice to a neutral value
			}
			catch (runtime_error e) { // output error message
				cout << "Warning: " << e.what();
			}
			switch (userChoice) {
			case 1:
				cout << "Enter Product Name to Count(ex. 'Peas', 'Spinach'): ";
				cin >> userWord;
				cout << endl;
				if (productMap.count(userWord) == 1) { // if map contains user entered Key
					cout << "Counting " << userWord << "..." << endl;
					cout << nCharString(6, ' '); // indent line for readability
					cout << userWord << " " << productMap.at(userWord) << endl;
				}
				else {
					cout << userWord << " does not appear in the product list." << endl;
					cout << "Please try again." << endl;
				}
				break;
			case 2:
				cout << endl << "Values for All Products" << endl;
				cout << nCharString(24, '-') << endl; // formatting for user readability
				for (i = productMap.begin(); i != productMap.end(); i++) { // loop through map printing each key and value
					cout << nCharString(6, ' ');
					cout << i->first << " " << i->second << endl;
				}
				break;
			case 3:
				cout << endl << "Histogram for All Products" << endl;
				cout << nCharString(26, '-') << endl;
				for (i = productMap.begin(); i != productMap.end(); i++) { // loop through map printing each key and value as symbols
					cout << nCharString(6, ' ');
					cout << i->first << " " << nCharString(i->second, '*') << endl;
				}
				break;
			case 4:
				cout << "Exiting Program" << endl;
				break;
			}
		}
	}