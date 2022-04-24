import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Project {

//	public static Item createItem(String item_code, String description, double price, int inventory_amount)
//			throws SQLException {
//		Connection connection = null;
//
////		connection = MySqlDatabase.getDatabaseConnection();
////		Statement sqlStatement = connection.createStatement();
//
////		String sql = String.format(
////				"INSERT INTO items (item_code, description, price, inventory_amount) VALUES ('%s', '%s', %s, %s);",
////				item_code, description, price, inventory_amount);
////		sqlStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
////
////		ResultSet resultSet = sqlStatement.getGeneratedKeys();
////		resultSet.next();
////
////		int item_id = resultSet.getInt(1);
////		connection.close();
////
////		return new Item(item_id, item_code, description, price, inventory_amount);
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
		String DBNAME = "Final";
		String PASSWD = "password123";
		int PORT = 50418;
		String USER = "msandbox";

		// Connection and statement vars.
		Connection conn = null;
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

//		switch (args[0]) {
//		case "CreateItem":
//			item_code = args[1];
//			description = args[2];
//			price = Double.parseDouble(args[3]);
//			inventory_amount = Integer.parseInt(args[4]);
//			tryToCreateNewItem(item_code, description, price, inventory_amount);
//			break;
//
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
//		default:
//			System.out.println("Incorrect format..." + "\n" + "Valid commands:" + "\n"
//					+ "CreateItem<item_code> <ItemDescription> <Price> <inventory_amount>," + "\n"
//					+ "UpdateInventory<item_code> < inventory_amount>," + "\n" + "DeleteItem<item_code>," + "\n"
//					+ "GetItems<item_code or % for all>," + "\n" + "CreateOrder <item_code> <quantity>," + "\n"
//					+ "DeleteOrder<item_code>," + "\n" + "GetOrders<item_code or % for all>," + "\n"
//					+ "GetOrderDetails<order_id or % for all>");

	}
}

