package com.dwardu.KNN.UI;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import com.dwardu.KNN.KNearestNeighbour;
import com.dwardu.KNN.Objects.Iris;
import com.dwardu.KNN.Objects.IrisDistanceConfig;

public class ApplicationWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationWindow window = new ApplicationWindow();
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
	public ApplicationWindow() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		final JTextArea txtFilePath = new JTextArea();
		txtFilePath.setEditable(false);
		txtFilePath.setBounds(10, 10, 290, 22);
		frame.getContentPane().add(txtFilePath);

		JButton btnOpenIrisData = new JButton("Open Iris Data");
		btnOpenIrisData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create a file chooser
				final JFileChooser fc = new JFileChooser();

				// In response to a button click:
				int returnVal = fc.showOpenDialog(frame);
				switch (returnVal) {
				case JFileChooser.APPROVE_OPTION:
					txtFilePath.setText(fc.getSelectedFile().getAbsoluteFile()
							.toString());
					break;

				default:
					break;
				}
			}
		});
		btnOpenIrisData.setBounds(310, 11, 114, 23);
		frame.getContentPane().add(btnOpenIrisData);

		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showSaveDialog(frame);

				List<Double> validationErrors = new ArrayList<Double>();
				List<IrisDistanceConfig> configs = new ArrayList<IrisDistanceConfig>();
				configs.add(new IrisDistanceConfig(true, true, true, true));

				configs.add(new IrisDistanceConfig(false, true, true, true));
				configs.add(new IrisDistanceConfig(true, false, true, true));
				configs.add(new IrisDistanceConfig(true, true, false, true));
				configs.add(new IrisDistanceConfig(true, true, true, false));

				configs.add(new IrisDistanceConfig(false, false, true, true));
				configs.add(new IrisDistanceConfig(false, true, false, true));
				configs.add(new IrisDistanceConfig(false, true, true, false));

				configs.add(new IrisDistanceConfig(false, false, true, true));
				configs.add(new IrisDistanceConfig(true, false, false, true));
				configs.add(new IrisDistanceConfig(true, false, true, false));

				configs.add(new IrisDistanceConfig(false, true, false, true));
				configs.add(new IrisDistanceConfig(true, false, false, true));
				configs.add(new IrisDistanceConfig(true, true, false, false));

				for (IrisDistanceConfig config : configs) {

					try {

						String irisPath = txtFilePath.getText();
						if (irisPath != "") {
							// Load the iris data
							Iris irisList[];
							for (int k = 1; k <= 150; k++) {
								irisList = Iris.openCsv(irisPath);
								Iris classifyMe[] = Iris.openCsv(irisPath);
								KNearestNeighbour nearestNeighbour = new KNearestNeighbour(
										k, irisList);
								classifyMe = nearestNeighbour.classifyIris(
										classifyMe, config.isSepalLength(),
										config.isSepalWidth(),
										config.isPetalLength(),
										config.isPetalWidth());

								validationErrors.add(nearestNeighbour
										.getValidationError(classifyMe));
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < validationErrors.size(); i++) {
						sb.append(validationErrors.get(i) + "\n");
					}
					String test = fc.getSelectedFile().getPath()
							+ "\\Results\\result-" + config.toString() + ".csv";
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(
								fc.getSelectedFile().getAbsolutePath()
										+ "\\Results\\result-"
										+ config.toString() + ".csv"));
						out.write(sb.toString());
						out.close();
					} catch (IOException e) {
						String error = e.getMessage();
					}
				}
			}
		});
		btnNewButton.setBounds(310, 142, 114, 23);
		frame.getContentPane().add(btnNewButton);
	}

	private static Component getComponentById(final Container container,
			final String componentId) {

		if (container.getComponents().length > 0) {
			for (Component c : container.getComponents()) {
				if (componentId.equals(c.getName())) {
					return c;
				}
				if (c instanceof Container) {
					return getComponentById((Container) c, componentId);
				}
			}
		}

		return null;
	}
}
