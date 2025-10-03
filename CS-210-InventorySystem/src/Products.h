#pragma once
#ifndef Products_H
#define Products_H

#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <stdexcept>

using namespace std;

class Products {

private:
	map<string, int> products; // product list

public:

	//function to open and read file, creating the map
	map<string, int> OpenAndReadFile(string file);

	// function to output product counts to data file
	void WriteDataFile(string file, map<string,int> productMap);

	// function to output a character n number of times for menu formatting
	string nCharString(size_t n, char c);

	// function to print menu
	void PrintMenu();

	//function to get input from user and output selected option
	void MainMenu(map<string, int> productMap);

};

#endif