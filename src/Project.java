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
				stmt = conn.prepareStatement("insert into school.class values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, class_course);
			stmt.setString(2, class_name);
			stmt.setString(3, class_term);
			stmt.setString(4, class_section);
			stmt.executeUpdate();
				int class_id;

				try(ResultSet rs = stmt.getGeneratedKeys()) {
					if(rs.next()) {
						class_id= rs.getInt(1);
						System.out.println("class id " + class_id + "class course"+class_course);
					} else {
						System.err.println("Did not get any class id");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			conn.setAutoCommit(true);
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

	public static void addAssignment() {

	}

	public static void addStudent_4(String user_name, int student_id, String last, String first) {

	}
	public static void addStudent_1(String user_name) {

	}

	public static void showStudents() {
		//Show all students in the current class
	}
	public static void showStudentsString() {
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


//	SQLException {
//		Connection connection = null;
//
//		connection = MySqlDatabase.getDatabaseConnection();
//		Statement sqlStatement = connection.createStatement();
//
//		String sql = String.format(
//				"INSERT INTO items (item_code, description, price, inventory_amount) VALUES ('%s', '%s', %s, %s);",
//				item_code, description, price, inventory_amount);
//		sqlStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
//
//		ResultSet resultSet = sqlStatement.getGeneratedKeys();
//		resultSet.next();
//
//		int item_id = resultSet.getInt(1);
//		connection.close();
//
//		return new Item(item_id, item_code, description, price, inventory_amount);
//	}


//
//	public static List<Item> getItems(String item_code) throws SQLException {
//		List<Item> items = new ArrayList<Item>();
//		Connection connection = null;
//
//		connection = MySqlDatabase.getDatabaseConnection();
//		Statement sqlStatement = connection.createStatement();
//
//		String sql1 = "SELECT * FROM items;";
//		String sql2 = String.format("SELECT * FROM items WHERE item_code = '%s';", item_code);
//
//		ResultSet resultSet;
//		if (item_code.equals('%')) {
//			resultSet = sqlStatement.executeQuery(sql1);
//			while (resultSet.next()) {
//				int item_id = resultSet.getInt(1);
//				item_code = resultSet.getString(2);
//				String description = resultSet.getString(3);
//				double price = resultSet.getDouble(4);
//				int inventory_amount = resultSet.getInt(5);
//
//				Item item = new Item(item_id, item_code, description, price, inventory_amount);
//				items.add(item);
//			}
//		} else {
//			resultSet = sqlStatement.executeQuery(sql2);
//			while (resultSet.next()) {
//				int item_id = resultSet.getInt(1);
//				String description = resultSet.getString(3);
//				double price = resultSet.getDouble(4);
//				int inventory_amount = resultSet.getInt(5);
//
//				Item item = new Item(item_id, item_code, description, price, inventory_amount);
//				items.add(item);
//			}
//		}
//
//		resultSet.close();
//		connection.close();
//		return items;
//	}
//
//	public static List<Item> updateItem(String item_code, int inventory_amount) throws SQLException {
//		List<Item> items = new ArrayList<Item>();
//		Connection connection = null;
//
//		connection = MySqlDatabase.getDatabaseConnection();
//		Statement sqlStatement = connection.createStatement();
//
//		String sql = String.format("UPDATE items SET inventory_amount = %s WHERE item_code = '%s';", inventory_amount,
//				item_code);
//
//		String sql2 = String.format("SELECT * FROM items WHERE item_code = '%s';", item_code);
//
//		sqlStatement.executeUpdate(sql);
//		ResultSet resultSet = sqlStatement.executeQuery(sql2);
//
//		while (resultSet.next()) {
//			int item_id = resultSet.getInt(1);
//			String description = resultSet.getString(3);
//			double price = resultSet.getDouble(4);
//
//			Item item = new Item(item_id, item_code, description, price, inventory_amount);
//			items.add(item);
//		}
//
//		resultSet.close();
//		connection.close();
//		return items;
//	}
//
//	public static void deleteItem(String item_code) throws SQLException {
//		Connection connection = null;
//
//		connection = MySqlDatabase.getDatabaseConnection();
//		Statement sqlStatement = connection.createStatement();
//
//		String sql = String.format("DELETE FROM items WHERE item_code = '%s';", item_code);
//		sqlStatement.executeUpdate(sql);
//		connection.close();
//	}
//
//	public static List<Order> createOrder(String item_code, int quantity) throws SQLException {
//		List<Order> orders = new ArrayList<Order>();
//		Connection connection = null;
//
//		connection = MySqlDatabase.getDatabaseConnection();
//		Statement sqlStatement = connection.createStatement();
//
//		String sql = String.format("INSERT INTO orders (item_code, quantity) VALUES ('%s' , %s);", item_code, quantity);
//
//		sqlStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
//
//		ResultSet resultSet1 = sqlStatement.getGeneratedKeys();
//		resultSet1.next();
//
//		int order_id = resultSet1.getInt(1);
//
//		String sql2 = String.format("SELECT * FROM orders WHERE order_id = '%s';", order_id);
//
//		ResultSet resultSet2 = sqlStatement.executeQuery(sql2);
//
//		while (resultSet2.next()) {
//			Timestamp timestamp = resultSet2.getTimestamp(4);
//			Order order = new Order(order_id, item_code, quantity);
//			order.setTimestamp(timestamp);
//			orders.add(order);
//		}
//
//		resultSet1.close();
//		resultSet2.close();
//		connection.close();
//		return orders;
//	}
//
//	public static List<Order> getOrders(String item_code) throws SQLException {
//		Connection connection = null;
//		List<Order> orders = new ArrayList<Order>();
//
//		connection = MySqlDatabase.getDatabaseConnection();
//		Statement sqlStatement = connection.createStatement();
//
//		String sql;
//		if (item_code.equals('%')) {
//			sql = "SELECT * FROM orders LEFT JOIN items ON orders.item_code = items.item_code;";
//
//		} else {
//			sql = String.format(
//					"SELECT * FROM orders LEFT JOIN items ON orders.item_code = items.item_code WHERE orders.item_code = '%s';",
//					item_code);
//		}
//		ResultSet resultSet = sqlStatement.executeQuery(sql);
//
//		while (resultSet.next()) {
//			int order_id = resultSet.getInt(1);
//			int quantity = resultSet.getInt(3);
//			Timestamp order_timestamp = resultSet.getTimestamp(4);
//			double price = resultSet.getDouble(8);
//
//			Order order = new Order(order_id, item_code, quantity);
//			order.setTimestamp(order_timestamp);
//			order.setOrderTotal((price * quantity));
//			orders.add(order);
//		}
//		resultSet.close();
//		connection.close();
//		return orders;
//	}
//
//	public static void deleteOrder(String item_code) throws SQLException {
//		Connection connection = null;
//
//		connection = MySqlDatabase.getDatabaseConnection();
//		Statement sqlStatement = connection.createStatement();
//
//		String sql = String.format("DELETE FROM orders WHERE item_code = '%s';", item_code);
//		sqlStatement.executeUpdate(sql);
//		connection.close();
//	}
//
//	public static String getOrderDetails(int order_id) throws SQLException {
//		Connection connection = null;
//
//		connection = MySqlDatabase.getDatabaseConnection();
//		Statement sqlStatement = connection.createStatement();
//
//		String sql = String.format(
//				"SELECT orders.order_id, orders.item_code, items.description, orders.quantity, items.price, (orders.quantity * items.price) AS order_total_amount FROM orders LEFT JOIN items ON orders.item_code = items.item_code WHERE %s = orders.order_id;",
//				order_id);
//		ResultSet resultSet = sqlStatement.executeQuery(sql);
//
//		resultSet.first();
//		String item_code = resultSet.getString(2);
//		String description = resultSet.getString(3);
//		int quantity = resultSet.getInt(4);
//		double price = resultSet.getDouble(5);
//		double order_total_amount = resultSet.getDouble(6);
//
//		String details = String.format("%s, %s, %s, %s, %s, %s", order_id, item_code, description, quantity, price,
//				order_total_amount);
//
//		resultSet.close();
//		connection.close();
//
//		return details;
//	}
//
//	public static List<String> getOrderDetails() throws SQLException {
//		Connection connection = null;
//
//		connection = MySqlDatabase.getDatabaseConnection();
//		Statement sqlStatement = connection.createStatement();
//
//		String sql = "SELECT orders.order_id, orders.item_code, items.description, orders.quantity, items.price, (orders.quantity * items.price) AS order_total_amount FROM orders LEFT JOIN items ON orders.item_code = items.item_code;";
//		ResultSet resultSet = sqlStatement.executeQuery(sql);
//
//		List<String> orderDetails = new ArrayList<String>();
//
//		while (resultSet.next()) {
//			int order_id = resultSet.getInt(1);
//			String item_code = resultSet.getString(2);
//			String description = resultSet.getString(3);
//			int quantity = resultSet.getInt(4);
//			double price = resultSet.getDouble(5);
//			double order_total_amount = resultSet.getDouble(6);
//
//			String details = String.format("%s, %s, %s, %s, %s, %s", order_id, item_code, description, quantity, price,
//					order_total_amount);
//			orderDetails.add(details);
//		}
//		resultSet.close();
//		connection.close();
//		return orderDetails;
//
//	}
//
//	public static void tryToCreateNewItem(String item_code, String description, double price, int inventory_amount) {
//		try {
//			Item item = createItem(item_code, description, price, inventory_amount);
//			System.out.println(item.toString());
//		} catch (SQLException sqlException) {
//			System.out.println("Failed to create item");
//			System.out.println(sqlException.getMessage());
//		}
//	}
//
//	public static void tryToGetItems(String item_code) {
//		try {
//			List<Item> items = getItems(item_code);
//			for (Item item : items) {
//				System.out.println(item.toString());
//			}
//		} catch (SQLException sqlException) {
//			System.out.println("Failed to get items");
//			System.out.println(sqlException.getMessage());
//		}
//	}
//
//	public static void tryToDeleteItem(String item_code) {
//		try {
//			deleteItem(item_code);
//			System.out.println("The item has been deleted");
//		} catch (SQLException sqlException) {
//			System.out.println("Failed to delete item");
//			System.out.println(sqlException.getMessage());
//		}
//	}
//
//	public static void tryToCreateNewOrder(String item_code, int quantity) {
//		try {
//			List<Order> orders = createOrder(item_code, quantity);
//			for (Order order : orders) {
//				System.out.println(order.toString());
//			}
//		} catch (SQLException sqlException) {
//			System.out.println("Failed to create order");
//			System.out.println(sqlException.getMessage());
//		}
//
//	}
//
//	public static void tryToGetOrders(String item_code) {
//		try {
//			List<Order> orders = getOrders(item_code);
//			for (Order order : orders) {
//				System.out.println(order.toString());
//			}
//		} catch (SQLException sqlException) {
//			System.out.println("Failed to get orders");
//			System.out.println(sqlException.getMessage());
//		}
//	}
//
//	public static void tryToDeleteOrder(String item_code) {
//		try {
//			deleteOrder(item_code);
//			System.out.println("The order has been deleted");
//		} catch (SQLException sqlException) {
//			System.out.println("Failed to delete order");
//			System.out.println(sqlException.getMessage());
//		}
//	}
//
//	public static void tryToUpdateInventory(String item_code, int inventory_quantity) {
//		try {
//			List<Item> items = updateItem(item_code, inventory_quantity);
//			for (Item item : items) {
//				System.out.println(item.toString());
//			}
//		} catch (SQLException sqlException) {
//			System.out.println("Failed to update inventory");
//			System.out.println(sqlException.getMessage());
//		}
//
//	}
//
//	public static void tryToGetOrderDetails() {
//		try {
//			List<String> orderDetails = getOrderDetails();
//			for (String details : orderDetails) {
//				System.out.println(details);
//			}
//		} catch (SQLException sqlException) {
//			System.out.println("Failed to get order details");
//			System.out.println(sqlException.getMessage());
//		}
//	}
//
//	public static void tryToGetOrderDetails(int order_id) {
//		try {
//			String details = getOrderDetails(order_id);
//			System.out.println(details);
//		} catch (SQLException sqlException) {
//			System.out.println("Failed to get order details");
//			System.out.println(sqlException.getMessage());
//		}
//	}

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
			case "add-catagories":

				addCatagories();
			case "add-category":
				String name = args[1];
				String weight = args[2];
			case "show-assignment":
				showAssignment();
			case "add-assignment":
				String category;
				String description;
				String points;
			case "add-student":
				String username = args[1];
				if(args.length == 2){
					addStudent_1(username);
				}
				if(args.length>2){
					username = args[1];
					int studentid = Integer.parseInt(args[2]);
					String last = args[3];
					String first = args[4];
					addStudent_4(username, studentid, last, first);
				}

			case "show-students":
				if(args.length == 1){
					showStudents();
				}
				if(args.length >1){
					String string = args[1];
					showStudentsString();
				}
				break;
			case "grade":
				String assignmentname = args[1];
				username = args[2];
				String grade = args[3];
				break;
			case "student-grades":
				username = args[1];
				studentGrades(username);
				break;
			case "gradebook":
				gradeBook();
				break;



//		case "UpdateInventory":
//			item_code = args[1];
//			inventory_amount = Integer.parseInt(args[2]);
//			tryToUpdateInventory(item_code, inventory_amount);
//			break;
//
//		case "DeleteItem":
//			item_code = args[1];
//			tryToDeleteItem(item_code);
//		case "GetItems":
//			item_code = args[1];
//			tryToGetItems(item_code);
//			break;
//
//		case "CreateOrder":
//			item_code = args[1];
//			quantity = Integer.parseInt(args[2]);
//			tryToCreateNewOrder(item_code, quantity);
//			break;
//
//		case "DeletOrder":
//			item_code = args[1];
//			tryToDeleteOrder(item_code);
//			break;
//
//		case "GetOrders":
//			item_code = args[1];
//			tryToGetOrders(item_code);
//			break;
//		case "GetOrderDetails":
//			if (args[1].equals("%")) {
//				tryToGetOrderDetails();
//			} else {
//				tryToGetOrderDetails(Integer.parseInt(args[1]));
//			}
//			break;
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

