package com.eric0210.encryptor.tabs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.eric0210.encryptor.Constants;

public class OutputTab extends JPanel
{
	private static final long serialVersionUID = -4790960903845232214L;
	private JTextArea consoleTextArea;

	public OutputTab()
	{
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] {
				0, 0
		};
		gbl_this.rowHeights = new int[] {
				0, 0
		};
		gbl_this.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gbl_this.rowWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		this.setBorder(new TitledBorder("Output"));
		this.setLayout(gbl_this);
		JScrollPane consoleScrollPane = new JScrollPane();
		GridBagConstraints gbc_consoleScrollPane = new GridBagConstraints();
		gbc_consoleScrollPane.fill = GridBagConstraints.BOTH;
		gbc_consoleScrollPane.gridx = 0;
		gbc_consoleScrollPane.gridy = 0;
		this.add(consoleScrollPane, gbc_consoleScrollPane);
		consoleTextArea = new JTextArea();
		consoleTextArea.setEditable(false);
		StringBuilder bw = new StringBuilder();
		bw.append("##############################################\n");
		bw.append("Eric's Advanced Java Cryptor\n");
		bw.append("##############################################\n");
		bw.append("\n");
		bw.append("\n");
		bw.append("Version: ").append(Constants.VERSION).append('\n');
		bw.append("Contributors: ").append(Constants.CONTRIBUTORS).append('\n');
		consoleTextArea.setText(bw.toString());
		consoleScrollPane.setViewportView(consoleTextArea);
		PrintStream customPrintStream = new PrintStream(new OutputStreamRedirect(consoleTextArea));
		System.setOut(customPrintStream);
		System.setErr(customPrintStream);
	}

	/**
	 * Custom {@link OutputStream}.
	 */
	class OutputStreamRedirect extends OutputStream
	{
		/**
		 * {@link JTextArea} System.out and System.err is redirected to.
		 */
		private JTextArea consoleOutput;

		private OutputStreamRedirect(JTextArea consoleOutput)
		{
			this.consoleOutput = consoleOutput;
		}

		@Override
		public void write(int b)
		{
			this.consoleOutput.append(String.valueOf((char) b));
			this.consoleOutput.setCaretPosition(this.consoleOutput.getDocument().getLength());
		}
	}

	/**
	 * Clears the {@link JTextArea} System.out and System.err is redirected to.
	 */
	public void reset()
	{
		consoleTextArea.setText(null);
	}
}
