# CS410-Final-Project


## Setup

We used intellij for this project. Project was made with SDK version 11. Edit configurations

Set run on: username@onyx.boisestate.edu:22
Build and run: java 11
Main class: Project

look through onyx from home directory: /opt/mysql/
fetch jar: mysql-connector-java-8.0.27.jar
Add jar to dependencies (ctrl-shift-alt+S)

For Databases, we will use our own remote sandboxes on Onyx.
Add MySQL>SSH> Use SSH tunnel > username@boisestate.edu:22 w/ password
MySQL workbench 8.0 CE (community edition)

Steps For Connectivity Between Java Program and Database
- Import the Packages
- Load the drivers using the forName() method 
- Register the drivers using DriverManager 
- Establish a connection using the Connection class object
- Create a statement
- Execute the query
- CLose the connections


## To Compile
javac Project.java
java Project


## To Run
`java Project, <command>, <arguments>`



## List of Commands
`new-class, <course>, <term>, <section>, <name>`
`list-classes`
`select-class, <course>, <term>, <section>`
`select-class, <class_course>`
`select-class, <class_course>, <class_term>`
`show-class`
`show-categories`
`add-category, <name>, <weight>`
`show-assignment`
`add-assignment, <name>, <category>, <description>, <points>`
`add-student, <username>, <studentid>, <last>, <first>`
`add-student, <username>`
`show-students`
`show-students, <string>`
`grade, <assignmentName>, <username>, <grade>`
`student-grades, <username>`
`gradebook`
