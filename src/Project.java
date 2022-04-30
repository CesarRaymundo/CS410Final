import java.sql.*;
import java.util.Scanner;

public class Project {

	static Connection conn;
	public static int activatedClass;

	/**
	 * Creates a new class
	 * @param class_course Course number of the class
	 * @param class_name Name of the class
	 * @param class_term Term of the class
	 * @param class_section Section of the class
	 * @throws SQLException
	 */
	public static void createClass(String class_course, String class_name, String class_term, String class_section) throws SQLException {
	    PreparedStatement stmt;
		conn.setAutoCommit(false);

		try {
			try {
				stmt = conn.prepareStatement("insert into school.class(class_course, class_name, class_term, class_section) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, class_course);
				stmt.setString(2, class_name);
				stmt.setString(3, class_term);
				stmt.setString(4, class_section);
				stmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			conn.setAutoCommit(true);
			System.out.println("Class created: " + class_course +", " +class_name +", "+ class_term +", " +class_section);
		}
	}

	/**
	 * Lists all classes and the students in each class
	 * @throws SQLException
	 */
	public static void listClasses() throws SQLException {
		PreparedStatement stmt;

		try {
			//list classes with the number of students enrolled in each class
			stmt = conn.prepareStatement("select class_id, class_course, class_name, class_term, class_section, count(*) as num_students from class join enrollment on class.class_id = enroll.class_id group by class_id;");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt("class_id")+", "+ rs.getString("class_course") + ", " + rs.getString("class_name") + ", " + rs.getString("class_term") + ", " + rs.getString("class_section") + ", " + rs.getInt("num_students"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selects a class by class course
	 * @param class_course Course number of the class
	 * @throws SQLException
	 */
	public static void selectClass_1(String class_course) throws SQLException {
		PreparedStatement stmt;
		String latest_class_term = getLatestTerm(class_course);
		try {
			stmt = conn.prepareStatement("select * from class where class_course = ? and class_term = ?;");
			stmt.setString(1, class_course);
			stmt.setString(2, latest_class_term);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				activatedClass = rs.getInt("class_id");
				System.out.println(rs.getInt("class_id")+", "+ rs.getString("class_course") + ", " + rs.getString("class_name") + ", " + rs.getString("class_term") + ", " + rs.getString("class_section"));
				if (rs.next() == true) {
					System.out.println("There are multiple classes with the same course number. Please select the correct class.");
				}
			}
			else {
				System.out.println("There are no classes with the course number " + class_course);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selects a class by class_course and class_term
	 * @param class_course Course number of the class
	 * @param class_term Term of the class
	 * @throws SQLException
	 */
	public static void selectClass_2(String class_course, String class_term) {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("select * from class where class_course = ? and class_term = ?;");
			stmt.setString(1, class_course);
			stmt.setString(2, class_term);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				if(!rs.next()) {
					activatedClass = rs.getInt("class_id");
//					System.out.println(rs.getInt("class_id")+", "+ rs.getString("class_course") + ", " + rs.getString("class_name") + ", " + rs.getString("class_term") + ", " + rs.getString("class_section"));
				}
				else {
					System.out.println("Multiple sections of the same course found");
				}
			}
			else{
				System.out.println("There are no classes with the course number " + class_course + " and term " + class_term);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selects a class by class_course, class_term, and class_section
	 * @param class_course Course number of the class
	 * @param class_term Term of the class
	 * @param class_section Section of the class
	 * @throws SQLException
	 */
	public static void selectClass_3(String class_course, String class_term, String class_section) throws SQLException {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("select * from class where class_course = ? and class_term = ? and class_section = ?;");
			stmt.setString(1, class_course);
			stmt.setString(2, class_term);
			stmt.setString(3, class_section);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				activatedClass = rs.getInt("class_id");
				System.out.println(rs.getInt("class_id")+", "+ rs.getString("class_course") + ", " + rs.getString("class_name") + ", " + rs.getString("class_term") + ", " + rs.getString("class_section"));
			}
			else {
				System.out.println("There are no classes with the course number " + class_course + " and term " + class_term + " and section " + class_section);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the latest class term
	 * @param course_number Course number of the class
	 * @return Latest class term
	 * @throws SQLException
	 */
	public static String getLatestTerm(String course_number) throws SQLException {
	PreparedStatement stmt;
	String latest_class_term = "";
	try {
		stmt = conn.prepareStatement("select class_term from class where class_course = ? order by class_term desc limit 1;");
		stmt.setString(1, course_number);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			latest_class_term = rs.getString("class_term");

		}
	}catch (SQLException e) {
		e.printStackTrace();

	}
		return latest_class_term;
	}

	/**
	 * Shows the active class
	 * @throws SQLException
	 */
	public static void showClass(){
	PreparedStatement stmt;
	try {
		stmt = conn.prepareStatement("select * from class where class_id = ?;");
		stmt.setInt(1, activatedClass);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			System.out.println(rs.getInt("class_id")+", "+ rs.getString("class_course") + ", " + rs.getString("class_name") + ", " + rs.getString("class_term") + ", " + rs.getString("class_section"));
		}
	}catch (SQLException e) {
		e.printStackTrace();
	}

	}

	/**
	 * Add a category to the active class
	 * @param category_name Name of the category to be added to the class
	 * @param category_weight Weight of the category to be added to the class
	 * @throws SQLException
	 */
	public static void addCategory(String category_name, String category_weight) throws SQLException {
			PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("insert into category (category_name, category_weight, class_id) values (?, ?, ?);");
			stmt.setString(1, category_name);
			stmt.setString(2, category_weight);
			stmt.setInt(3, activatedClass);
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			System.out.println("Category added: " + category_name + ", " + category_weight);
		}
	}

	/**
	 * Show all categories in the active class
	 * @throws SQLException
	 */
	public static void showCategories() throws SQLException {
	PreparedStatement stmt;
	try {
		stmt = conn.prepareStatement("select * from category where class_id = ?;");
		stmt.setInt(1, activatedClass);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			System.out.println(rs.getInt("category_id")+", "+ rs.getString("category_name") + ", " + rs.getString("category_weight"));
		}else {
			System.out.println("There are no categories in this class");
		}

	}catch (SQLException e) {
		e.printStackTrace();
	}
	}

	/**
	 * Show all assignments in the active class
	 * @throws SQLException
	 */
	public static void showAssignment() throws SQLException {
		PreparedStatement stmt;
		//show all assignments in the active class grouped by category
		try {
			stmt = conn.prepareStatement("select * from assignment where class_id = ? group by category_id;");
			stmt.setInt(1, activatedClass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getInt("assignment_id")+", "+ rs.getString("assignment_name") + ", " + rs.getString("assignment_weight") + ", " + rs.getString("assignment_due_date") + ", " + rs.getString("assignment_description"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add an assignment to the active class
	 * @param name Name of the assignment to be added to the class
	 * @param Category Category of the assignment to be added to the class
	 * @param Description Description of the assignment to be added to the class
	 * @param points Points of the assignment to be added to the class
	 * @throws SQLException
	 */
	public static void addAssignment( String name, String Category, String Description, int points) throws SQLException {
//		add- assignment name Category Description points – add a new assignment
		PreparedStatement stmt;
		PreparedStatement stmt2;
		int category_id = 0;

		try {
			stmt = conn.prepareStatement("select category_id from category where category_name = ? and class_id = ?;");
			stmt.setString(1, Category);
			stmt.setInt(2, activatedClass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				category_id = rs.getInt("category_id");
			}

			stmt2 = conn.prepareStatement("insert into assignment (assign_name, assign_description, assign_value, category_id,class_id) values (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			stmt2.setString(1, name);
			stmt2.setString(2, Description);
			stmt2.setInt(3, points);
			stmt2.setInt(4, category_id);
			stmt2.setInt(5, activatedClass);
			stmt2.executeUpdate();

		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			System.out.println("Assignment added: " + name + ", " + Description + ", " + points);
		}

	}

	/**
	 * Add a new student to the database and enroll them in the active class
	 * @param user_name Name of the user to be added to the class
	 * @param student_id ID of the user to be added to the class
	 * @param last Name of the user to be added to the class
	 * @param first Name of the user to be added to the class
	 * @throws SQLException
	 */
	public static void addStudent_4(String user_name, int student_id, String last, String first) {
		//add-student username studentid Last First — adds a student and enrolls
		//them in the current class. If the student already exists, enroll them in the class; if the
		//name provided does not match their stored name, update the name but print a warning
		//that the name is being changed.
		PreparedStatement stmt;
		PreparedStatement stmt2;
		try {

			stmt = conn.prepareStatement("insert into student (user_name, student_id, first_name, last_name) values (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, user_name);
			stmt.setInt(2, student_id);
			stmt.setString(3, first);
			stmt.setString(4, last);
			stmt.executeUpdate();
			//enroll student in class
			stmt2 = conn.prepareStatement("insert into enroll (student_id, class_id) values (?, ?);", Statement.RETURN_GENERATED_KEYS);
			stmt2.setInt(1, student_id);
			stmt2.setInt(2, activatedClass);
			stmt2.executeUpdate();

		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			System.out.println("Student added to class: " + user_name + ", " + student_id + ", " + last + ", " + first);
		}

	}

	/**
	 * Enroll an already existing student in the active class
	 * @param user_name
	 */
	public static void addStudent_1(String user_name) {
		PreparedStatement stmt;
		PreparedStatement stmt1;
		PreparedStatement stmt2;
		String first_name_from_username = null;
		String last_name_from_username = null;
		try {
			stmt1 = conn.prepareStatement("select first_name from student where user_name = ?;");
			stmt1.setString(1, user_name);
			ResultSet rs = stmt1.executeQuery();
			if (rs.next()) {
				first_name_from_username = rs.getString("first_name");
			}
			stmt2 = conn.prepareStatement("select last_name from student where user_name = ?;");
			stmt2.setString(1, user_name);
			ResultSet rs2 = stmt2.executeQuery();
			if (rs2.next()) {
				last_name_from_username = rs2.getString("last_name");
			}
			stmt = conn.prepareStatement("" +
					"insert into student (user_name, first_name, last_name) value(?, ?, ?);");
			stmt.setString(1, user_name);
			stmt.setString(2, first_name_from_username);
			stmt.setString(3, last_name_from_username);

			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}


	}

	/**
	 * Shows all the students in the active class
	 *
	 * @throws SQLException
	 */
	public static void showStudents() throws SQLException {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("select * from student where class_id = ?;");
			stmt.setInt(1, activatedClass);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("user_name") + ", " + rs.getInt("student_id") + ", " + rs.getString("last_name") + ", " + rs.getString("first_name"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *This function maximized the use of LIKE query to search through select columns of student table
	 * Adds the latest match to the stmt setString.
	 * @param string
	 */
	public static void showStudentsString(String string) {
		//Show students with 'string' in their name or username (NOT case-sensitive)
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("select * from student where user_name like ? or first_name like ? or last_name like ?;");
			stmt.setString(1, "%" + string + "%");
			stmt.setString(2, "%" + string + "%");
			stmt.setString(3, "%" + string + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("user_name") + ", " + rs.getInt("student_id") + ", " + rs.getString("last_name") + ", " + rs.getString("first_name"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * We did not get a chance to get this far
	 * @param assignmentName
	 * @param userName
	 * @param grade
	 */
	public static void grade(String assignmentName, String userName, String grade) {
		//if student already has a grade for that assignment -- replace it
		//If number of points exceed assignment config cap, print a warning w/ number of points configed
	}

	/**
	 * We did not get a chance to get this far
	 * @param username
	 */
	public static void studentGrades(String username) {
		//group by category
		//subtotal for each category
		//overall grade in class

	}

	/**
	 * We did not get a chance to get this far
	 */
	public static void gradeBook(){
		//students with their total grades
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("" +
					"select *\n" +
					"from grade\n" +
					"join enroll on grade.student_id = enroll.student_id\n" +
					"where enroll.class_id = 1;");//TODO
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * After cutting up the string array into multiple arguments of 1 or more
	 * We can begin sending the user to right function
	 * @param input
	 * @throws SQLException
	 */
	public static void listOfFunctions(String [] input) throws SQLException {
		switch (input[0]) {  //case depend on first argument
			case "new-class":
				String class_course = input[1];
				String class_term = input[2];
				String class_section = input[3];
				String class_name = input[4];
				createClass(class_course, class_name, class_term, class_section);
				break;
			case "list-classes":
				listClasses();
				break;
			case "select-class":
				if(input.length==2){ //if user passes 2 arguments etc.
					class_course = input[1];
					selectClass_1(class_course);
				}
				if(input.length==3){
					class_course = input[1];
					class_term = input[2];
					selectClass_2(class_course,class_term);
				}
				if(input.length==4){
					class_course = input[1];
					class_term = input[2];
					class_section = input[3];
					selectClass_3(class_course,class_term,class_section);
				}
				break;
			case "show-class":
				showClass();
				break;
			case "show-categories":
				showCategories();
				break;
			case "add-category":
				String category_name = input[1];
				String category_weight = input[2];
				addCategory(category_name,category_weight);
				break;
			case "show-assignment":
				showAssignment();
				break;
			case "add-assignment":
				String name = input[1];
				String category = input[2];
				String description = input[3];
				int points = Integer.parseInt(input[4]);
				addAssignment(name, category, description, points);
				break;
			case "add-student":
				String username = input[1];
				if(input.length == 2){
					addStudent_1(username);
				}
				if(input.length>2){
					username = input[1];
					int student_id = Integer.parseInt(input[2]);
					String last = input[3];
					String first = input[4];
					addStudent_4(username, student_id, last, first);
				}
				break;

			case "show-students":
				if(input.length == 1){
					showStudents();
				}
				if(input.length >1){
					String string = input[1];
					showStudentsString(string);
				}
				break;
			case "grade":
				String assignmentName = input[1];
				username = input[2];
				String grade = input[3];
				grade(assignmentName, username, grade);
				break;
			case "student-grades":
				username = input[1];
				studentGrades(username);
				break;
			case "gradebook":
				gradeBook();
				break;
				case "exit":
					System.exit(0);
					break;
			default:
				System.out.println("Incorrect format..." + "\n"+ commands);
		}
		System.out.println("Please enter another command...");

	}
	static String commands = "Valid commands:" + "\n"
			+"new-class, <course>, <term>, <section>, <name>" + "\n"
			+"list-classes" + "\n"
			+"select-class, <course>, <term>, <section>" + "\n"
			+"select-class, <class_course>" + "\n"
			+"select-class, <class_course>, <class_term>" + "\n"
			+"show-class" + "\n"
			+"show-categories" + "\n"
			+"add-category, <name>, <weight>" + "\n"
			+"show-assignment" + "\n"
			+"add-assignment, <name>, <category>, <description>, <points>" + "\n"
			+"add-student, <username>, <studentid>, <last>, <first>" + "\n"
			+"add-student, <username>" + "\n"
			+"show-students" + "\n"
			+"show-students, <string>" + "\n"
			+"grade, <assignmentName>, <username>, <grade>" + "\n"
			+"student-grades, <username>" + "\n"
			+"gradebook" + "\n"
			+"exit";


	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		boolean DEBUG = true;
		String DBNAME = "school";
		String PASSWD = "password123";
		int PORT = 50418;
		String USER = "msandbox";

		Statement statement = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			// Load JDBC driver and obtain a connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:" + PORT + "/" + DBNAME + "?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC", USER, PASSWD);

			if (DEBUG) {
				System.out.println("jdbc:mysql://localhost:" + PORT + "/" + DBNAME + "?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC");
				System.out.println("Connection succeeded!");
			}
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Failed to load MySQL database driver.");
			System.err.println("  ClassNotFoundException: " + e.getMessage());
		}


		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the Managing class database!");
		System.out.println("List of the following commands are available:" +" \n" + commands + "\n" + "**Please note that the commands are case sensitive and need to entered as displayed**");
		System.out.println("Please enter a command:");
		String input = "";
		while(!input.equals("exit")){
			input = scanner.nextLine();
			String [] inputArray = input.split(", "); //split command to seperate arguments using comma
			listOfFunctions(inputArray);
		}

		scanner.close();
	}

}

