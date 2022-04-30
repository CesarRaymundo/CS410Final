import java.sql.*;
import java.util.Scanner;

public class Project {

	static Connection conn;
	public static int activatedClass;


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

	public static void listClasses() throws SQLException {
		PreparedStatement stmt;

		try {
//			stmt = conn.prepareStatement("select class.*, count(enroll.student_id) as total_num_of_students\n" +
//					"from class\n" +
//					"join enroll on class.class_id = enroll.class_id\n" +
//					"group by class.class_id;");
			stmt = conn.prepareStatement("select * from class;");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt("class_id")+", "+ rs.getString("class_course") + ", " + rs.getString("class_name") + ", " + rs.getString("class_term") + ", " + rs.getString("class_section"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Returns the most recent class course but if there's multiple sections of the same class, it will fail
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

	//Returns the most recent class course but if there's multiple sections of the same class, it will fail
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

	//Get the latest term of a class
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

	//Show the currently activated class
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
	public static void addCatagory(String catagory_name, String catagory_weight) throws SQLException {
			PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("insert into catagory (catagory_name, catagory_weight) values (?, ?);");
			stmt.setString(1, catagory_name);
			stmt.setString(2, catagory_weight);
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void showCatagories() {

	}

	public static void showAssignment() {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("select category.category_id, category.category_name, group_concat(assign_description), group_concat(assign_value)\n" +
					"from category\n" +
					"join assignment on category.category_id = assignment.category_id\n" +
					"group by category.category_id;");
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addAssignment( String name, String Category, String Description, int points) {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("" +
					"insert into assignment(assign_name, class_id, category_id, assign_description, assign_value) " +
					"value('hw1', 1, 1, 'cool database stuff', 80);\n" +
					"insert into category(category_name) " +
					"value('homework');");//TODO
			stmt.setString(1, name);
			stmt.setString(2, Category);
			stmt.setString(1, Description);
			stmt.setInt(2, points);
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void addStudent_4(String user_name, int student_id, String last, String first) {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("" +
					"insert into student (user_name, first_name, last_name) " +
					"value('phillipnewell', 'phillip', 'newell');");//TODO
			stmt.setString(1, user_name);
			stmt.setInt(2, student_id);
			stmt.setString(1, last);
			stmt.setString(2, first);
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public static void addStudent_1(String user_name) {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("" +
					"insert into student (user_name, first_name, last_name) " +
					"value('phillipnewell', 'phillip', 'newell');");//TODO
			stmt.setString(1, user_name);
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void showStudents() {
		//Show all students in the current class
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("select class.*, student.*\n" +
					"from class\n" +
					"join enroll on class.class_id = enroll.class_id\n" +
					"join student on enroll.student_id = student.student_id\n" +
					"having class.class_id = 1;");//TODO
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void showStudentsString(String string) {
		//Show students with 'string' in their name or username (NOT case-sensitive)

	}

	public static void grade(String assignmentName, String userName, String grade) {
		//if student already has a grade for that assignment -- replace it
		//If number of points exceed assignment config cap, print a warning w/ number of points configed
	}

	public static void studentGrades(String username) {
		//group by category
		//subtotal for each category
		//overall grade in class
	}
	public static void gradeBook(){
		//students with their total grades
	}
	public static void listOfFunctions(String [] input) throws SQLException {
		switch (input[0]) {
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
				if(input.length==2){
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
			case "show-catagories":
				showCatagories();
				break;
			case "add-catagory":
				String catagory_name = input[1];
				String catagory_weight = input[2];
				addCatagory(catagory_name,catagory_weight);
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
				System.out.println("Incorrect format..." + "\n" + "Valid commands:" + "\n"
						+ "CreateItem<item_code> <ItemDescription> <Price> <inventory_amount>," + "\n"
						+ "UpdateInventory<item_code> < inventory_amount>," + "\n" + "DeleteItem<item_code>," + "\n"
						+ "GetItems<item_code or % for all>," + "\n" + "CreateOrder <item_code> <quantity>," + "\n"
						+ "DeleteOrder<item_code>," + "\n" + "GetOrders<item_code or % for all>," + "\n"
						+ "GetOrderDetails<order_id or % for all>");
		}
		System.out.println("Please enter another command...");

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		boolean DEBUG = true;
		String DBNAME = "school";
		String PASSWD = "password123";
		int PORT = 50418;
		String USER = "msandbox";

		// Connection and statement vars.
//		Connection conn = null;
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


		//use a scanner to get input from the user and call the listOfFunctions method
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter a command:");
		String input = "";
		while(!input.equals("exit")){
			input = scanner.nextLine();
			String [] inputArray = input.split(" ");
			listOfFunctions(inputArray);
		}




//		System.out.println("Welcome to the school database!");
//		System.out.println("Please enter a command:");
//		String input = scanner.nextLine();
//		String [] input_array = input.split(" ");
//		while(scanner.hasNextLine()){
////			input = scanner.nextLine();
//			listOfFunctions(input_array);
//			if(input.equals("exit")){
//						break;
//					}
//		}
		scanner.close();



//		switch (args[0]) {
//			case "new-class":
//				String class_course = args[1];
//				String class_term = args[2];
//				String class_section = args[3];
//				String class_name = args[4];
//				createClass(class_course, class_name, class_term, class_section);
//				break;
//				case "list-classes":
//					listClasses();
//					break;
//			case "select-class":
//				if(args.length==2){
//					class_course = args[1];
//					selectClass_1(class_course);
//				}
//				if(args.length==3){
//					class_course = args[1];
//					class_term = args[2];
//					selectClass_2(class_course,class_term);
//				}
//				if(args.length==4){
//					class_course = args[1];
//					class_term = args[2];
//					class_section = args[3];
//					selectClass_3(class_course,class_term,class_section);
//				}
//				break;
//			case "show-class":
//				showClass();
//				break;
//			case "show-catagories":
//				showCatagories();
//				break;
//			case "add-catagory":
//				String catagory_name = args[1];
//				String catagory_weight = args[2];
//				addCatagory(catagory_name,catagory_weight);
//				break;
//			case "show-assignment":
//				showAssignment();
//				break;
//			case "add-assignment":
//				String name = args[1];
//				String category = args[2];
//				String description = args[3];
//				int points = Integer.parseInt(args[4]);
//				addAssignment(name, category, description, points);
//				break;
//			case "add-student":
//				String username = args[1];
//				if(args.length == 2){
//					addStudent_1(username);
//				}
//				if(args.length>2){
//					username = args[1];
//					int student_id = Integer.parseInt(args[2]);
//					String last = args[3];
//					String first = args[4];
//					addStudent_4(username, student_id, last, first);
//				}
//				break;
//
//			case "show-students":
//				if(args.length == 1){
//					showStudents();
//				}
//				if(args.length >1){
//					String string = args[1];
//					showStudentsString(string);
//				}
//				break;
//			case "grade":
//				String assignmentName = args[1];
//				username = args[2];
//				String grade = args[3];
//				grade(assignmentName, username, grade);
//				break;
//			case "student-grades":
//				username = args[1];
//				studentGrades(username);
//				break;
//			case "gradebook":
//				gradeBook();
//				break;
//			default:
//				System.out.println("Incorrect format..." + "\n" + "Valid commands:" + "\n"
//						+ "CreateItem<item_code> <ItemDescription> <Price> <inventory_amount>," + "\n"
//						+ "UpdateInventory<item_code> < inventory_amount>," + "\n" + "DeleteItem<item_code>," + "\n"
//						+ "GetItems<item_code or % for all>," + "\n" + "CreateOrder <item_code> <quantity>," + "\n"
//						+ "DeleteOrder<item_code>," + "\n" + "GetOrders<item_code or % for all>," + "\n"
//						+ "GetOrderDetails<order_id or % for all>");
//		}
	}

}

