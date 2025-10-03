//============================================================================
// Name        : Final.cpp
// Author      : Sarah Tomlinson 12/15/2024
// Version     : 1.0
//============================================================================

#include <iostream>
#include <fstream>
#include <string>
#include <time.h>
#include <Windows.h>
#include <vector>

using namespace std;

const int GLOBAL_SLEEP_TIME = 5000;//default time for sleep

/*
Struct to hold the course information
*/
struct Course {

    string courseId;
    string courseName;
    vector<string> preList;
};

class BinarySearchTree {

private:
    // Define structures to hold courses
    struct Node {
        Course course;
        Node* right;
        Node* left;

        // default constructor
        Node() {
            left = nullptr;
            right = nullptr;
        }

        // initialize with a course
        Node(Course aCourse) {
            course = aCourse;
            left = nullptr;
            right = nullptr;
        }
    };

    Node* root;
    void inOrder(Node* node);
    int size = 0;

public:
    BinarySearchTree();
    void InOrder();
    void Insert(Course aCourse);
    Course Search(string courseId);
    int Size();
};

/**
 * Default constructor
 */
BinarySearchTree::BinarySearchTree() {
    this->root = nullptr;
}

/**
 * Traverse the tree in order
 */
void BinarySearchTree::InOrder() {
    inOrder(root);
}

/**
 * Insert a course
 */
void BinarySearchTree::Insert(Course aCourse) {

    // set current node to root
    Node* currentNode = root;

    // if root is null
    if (root == NULL) {

        root = new Node(aCourse);

    }
    // else root is not null
    else {
        // while current node is not null
        while (currentNode != NULL) {

            if (aCourse.courseId < currentNode->course.courseId) {

                // if left node is null
                if (currentNode->left == nullptr) {

                    currentNode->left = new Node(aCourse);
                    currentNode = NULL;
                }
                else {

                    currentNode = currentNode->left;
                }
            }
            else {
                // if right node is null
                if (currentNode->right == nullptr) {

                    currentNode->right = new Node(aCourse);
                    currentNode = NULL;
                }
                else {

                    currentNode = currentNode->right;
                }
            }
        }
    }
    size++;
}

/**
 * Search for a course
 */
Course BinarySearchTree::Search(string courseId) {

    Course aCourse;

    Node* currentNode = root;

    // while current node is not null
    while (currentNode != NULL) {

        // if current node matches courseId
        if (currentNode->course.courseId == courseId) {

            return currentNode->course;
        }
        else if (courseId < currentNode->course.courseId) {
            // if courseid is less than current node, traverse left
            currentNode = currentNode->left;
        }
        else {
            currentNode = currentNode->right;
        }
    }

    return aCourse;
}

void BinarySearchTree::inOrder(Node* node) {

    if (node == NULL) {

        return;
    }
    inOrder(node->left);

    //print the node
    cout << node->course.courseId << ", " << node->course.courseName << endl;

    inOrder(node->right);
}

int BinarySearchTree::Size() {

    return size;
}

/*
from www.codegrepper.com/code-examples/cpp/c%2B%2B+split+string+by+delimiter
*/
vector<string> Split(string lineFeed) {

    char delim = ',';

    lineFeed += delim; //includes a delimiter at the end so last word is also read
    vector<string> lineTokens;
    string temp = "";
    for (int i = 0; i < lineFeed.length(); i++)
    {
        if (lineFeed[i] == delim)
        {
            lineTokens.push_back(temp); //store words in token vector
            temp = "";
            i++;
        }
        temp += lineFeed[i];
    }
    return lineTokens;
}

/*
function to load courses
*/
void loadCourses(string csvPath, BinarySearchTree* courseList) {
    // Create a data structure and add to the collection of courses 

    ifstream inFS; //ifstream to read file
    string line; //line feed 
    vector<string> stringTokens;

    inFS.open(csvPath); // open file

    if (!inFS.is_open()) {// small error handler
        cout << "Could not open file. Please check inputs. " << endl;
        return;
    }

    while (!inFS.eof()) {

        Course aCourse;// create a new struct for each "line"

        getline(inFS, line);
        stringTokens = Split(line); // split the line into tokens via the delimiter

        if (stringTokens.size() < 2) { // if there aren't 2 tokens the line is misformatted

            cout << "\nError. Skipping line." << endl;
        }

        else {

            aCourse.courseId = stringTokens.at(0);
            aCourse.courseName = stringTokens.at(1);

            for (unsigned int i = 2; i < stringTokens.size(); i++) {

                aCourse.preList.push_back(stringTokens.at(i));
            }

            // push this course to the end
            courseList->Insert(aCourse);
        }
    }

    inFS.close(); // close the file
}

/*
Course passed in
Prints:
courseId, courseName
Prereq list (if none print "No prereq")
*/
void displayCourse(Course aCourse) {

    cout << aCourse.courseId << ", " << aCourse.courseName << endl;
    cout << "Prerequisites: ";

    if (aCourse.preList.empty()) {// if the lsit is empty then there are no prereq

        cout << "none" << endl;
    }
    else {

        for (unsigned int i = 0; i < aCourse.preList.size(); i++) {

            cout << aCourse.preList.at(i);

            if (aCourse.preList.size() > 1 && i < aCourse.preList.size() - 1) {// put a comma for any elements greater than 1

                cout << ", ";
            }
        }
    }

    cout << endl;
}

/*
Force the case of any string to uppercase
Pass the string by reference and change the case of any alpha to upper
*/
void convertCase(string& toConvert) {

    for (unsigned int i = 0; i < toConvert.length(); i++) {

        if (isalpha(toConvert[i])) {

            toConvert[i] = toupper(toConvert[i]);
        }
    }
}

int main(int argc, char* argv[]) {

    // process command line arguments
    string csvPath, aCourseKey;

    switch (argc) {
    case 2:
        csvPath = argv[1];
        break;
    case 3:
        csvPath = argv[1];
        aCourseKey = argv[2];
        break;
    default:
        csvPath = "CS 300 ABCU_Advising_Program_Input.csv";
    }

    // Define a table to hold all the courses
    BinarySearchTree* courseList = new BinarySearchTree();

    Course course;
    bool goodInput;
    int choice = 0;

    while (choice != 9) {
        cout << "Menu:" << endl;
        cout << "  1. Load Data Structure" << endl;
        cout << "  2. Print Course List" << endl;
        cout << "  3. Print Course" << endl;
        cout << "  9. Exit" << endl;
        cout << "Enter choice: ";

        aCourseKey = ""; // clear the string        
        string anyKey = " "; // clear the string
        choice = 0; // clear the choice

        try {
            cin >> choice;

            if ((choice > 0 && choice < 5) || (choice == 9)) {// limit the user menu inputs to good values
                goodInput = true;
            }
            else {// throw error for catch
                goodInput = false;
                throw 1;
            }

            switch (choice) {
            case 1:

                // Complete the method call to load the courses
                loadCourses(csvPath, courseList);
                cout << courseList->Size() << " courses read" << endl;

                Sleep(GLOBAL_SLEEP_TIME);

                break;

            case 2:
                courseList->InOrder();

                cout << "\nEnter \'y\' to continue..." << endl;

                cin >> anyKey;

                break;

            case 3:

                cout << "\nWhat course do you want to know about? " << endl;
                cin >> aCourseKey;

                convertCase(aCourseKey); // convert the case of user input

                course = courseList->Search(aCourseKey);

                if (!course.courseId.empty()) {
                    displayCourse(course);
                }
                else {
                    cout << "\nCourse ID " << aCourseKey << " not found." << endl;
                }

                Sleep(GLOBAL_SLEEP_TIME);

                break;

            case 9:
                exit;
                break;

            default:

                throw 2;
            }
        }

        catch (int err) {

            std::cout << "\nThat is not a valid option." << endl;
            Sleep(GLOBAL_SLEEP_TIME);
        }

        // need to clear the cin operator of extra input, e.g., 9 9, or any errors generated by bad input, e.g., 'a'
        cin.clear();
        cin.ignore();

        // clear the consolse to redraw a fresh menu
        system("cls");
    }

    cout << "Thank you for using the course planner!" << endl;

    Sleep(GLOBAL_SLEEP_TIME);

    return 0;
}