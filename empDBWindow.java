import java.awt.EventQueue;
import java.util.*;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;


public class empDBWindow {
	
	private JFrame frame;
	private static List<Employee> list = new ArrayList<Employee>();
	private static String fileName = "employeeList.txt";
	private static String[] positions = { "Usher", "Ticket Taker" };
	private static String position;
	
	private static List<Employee> tempList = new ArrayList<Employee>();
	
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		initList();	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					empDBWindow window = new empDBWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public empDBWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 550, 353);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panelMenu = new JPanel();
		frame.getContentPane().add(panelMenu, "name_1148943330196774");
		
		JPanel panelPosList = new JPanel();
		frame.getContentPane().add(panelPosList, "name_1148943347996075");
		
		JButton btnPosListDone = new JButton("Done");
		btnPosListDone.setBounds(435, 280, 89, 23);
		btnPosListDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMenu.setVisible(true);
				panelPosList.setVisible(false);
			}
		});
		panelPosList.setLayout(null);
		panelPosList.add(btnPosListDone);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 514, 258);
		panelPosList.add(scrollPane);
		
		JTextArea textAreaPosList = new JTextArea();
		scrollPane.setViewportView(textAreaPosList);
		
		JButton btnSave_1 = new JButton("Save");
		btnSave_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTempList(position + ".txt");
			}

			private void saveTempList(String string) {
				int i = 0;
				try {
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(string, false)));
					out.println(tempList.get(i).print());
					
					out.close();
					
					out = new PrintWriter(new BufferedWriter(new FileWriter(string, true)));
					
					for ( i = 1; i < tempList.size(); i++) {
						out.println(tempList.get(i).print());
					}
					
					out.close();
				
				} catch (IOException e) {
					//exception handling left as an exercise for the reader
				}				
			}
		});
		btnSave_1.setBounds(336, 280, 89, 23);
		panelPosList.add(btnSave_1);
		
		JPanel panelEmployee = new JPanel();
		panelEmployee.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panelEmployee, "name_1148943367201438");
		
		JButton btnEmpDone = new JButton("Done");
		btnEmpDone.setBounds(279, 289, 79, 23);
		btnEmpDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMenu.setVisible(true);
				panelEmployee.setVisible(false);
			}
		});
		panelEmployee.setLayout(null);
		panelEmployee.add(btnEmpDone);
		JButton btnRetrieve = new JButton("Retrieve");
		textFieldEmpID = new JTextField();
		textFieldEmpID.setBounds(70, 13, 102, 20);
		textFieldEmpID.setHorizontalAlignment(SwingConstants.CENTER);
		panelEmployee.add(textFieldEmpID);
		textFieldEmpID.setColumns(10);
		textFieldEmpID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRetrieve.doClick();
			}
		});
		
		JCheckBox chBxUsher = new JCheckBox("Usher");
		chBxUsher.setBounds(86, 83, 97, 23);
		chBxUsher.setHorizontalAlignment(SwingConstants.LEFT);
		panelEmployee.add(chBxUsher);
		
		JCheckBox checkBoxTicketTaker = new JCheckBox("TicketTaker");
		checkBoxTicketTaker.setBounds(292, 83, 107, 23);
		checkBoxTicketTaker.setHorizontalAlignment(SwingConstants.LEFT);
		panelEmployee.add(checkBoxTicketTaker);
		
		JComboBox<String> comboBoxShirtSize = new JComboBox<>();
		comboBoxShirtSize.setBounds(380, 39, 130, 20);
		comboBoxShirtSize.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxShirtSize.setModel(new DefaultComboBoxModel<>(Employee.SIZES));
		comboBoxShirtSize.setSelectedIndex(0);
		panelEmployee.add(comboBoxShirtSize);
		

		btnRetrieve.setBounds(178, 12, 91, 23);
		btnRetrieve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = doesExist(Integer.parseInt(textFieldEmpID.getText()));
				if ( i != -1 ) {
					textFieldEmpName.setText(list.get(i).returnName());
					textFieldEmpTelNum.setText(list.get(i).returnTelNumber());
					comboBoxShirtSize.setSelectedIndex(list.get(i).returnShirtSize());
					chBxUsher.setSelected(list.get(i).isPosition("usher"));
					checkBoxTicketTaker.setSelected(list.get(i).isPosition("ticket taker"));
					
				}
				else {
					JOptionPane.showMessageDialog(frame, "That user doesn't exist!",
						    "Message",
						    JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		panelEmployee.add(btnRetrieve);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(73, 289, 99, 23);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = doesExist(Integer.parseInt(textFieldEmpID.getText()));
				
				String line = textFieldEmpID.getText() + "," +
						textFieldEmpName.getText() + "," +
						textFieldEmpTelNum.getText() + "," +
						comboBoxShirtSize.getSelectedIndex() + ",";
				if ( chBxUsher.isSelected())
					line += 1;
				else
					line += 0;
				
				line += ",";
				
				if ( checkBoxTicketTaker.isSelected() )
					line += 1;
				else
					line += 0;
				
				line += ",";
				
				if ( i != -1 )
					list.get(i).updateEmp(line);
				else {
					int response = JOptionPane.showConfirmDialog(null, "Do you wish to save new employee?", "Confirm",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					    if (response == JOptionPane.NO_OPTION) {
					    } else if (response == JOptionPane.YES_OPTION) {
					      list.add(new Employee(line));
					    } else if (response == JOptionPane.CLOSED_OPTION) {
					    }
				}

			      Collections.sort(list, NAMES);
			      saveList();
			}
		});
		panelEmployee.add(btnSave);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setBounds(21, 44, 39, 14);
		panelEmployee.add(lblName);
		
		textFieldEmpName = new JTextField();
		textFieldEmpName.setBounds(70, 41, 198, 20);
		panelEmployee.add(textFieldEmpName);
		textFieldEmpName.setColumns(10);
		
		JLabel lblTelephone = new JLabel("Telephone #:");
		lblTelephone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelephone.setBounds(298, 12, 72, 22);
		panelEmployee.add(lblTelephone);
		
		textFieldEmpTelNum = new JTextField();
		textFieldEmpTelNum.setBounds(380, 13, 130, 20);
		textFieldEmpTelNum.setColumns(10);
		panelEmployee.add(textFieldEmpTelNum);
		
		JLabel lblEmpID = new JLabel("Emp ID #:");
		lblEmpID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmpID.setBounds(6, 16, 54, 14);
		panelEmployee.add(lblEmpID);
		
		JLabel lblShirtSize = new JLabel("Shirt Size:");
		lblShirtSize.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShirtSize.setBounds(279, 42, 91, 14);
		panelEmployee.add(lblShirtSize);
		
		JButton btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = doesExist(Integer.parseInt(textFieldEmpID.getText()));
				
				if ( i != -1 ) {
					int response = JOptionPane.showConfirmDialog(null, "Do you wish to remove this employee?", "Confirm",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					    if (response == JOptionPane.NO_OPTION) {
					    } else if (response == JOptionPane.YES_OPTION) {
							list.remove(i);
							saveList();
					    } else if (response == JOptionPane.CLOSED_OPTION) {
					    }
				}
			}
		});
		btnNewButton.setBounds(180, 289, 89, 23);
		panelEmployee.add(btnNewButton);
		
		
		
		panelMenu.setLayout(null);
		
		JLabel lblMenuGetPos = new JLabel("Get Position List");
		lblMenuGetPos.setBounds(47, 48, 133, 14);
		panelMenu.add(lblMenuGetPos);
		
		JButton btnMenuPosList = new JButton("Get");
		btnMenuPosList.setEnabled(false);
		btnMenuPosList.setBounds(302, 44, 64, 23);
		btnMenuPosList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPosList.setVisible(true);
				panelMenu.setVisible(false);
				displayPosition(textAreaPosList);
			}
		});
		
		JComboBox<Object> comboBoxMenuPos = new JComboBox<Object>(positions);
		comboBoxMenuPos.setSelectedIndex(-1);
		comboBoxMenuPos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				position = (String)comboBoxMenuPos.getSelectedItem();
				btnMenuPosList.setEnabled(true);
			}
		});
		comboBoxMenuPos.setBounds(190, 45, 102, 20);
		panelMenu.add(comboBoxMenuPos);
		
		JButton btnMenuEmployee = new JButton("Edit/Add Employee");
		btnMenuEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelEmployee.setVisible(true);
				panelMenu.setVisible(false);
			}
		});
		
		
		panelMenu.add(btnMenuPosList);
		btnMenuEmployee.setBounds(167, 73, 125, 23);
		panelMenu.add(btnMenuEmployee);
	}
	
	private int doesExist(int empID) {
		int exists = -1; 
		for (int i = 0; i < list.size(); i++) {
			if ( empID == list.get(i).getEmpNumb())
				exists = i;
		}
		return exists;
	}

	private static void initList () throws IOException {
		String line = null;
		Scanner input = new Scanner(System.in);
		@SuppressWarnings("unused")
		int i = 0;
		   
		FileReader fileReader = new FileReader (fileName);
		   
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		   
		while( ( line = bufferedReader.readLine()) != null ) {
				list.add(new Employee(line));
				i++;
		}
		bufferedReader.close();
		input.close();
		Collections.sort(list, NAMES);
		
	}
	
	static final Comparator<Employee> NAMES = 
            new Comparator<Employee>() {
				public int compare(Employee e1, Employee e2) {
				return e1.name().toLowerCase().compareTo(e2.name().toLowerCase());
				}
	};
	private JTextField textFieldEmpID;
	private JTextField textFieldEmpName;
	private JTextField textFieldEmpTelNum;
	
	static void displayPosition(JTextArea textAreaPosList) {
		textAreaPosList.setText(null);
		tempList.clear();
		textAreaPosList.append("List of " + position + "s:\n");
			for (int i = 0; i < list.size(); i++) {
				if ( list.get(i).isPosition(position) ) {
					textAreaPosList.append(list.get(i).print() + "\n");
					System.out.println(list.get(i).print());
					tempList.add(list.get(i));
				}	
			}
	}
	
	static void saveList() {
		int i = 0;
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));
			System.out.println(list.get(i).toString());
			out.println(list.get(i).toString());
			
			out.close();
			
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
			
			for ( i = 1; i < list.size(); i++) {
				System.out.println(list.get(i).toString());
				out.println(list.get(i).toString());
			}
			
			out.close();
		
		} catch (IOException e) {
			//exception handling left as an exercise for the reader
		}
		
	}
}	
	



