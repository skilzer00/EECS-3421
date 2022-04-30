import java.util.*;
import java.net.*;
import java.text.*;
import java.lang.*;
import java.io.*;
import java.sql.*;	


public class custTotal {
	
	private Connection conDB;   // Connection to the database system.
    private String url;         // URL: Which database?
    private Integer custID;     // Who are we tallying?
    private String club;
	private HashMap<Integer, String[]> bookMap = new HashMap<Integer, String[]>();
	private int bookNum;
    
    public custTotal() {
    	
        // Set up the DB connection.
        try {
            // Register the driver with DriverManager.
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // URL: Which database?
        url = "jdbc:db2:c3421a";

        // Initialize the connection.
        try {
            // Connect with a fall-thru id & password
            conDB = DriverManager.getConnection(url);
        } catch(SQLException e) {
            System.out.print("\nSQL: database connection error.\n");
            System.out.println(e.toString());
            System.exit(0);
        }
        
        // Commit.  Okay, here nothing to commit really, but why not...
        try {
            conDB.commit();
        } catch(SQLException e) {
            System.out.print("\nFailed trying to commit.\n");
            e.printStackTrace();
            System.exit(0);
        }    

        System.out.println("******************* York Book Store Application ***************************");
        System.out.print("Customer ID: ");
        Scanner input = new Scanner(System.in);
        while(!input.hasNextInt()) {
        	input.next();
        	System.out.print("\nCustomer ID: ");
        }
        int custID = input.nextInt();
        
        boolean exi = false;
        while(!exi){
        	if(find_customer(custID)){
        		exi = true;
        	}
        	else{
        		System.out.print("Customer ID not found , please re-enter ID ");
                while(!input.hasNextInt()) {
                	input.next();
                	System.out.print("Customer ID not found , please re-enter ID ");
                }
        		custID = input.nextInt();
        		exi = false;
        	}
        }
        
        System.out.print("\nWould you like to update the customer information? (Y/N) ");
        Scanner input1 = new Scanner(System.in);
        String update = input1.nextLine();
        
        boolean checkupdate = false;
        
        while(!checkupdate){
        	if(update.equals("y") || update.equals("Y")){
            	System.out.print("Customer Name: ");
            	Scanner input2 = new Scanner(System.in);
                String updatename = input2.nextLine();
                System.out.print("Customer city: ");
            	Scanner input3 = new Scanner(System.in);
                String updatecity = input3.nextLine();
                update_customer(custID, updatename, updatecity);
 
        		checkupdate = true;
        	}
        	else if(update.equals("n") || update.equals("N")){
        		break;
           		
        	}
        	else {
                update = input1.nextLine();
                continue;
        	}
        	
        }
        
        
        boolean reverse = false;
        
        while(!reverse)
        {
        	
        
        System.out.println("\n********************* Book Categories ************************ ");
        fetch_categories();
        System.out.print("Choose a category: ");
        Scanner input4 = new Scanner(System.in);
        while(!input4.hasNextInt()) {
        	input4.next();
        	System.out.print("Choose a category: ");
        }
        int category = input4.nextInt();
        while(category <=0 || category > 12){
        	System.out.print("Choose a category: ");
            while(!input4.hasNextInt()) {
            	input4.next();
            	System.out.print("\nChoose a category: ");
         
            }
        	category = input4.nextInt();
        }
        
        String catEnter = "";
        if(category == 1){
        	catEnter = "children";
        }
        else if(category == 2){
        	catEnter = "cooking";
        }
        else if(category == 3){
        	catEnter = "drama";
        }
        else if(category == 4){
        	catEnter = "guide";
        }
        else if(category == 5){
        	catEnter = "history";
        }
        else if(category == 6){
        	catEnter = "horror";
        }
        else if(category == 7){
        	catEnter = "humor";
        }
        else if(category == 8){
        	catEnter = "mystery";
        }
        else if(category == 9){
        	catEnter = "phil";
        }
        else if(category == 10){
        	catEnter = "romance";
        }
        else if(category == 11){
        	catEnter = "science";
        }
        else if(category == 12){
        	catEnter = "travel";
        }
        
        System.out.print("\nCategory " + catEnter + " is selected");
        System.out.println("");
        System.out.println("\nBooks in Category: ");
        displayBooks(catEnter);
        System.out.println("");
        System.out.println("Title:");
        Scanner input5 = new Scanner(System.in);
        String booklist = input5.nextLine();
        
        boolean xxx = find_book(booklist,catEnter);
        
if(xxx){
        	
        	boolean wish = false;
        	while(!wish){
        	System.out.println(" ");
        	System.out.print("Do you want this book ? (Y/N): ");
            Scanner input6 = new Scanner(System.in);
            String buybuy = input6.nextLine();
            if(buybuy.equals("Y") || buybuy.equals("y")){
        	reverse = true;
        	wish = true;
            }
            else if(buybuy.equals("N") || buybuy.equals("n")){
            	System.out.println("pick the category again!");
            	reverse = false;
            	break;
            }
            
        	}
        }
        else{
        	System.out.println(" ");
        	System.out.println("Book does not exist please choose cateogry again and pick a title");
        	System.out.println(" ");
        	reverse = false;
        }
        
        
        }
        
        
        
        
        System.out.print("Select a book to purchase: ");
        Scanner inputS = new Scanner(System.in);
        while(!inputS.hasNextInt()) {
        	inputS.next();
        	System.out.print("Select a book to purchase: ");
        }
        int Selected = inputS.nextInt();
        int sNumber = 0;
        boolean choice1 = false;
        while(!choice1){
        	if(Selected <=0 || Selected > bookNum){
        		choice1 = false;
        		System.out.print("Select a book to purchase: "); 
        		Selected = inputS.nextInt();
        	}
        	else{
        		choice1 = true;
        		sNumber = Selected;
        		
        	}
        }
        

        
        String[] storevalue = bookMap.get(sNumber);
        String title = storevalue[0];
        String year = storevalue[1];
        double minPrice = min_price(custID, title, year);
        System.out.println("The minimum price for this book is $" + minPrice);
        System.out.print("How many would you like to purchase? ");
        Scanner inputP = new Scanner(System.in);
        while(!inputP.hasNextInt()) {
        	inputP.next();
        	System.out.print("How many would you like to purchase? ");
        } 
        int purchase = inputP.nextInt();
        
        boolean purchasecheck = false;
        while(!purchasecheck){
        	if(purchase <=0){
        		System.out.print("Please purchase at least one: ");
                while(!inputP.hasNextInt()) {
                	inputP.next();
                	System.out.print("Please purchase at least one: ");
                } 
        		purchase = inputP.nextInt();
				purchasecheck = false;
        	}
        	else{
        		purchasecheck = true;
        	}
        }
        
        club = getClub(title, year, minPrice);
        double sum;
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("The total price for the purchase is: $" + df.format((sum = minPrice * purchase)));
        System.out.print("Would you want to purchase the book/books? (y/n) ");
        Scanner inputgt = new Scanner(System.in);
		String purchaseC = inputgt.nextLine();
        
    	boolean lastpurchase = false;
    	while(!lastpurchase){
    		if(purchaseC.equals("Y") || purchaseC.equals("y")){
    			insert_purchase(purchase, title, Integer.parseInt(year));
				System.out.println("Thank you for your purchase.\n");
				lastpurchase = true;
    		}else if(purchaseC.equals("N") || purchaseC.equals("n")){
    			System.out.println("Would you like to continue? (Y/N)");
    			Scanner inputre = new Scanner(System.in);
    			String YN = inputre.nextLine();
    			if(YN.equals("Y") || YN.equals("y")){
    				reverse = false;
                	break;
    			}
    			else if (YN.equals("N") || YN.equals("n")) {
    				System.out.print("Good bye!\n");
    				lastpurchase = true;
    				reverse = true;
    			}
    			
    		}
    	}
    		
        
    	
    	
    	
        // Close the connection.
        try {
            conDB.close();
        } catch(SQLException e) {
            System.out.print("\nFailed trying to close the connection.\n");
            e.printStackTrace();
            System.exit(0);
        }
            
    	
    	
    	
    	  	
    }
    
    
    public boolean find_customer (int id){
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		ResultSet answers = null; // A cursor.
		
		boolean exist = false;
		
		queryText = "SELECT *  " + "FROM yrb_customer " + "WHERE cid = ?";
		
        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
            querySt.setInt(1, id);
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        try {
            if (answers.next()) {
            	exist = true;
            	custID = answers.getInt(1);
            	System.out.println("Cid: " + answers.getInt(1) + " Name: " + answers.getString(2) + " City: " + answers.getString(3) );
            } else {
            	exist = false;
            	custID = null;
            }
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Close the cursor.
        try {
            answers.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        return exist;
    	 	
    	
    }
    
    public void update_customer(int cid, String name, String city){
    	
    	String queryText = ""; // The SQL text.
    	PreparedStatement querySt = null; // The query handle.
    	
    	queryText = "UPDATE yrb_customer SET name = ?, city = ?  WHERE cid = ?";
    	
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
            querySt.setString(1, name);
            querySt.setString(2, city);
            querySt.setInt(3, cid);
            
            querySt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in Update");
            System.out.println(e.toString());
            System.exit(0);
        }
        
		// We're done with the handle.
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
    	   	
    }
    
	public void fetch_categories() {
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		ResultSet answers = null; // A cursor.
		
		queryText = "SELECT * " + "FROM yrb_category ";
		
		// Prepare the query.
		try {
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Execute the query.
		try {
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Any answer?
		try {
			for (int i = 1; answers.next(); i++) {
				String list = answers.getString(1);
				System.out.println(i + "." + list);
			}
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in cursor.");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Close the cursor.
		try {
			answers.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing cursor.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

		// We're done with the handle.
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
	}
	
	
		public void displayBooks(String cat)
	{
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		ResultSet answers = null; // A cursor.
		
		queryText = "select distinct title from yrb_book where cat = ?";
	


		// Prepare the query.
		try {			
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}

	
		try {
			querySt.setString(1, cat);
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}

		try {
			boolean exist = answers.next();
			int bookNum1 = 1;
			String bookTitle;
			for (; exist; bookNum1++) 
			{			
				bookTitle = answers.getString(1);
				System.out.println(bookNum1 + ". " + bookTitle);
				if(answers.next() == true)
				{
					exist = true;
				}
				else
				{
					exist = false;
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in cursor.");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Close the cursor.
		try {
			answers.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing cursor.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

		// We're done with the handle.
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

	}
	
	
   
	public boolean find_book(String title, String cat)
	{
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		ResultSet answers = null; // A cursor.

		boolean inDB = false; // Return.

		queryText = "SELECT * FROM yrb_book WHERE title = ? AND cat = ?";
		
		
		String[] findBook = new String[4]; 

		// Prepare the query.
		try {
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Execute the query.
		try {
			querySt.setString(1, title);
			querySt.setString(2, cat);
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Any answer?
		try {
			if(!answers.next())
			{
				inDB = false;
			}
			else
			{
				System.out.println("\nBooks available:");
				inDB = true;
				boolean nExist = true;
				int pos = 1;
				for (; nExist; pos++) 
				{
					findBook = new String[4];
					findBook[0] = answers.getString(1);
					findBook[1] = String.valueOf(answers.getInt(2));
					findBook[2] = answers.getString(3);
					findBook[3] = String.valueOf(answers.getInt(5));
					bookMap.put(pos, findBook);
					
					
					System.out.println(pos + ": " + "Title: " + answers.getString(1) + "  Year: " + answers.getInt(2) + "  Language: " + answers.getString(3) + "  Weight: " + answers.getInt(5));
					if(answers.next() == true)
					{
						nExist = true;
					}
					else
					{
						nExist = false;
					}
				}
				
				bookNum = pos - 1;
			}
			
			
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in cursor.");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Close the cursor.
		try {
			answers.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing cursor.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

		// We're done with the handle.
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

		return inDB;
		
	}
    

	public double min_price(int cid, String title, String year)
	{
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		ResultSet answers = null; // A cursor.
		double price = 0.0;

		queryText = "SELECT distinct min(price) "
				+ "FROM yrb_offer WHERE year = ? and title = ? and club in "
				+ "(select club from yrb_member where cid = ?)";


		// Prepare the query.
		try {			
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Execute the query.
		try {
			querySt.setInt(1, Integer.parseInt(year));
			querySt.setString(2, title);
			querySt.setInt(3, custID);
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Any answer?
		try {
			if(answers.next())
			{
				price = answers.getDouble(1);
			}
			else
			{
				System.out.println("Can't find the minimum.");
			}
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in cursor.");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Close the cursor.
		try {
			answers.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing cursor.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

		// We're done with the handle.
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

		
		return price;
	}   
    

	private void insert_purchase(int amount, String title, int year) 
	{
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
	    String todaytime = dateFormat.format(timestamp);
		
		queryText = "INSERT INTO yrb_purchase VALUES (?,?,?,?,?,?)";
		


		// Prepare the query.
		try {			
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Execute the query.
		try {
			
			querySt.setInt(1, custID);
			querySt.setString(2, club);
			querySt.setString(3, title);
			querySt.setInt(4, year);
			querySt.setString(5, todaytime);
			querySt.setInt(6, amount);
			
			querySt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in update");
			System.out.println(e.toString());
			System.exit(0);
		}
		
		// We're done with the handle.
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		
		
		
	

	}	

	private String getClub(String title, String year, double price)
	{

		String club = "";
		
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		ResultSet answers = null; // A cursor.
		
		queryText = "select o.club from yrb_member m, "
				+ "yrb_offer o where o.club = m.club and o.year = ? "
				+ "and m.cid = ? and o.title = ? and o.price = ?";
		


		// Prepare the query.
		try {			
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Execute the query.
		try {
			
			querySt.setInt(1, Integer.parseInt(year));
			querySt.setInt(2, custID);
			querySt.setString(3, title);
			querySt.setDouble(4, price);
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Any answer?
		try {
			if(answers.next())
			{
				club = answers.getString(1);
			}
			else
			{
				System.out.println("No club found");
			}
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in cursor.");
			System.out.println(e.toString());
			System.exit(0);
		}

		// Close the cursor.
		try {
			answers.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing cursor.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

		// We're done with the handle.
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

		
		return club;
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		custTotal ct = new custTotal();
	}

}
