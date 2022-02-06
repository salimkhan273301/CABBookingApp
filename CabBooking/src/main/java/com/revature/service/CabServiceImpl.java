package com.revature.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedHashSet;
import java.util.Set;

import com.revature.booking.BookingImpl;
import com.revature.db.ConnectDb;
import com.revature.exception.UnAvailableException;
import com.revature.model.CabDetails;

public class CabServiceImpl implements CabService{
	
	private Set<CabDetails> cabs=new LinkedHashSet<CabDetails>(); //A set of available Cabs

	
	public void addCab(CabDetails newCab) throws Exception{
		BookingImpl bi = new BookingImpl();
		cabs = bi.copycabs();//getting all the data from cab table into our hashSet
		
		Connection con=ConnectDb.getConnection();//getting our connection object
		
		//adding new cab to the database
		PreparedStatement pst = con.prepareStatement("insert into cab(cabNo,freeOrBooked) values (?,?)");
		pst.setInt(1, newCab.getCabNo());
		pst.setInt(2, newCab.getFreeOrBooked());
		pst.execute(); //executing insert command
		System.out.println(cabs);
		
		cabs.add(newCab);//adding into cabs Set
		
	}


	public Integer requestCab() throws Exception{
		
		//checking for free cabs and returning its number to Admin
		BookingImpl bi = new BookingImpl();
		cabs = bi.copycabs();
		if(cabs.isEmpty()) {
			try {//if there isn't any cab registered in the system, ie, No cabs in the data base
				throw new UnAvailableException("No Cab is Available");
			} catch (UnAvailableException e) {
				System.out.println(e.toString());
				return -1;
			}
		}
		
		for(CabDetails cab:cabs) //checking whether they are free or booked. would return only if they are free
			if(cab.getFreeOrBooked()==0) {
				cab.setFreeOrBooked(1);
				return cab.getCabNo();
			}
			
		try {//incase all the cabs in the database is booked by some other employee and currently no other cabs are available for booking
			throw new UnAvailableException("No Cab is Available");
		} catch (UnAvailableException e) {
			System.out.println(e.getMessage());
			return-1;
		}
		
	}

	
	public Integer getNoOfAvailableCabs() throws Exception{
		
		//returning total number of cabs that are available for booking
		BookingImpl bi = new BookingImpl();
		cabs = bi.copycabs();
		Integer count=0;
		for(CabDetails cab:cabs) {
			if(cab.getFreeOrBooked()==0) {
				count++;
			}
		}
		return count;
	}
	

	

}