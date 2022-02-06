package com.revature.model;
import java.util.Objects;
import java.time.LocalTime;


public class CabDetails {
	private final Integer cabNo;
	private LocalTime sTiming;
	private LocalTime eTiming;
	private Integer freeOrBooked;//0 means available, 1 means Booked
	
	public CabDetails(Integer cnum) {
		this.cabNo = cnum;
		this.freeOrBooked = 0;
		
	}
	public CabDetails(Integer cnum,Integer fOB) {
		this.cabNo = cnum;
		this.freeOrBooked = fOB;
		
	}
	
	public Integer getFreeOrBooked() {
		return freeOrBooked;
	}

	public void setFreeOrBooked(Integer freeOrBooked) {
		this.freeOrBooked = freeOrBooked;
	}


	public Integer getCabNo() {
		return cabNo;
	}

	public LocalTime getStartTiming() {
		return sTiming;
	}

	public LocalTime getEndTiming() {
		return eTiming;
	}

	public void setStartTiming(LocalTime sTiming) {
		this.sTiming = sTiming;
	}
	
	public void setEndTiming(LocalTime eTiming) {
		this.eTiming = eTiming;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cabNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CabDetails other = (CabDetails) obj;
		return Objects.equals(cabNo, other.cabNo);
	}
	@Override
	public String toString() {
		return "CabDetails [cabNo=" + cabNo + ", sTiming=" + sTiming + ", eTiming=" + eTiming + ", freeOrBooked="
				+ freeOrBooked + "]";
	}
	
	

		

}