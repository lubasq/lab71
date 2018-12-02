import java.sql.*;
import java.util.Scanner;

public class DockerConnectMySQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/baza";

   static final String USER = "PLmysql";
   static final String PASS = "123qwe";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   boolean baseExist = false;
   String sql;
   Scanner reader = new Scanner(System.in);
  
   try{
      Class.forName("com.mysql.cj.jdbc.Driver");
	   
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);	   
      	   
      System.out.println("Check if table in base exist");
      DatabaseMetaData md = conn.getMetaData();
      ResultSet rs = md.getTables(null, null, "Persons", null);
      while (rs.next()) {
            System.out.println("Base Exist");
	    baseExist = true;
      }
      rs = null;  
      if(!baseExist){
      	System.out.println("Creating Table");
      	stmt = conn.createStatement();
      	sql = "CREATE TABLE Persons (PersonID int, LastName varchar(255), FirstName varchar(255), Address varchar(255), City varchar(255) )";
      	stmt.executeUpdate(sql);
      	stmt = null;
      }
	   
      stmt = conn.createStatement();
      System.out.println("Inserting Data to Table. Please enter how many entries u want to add: ");
	  String howManyInserts = reader.nextLine();
      stmt.executeUpdate(Inserts( Integer.parseInt(howManyInserts) ));	 
      stmt = null;
	   
      stmt = conn.createStatement();
      sql = "SELECT PersonID, FirstName, LastName, Address, City FROM Persons";
      rs = stmt.executeQuery(sql);

      while(rs.next()){
         int id  = rs.getInt("PersonID");
         String first = rs.getString("FirstName");
         String last = rs.getString("LastName");
		 String address = rs.getString("Address");
		 String city = rs.getString("City");

         System.out.println("ID: " + id);
         System.out.println(", First: " + first);
         System.out.println(", Last: " + last);
		 System.out.println(", Address: " + address);
		 System.out.println(", City: " + city);
      }
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
 }
 
 static private String Inserts(int iterate){
	String sql = "";
	sql += "INSERT INTO Persons (PersonID, LastName, FirstName, Address, City) VALUES ";
	for (int i=0; i < iterate; i++){
		sql += "(" + i + " ,Surname"+i + " ,Name"+i + " ,Street"+i + ", Lublin)";
		
		if(i != iterate-1){
			sql += ", ";
		}
	}
	return sql;
 }
}
