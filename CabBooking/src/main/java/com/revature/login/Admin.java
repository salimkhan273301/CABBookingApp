package com.revature.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import com.revature.booking.Booking;
import com.revature.booking.BookingImpl;
import com.revature.db.ConnectDb;
import com.revature.model.CabDetails;
import com.revature.model.Employee;
import com.revature.service.CabService;
import com.revature.service.CabServiceImpl;

public class Admin {
	
	int stat() {
		System.out.println("!!! WELCOME to Admin Login !!!");
		while(true) {
			System.out.println("What do you wanna do?");
			System.out.println("1. Add Employee");
//			System.out.println("2. Delete Employee");
			System.out.println("2. Add Cab");
			System.out.println("3. Check Requests");
			System.out.println("Press anything else to Log Out!");
			Scanner sc =new Scanner(System.in);
			Integer ch = sc.nextInt();
			Admin a = new Admin();
			switch (ch) {
			case 1:
				try {
					a.addEmp();
					System.out.println();
				} catch (Exception e) {
					System.out.println("An exception is generated!" +e);
				}break;
			/*
			 * case 2: a.removeEmp(); break;
			 */
			case 2:
				a.addCab();
				break;
			case 3:
				try {
					a.checkRequest();
				} catch (Exception e) {
					System.out.println("An exception is generated!" +e);
				}
				break;
			default:
				System.out.println("Logging Out!");
				return 0;
			}
		}
	}
	public  ArrayList<Employee> list= new ArrayList<Employee>();// LIST IS USED TO STORE THE NEW EMPLOYEE INFORMATION HERE
	void addEmp() throws Exception {
		Connection con = ConnectDb.getConnection();
		PreparedStatement pt = con.prepareStatement("insert into employee(id,name,email,manager,active,dept,password) values(?,?,?,?,?,?,?)");
		Employee emp = this.getNewEmployee();
		pt.setInt(1, emp.getId());
		pt.setString(2, emp.getName());
		pt.setString(3, emp.getEmail());
		if(emp.getManager()==true)
			pt.setInt(4, 1);
		else
			pt.setInt(4, 0);
		pt.setInt(5,1);
		pt.setString(6, emp.getDept());
		pt.setString(7, emp.getName()+"123");
		
		pt.execute(); //insert
		list.add(emp);
		System.out.println(list);// Here i am showing the information which is stored in the list
	}
	
	/*
	 * void removeEmp() {
	 * PreparedStatement pt = con.prepareStatement("update employee set active=0 where (id=?)");
	 * 
	 * 
	 * }
	 */
	void addCab() {
		System.out.println("Enter Cab Number: ");
		Scanner sc =new Scanner(System.in);
		Integer CNo = sc.nextInt();
		CabDetails newCab = new CabDetails(CNo);
		CabService cs =new CabServiceImpl();// here i have created the obj cs
		try {
			cs.addCab(newCab);// i have done some thing here
		} catch (Exception e) {
			System.out.println("Got an Exception. " +e);
		}
		
	}
	
	void checkRequest() throws Exception{
		Connection con = ConnectDb.getConnection();
		Scanner sc = new Scanner(System.in);
		CabService cs =new CabServiceImpl();// i have created the obj cs.....
		//BookingImpl bs=new BookingImpl();
		Integer cabNo;
		System.out.println("All the manager approved requests are shown below one by one. \nPlease assign them a Cab.");
		PreparedStatement pt = con.prepareStatement("select * from request where statusId=2");
		ResultSet rs = pt.executeQuery();
		while(rs.next()) {
			System.out.println("Employee ID : " +rs.getInt(2) +" has requested an Cab with Request ID:"+rs.getInt(1)+"\nIts approved by his mamnager.");
			System.out.println("Assign a Cab? y/n");
			char m=sc.next().charAt(0);
			if(m=='y') {
				cabNo = cs.requestCab();// i have done some thing here
				//cabNo=bs.requestCab();
				if(cabNo>1000) {
					PreparedStatement pst = con.prepareStatement("insert into booking (cabNo,employeeId) values(?,?)");
					pst.setInt(1,cabNo);
					pst.setInt(2, rs.getInt(2));
					pst.execute(); //insert
					System.out.println(cabNo+" assigned");
					//update the result in to request table
					pst = con.prepareStatement("select bookingId from booking where cabNo=? and employeeId=? order by bookingId desc limit 1");
					pst.setInt(1, cabNo);
					pst.setInt(2, rs.getInt(2));
					ResultSet rs1 = pst.executeQuery();
					if(rs1.next()) {
						pst = con.prepareStatement("update request set statusId=4,bookingId=? where requestId=?");
						pst.setInt(1, rs1.getInt(1));
						pst.setInt(2, rs.getInt(1));
						int a = pst.executeUpdate(); //update
					}
				}
				else
					System.out.println("Exception Occoured!");
			}
				
		}
		System.out.println("No nore Remaining");
		return;
	
	}
	
	Employee getNewEmployee() {
		Employee a;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Id of Employee");
		Integer i=sc.nextInt();sc.nextLine();
		System.out.println("Enter the name of Employee");
		String n = sc.nextLine();
		System.out.println("Enter New employee Email ID");
		String e = sc.nextLine();
		System.out.println("Enter New employee assigned Department");
		String d=sc.nextLine();
		System.out.println("Is he a Manager? y/n");
		char m=sc.next().charAt(0);
		if(m=='y')
			a = new Employee(i,n,e,true,d);
		else
			a = new Employee(i,n,e,d);
		return a;
	}


}