import java.util.*;

public class Employee {
	public final static String[] SIZES = { "Small", "Medium", "Large", "Extra Large", "2XL", "3XL", "4XL" };
	int empNum;
	String name;
	List<Integer> positions = new ArrayList<Integer>();
	Boolean usherA;
	Boolean ticketTakerA;
	String telNum;
	int	 shirtSize;
	
	
	public Employee (String string, int numPos) {
		int i = 0, j = 0;
		
		for ( int counter = 0; counter < numPos; counter++ ) 
			positions.add(0);
		String[] tokens = string.split(",");
		empNum = Integer.parseInt(tokens[i++]);
		name = tokens[i++];
		telNum = tokens[i++];
		shirtSize = Integer.parseInt(tokens[i++]);
		while ( i < tokens.length ) {
				positions.set(j, Integer.parseInt(tokens[i++]));
				j++;
		}
	}

	public String name () {
		return name;
	}
	
	public int getEmpNumb () {
		return empNum;
	}
	
	public boolean isPosition (int position) {
		return positions.get(position) != 0;
	}
	
	public String print(){
		return (empNum + ": " + name + ": " + telNum);
	}
	
	public String toString() {
		String line = null;
		line = empNum + "," + name + "," + telNum + "," + shirtSize + ",";
		for (int i = 0; i < positions.size(); i++) {
			line += positions.get(i) + ",";
		}
		
		return line;
	}

	public String returnName() {
		return name;
	}

	public String returnTelNumber() {
		return telNum;
	}

	public void updateEmp(String string) {
		int i = 0, j = 0;
		String[] tokens = string.split(",");
		empNum = Integer.parseInt(tokens[i++]);
		name = tokens[i++];
		telNum = tokens[i++];
		shirtSize = Integer.parseInt(tokens[i++]);
		while ( i < tokens.length ) {
			positions.set(j, Integer.parseInt(tokens[i++]));
			j++;
		}
	}

	public int returnShirtSize() {
		return shirtSize;
	}
}