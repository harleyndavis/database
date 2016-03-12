

public class Employee {
	public final static String[] SIZES = { "Small", "Medium", "Large", "Extra Large", "2XL", "3XL", "4XL" };
	int empNum;
	String name;
	Boolean usher;
	Boolean ticketTaker;
	String telNum;
	int	 shirtSize;
	
	
	public Employee (String string) {
		String[] tokens = string.split(",");
		empNum = Integer.parseInt(tokens[0]);
		name = tokens[1];
		telNum = tokens[2];
		shirtSize = Integer.parseInt(tokens[3]);
		updateUsher( Integer.parseInt(tokens[4]) != 0 );
		updateTicketTaker( Integer.parseInt(tokens[5]) != 0 );
	}
	
	public String name () {
		return name;
	}
	
	public int getEmpNumb () {
		return empNum;
	}
	
	void updateUsher(boolean bool) {
			usher = bool;
		
	}
	
	public boolean isPosition (String position) {
		position = position.toLowerCase();
		switch (position) {
			case "usher":
				return usher;
			case "ticket taker":
				return ticketTaker;
		}
		return false; 
	}
	
	void updateTicketTaker(boolean bool) {
			ticketTaker = bool;
	}
	
	public String print(){
		return (name + ": " + telNum);
	}
	
	public String toString() {
		String line = null;
		line = empNum + "," + name + "," + telNum + "," + shirtSize + ",";
		if ( usher )
			line += 1;
		else
			line += 0;
		line += ",";
		if ( ticketTaker )
			line += 1;
		else
			line += 0;
		line += ",";
		
		return line;
	}

	public String returnName() {
		return name;
	}

	public String returnTelNumber() {
		return telNum;
	}

	public void updateEmp(String string) {
		String[] tokens = string.split(",");
		empNum = Integer.parseInt(tokens[0]);
		name = tokens[1];
		telNum = tokens[2];
		shirtSize = Integer.parseInt(tokens[3]);
		updateUsher( Integer.parseInt(tokens[4]) != 0 );
		updateTicketTaker( Integer.parseInt(tokens[5]) != 0 );
	}

	public int returnShirtSize() {
		return shirtSize;
	}
}