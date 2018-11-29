public class Evaluation {
	int empNum;
	String event;
	String supervisor;
	String date;
	String position;
	int performance;
	int attitude;
	int attire;
	String comments;

	public Evaluation (String string) {
		int i = 0;
		String[] tokens = string.split(",");
		empNum = Integer.parseInt(tokens[i++]);
		event = tokens[i++];
		supervisor = tokens[i++];
		date = tokens[i++];
		position = tokens[i++];
		performance = Integer.parseInt(tokens[i++]);
		attitude = Integer.parseInt(tokens[i++]);
		attire = Integer.parseInt(tokens[i++]);
		comments = tokens[i++];

	}
	
	public String toString() {
		
		String line = empNum + "," + event + "," + supervisor + "," +
				date + "," + position + "," + performance + "," + attitude + "," + attire + "," + comments + ",";
		
		return line;
	}
	
	public String print(String name) {
		return empNum + ": " + name + ": "+ event + ", " + ", " + supervisor + ", " +
				date + ": " + performance + ", " + attitude + ", " + attire + ", " + comments;
	}
	
	public int perf() {
		return performance;
	}
	public int attit() {
		return attitude;
	}
	public int attir() {
		return attire;
	}
}
