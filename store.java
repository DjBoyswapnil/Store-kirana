package com.kiran_store_management_system;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class store 
{
	Scanner sc=new Scanner(System.in);
	int p_id;// Variables to store product details
	String p_name;
	int p_price;
	int p_quantity;
	public static void main(String[] args) throws SQLException 
	{
		System.out.println("Welcome to the store management System");
		System.out.println("Lakshmi kirana");
		store obj=new store();// Create an object of the store class
		obj.operations();// Call the operations method
	}
	public void operations() throws SQLException 
	{
		try
		{  // Establish connection to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn;
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/shop","root","root");
			while(true)// Main menu loop
			{
			System.out.println("Enter your choice from below options");
			System.out.println("1:for showing the product with there quantity");
			System.out.println("2:for add the new product in the System");
			System.out.println("3:for Updating the quantity & price in existing product");
			System.out.println("4:for showing product according to the product id");
			System.out.println("5:for deleting the product from the system");
			System.out.println("6:for showing customise list");
			System.out.println("7:Exit");
			int ch=sc.nextInt();
			switch (ch) 
			{
			case 1:
				view(conn);
				break;
			case 2:
				new_input(conn);
				break;
			case 3:
				update(conn);
				break;
			case 4:
				row_view(conn);
				break;
			case 5:
				delete_product(conn);
				break;
			case 6:
				customize(conn);
				break;
			case 7:
				System.exit(0);// Exit the program
				break;
			default:
				System.out.println("Invalid choice");
				break;
			}
		
			}
		} 
		catch (ClassNotFoundException e) 
		{
			 // Handle class not found exception
			e.printStackTrace();
		}
		
		
	}
	 // Method to view all products in the store
	public void view(Connection conn) throws SQLException
	{
		String qur="Select * from store";
		java.sql.Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery(qur);
		System.out.println("Product id \tProduct name \tProduct price \tproduct quantity \tProduct Packing \tProduct expirty");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"+rs.getInt(4)+"\t\t\t"+rs.getDate(5)+"\t\t\t"+rs.getDate(6));
		}
	}
	 // Method to add a new product to the store
	public void new_input(Connection conn) throws SQLException
	{
		char ans;
		do
		{
		String qur="insert into store values(?,?,?,?,?,?)";
		PreparedStatement ps=conn.prepareStatement(qur);
		int p_id;
		String p_name;
		int p_price;
		int p_quantity;
		String p_packing;
		String p_expirty;
		System.out.println("Enter product id");
		p_id=sc.nextInt();
		System.out.println("Enter prouct name");
		p_name=sc.next();
		System.out.println("Enter product price in Rs.");
		p_price=sc.nextInt();
		System.out.println("Enter product quantity Kg.");
		p_quantity=sc.nextInt();
		System.out.println("Enter product packing(YYYY-MM-DD)");
		p_packing=sc.next();
		Date packing=Date.valueOf(p_packing);
		System.out.println("Enter date of expirty(YYYY-MM-DD)");
		p_expirty=sc.next();
		Date expirty=Date.valueOf(p_expirty);
		ps.setInt(1,p_id);
		ps.setString(2, p_name);
		ps.setInt(3, p_price);
		ps.setInt(4, p_quantity);
		ps.setDate(5,packing);
		ps.setDate(6,expirty);
		int count=ps.executeUpdate();
		if(count>0)
		{
			System.out.println("Data added successfully");
		}
		System.out.println("do you want add another data ");
		System.out.println("Enter 'Y' for yes & 'N' for no");
		 ans=sc.next().charAt(0);
		}while(ans=='y'|| ans=='Y');
	}
	// Method to update product quantity, price, packing date, or expiry date
	public void update(Connection conn) throws SQLException
	{
		int ch1;
		System.out.println("WHich item do you want to update");
		System.out.println("1:quantity");
		System.out.println("2:price");
		System.out.println("3:Product Packing date");
		System.out.println("4:Product Expirty Date");
		ch1=sc.nextInt();
		switch(ch1)
		{
		case 1:
			int p_id;
			int p_quantity;
			System.out.println("Enter Product id");
			p_id=sc.nextInt();
			System.out.println("Enter new quantity");
			p_quantity=sc.nextInt();
			String qur="update store set p_quantity=? where p_id=?";
			PreparedStatement ps=conn.prepareStatement(qur);
			ps.setInt(1,p_quantity);
			ps.setInt(2,p_id);
			ps.executeUpdate();
			System.out.println("Data updated successfully");
			break;
		case 2:
			int p_price;
			System.out.println("Enter Product id");
			p_id=sc.nextInt();
			System.out.println("Enter the new price of product");
			p_price=sc.nextInt();
			String qur1="update store set p_price=? where p_id=?";
			PreparedStatement ps1=conn.prepareStatement(qur1);
			ps1.setInt(1, p_price);
			ps1.setInt(2,p_id);
			ps1.executeUpdate();
			System.out.println("Data updated successfully");
			break;
		case 3:
			String p_packing;
			System.out.println("Enter Product Id");
			p_id=sc.nextInt();
			System.out.println("Enter New Packing Date Of Product(YYYY-MM-DD)");
			p_packing=sc.next();
			String qui="update store set p_packing=? where p_id=?";
			Date packing=Date.valueOf(p_packing);
			PreparedStatement psu=conn.prepareStatement(qui);
			psu.setDate(1,packing);
			psu.setInt(2,p_id);
			psu.executeUpdate();
			System.out.println("Data updated successfully");
			break;
		case 4:
			String p_expirty;
			System.out.println("Enter Product Id");
			p_id=sc.nextInt();
			System.out.println("Enter new Expirty Date of Product");
			p_expirty=sc.next();
			Date expirty=Date.valueOf(p_expirty);
			PreparedStatement psj=conn.prepareStatement("update store set p_expirty=? where p_id=?");
			psj.setDate(1, expirty);
			psj.setInt(2, p_id);
			psj.executeUpdate();
			System.out.println("Data updated successfully");
			break;
		default:
			System.out.println("Sorry you enter invalid choice");
			break;
		}	
	  }
	// Method to view a specific product by its ID
	public void row_view(Connection conn) throws SQLException
	{
		System.out.println("Enter Product id");
		p_id=sc.nextInt();
		String qur="select * from store where p_id=?";
		PreparedStatement pse=conn.prepareStatement(qur);
		pse.setInt(1, p_id);
		ResultSet rs=pse.executeQuery();
		System.out.println("Product id \tProduct name \tProduct price \tProduct quantity");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"+rs.getInt(4));
		}
	}
	 // Method to delete a product from the store
	public void delete_product(Connection conn) throws SQLException
	{
		String confirm;
		System.out.println("Enter Product id");
		int p_id=sc.nextInt();
		System.out.println("Are you sure for deleting the entered record"
				+ "press Y for yes and N for No");
		confirm=sc.next();
		if(confirm.equalsIgnoreCase("Y"))
		{
		String qur="delete from store where p_id=?";
		PreparedStatement ps=conn.prepareStatement(qur);
		ps.setInt(1, p_id);
		ps.executeLargeUpdate();
		System.out.println("deleted successfully");
		}
		else
		{
			System.out.println("cancelling the deletion of record");
		}
	}
	// Method to generate customized lists of products
	public void customize(Connection conn) throws SQLException
	{
		char ans1;
		do 
		{
		System.out.println("which list do you want according to");
		System.out.println("1:Product Id");
		System.out.println("2:Product name");
		System.out.println("3:Product Quantity");
		System.out.println("4:Product Price");
		System.out.println("5:Product Packing");
		System.out.println("6:Product Expirty");
		int ch2=sc.nextInt();
		switch(ch2)
		{
		case 1:
			System.out.println("Enter Product Id");
			p_id=sc.nextInt();
			String qur="select * from store where p_id=?";
			PreparedStatement pse=conn.prepareStatement(qur);
			pse.setInt(1, p_id);
			ResultSet rs=pse.executeQuery();
			System.out.println("Product id \tProduct name \tProduct price \tProduct quantity \tProduct packing \tProduct expirty");
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getDate(5)+"\t\t"+rs.getDate(6));
			}
			break;
		case 2:
			System.out.println("Enter Product name");
			p_name=sc.next();
			String que="select * from store where p_name=?";
			PreparedStatement ps=conn.prepareStatement(que);
			ps.setString(1,p_name);
			ResultSet rs1=ps.executeQuery();
			System.out.println("Product id \tProduct name \tProduct price \tProduct quantity \tProduct packing \tProduct expirty");
			while(rs1.next())
			{
				System.out.println(rs1.getInt(1)+"\t\t"+rs1.getString(2)+"\t\t"+rs1.getInt(3)+"\t\t"+rs1.getInt(4)+"\t\t"+rs1.getDate(5)+"\t\t"+rs1.getDate(6));
			}
			break;
		case 3:
			System.out.println("Enter Product quantity");
			p_quantity=sc.nextInt();
			String qui="select * from store where p_quantity=?";
			PreparedStatement psi=conn.prepareStatement(qui);
			psi.setInt(1,p_quantity);
			ResultSet re=psi.executeQuery();
			System.out.println("Product id \tProduct name \tProduct price \tProduct quantity \tProduct packing \tProduct expirty");
			while(re.next())
			{
				System.out.println(re.getInt(1)+"\t\t"+re.getString(2)+"\t\t"+re.getInt(3)+"\t\t"+re.getInt(4)+"\t\t"+re.getDate(5)+"\t\t"+re.getDate(6));
			}
			break;
		case 4:
			System.out.println("Enter Product price");
			p_price=sc.nextInt();
			String qut="select * from store where p_price=?";
			PreparedStatement psp=conn.prepareStatement(qut);
			psp.setInt(1,p_price);
			ResultSet rw=psp.executeQuery();
			System.out.println("Product id \tProduct name \tProduct price \tProduct quantity \tProduct packing \tProduct expirty");
			while(rw.next())
			{
				System.out.println(rw.getInt(1)+"\t\t"+rw.getString(2)+"\t\t"+rw.getInt(3)+"\t\t"+rw.getInt(4)+"\t\t"+rw.getDate(5)+"\t\t"+rw.getDate(6));
			}
			break;
		case 5:
			String p_packing;
			System.out.println("Enter Product Packing Date(YYYY-MM-DD)");
			p_packing=sc.next();
			Date packing=Date.valueOf(p_packing);
			String quj="select * from store where p_packing=?";
			PreparedStatement psj=conn.prepareStatement(quj);
			psj.setDate(1, packing);
			ResultSet rj=psj.executeQuery();
			System.out.println("Product id \tProduct name \tProduct price \tProduct quantity \tProduct packing \tProduct expirty");
			while(rj.next())
			{
				System.out.println(rj.getInt(1)+"\t\t"+rj.getString(2)+"\t\t"+rj.getInt(3)+"\t\t"+rj.getInt(4)+"\t\t"+rj.getDate(5)+"\t\t"+rj.getDate(6));
			}
			break;
		case 6:
			String p_expirty;
			System.out.println("Enter Product Packing Date(YYYY-MM-DD)");
			p_expirty=sc.next();
			Date expirty=Date.valueOf(p_expirty);
			String quk="select * from store where p_packing=?";
			PreparedStatement psk=conn.prepareStatement(quk);
			psk.setDate(1, expirty);
			ResultSet rk=psk.executeQuery();
			System.out.println("Product id \tProduct name \tProduct price \tProduct quantity \tProduct packing \tProduct expirty");
			while(rk.next())
			{
				System.out.println(rk.getInt(1)+"\t\t"+rk.getString(2)+"\t\t"+rk.getInt(3)+"\t\t"+rk.getInt(4)+"\t\t"+rk.getDate(5)+"\t\t"+rk.getDate(6));
			}
			break;
		default:
			System.out.println("Sorry you entered Wrong choice");
			break;
		}
		System.out.println("Do you want another Customize list???");
		System.out.println("'Y' for Yes & 'N' for No");
		ans1=sc.next().charAt(0);
	  }while(ans1=='Y' || ans1=='y');
	 }
	}
	

