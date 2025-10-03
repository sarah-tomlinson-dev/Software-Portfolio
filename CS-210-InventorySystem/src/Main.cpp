//Sarah Tomlinson, CS210, Project 3
// 2/22/2024

#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <stdexcept>
#include "Products.h"

using namespace std;

int main() {
	Products productTracking; // create class object
	string fileName = "ProductList.txt";

	cout << "    Corner Grocer Item Tracking" << endl;

	productTracking.WriteDataFile("frequency.dat", productTracking.OpenAndReadFile(fileName));
	productTracking.MainMenu(productTracking.OpenAndReadFile(fileName));


	return 0;
}