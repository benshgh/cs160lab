import java.util.*;

/*
 * A naive algorithm for sorting an array based on the order of another array
 * */

class Lab4 {

	public static class Employee implements Cloneable {
	    Date   startingDate;
		String name;
	    int empId;
	 
	    public Employee(Date startingDate, String name, int empId) {
	    	this.startingDate = startingDate;
	    	this.name = name;
	        this.empId = empId;
	    }
	 
	    @Override
	    public Employee clone() {
	    	Employee clonedEmployee = null;
	        try {
	        	clonedEmployee = (Employee) super.clone();
	        	clonedEmployee.setEmpStartingDate((Date)this.startingDate.clone());
	        } catch (CloneNotSupportedException e) {
	            e.printStackTrace();
	        }
	 
	        return clonedEmployee;
	    }
	    
	    @Override
	    public String toString() {
	        return String.format("Employee: " + "Name - " + this.name +
	        		             ", ID - " + this.empId + 
	        		             ", Starting Date - " + this.startingDate);
	    }
	    
	    //Getters and Setters of property fields
	 
	    public Date getEmpStartingDate() {
	        return startingDate;
	    }
	 
	    public void setEmpStartingDate(Date startingDate) {
	        this.startingDate = startingDate;
	    }
	    
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
	 
	    public int getId() {
	        return empId;
	    }
	 
	    public void setId(int id) {
	        this.empId = id;
	    }
	}
	
	public static void main(String[] args) {
		
	}
	
}