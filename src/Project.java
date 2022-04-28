import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Project {

	static Connection conn;


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

	public static void selectClass_1(String class_course) {

	}

	public static void selectClass_2(String class_course, String class_term) {


	}
	public static void selectClass_3(String class_course, String class_term, String class_section) {


	}

	public static void showCatagories() {

	}

	public static void addCatagories() {

	}

	public static void showAssignment() {

	}

	public static void addAssignment( String name, String Category, String Description, int points) {

	}

	public static void addStudent_4(String user_name, int student_id, String last, String first) {

	}
	public static void addStudent_1(String user_name) {

	}

	public static void showStudents() {
		//Show all students in the current class
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

//		statement = conn.createStatement();
//		String item_code;
//		String description;
//		Double price;
//		int inventory_amount;
//		int quantity;

		switch (args[0]) {
			case "new-class":
				String class_course = args[1];
				String class_name = args[2];
				String class_term = args[3];
				String class_section = args[4];
				createClass(class_course, class_name, class_term, class_section);
				break;
			case "select-class":
				if(args.length==2){
					class_course = args[1];
					selectClass_1(class_course);
				}
				if(args.length==3){
					class_course = args[1];
					class_term = args[2];
					selectClass_2(class_course,class_term);
				}
				if(args.length==4){
					class_course = args[1];
					class_term = args[2];
					class_section = args[3];
					selectClass_3(class_course,class_term,class_section);
				}
				break;
			case "show-catagories":
				showCatagories();
				break;
			case "add-catagories":

				addCatagories();
				break;
			case "add-category":
				String name = args[1];
				String weight = args[2];
				break;
			case "show-assignment":
				showAssignment();
				break;
			case "add-assignment":
				name = args[1];
				String category = args[2];
				String description = args[3];
				int points = Integer.parseInt(args[4]);
				addAssignment(name, category, description, points);
				break;
			case "add-student":
				String username = args[1];
				if(args.length == 2){
					addStudent_1(username);
				}
				if(args.length>2){
					username = args[1];
					int student_id = Integer.parseInt(args[2]);
					String last = args[3];
					String first = args[4];
					addStudent_4(username, student_id, last, first);
				}
				break;

			case "show-students":
				if(args.length == 1){
					showStudents();
				}
				if(args.length >1){
					String string = args[1];
					showStudentsString(string);
				}
				break;
			case "grade":
				String assignmentName = args[1];
				username = args[2];
				String grade = args[3];
				grade(assignmentName, username, grade);
				break;
			case "student-grades":
				username = args[1];
				studentGrades(username);
				break;
			case "gradebook":
				gradeBook();
				break;
			default:
				System.out.println("Incorrect format..." + "\n" + "Valid commands:" + "\n"
						+ "CreateItem<item_code> <ItemDescription> <Price> <inventory_amount>," + "\n"
						+ "UpdateInventory<item_code> < inventory_amount>," + "\n" + "DeleteItem<item_code>," + "\n"
						+ "GetItems<item_code or % for all>," + "\n" + "CreateOrder <item_code> <quantity>," + "\n"
						+ "DeleteOrder<item_code>," + "\n" + "GetOrders<item_code or % for all>," + "\n"
						+ "GetOrderDetails<order_id or % for all>");
		}
	}
}

