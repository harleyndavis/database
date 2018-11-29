import java.util.*;
import java.util.List;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import org.eclipse.wb.swing.FocusTraversalOnArray;



public class empDBWindow {
	
	private static final String evalFileName = "evaluationList.txt";
	private static JFrame frame;
	private static List<Employee> list = new ArrayList<Employee>();
	private static List<Evaluation> evalList = new ArrayList<Evaluation>();
	private static final String empFileName = "employeeList.txt";
	private static String[] positions = { "Arena Usher", "Arena Ticket Taker", "Arena Supervisor", 
			"Arena Security", "Arena Rover", "Arena Bag Check", "CC Ticket Taker", "CC Supervisor",
			"CC Usher", "CC Security", "INB Supervisor", "INB Usher", "INB Ticket Taker", "INB Security",
			"INB Stage Door", "Parking Supervisor", "Parking Crew", "REC", "Lincoln Center", "WSU", "EWU",
			"Seattle", "Northern Quest", "Mill Bay"};

	private static int position;
	private static List<JCheckBox> positionChBx = new ArrayList<JCheckBox>();
	
	private static List<Employee> tempEmpList = new ArrayList<Employee>();
	private static List<Evaluation> tempEvalList = new ArrayList<Evaluation>();
	
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		initEmpList();	
		initEvalList();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					empDBWindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		frame = new JFrame();
		frame.setTitle("Employee Data Management");
		frame.setResizable(false);
		frame.setBounds(100, 100, 585, 371);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panelMenu = new JPanel();
		frame.getContentPane().add(panelMenu, "name_1148943330196774");
		
		
		JPanel panelPosList = new JPanel();
		frame.getContentPane().add(panelPosList, "name_1148943347996075");
		JPanel panelEvaluation = new JPanel();
		frame.getContentPane().add(panelEvaluation, "name_262217981854538");
		panelEvaluation.setLayout(null);
		JPanel panelEmployee = new JPanel();
		
		JPanel panelAddEval = new JPanel();
		frame.getContentPane().add(panelAddEval, "name_1133241573147910");
		panelAddEval.setLayout(null);
		
		JComboBox<String> comboBoxShirtSize = new JComboBox<>();
		comboBoxShirtSize.setBounds(380, 39, 130, 20);
		comboBoxShirtSize.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxShirtSize.setModel(new DefaultComboBoxModel<>(Employee.SIZES));
		comboBoxShirtSize.setSelectedIndex(0);
		panelEmployee.add(comboBoxShirtSize);
		

		
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelEmployee.setVisible(true);
				panelEvaluation.setVisible(false);				
			}
		});
		btnDone.setBounds(452, 290, 71, 23);
		panelEvaluation.add(btnDone);
		
		JLabel label = new JLabel("Emp ID #:");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(10, 15, 54, 14);
		panelEvaluation.add(label);
		
		textFieldEvalEmpID = new JFormattedTextField();
		textFieldEvalEmpID.setEditable(false);
		textFieldEvalEmpID.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEvalEmpID.setColumns(10);
		textFieldEvalEmpID.setBounds(74, 12, 102, 20);
		panelEvaluation.add(textFieldEvalEmpID);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 40, 514, 246);
		panelEvaluation.add(scrollPane_1);
		
		JTextArea textAreaEvalList = new JTextArea();
		textAreaEvalList.setEditable(false);
		scrollPane_1.setViewportView(textAreaEvalList);
		
		JButton button = new JButton("Retrieve");
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( textFieldEvalEmpID.getText().isEmpty() || isNotNum(textFieldEvalEmpID.getText()) ) {
					JOptionPane.showMessageDialog(frame, "Please enter an employee ID number.",
						    "Message",
						    JOptionPane.WARNING_MESSAGE);
				}
				else if (doesExist(Integer.parseInt(textFieldEvalEmpID.getText())) == -1) {
					JOptionPane.showMessageDialog(frame, "That employee doesn't exist!",
						    "Message",
						    JOptionPane.WARNING_MESSAGE);
				}
				isEmpEval(Integer.parseInt(textFieldEvalEmpID.getText()));
				displayEmpEvaluations(tempEvalList, textAreaEvalList);
			}
		});
		button.setBounds(182, 11, 91, 23);
		panelEvaluation.add(button);
		

		
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
				saveTempList(positions[position] + ".txt");
			}

			private void saveTempList(String string) {
				int i = 0;
				try {
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(string, false)));
					out.println(tempEmpList.get(i).print());
					
					out.close();
					
					out = new PrintWriter(new BufferedWriter(new FileWriter(string, true)));
					
					for ( i = 1; i < tempEmpList.size(); i++) {
						out.println(tempEmpList.get(i).print());
					}
					
					out.close();
				
				} catch (IOException e) {
					//exception handling left as an exercise for the reader
				}				
			}
		});
		btnSave_1.setBounds(336, 280, 89, 23);
		panelPosList.add(btnSave_1);
		
		JButton btnEvaluation = new JButton("Evaluations");
		
		panelEmployee.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panelEmployee, "name_1148943367201438");
		
		JLabel jLabelAttitude = new JLabel("");
		jLabelAttitude.setBounds(261, 70, 46, 14);
		panelEmployee.add(jLabelAttitude);
		JLabel jLabelAttire = new JLabel("");
		jLabelAttire.setBounds(373, 70, 46, 14);
		panelEmployee.add(jLabelAttire);
		JLabel jLabelPerformance = new JLabel("");
		jLabelPerformance.setBounds(122, 70, 46, 14);
		panelEmployee.add(jLabelPerformance);
		
		JButton btnEmpDone = new JButton("Done");
		btnEmpDone.setBounds(404, 305, 79, 23);
		btnEmpDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldEmpID.setText(null);
				textFieldEmpName.setText(null);
				textFieldEmpTelNum.setText(null);
				jLabelPerformance.setText("");
				jLabelAttitude.setText("");
				jLabelAttire.setText("");
				textFieldEvalEmpID.setText("");
				textAreaEvalList.setText("");
				btnEvaluation.setEnabled(false);
				comboBoxShirtSize.setSelectedIndex(0);
				for ( int j = 0; j < positionChBx.size(); j++) 
						positionChBx.get(j).setSelected( false );
				panelMenu.setVisible(true);
				panelEmployee.setVisible(false);
			}
		});
		
		
		
		AbstractAction retrieveEmp = new AbstractAction()
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
		    {
		    	int j;
				if ( textFieldEmpID.getText().isEmpty() || isNotNum(textFieldEmpID.getText()) ) {
					JOptionPane.showMessageDialog(frame, "Please enter an employee ID number.",
						    "Message",
						    JOptionPane.WARNING_MESSAGE);
					btnEvaluation.setEnabled(false);
				} else {
					double averagePerformance = 0;
					double averageAttitude = 0;
					double averageAttire = 0;
					int i = doesExist(Integer.parseInt(textFieldEmpID.getText()));
					if ( i != -1 ) {
						textFieldEmpName.setText(list.get(i).returnName());
						textFieldEmpTelNum.setText(list.get(i).returnTelNumber());
						comboBoxShirtSize.setSelectedIndex(list.get(i).returnShirtSize());
							//System.out.println(positionChBx.size());
							for ( j = 0; j < positionChBx.size(); j++) 
									positionChBx.get(j).setSelected( list.get(i).isPosition(j));	
							btnEvaluation.setEnabled(true);
							textFieldEvalEmpID.setText(textFieldEmpID.getText());
							isEmpEval(Integer.parseInt(textFieldEmpID.getText()));
							displayEmpEvaluations(tempEvalList, textAreaEvalList);
							System.out.println(tempEvalList.size());
							for (Evaluation eval: tempEvalList ) {
								averagePerformance += (double) eval.perf();
								averageAttitude += (double) eval.attit();
								averageAttire += (double) eval.attir();
							}
							jLabelPerformance.setText(String.valueOf(averagePerformance /= (double) tempEvalList.size()));
							jLabelAttitude.setText(String.valueOf(averageAttitude /= (double)tempEvalList.size()));
							jLabelAttire.setText(String.valueOf(averageAttire /= (double) tempEvalList.size()));
					}
					else {
						JOptionPane.showMessageDialog(frame, "That user doesn't exist!",
							    "Message",
							    JOptionPane.WARNING_MESSAGE);
						btnEvaluation.setEnabled(false);
					}
				
				}
		    }
		};
		panelEmployee.setLayout(null);
		panelEmployee.add(btnEmpDone);
		JButton btnRetrieve = new JButton("Retrieve");
		textFieldEmpID = new JFormattedTextField();
		textFieldEmpID.addActionListener(retrieveEmp);
		textFieldEmpID.setFocusLostBehavior(JFormattedTextField.REVERT);
		textFieldEmpID.setBounds(70, 13, 102, 20);
		textFieldEmpID.setHorizontalAlignment(SwingConstants.CENTER);
		panelEmployee.add(textFieldEmpID);
		textFieldEmpID.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Arena", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(21, 95, 200, 105);
		panelEmployee.add(panel);
		panel.setLayout(null);
		
		JCheckBox chBxUsherA = new JCheckBox("Usher");
		chBxUsherA.setBounds(105, 23, 62, 23);
		panel.add(chBxUsherA);
		positionChBx.add(chBxUsherA);
		chBxUsherA.setHorizontalAlignment(SwingConstants.LEFT);
		
		JCheckBox checkBoxTicketTakerA = new JCheckBox("Ticket Taker");
		checkBoxTicketTakerA.setBounds(6, 49, 97, 23);
		panel.add(checkBoxTicketTakerA);
		positionChBx.add(checkBoxTicketTakerA);
		checkBoxTicketTakerA.setHorizontalAlignment(SwingConstants.LEFT);
		
		JCheckBox chckbxSupervisorA = new JCheckBox("Supervisor");
		chckbxSupervisorA.setBounds(6, 23, 97, 23);
		panel.add(chckbxSupervisorA);
		positionChBx.add(chckbxSupervisorA);
		
		JCheckBox chckbxSecurityA = new JCheckBox("Security");
		chckbxSecurityA.setBounds(105, 49, 67, 23);
		panel.add(chckbxSecurityA);
		positionChBx.add(chckbxSecurityA);
		
		JCheckBox chckbxRoverA = new JCheckBox("Rover");
		chckbxRoverA.setBounds(6, 75, 97, 23);
		panel.add(chckbxRoverA);
		positionChBx.add(chckbxRoverA);
		
		JCheckBox chckbxBagCheckA = new JCheckBox("Bag Check");
		chckbxBagCheckA.setBounds(104, 75, 91, 23);
		panel.add(chckbxBagCheckA);
		positionChBx.add(chckbxBagCheckA);
		
		
		

		btnRetrieve.setBounds(178, 12, 91, 23);
		btnRetrieve.addActionListener( retrieveEmp );
		
		
		panelEmployee.add(btnRetrieve);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(78, 305, 99, 23);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = doesExist(Integer.parseInt(textFieldEmpID.getText()));
				
				String line = textFieldEmpID.getText() + "," +
						textFieldEmpName.getText() + "," +
						textFieldEmpTelNum.getText() + "," +
						comboBoxShirtSize.getSelectedIndex() + ",";
				for (int j = 0; j < positionChBx.size(); j++) {
					if ( positionChBx.get(j).isSelected() )
						line += 1 + ",";
					else
						line += 0 + ",";
				}
				
				if ( i != -1 )
					list.get(i).updateEmp(line);
				else {
					int response = JOptionPane.showConfirmDialog(null, "Do you wish to save new employee?", "Confirm",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					    if (response == JOptionPane.NO_OPTION) {
					    } else if (response == JOptionPane.YES_OPTION) {
					      list.add(new Employee(line, positions.length));
							btnEvaluation.setEnabled(true);
							textFieldEvalEmpID.setText(textFieldEmpID.getText());
							isEmpEval(Integer.parseInt(textFieldEmpID.getText()));
							displayEmpEvaluations(tempEvalList, textAreaEvalList);
					    } else if (response == JOptionPane.CLOSED_OPTION) {
					    }
				}

			      Collections.sort(list, NAMES);
			      saveList(list, empFileName);
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
							saveList(list, empFileName);
							textFieldEvalEmpID.setText("");
							textAreaEvalList.setText("");
							btnEvaluation.setEnabled(false);
					    } else if (response == JOptionPane.CLOSED_OPTION) {
					    }
				}
			}
		});
		btnNewButton.setBounds(185, 305, 89, 23);
		panelEmployee.add(btnNewButton);
		
		
		btnEvaluation.setEnabled(false);
		btnEvaluation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelEvaluation.setVisible(true);
				panelEmployee.setVisible(false);
			}
		});
		
		
		btnEvaluation.setBounds(286, 305, 108, 23);
		panelEmployee.add(btnEvaluation);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Convention Center", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(225, 95, 180, 72);
		panelEmployee.add(panel_2);
		panel_2.setLayout(null);
		
		JCheckBox checkBxTicketTakerCC = new JCheckBox("Ticket Taker");
		checkBxTicketTakerCC.setBounds(6, 42, 97, 23);
		panel_2.add(checkBxTicketTakerCC);
		positionChBx.add(checkBxTicketTakerCC);
		checkBxTicketTakerCC.setHorizontalAlignment(SwingConstants.LEFT);
		
		JCheckBox checkBxSupervisorCC = new JCheckBox("Supervisor");
		checkBxSupervisorCC.setBounds(6, 16, 97, 23);
		panel_2.add(checkBxSupervisorCC);
		positionChBx.add(checkBxSupervisorCC);
		
		JCheckBox checkBxUsherCC = new JCheckBox("Usher");
		checkBxUsherCC.setBounds(105, 42, 62, 23);
		panel_2.add(checkBxUsherCC);
		positionChBx.add(checkBxUsherCC);
		checkBxUsherCC.setHorizontalAlignment(SwingConstants.LEFT);
		
		JCheckBox checkBxSecurityCC = new JCheckBox("Security");
		checkBxSecurityCC.setBounds(105, 16, 67, 23);
		panel_2.add(checkBxSecurityCC);
		positionChBx.add(checkBxSecurityCC);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "INB", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(21, 202, 200, 98);
		panelEmployee.add(panel_1);
		panel_1.setLayout(null);
		
		JCheckBox checkBxSupervisorINB = new JCheckBox("Supervisor");
		checkBxSupervisorINB.setBounds(6, 16, 97, 23);
		panel_1.add(checkBxSupervisorINB);
		positionChBx.add(checkBxSupervisorINB);
		
		JCheckBox checkBxUsherINB = new JCheckBox("Usher");
		checkBxUsherINB.setBounds(105, 16, 62, 23);
		panel_1.add(checkBxUsherINB);
		positionChBx.add(checkBxUsherINB);
		checkBxUsherINB.setHorizontalAlignment(SwingConstants.LEFT);
		
		JCheckBox checkBxTicketTakerINB = new JCheckBox("Ticket Taker");
		checkBxTicketTakerINB.setBounds(6, 42, 97, 23);
		panel_1.add(checkBxTicketTakerINB);
		positionChBx.add(checkBxTicketTakerINB);
		checkBxTicketTakerINB.setHorizontalAlignment(SwingConstants.LEFT);
		
		JCheckBox checkBxSecurityINB = new JCheckBox("Security");
		checkBxSecurityINB.setBounds(105, 42, 67, 23);
		panel_1.add(checkBxSecurityINB);
		positionChBx.add(checkBxSecurityINB);
		
		JCheckBox chckBxStageDoorINB = new JCheckBox("Stage Door");
		chckBxStageDoorINB.setBounds(6, 68, 97, 23);
		panel_1.add(chckBxStageDoorINB);
		positionChBx.add(chckBxStageDoorINB);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Parking", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(225, 177, 180, 46);
		panelEmployee.add(panel_3);
		panel_3.setLayout(null);
		
		JCheckBox checkBxSupervisorPark = new JCheckBox("Supervisor");
		checkBxSupervisorPark.setBounds(6, 16, 97, 23);
		panel_3.add(checkBxSupervisorPark);
		positionChBx.add(checkBxSupervisorPark);
		
		JCheckBox chckbxCrewPark = new JCheckBox("Crew");
		chckbxCrewPark.setBounds(105, 16, 69, 23);
		panel_3.add(chckbxCrewPark);
		positionChBx.add(chckbxCrewPark);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Out of Town", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(410, 95, 109, 201);
		panelEmployee.add(panel_4);
		panel_4.setLayout(null);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("REC");
		chckbxNewCheckBox.setBounds(6, 16, 97, 23);
		panel_4.add(chckbxNewCheckBox);
		positionChBx.add(chckbxNewCheckBox);
		
		JCheckBox chckbxLinCtr = new JCheckBox("Lin Ctr");
		chckbxLinCtr.setBounds(6, 42, 97, 23);
		panel_4.add(chckbxLinCtr);
		positionChBx.add(chckbxLinCtr);
		
		JCheckBox chckbxWsu = new JCheckBox("WSU");
		chckbxWsu.setBounds(6, 68, 97, 23);
		panel_4.add(chckbxWsu);
		positionChBx.add(chckbxWsu);
		
		JCheckBox chckbxEwu = new JCheckBox("EWU");
		chckbxEwu.setBounds(6, 94, 97, 23);
		panel_4.add(chckbxEwu);
		positionChBx.add(chckbxEwu);
		
		JCheckBox chckbxSeattle = new JCheckBox("Seattle");
		chckbxSeattle.setBounds(6, 119, 97, 23);
		panel_4.add(chckbxSeattle);
		positionChBx.add(chckbxSeattle);
		
		JCheckBox chckbxNorthQuest = new JCheckBox("North Quest");
		chckbxNorthQuest.setBounds(6, 145, 97, 23);
		panel_4.add(chckbxNorthQuest);
		positionChBx.add(chckbxNorthQuest);
		
		JCheckBox chckbxMillBay = new JCheckBox("Mill Bay");
		chckbxMillBay.setBounds(6, 171, 97, 23);
		panel_4.add(chckbxMillBay);
		positionChBx.add(chckbxMillBay);
		
		JLabel lblAttitude_1 = new JLabel("Attitude: ");
		lblAttitude_1.setBounds(178, 70, 79, 14);
		panelEmployee.add(lblAttitude_1);
		

		
		JLabel lblAttire_1 = new JLabel("Attire:");
		lblAttire_1.setBounds(317, 69, 46, 14);
		panelEmployee.add(lblAttire_1);
		

		
		JLabel lblPerformance_1 = new JLabel("Performance:");
		lblPerformance_1.setBounds(21, 69, 91, 14);
		panelEmployee.add(lblPerformance_1);
		panelEmployee.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textFieldEmpID, btnRetrieve, textFieldEmpTelNum, textFieldEmpName, comboBoxShirtSize, panel, panel_2, panel_1, panel_3, panel_4, btnSave, btnNewButton, btnEvaluation, btnEmpDone, jLabelAttitude, jLabelAttire, jLabelPerformance, chBxUsherA, checkBoxTicketTakerA, chckbxSupervisorA, chckbxSecurityA, chckbxRoverA, chckbxBagCheckA, lblName, lblTelephone, lblEmpID, lblShirtSize, checkBxTicketTakerCC, checkBxSupervisorCC, checkBxUsherCC, checkBxSecurityCC, checkBxSupervisorINB, checkBxUsherINB, checkBxTicketTakerINB, checkBxSecurityINB, chckBxStageDoorINB, checkBxSupervisorPark, chckbxCrewPark, chckbxNewCheckBox, chckbxLinCtr, chckbxWsu, chckbxEwu, chckbxSeattle, chckbxNorthQuest, chckbxMillBay, lblAttitude_1, lblAttire_1, lblPerformance_1}));
		

		
		
		
		panelMenu.setLayout(null);
		
		JLabel lblMenuGetPos = new JLabel("Get Position List");
		lblMenuGetPos.setBounds(47, 48, 133, 14);
		panelMenu.add(lblMenuGetPos);
		
		JButton btnMenuPosList = new JButton("Get");
		btnMenuPosList.setEnabled(false);
		btnMenuPosList.setBounds(345, 44, 64, 23);
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
				position = comboBoxMenuPos.getSelectedIndex();
				btnMenuPosList.setEnabled(true);
			}
		});
		comboBoxMenuPos.setBounds(167, 45, 168, 20);
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
		
		JButton btnMenuEval = new JButton("Add Evaluations");
		btnMenuEval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date = Calendar.getInstance().getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				textFieldAddEvalDate.setText(sdf.format(date));
				panelAddEval.setVisible(true);
				panelMenu.setVisible(false);
				
			}
		});
		btnMenuEval.setBounds(167, 107, 125, 23);
		panelMenu.add(btnMenuEval);
		
		JButton btnDone_1 = new JButton("Done");
		btnDone_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAddEval.setVisible(false);
				panelMenu.setVisible(true);
			}
		});
		btnDone_1.setBounds(445, 309, 89, 23);
		panelAddEval.add(btnDone_1);
		
		JLabel lblEmployeeEvaluations = new JLabel("Employee Evaluations");
		lblEmployeeEvaluations.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmployeeEvaluations.setBounds(10, 11, 140, 15);
		panelAddEval.add(lblEmployeeEvaluations);
		
		JLabel lblEvent = new JLabel("Event");
		lblEvent.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEvent.setBounds(10, 37, 46, 14);
		panelAddEval.add(lblEvent);
		
		textFieldAddEvalSupervisor = new JTextField();
		textFieldAddEvalSupervisor.setColumns(10);
		textFieldAddEvalSupervisor.setBounds(207, 34, 86, 20);
		panelAddEval.add(textFieldAddEvalSupervisor);
		
		JLabel labelSupervisora = new JLabel("Supervisor");
		labelSupervisora.setFont(new Font("Tahoma", Font.PLAIN, 10));
		labelSupervisora.setBounds(137, 37, 66, 14);
		panelAddEval.add(labelSupervisora);
		
		textFieldAddEvalDate = new JTextField();
		textFieldAddEvalDate.setColumns(10);
		textFieldAddEvalDate.setBounds(342, 34, 67, 20);
		panelAddEval.add(textFieldAddEvalDate);
		
		JLabel labelDate = new JLabel("Date");
		labelDate.setFont(new Font("Tahoma", Font.PLAIN, 10));
		labelDate.setBounds(303, 37, 48, 14);
		panelAddEval.add(labelDate);
		
		textFieldAddEvalEmpID = new JTextField();
		textFieldAddEvalEmpID.setColumns(10);
		textFieldAddEvalEmpID.setBounds(80, 72, 86, 20);
		panelAddEval.add(textFieldAddEvalEmpID);
		
		JLabel lblEmployeeId = new JLabel("Employee ID");
		lblEmployeeId.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEmployeeId.setBounds(10, 75, 60, 14);
		panelAddEval.add(lblEmployeeId);
		
		JLabel lblPerformance = new JLabel("Performance");
		lblPerformance.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPerformance.setBounds(10, 124, 66, 14);
		panelAddEval.add(lblPerformance);
		
		JRadioButton rdbtnPerformanceA = new JRadioButton("");
		buttonGroupPerformance.add(rdbtnPerformanceA);
		rdbtnPerformanceA.setActionCommand("4");
		rdbtnPerformanceA.setBounds(80, 122, 23, 23);
		panelAddEval.add(rdbtnPerformanceA);
		
		JRadioButton rdbtnPerformanceB = new JRadioButton("");
		buttonGroupPerformance.add(rdbtnPerformanceB);
		rdbtnPerformanceB.setActionCommand("3");
		rdbtnPerformanceB.setBounds(105, 122, 23, 23);
		panelAddEval.add(rdbtnPerformanceB);
		
		JRadioButton rdbtnPerformanceC = new JRadioButton("");
		buttonGroupPerformance.add(rdbtnPerformanceC);
		rdbtnPerformanceC.setActionCommand("2");
		rdbtnPerformanceC.setBounds(130, 122, 23, 23);
		panelAddEval.add(rdbtnPerformanceC);
		
		JRadioButton rdbtnPerformanceF = new JRadioButton("");
		buttonGroupPerformance.add(rdbtnPerformanceF);
		rdbtnPerformanceF.setActionCommand("0");
		rdbtnPerformanceF.setBounds(155, 122, 23, 23);
		panelAddEval.add(rdbtnPerformanceF);
		
		JRadioButton rdbtnAttitudeA = new JRadioButton("");
		buttonGroupAttitude.add(rdbtnAttitudeA);
		rdbtnAttitudeA.setActionCommand("4");
		rdbtnAttitudeA.setBounds(253, 125, 23, 23);
		panelAddEval.add(rdbtnAttitudeA);
		
		JRadioButton rdbtnAttitudeB = new JRadioButton("");
		buttonGroupAttitude.add(rdbtnAttitudeB);
		rdbtnAttitudeB.setActionCommand("3");
		rdbtnAttitudeB.setBounds(278, 126, 23, 23);
		panelAddEval.add(rdbtnAttitudeB);
		
		JRadioButton rdbtnAttitudeC = new JRadioButton("");
		buttonGroupAttitude.add(rdbtnAttitudeC);
		rdbtnAttitudeC.setActionCommand("2");
		rdbtnAttitudeC.setBounds(303, 125, 23, 23);
		panelAddEval.add(rdbtnAttitudeC);
		
		JRadioButton rdbtnAttitudeF = new JRadioButton("");
		buttonGroupAttitude.add(rdbtnAttitudeF);
		rdbtnAttitudeF.setActionCommand("0");
		rdbtnAttitudeF.setBounds(328, 125, 23, 23);
		panelAddEval.add(rdbtnAttitudeF);
		
		JRadioButton rdbtnAttireA = new JRadioButton("");
		buttonGroupAttire.add(rdbtnAttireA);
		rdbtnAttireA.setActionCommand("4");
		rdbtnAttireA.setBounds(436, 124, 23, 23);
		panelAddEval.add(rdbtnAttireA);
		
		JRadioButton rdbtnAttireB = new JRadioButton("");
		buttonGroupAttire.add(rdbtnAttireB);
		rdbtnAttireB.setActionCommand("3");
		rdbtnAttireB.setBounds(461, 124, 23, 23);
		panelAddEval.add(rdbtnAttireB);
		
		JRadioButton rdbtnAttireC = new JRadioButton("");
		buttonGroupAttire.add(rdbtnAttireC);
		rdbtnAttireC.setActionCommand("2");
		rdbtnAttireC.setBounds(486, 124, 23, 23);
		panelAddEval.add(rdbtnAttireC);
		
		JRadioButton rdbtnAttireF = new JRadioButton("");
		buttonGroupAttire.add(rdbtnAttireF);
		rdbtnAttireF.setActionCommand("0");
		rdbtnAttireF.setBounds(511, 124, 23, 23);
		panelAddEval.add(rdbtnAttireF);
		
		JLabel lblAttitude = new JLabel("Attitude");
		lblAttitude.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAttitude.setBounds(183, 124, 46, 14);
		panelAddEval.add(lblAttitude);
		
		JLabel lblAttire = new JLabel("Attire");
		lblAttire.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAttire.setBounds(366, 126, 30, 14);
		panelAddEval.add(lblAttire);
		
		JLabel lblComments = new JLabel("Comments");
		lblComments.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblComments.setBounds(176, 75, 66, 14);
		panelAddEval.add(lblComments);
		
		textFieldAddEvalComments = new JTextField();
		textFieldAddEvalComments.setColumns(10);
		textFieldAddEvalComments.setBounds(229, 75, 305, 20);
		panelAddEval.add(textFieldAddEvalComments);
		
		JLabel lblA = new JLabel("A");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblA.setBounds(87, 103, 12, 14);
		panelAddEval.add(lblA);
		
		JLabel lblB = new JLabel("B");
		lblB.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblB.setBounds(112, 103, 12, 14);
		panelAddEval.add(lblB);
		
		JLabel lblC = new JLabel("C");
		lblC.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblC.setBounds(137, 103, 12, 14);
		panelAddEval.add(lblC);
		
		JLabel lblD = new JLabel("F");
		lblD.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblD.setBounds(161, 103, 12, 14);
		panelAddEval.add(lblD);
		
		JLabel label_1 = new JLabel("A");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(261, 106, 12, 14);
		panelAddEval.add(label_1);
		
		JLabel label_2 = new JLabel("B");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_2.setBounds(286, 106, 12, 14);
		panelAddEval.add(label_2);
		
		JLabel label_3 = new JLabel("C");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_3.setBounds(311, 106, 12, 14);
		panelAddEval.add(label_3);
		
		JLabel label_4 = new JLabel("F");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_4.setBounds(335, 106, 12, 14);
		panelAddEval.add(label_4);
		
		JLabel label_5 = new JLabel("A");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_5.setBounds(444, 103, 12, 14);
		panelAddEval.add(label_5);
		
		JLabel label_6 = new JLabel("B");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_6.setBounds(469, 103, 12, 14);
		panelAddEval.add(label_6);
		
		JLabel label_7 = new JLabel("C");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_7.setBounds(494, 103, 12, 14);
		panelAddEval.add(label_7);
		
		JLabel label_8 = new JLabel("F");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_8.setBounds(518, 103, 12, 14);
		panelAddEval.add(label_8);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(22, 197, 514, 104);
		panelAddEval.add(scrollPane_2);
		
		JTextArea textAreaAddEval = new JTextArea();
		textAreaAddEval.setEditable(false);
		scrollPane_2.setViewportView(textAreaAddEval);
		
		textFieldAddEvalEvent = new JTextField();
		textFieldAddEvalEvent.setBounds(47, 34, 86, 20);
		panelAddEval.add(textFieldAddEvalEvent);
		textFieldAddEvalEvent.setColumns(10);
		
		JComboBox<Object> comboBoxAddEvalPosition = new JComboBox<Object>(positions);
		comboBoxAddEvalPosition.setFont(new Font("Tahoma", Font.PLAIN, 10));
		comboBoxAddEvalPosition.setBounds(461, 34, 108, 20);
		panelAddEval.add(comboBoxAddEvalPosition);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
				if ( textFieldAddEvalEmpID.getText().isEmpty() || isNotNum(textFieldAddEvalEmpID.getText()) ) {
					JOptionPane.showMessageDialog(frame, "Please enter an employee ID number.",
						    "Message",
						    JOptionPane.WARNING_MESSAGE);
				} 
				
				else if ( doesExist(Integer.parseInt(textFieldAddEvalEmpID.getText())) == -1) {
					JOptionPane.showMessageDialog(frame, "That user doesn't exist!",
						    "Message",
						    JOptionPane.WARNING_MESSAGE);
				}
				
				else if ( isDuplicateEval(Integer.parseInt(textFieldAddEvalEmpID.getText())) ){
					JOptionPane.showMessageDialog(frame, "This is a duplicate evaluation!",
						    "Message",
						    JOptionPane.WARNING_MESSAGE);
				} 
				
				else if ( addEvalFieldsEmpty() ) {
					JOptionPane.showMessageDialog(frame, "Please ensure you filled out all items!",
						    "Message",
						    JOptionPane.WARNING_MESSAGE);
				} 
				
				else if ( isNotValidDate(textFieldAddEvalDate.getText()) ) {
					JOptionPane.showMessageDialog(frame, "Invalid date, please enter date in MM/dd/yy format!",
						    "Message",
						    JOptionPane.WARNING_MESSAGE);
				}
				
				else {
						String line = textFieldAddEvalEmpID.getText() + "," + textFieldAddEvalEvent.getText().replace(",", "") + "," + 
								textFieldAddEvalSupervisor.getText().replace(",", "") + "," + textFieldAddEvalDate.getText().replace(",", "") + "," +
								comboBoxAddEvalPosition.getSelectedItem() + "," +
								buttonGroupPerformance.getSelection().getActionCommand() + "," + 
								buttonGroupAttitude.getSelection().getActionCommand() + "," + 
								buttonGroupAttire.getSelection().getActionCommand() + "," + 
								textFieldAddEvalComments.getText().replace(",", "") + ",";
						System.out.println("Eval Added: " + line + "\n");
						tempEvalList.add(new Evaluation(line));
						textAreaAddEval.append(line + "\n");
						textFieldAddEvalEmpID.setText("");
						textFieldAddEvalComments.setText("");
						buttonGroupPerformance.clearSelection();
						buttonGroupAttitude.clearSelection();
						buttonGroupAttire.clearSelection();
				}
		    }

				private boolean addEvalFieldsEmpty() {
					if ( textFieldAddEvalEmpID.getText().isEmpty() || textFieldAddEvalEvent.getText().isEmpty() 
							|| textFieldAddEvalSupervisor.getText().isEmpty() || textFieldAddEvalDate.getText().isEmpty()
							|| buttonGroupPerformance.getSelection() == null || buttonGroupAttitude.getSelection() == null
							|| buttonGroupAttire.getSelection() == null || textFieldAddEvalComments.getText().isEmpty() ) {
						return true;
					}
					
					return false;
				}

				private boolean isDuplicateEval(int text) {
					for (Evaluation obj: tempEvalList) {
						System.out.println("Previously Added Eval: " + obj.toString() + "\n" + text + "\n");
						if ( obj.empNum == text ) {
							return true;
						}
					}
					return false;
				}
			
		});
		btnAdd.setBounds(342, 163, 89, 23);
		panelAddEval.add(btnAdd);
	
		JLabel lblPosition = new JLabel("Position");
		lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPosition.setBounds(419, 37, 37, 14);
		panelAddEval.add(lblPosition);
		
		JButton btnSave_2 = new JButton("Save");
		btnSave_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evalList.addAll(tempEvalList);
				tempEvalList.clear();
				textAreaAddEval.setText(null);
				saveList(evalList, evalFileName);
			}
		});
		btnSave_2.setBounds(346, 309, 89, 23);
		panelAddEval.add(btnSave_2);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < tempEvalList.size(); i++)  {
					if ( tempEvalList.get(i).empNum == Integer.parseInt(textFieldAddEvalEmpID.getText()) ) {
						tempEvalList.remove(i);
						displayEmpEvaluations(tempEvalList, textAreaAddEval);
					}
					else {
						JOptionPane.showMessageDialog(frame, "That evaluation doesn't exist!",
							    "Message",
							    JOptionPane.WARNING_MESSAGE);
					}
						
				}
			}
		});
		btnDelete.setBounds(441, 163, 89, 23);
		panelAddEval.add(btnDelete);
		

	}

	protected static boolean isNotValidDate(String dateString) {
	    SimpleDateFormat df = new SimpleDateFormat("M/d/yy");
	    df.setLenient(false);
	    try {
	        df.parse(dateString);
	        return false;
	    } catch (ParseException e) {
	        return true;
	    }
	}

	protected static void isEmpEval(int i) {
		tempEvalList.clear();
		for (Evaluation obj: evalList) {
			if (obj.empNum == i) {
				tempEvalList.add(obj);
			}
		}
		
	}

	// Catch rather or not an input is a number.
	protected static boolean isNotNum(String str) {
	    if (str == null) {
	        return true;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return true;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return true;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return true;
	        }
	    }
	    return false;
	}
	// Review list of employees, return location if employee ID matches, else return -1
	// indicating no such employee exists. 
	private static int doesExist(int empID) {
		for (int i = 0; i < list.size(); i++) {
			if ( empID == list.get(i).getEmpNumb())
				return i;
		}
		return -1;
	}
	
	// Attempts to open up "database" file, if it doesn't exist, warn user
	// if it does exist, load employee list.
	private static void initEmpList () throws IOException {
		String line = null;
		Scanner input = new Scanner(System.in);
		
		
		   
		try {
			FileReader fileReader = new FileReader (empFileName);
			   
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			   
			while( ( line = bufferedReader.readLine()) != null ) {
					if ( ! line.startsWith("//") )
						list.add(new Employee(line, positions.length));
			}
			bufferedReader.close();
			input.close();
			Collections.sort(list, NAMES);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "The employee database doesn't exist, please input new items"
					+ " or check that the file is in the right location.",
				    "Message",
				    JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	private static void initEvalList () throws IOException {
		String line = null;
		
		try {
			FileReader fileReader = new FileReader (evalFileName);
			   
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			   
			while( ( line = bufferedReader.readLine()) != null ) {
					evalList.add(new Evaluation(line));
			}
			bufferedReader.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "The evaluation database doesn't exist, please input new items"
					+ " or check that the file is in the right location.",
				    "Message",
				    JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	static final Comparator<Employee> NAMES = 
            new Comparator<Employee>() {
				public int compare(Employee e1, Employee e2) {
				return e1.name().toLowerCase().compareTo(e2.name().toLowerCase());
				}
	};
	private static JFormattedTextField textFieldEmpID;
	private static JTextField textFieldEmpName;
	private static JTextField textFieldEmpTelNum;
	private static JFormattedTextField textFieldEvalEmpID;
	private static JTextField textFieldAddEvalSupervisor;
	private static JTextField textFieldAddEvalDate;
	private static JTextField textFieldAddEvalEmpID;
	private final static ButtonGroup buttonGroupPerformance = new ButtonGroup();
	private final static ButtonGroup buttonGroupAttitude = new ButtonGroup();
	private final static ButtonGroup buttonGroupAttire = new ButtonGroup();
	private static JTextField textFieldAddEvalComments;
	private static JTextField textFieldAddEvalEvent;
	
	static void displayPosition(JTextArea textAreaPosList) {
		textAreaPosList.setText(null);
		tempEmpList.clear();
		textAreaPosList.append("List of " + positions[position] + "s:\n");
		for (int i = 0; i < list.size(); i++) {
			if ( list.get(i).isPosition(position) ) {
				textAreaPosList.append(list.get(i).print() + "\n");
				System.out.println(list.get(i).print());
				tempEmpList.add(list.get(i));
			}	
		}
	}
	
	static void displayEmpEvaluations(List<Evaluation> list, JTextArea textAreaEvalList) {
		textAreaEvalList.setText(null);
		for (Evaluation obj: list) {
				textAreaEvalList.append(obj.toString() + "\n");
		}
	}
	
	static void saveList(@SuppressWarnings("rawtypes") List list, String fileName) {
		int i = 0;
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));
			if ( fileName == empFileName	)
				out.println("// Emp ID, Name, Phone #, Shirt Size, A U, A TT, A Sup, A Sec, A R, A BC, CC TT, CC Sup,CC U, CC Sec, I Sup, I U, I TT, I Sec,I SD, P S, P C, REC, Lincoln Center, WSU, EWU,Seattle, Northern Quest, Mill Bay,");
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
