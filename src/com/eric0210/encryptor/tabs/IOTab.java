package com.eric0210.encryptor.tabs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class IOTab extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1006217998731895419L;
	private JTextField inputField;
	private JTextField outputField;
	private File lastPath;

	public IOTab()
	{
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] {
				0, 0
		};
		gbl_this.rowHeights = new int[] {
				0, 378, 0
		};
		gbl_this.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gbl_this.rowWeights = new double[] {
				0.0, 1.0, Double.MIN_VALUE
		};
		this.setLayout(gbl_this);
		JPanel inputOutputPanel = new JPanel();
		GridBagConstraints gbc_inputOutputPanel = new GridBagConstraints();
		gbc_inputOutputPanel.insets = new Insets(0, 0, 5, 0);
		gbc_inputOutputPanel.fill = GridBagConstraints.BOTH;
		gbc_inputOutputPanel.gridx = 0;
		gbc_inputOutputPanel.gridy = 0;
		inputOutputPanel.setBorder(new TitledBorder("Input-Output"));
		this.add(inputOutputPanel, gbc_inputOutputPanel);
		GridBagLayout gbl_inputOutputPanel = new GridBagLayout();
		gbl_inputOutputPanel.columnWidths = new int[] {
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
		};
		gbl_inputOutputPanel.rowHeights = new int[] {
				0, 0, 0
		};
		gbl_inputOutputPanel.columnWeights = new double[] {
				0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE
		};
		gbl_inputOutputPanel.rowWeights = new double[] {
				0.0, 0.0, Double.MIN_VALUE
		};
		inputOutputPanel.setLayout(gbl_inputOutputPanel);
		JLabel inputLabel = new JLabel("Input:");
		GridBagConstraints gbc_inputLabel = new GridBagConstraints();
		gbc_inputLabel.anchor = GridBagConstraints.EAST;
		gbc_inputLabel.insets = new Insets(5, 5, 5, 5);
		gbc_inputLabel.gridx = 0;
		gbc_inputLabel.gridy = 0;
		inputOutputPanel.add(inputLabel, gbc_inputLabel);
		this.inputField = new JTextField();
		GridBagConstraints gbc_inputField = new GridBagConstraints();
		gbc_inputField.gridwidth = 17;
		gbc_inputField.insets = new Insets(5, 0, 5, 5);
		gbc_inputField.fill = GridBagConstraints.BOTH;
		gbc_inputField.gridx = 1;
		gbc_inputField.gridy = 0;
		inputOutputPanel.add(inputField, gbc_inputField);
		inputField.setColumns(10);
		JButton inputButton = new JButton("Select");
		GridBagConstraints gbc_inputButton = new GridBagConstraints();
		gbc_inputButton.fill = GridBagConstraints.BOTH;
		gbc_inputButton.insets = new Insets(5, 0, 5, 5);
		gbc_inputButton.gridx = 18;
		gbc_inputButton.gridy = 0;
		inputButton.addActionListener((e) -> {
			JFileChooser chooser = new JFileChooser();
			if (inputField.getText() != null && !inputField.getText().isEmpty())
			{
				chooser.setSelectedFile(new File(inputField.getText()));
			}
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (lastPath != null)
				chooser.setCurrentDirectory(lastPath);
			int result = chooser.showOpenDialog(this);
			if (result == 0)
			{
				SwingUtilities.invokeLater(() -> {
					inputField.setText(chooser.getSelectedFile().getAbsolutePath());
					lastPath = chooser.getSelectedFile();
				});
			}
		});
		inputOutputPanel.add(inputButton, gbc_inputButton);
		JLabel outputLabel = new JLabel("Output:");
		GridBagConstraints gbc_outputLabel = new GridBagConstraints();
		gbc_outputLabel.anchor = GridBagConstraints.EAST;
		gbc_outputLabel.insets = new Insets(0, 5, 5, 5);
		gbc_outputLabel.gridx = 0;
		gbc_outputLabel.gridy = 1;
		inputOutputPanel.add(outputLabel, gbc_outputLabel);
		this.outputField = new JTextField();
		GridBagConstraints gbc_outputField = new GridBagConstraints();
		gbc_outputField.gridwidth = 17;
		gbc_outputField.insets = new Insets(0, 0, 5, 5);
		gbc_outputField.fill = GridBagConstraints.BOTH;
		gbc_outputField.gridx = 1;
		gbc_outputField.gridy = 1;
		inputOutputPanel.add(outputField, gbc_outputField);
		outputField.setColumns(10);
		JButton outputButton = new JButton("Select");
		GridBagConstraints gbc_outputButton = new GridBagConstraints();
		gbc_outputButton.fill = GridBagConstraints.BOTH;
		gbc_outputButton.insets = new Insets(0, 0, 5, 5);
		gbc_outputButton.gridx = 18;
		gbc_outputButton.gridy = 1;
		outputButton.addActionListener((e) -> {
			JFileChooser chooser = new JFileChooser();
			if (outputField.getText() != null && !outputField.getText().isEmpty())
			{
				chooser.setSelectedFile(new File(outputField.getText()));
			}
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (lastPath != null)
				chooser.setCurrentDirectory(lastPath);
			int result = chooser.showOpenDialog(this);
			if (result == 0)
			{
				SwingUtilities.invokeLater(() -> {
					outputField.setText(chooser.getSelectedFile().getAbsolutePath());
					lastPath = chooser.getSelectedFile();
				});
			}
		});
		inputOutputPanel.add(outputButton, gbc_outputButton);
	}

	/**
	 * Gets and returns the specified input file path as a {@link String}.
	 *
	 * @return the specified input file path as a {@link String}.
	 */
	public String getInputPath()
	{
		return this.inputField.getText();
	}

	/**
	 * Gets and returns the specified output file path as a {@link String}.
	 *
	 * @return the specified output file path as a {@link String}.
	 */
	public String getOutputPath()
	{
		return this.outputField.getText();
	}
}
