package com.revature.service;

import com.revature.model.CabDetails;

public interface CabService{
	
	void addCab(CabDetails newCab) throws Exception;
	Integer requestCab() throws Exception;
	Integer getNoOfAvailableCabs() throws Exception;
	

}
