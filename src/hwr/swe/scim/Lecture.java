package hwr.swe.scim;

import java.util.Date;

public class Lecture {

	private String name;
	private Date startTime;
	private Date endTime;
	private boolean isCreated;

	public void setName(String pName) {
		this.name = pName;
	}

	public void setStartTime(Date pDate) {
		this.startTime = pDate;
	}

	public void setEndTime(Date pDate) {
		this.endTime = pDate;
	}

	public void setIsCreated(boolean pFlag) {
		this.isCreated = pFlag;
	}
	
	@Override
	public String toString(){
		return "";
	}
}