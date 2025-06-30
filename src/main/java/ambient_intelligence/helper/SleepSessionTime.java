package ambient_intelligence.helper;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SleepSessionTime {
	
private Date startTime,endTime;

private long sleepLength;

public SleepSessionTime() {
	
}
public SleepSessionTime(Date startTime, Date endTime) {
	super();
	if (startTime != null && endTime!=null)
	{
		this.startTime = startTime;
		this.endTime = endTime;
		sleepLength=endTime.getTime()-startTime.getTime();
		if (sleepLength > 0)
		{
				sleepLength=TimeUnit.MILLISECONDS.toMinutes(sleepLength);
		}else
			throw new IllegalArgumentException("startTime must be before endTime");
		
	}else
		throw new IllegalArgumentException("start and end Date must not be null");	
}


public void setStartTime(Date startTime) {
	this.startTime = startTime;
}


public void setEndTime(Date endTime) {
	this.endTime = endTime;
}


public void setSleepLength(long sleepLength) {
	this.sleepLength = sleepLength;
}


public Date getStartTime() {
	return startTime;
}

public Date getEndTime() {
	return endTime;
}

public long getSleepLength() {
	return sleepLength;
}
@Override
public String toString() {
	return "{startTime:"
+startTime+", endTime:"+endTime+", sleepLength:"+sleepLength+"}";
}


}
