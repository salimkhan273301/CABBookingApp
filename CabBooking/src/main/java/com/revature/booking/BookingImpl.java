package com.revature.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import com.revature.db.ConnectDb;
import com.revature.model.CabDetails;



public class BookingImpl implements Booking{

	
	public Set<CabDetails> copycabs() {
		Set<CabDetails> cabs=new LinkedHashSet<CabDetails>();
		try {
			Connection con = ConnectDb.getConnection();
			PreparedStatement pt = con.prepareStatement("select cabNo,freeOrBooked from cab");
			ResultSet rs = pt.executeQuery();
			while(rs.next()) {
				Integer cabNo = rs.getInt(1);
				Integer fOB = rs.getInt(2);
				CabDetails a = new CabDetails(cabNo,fOB);
				cabs.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cabs;
	}

	

}
