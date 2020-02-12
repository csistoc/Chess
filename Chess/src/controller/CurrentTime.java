package controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CurrentTime {
	Calendar cal;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");;
    
    public String getCurrentTime() {
    	cal = Calendar.getInstance();
    	return sdf.format(cal.getTime());
    }
}
