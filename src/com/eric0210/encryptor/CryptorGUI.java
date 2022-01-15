package com.eric0210.encryptor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import com.eric0210.encryptor.exceptions.ArgumentException;
import com.eric0210.encryptor.exceptions.UnexpectedException;
import com.eric0210.encryptor.exceptions.WrongKeyException;
import com.eric0210.encryptor.tabs.DecryptTab;
import com.eric0210.encryptor.tabs.EncryptTab;
import com.eric0210.encryptor.tabs.IOTab;
import com.eric0210.encryptor.tabs.OutputTab;
import javax.swing.JPanel;
import javax.swing.JButton;

public class CryptorGUI extends JFrame
{
	private static final long serialVersionUID = -664788343042829558L;
	private static final Dimension defaultDim = new Dimension(640, 500);

	public static void main(String[] args)
	{
		try
		{
			new CryptorGUI();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public CryptorGUI()
	{
		setTitle("Eric's Advanced Java Encrypt/Decrypt tool");
		setSize(new Dimension(1200, 778));
		setMinimumSize(defaultDim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		getContentPane().setLayout(null);
		JTabbedPane tabs = new JTabbedPane();
		getContentPane().add(tabs, BorderLayout.CENTER);
		IOTab iotab;
		tabs.addTab("Input/Output", iotab = new IOTab());
		EncryptTab encryptiontab;
		tabs.addTab("Encryption", encryptiontab = new EncryptTab());
		DecryptTab decryptiontab;
		tabs.addTab("Decryption", decryptiontab = new DecryptTab());
		OutputTab outputtab;
		tabs.addTab("Console", outputtab = new OutputTab());
		JPanel toolbarPanel = new JPanel();
		getContentPane().add(toolbarPanel, BorderLayout.SOUTH);
		toolbarPanel.setLayout(new BorderLayout(0, 0));
		JPanel processButtonPanel = new JPanel();
		toolbarPanel.add(processButtonPanel, BorderLayout.EAST);
		JButton processButton = new JButton("Process");
		processButton.setToolTipText("Execute specified actions.");
		processButton.addActionListener((e) -> {
			processButton.setText("Processing...");
			System.out.println("[  Started operation " + (encryptiontab.selected() ? "encryption" : "decryption")
					+ " at " + new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.SSS").format(new Date()) + "  ]");
			processButton.setEnabled(false);
			new SwingWorker<Object, Object>()
			{
				@Override
				public Object doInBackground()
				{
					try
					{
						if (encryptiontab.selected())
						{
							SessionInfo info = new SessionInfo();
							info.input = new File(iotab.getInputPath());
							if (!info.input.exists())
							{
								broadcastError("Exception", "The input file is doesn't exists.", new FileNotFoundException());
								return null;
							}
							info.encrypt = true;
							info.output = new File(iotab.getOutputPath());
							info.calgorithm = encryptiontab.getCipherAlgorithm();
							info.cmode = encryptiontab.getCipherMode();
							info.cpadding = encryptiontab.getCipherPadding();
							info.bitblocksize = encryptiontab.getCipherBitBlockSize();
							info.useCustomKey = encryptiontab.useCustomKey();
							info.customKeyPath = encryptiontab.getCustomKeyFilePath();
							info.encode_output = encryptiontab.encodeOutput();
							info.excludemp = encryptiontab.isExcludeModeAndPadding();
							info.usePublic = encryptiontab.usePublicToEncrypt();
							info.decode_custom_key = encryptiontab.decodeCustomKeyWithBase64();
							new Cryptor(info).execute();
							if (info.generatedPublic != null)
								encryptiontab.setGeneratedPublicKey(info.generatedPublic);
							if (info.generatedPrivate != null)
								encryptiontab.setGeneratedPrivateKey(info.generatedPrivate);
							if (info.generatedSecret != null)
								encryptiontab.setGeneratedSecretKey(info.generatedSecret);
						}
						else if (decryptiontab.selected())
						{
							SessionInfo info = new SessionInfo();
							info.input = new File(iotab.getInputPath());
							if (!info.input.exists())
							{
								broadcastError("Exception", "The input file is doesn't exists.", new FileNotFoundException());
								return null;
							}
							info.encrypt = false;
							info.output = new File(iotab.getOutputPath());
							info.calgorithm = decryptiontab.getCipherAlgorithm();
							info.cmode = decryptiontab.getCipherMode();
							info.cpadding = decryptiontab.getCipherPadding();
							info.bitblocksize = decryptiontab.getCipherBitBlockSize();
							info.customKeyPath = decryptiontab.getCustomKeyFilePath();
							info.excludemp = decryptiontab.isExcludeModeAndPadding();
							info.decode_custom_key = decryptiontab.decodeCustomKey();
							info.decode_input = decryptiontab.decodeInput();
							new Cryptor(info).execute();
						}
						JOptionPane.showMessageDialog(null, "Processed successfully.", "Done",
								JOptionPane.INFORMATION_MESSAGE);
					}
					catch (WrongKeyException t)
					{
						broadcastError("Exception", "Wrong or invalid key!", t);
					}
					catch (ArgumentException t)
					{
						broadcastError("Exception", "Illegal or invalid argument detected during operation", t);
					}
					catch (UnexpectedException t)
					{
						broadcastError("Exception", "Unexpected exception during operation", t);
					}
					finally
					{
						processButton.setText("Process");
						processButton.setEnabled(true);
					}
					return null;
				}
			}.execute();
		});
		processButtonPanel.add(processButton);
		pack();
		setVisible(true);
	}

	public static final void broadcastError(String titlebar, String desc, Throwable t)
	{
		JOptionPane.showMessageDialog(null, desc, titlebar, JOptionPane.ERROR_MESSAGE);
		System.err.println("ERROR: " + desc + (t != null ? ": " + t.getMessage() : ""));
		if (t != null)
			t.fillInStackTrace().printStackTrace(System.err);
	}
}
