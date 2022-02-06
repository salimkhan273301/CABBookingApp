package com.revature.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.logging.Logger;

import com.revature.db.ConnectDb;



public class Login {
	String email;
	String password;
	private static final Logger logger = Logger.getLogger("Login.class");
	
	public static void main(String[] args) throws Exception {
		Connection con = ConnectDb.getConnection();
		Scanner sc = new Scanner(System.in);
		int ch;
		logger.info("excecution is starting from here.....");
		while(true) 
		{
			System.out.println("=========================== LOGIN-MENU==========================================");
			System.out.println("-----------------Please choose your Option-----------------------");
			System.out.println("1. Employee Login");
			System.out.println("2. Manager Login");
			System.out.println("3. Admin Login");
			
			//String nextIntString = sc.nextLine(); //get the number as a single line
			//ch = Integer.parseInt(nextIntString); //convert the string to an int
			
			ch =sc.nextInt();
			if(sc.hasNextLine()){
			    String strunfh = sc.nextLine();
			}
			switch(ch) {
			case 1:
			{
				Login emp = new Login();
				logger.info("Employee Login initiated");
				emp.getEmailPass();
				PreparedStatement pst = con.prepareStatement("select * from employee where email=? and password=? and manager=0");
				pst.setString(1, emp.email);
				pst.setString(2, emp.password);
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					Integer id = rs.getInt("id");
					String name = rs.getString("name");
					String email = rs.getString("email");
					String dept = rs.getString("dept");
					EmployeeImpl empi = new EmployeeImpl(id,name,email,dept);
					logger.info("Going in to EmployeeImpl.Java file");
					empi.request();
				}
				else {
					logger.info("Wrong email or  password entered by  Employee");
					System.out.println("you have entered Wrong  Email and password");
					System.out.println("please try again, and enter right information");
				}
				break;
			}
			case 2:
			{
				Login manager = new Login();
				logger.info("Manager Login initiated");
				manager.getEmailPass();
				PreparedStatement ps = con.prepareStatement("select * from employee where email=? and password=? and manager=1");
				ps.setString(1, manager.email);
				ps.setString(2, manager.password);
				ResultSet rst = ps.executeQuery();
				if(rst.next()) {
					Integer id = rst.getInt("id");
					String name = rst.getString("name");
					String email = rst.getString("email");
					String dept = rst.getString("dept");
					ManagerImpl manage = new ManagerImpl(id,name,email,true,dept);
					try {
						logger.info("Going in to ManagerImpl.Java file");
						manage.accept();
					} catch (Exception e) {
						logger.info("Got exception while executing ManagerImpl.Java file");
						System.out.println("Got an Exception. " +e);
					}
				}
				else {
					logger.info("Wrong emai id or password  entere by  Manager");
					System.out.println("Wrong  Email and Password");
					System.out.println("please enter right information");
				}
				break;
			}
			case 3:
			{
				Login admin = new Login();
				logger.info("Admin Login initiated");
				admin.getEmailPass();
				PreparedStatement pt = con.prepareStatement("select * from employee where email=? and password=? and manager=0");
				pt.setString(1, admin.email);
				pt.setString(2, admin.password);
				ResultSet rts = pt.executeQuery();
				if(rts.next()) {
					Admin empi = new Admin();
					logger.info("Going in to Admin.Java file");
					int a = empi.stat();
				}
				else {
					logger.info("Wrong password by Admin");
					System.out.println("Wrong  Email and password");
					System.out.println("please enter right information");
				}
				break;
			}
			default:
			{
				logger.info("Closing the Application");
				System.out.println("Sorry you entered a wrong choice!!");
				System.out.println("!!! QUITING !!!");
				System.exit(0);
			}
			}	
		}}
	
	void getEmailPass() {
		logger.info("Getting email and password");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your Email :");
		this.email = sc.nextLine();
		System.out.println("Enter your Password :");
		this.password = sc.nextLine();
		logger.info("Got email and password. Now validating details");
	}
	

}
