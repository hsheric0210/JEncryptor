package com.eric0210.encryptor.tabs;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.eric0210.encryptor.Constants.CipherAlgorithm;
import com.eric0210.encryptor.Constants.CipherMode;
import com.eric0210.encryptor.Constants.CipherPadding;

public class EncryptTab extends JPanel
{
	private static final long serialVersionUID = 215253624824651650L;
	private static JCheckBox enabledButton;
	private JPanel encryption_settings_panel;
	private JPanel encryption_mode_panel;
	private JComboBox<CipherAlgorithm> encryption_algorithm_select;
	private JComboBox<CipherMode> encryption_mode_select;
	private JPanel encryption_padding_panel;
	private JComboBox<CipherPadding> encryption_padding_select;
	private JPanel encryption_bit_block_size_panel;
	private JSpinner encryption_bit_block_size_select;
	private JPanel encryption_cipher_settings_panel;
	private JPanel encryption_base64_option_panel;
	private JCheckBox encryption_encode_output_base64;
	private JCheckBox encryption_use_custom_key;
	private JPanel encryption_custom_key_panel;
	private File encryption_custom_key_path_cache;
	private JTextField private_key_area;
	private JButton privkeysave2file;
	private JLabel custom_key_label;
	private JTextField custom_key;
	private JButton custom_key_select_button;
	private JPanel encryption_pubkeypanel;
	private JTextField public_key_area;
	private JButton publickeysave2file;
	private JPanel encryption_secretkeypanel;
	private JTextField secret_key_area;
	private JButton secretkeysave2file;
	private JPanel encryption_privkeypanel;
	private JCheckBox excludemp;
	private byte[] public_key;
	private byte[] private_key;
	private byte[] secret_key;
	private byte[] public_key_encoded;
	private byte[] private_key_encoded;
	private byte[] secret_key_encoded;
	private JCheckBox encode_public_key;
	private JCheckBox encode_private_key;
	private JCheckBox encode_secret_key;
	private JPanel encasymmetrickeyoptpanel;
	private JRadioButton usepublic;
	private JRadioButton useprivate;
	private JCheckBox decodebase64;

	/**
	 * Create the panel.
	 */
	public EncryptTab()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {
				0, 0
		};
		gridBagLayout.rowHeights = new int[] {
				0, 231, 0, 0, 0, 0, 0
		};
		gridBagLayout.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gridBagLayout.rowWeights = new double[] {
				0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE
		};
		setLayout(gridBagLayout);
		enabledButton = new JCheckBox("enable");
		enabledButton.addActionListener((e) -> {
			if (selected())
				DecryptTab.setSelected(false);
		});
		GridBagConstraints gbc_enabledButton = new GridBagConstraints();
		gbc_enabledButton.insets = new Insets(0, 0, 5, 0);
		gbc_enabledButton.anchor = GridBagConstraints.WEST;
		gbc_enabledButton.gridx = 0;
		gbc_enabledButton.gridy = 0;
		add(enabledButton, gbc_enabledButton);
		encryption_settings_panel = new JPanel();
		encryption_settings_panel.setBorder(
				new TitledBorder(null, "Encryption Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_encsettingspanel = new GridBagConstraints();
		gbc_encsettingspanel.anchor = GridBagConstraints.NORTH;
		gbc_encsettingspanel.insets = new Insets(0, 0, 5, 0);
		gbc_encsettingspanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_encsettingspanel.gridx = 0;
		gbc_encsettingspanel.gridy = 1;
		add(encryption_settings_panel, gbc_encsettingspanel);
		GridBagLayout gbl_encsettingspanel = new GridBagLayout();
		gbl_encsettingspanel.columnWidths = new int[] {
				438, 0
		};
		gbl_encsettingspanel.rowHeights = new int[] {
				71, 0, 0, 0, 0
		};
		gbl_encsettingspanel.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gbl_encsettingspanel.rowWeights = new double[] {
				1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE
		};
		encryption_settings_panel.setLayout(gbl_encsettingspanel);
		encryption_cipher_settings_panel = new JPanel();
		encryption_cipher_settings_panel.setBorder(
				new TitledBorder(null, "Cipher Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_encciphersettingspanel = new GridBagConstraints();
		gbc_encciphersettingspanel.insets = new Insets(0, 0, 5, 0);
		gbc_encciphersettingspanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_encciphersettingspanel.anchor = GridBagConstraints.NORTH;
		gbc_encciphersettingspanel.gridx = 0;
		gbc_encciphersettingspanel.gridy = 0;
		encryption_settings_panel.add(encryption_cipher_settings_panel, gbc_encciphersettingspanel);
		GridBagLayout gbl_encciphersettingspanel = new GridBagLayout();
		gbl_encciphersettingspanel.columnWidths = new int[] {
				98, 68, 241, 115, 0
		};
		gbl_encciphersettingspanel.rowHeights = new int[] {
				46, 0
		};
		gbl_encciphersettingspanel.columnWeights = new double[] {
				1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE
		};
		gbl_encciphersettingspanel.rowWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		encryption_cipher_settings_panel.setLayout(gbl_encciphersettingspanel);
		JPanel encalgpanel = new JPanel();
		GridBagConstraints gbc_encalgpanel = new GridBagConstraints();
		gbc_encalgpanel.fill = GridBagConstraints.BOTH;
		gbc_encalgpanel.insets = new Insets(0, 0, 0, 5);
		gbc_encalgpanel.gridx = 0;
		gbc_encalgpanel.gridy = 0;
		encryption_cipher_settings_panel.add(encalgpanel, gbc_encalgpanel);
		encalgpanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Encryption Algorithm",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gbl_encalgpanel = new GridBagLayout();
		gbl_encalgpanel.columnWidths = new int[] {
				0, 0
		};
		gbl_encalgpanel.rowHeights = new int[] {
				0, 0
		};
		gbl_encalgpanel.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gbl_encalgpanel.rowWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		encalgpanel.setLayout(gbl_encalgpanel);
		encryption_algorithm_select = new JComboBox<CipherAlgorithm>();
		encryption_algorithm_select.addItemListener((e) -> {
			boolean b = ((CipherAlgorithm) encryption_algorithm_select.getSelectedItem()).isAsymmetricKeyEncryption;
			boolean b2 = !encryption_use_custom_key.isSelected();
			encryption_pubkeypanel.setEnabled(b && b2);
			public_key_area.setEnabled(b && b2);
			publickeysave2file.setEnabled(b && b2);
			encode_public_key.setEnabled(b && b2);
			encryption_pubkeypanel.setEnabled(b && b2);
			encryption_privkeypanel.setEnabled(b && b2);
			private_key_area.setEnabled(b && b2);
			privkeysave2file.setEnabled(b && b2);
			encode_private_key.setEnabled(b && b2);
			encryption_secretkeypanel.setEnabled(!b && b2);
			secret_key_area.setEnabled(!b && b2);
			secretkeysave2file.setEnabled(!b && b2);
			encode_secret_key.setEnabled(!b && b2);
			encasymmetrickeyoptpanel.setEnabled(b && b2);
			usepublic.setEnabled(b && b2);
			useprivate.setEnabled(b && b2);
		});
		GridBagConstraints gbc_encalg = new GridBagConstraints();
		gbc_encalg.fill = GridBagConstraints.HORIZONTAL;
		gbc_encalg.gridx = 0;
		gbc_encalg.gridy = 0;
		encalgpanel.add(encryption_algorithm_select, gbc_encalg);
		encryption_algorithm_select.setModel(new DefaultComboBoxModel<CipherAlgorithm>(CipherAlgorithm.values()));
		encryption_bit_block_size_panel = new JPanel();
		GridBagConstraints gbc_encbitblockpanel = new GridBagConstraints();
		gbc_encbitblockpanel.fill = GridBagConstraints.BOTH;
		gbc_encbitblockpanel.insets = new Insets(0, 0, 0, 5);
		gbc_encbitblockpanel.gridx = 1;
		gbc_encbitblockpanel.gridy = 0;
		encryption_cipher_settings_panel.add(encryption_bit_block_size_panel, gbc_encbitblockpanel);
		encryption_bit_block_size_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Encryption Bit Block Size", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gbl_encbitblockpanel = new GridBagLayout();
		gbl_encbitblockpanel.columnWidths = new int[] {
				56, 0
		};
		gbl_encbitblockpanel.rowHeights = new int[] {
				22, 0
		};
		gbl_encbitblockpanel.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gbl_encbitblockpanel.rowWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		encryption_bit_block_size_panel.setLayout(gbl_encbitblockpanel);
		encryption_bit_block_size_select = new JSpinner();
		GridBagConstraints gbc_encbitblock = new GridBagConstraints();
		gbc_encbitblock.fill = GridBagConstraints.HORIZONTAL;
		gbc_encbitblock.gridx = 0;
		gbc_encbitblock.gridy = 0;
		encryption_bit_block_size_panel.add(encryption_bit_block_size_select, gbc_encbitblock);
		encryption_bit_block_size_select.setModel(new SpinnerNumberModel(128, 1, 8192, 1));
		encryption_mode_panel = new JPanel();
		GridBagConstraints gbc_encmodepanel = new GridBagConstraints();
		gbc_encmodepanel.fill = GridBagConstraints.BOTH;
		gbc_encmodepanel.insets = new Insets(0, 0, 0, 5);
		gbc_encmodepanel.gridx = 2;
		gbc_encmodepanel.gridy = 0;
		encryption_cipher_settings_panel.add(encryption_mode_panel, gbc_encmodepanel);
		encryption_mode_panel.setBorder(
				new TitledBorder(null, "Encryption Mode", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_encmodepanel = new GridBagLayout();
		gbl_encmodepanel.columnWidths = new int[] {
				0, 0
		};
		gbl_encmodepanel.rowHeights = new int[] {
				0, 0
		};
		gbl_encmodepanel.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gbl_encmodepanel.rowWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		encryption_mode_panel.setLayout(gbl_encmodepanel);
		encryption_mode_select = new JComboBox<CipherMode>();
		GridBagConstraints gbc_encmode = new GridBagConstraints();
		gbc_encmode.fill = GridBagConstraints.HORIZONTAL;
		gbc_encmode.gridx = 0;
		gbc_encmode.gridy = 0;
		encryption_mode_panel.add(encryption_mode_select, gbc_encmode);
		encryption_mode_select.setModel(new DefaultComboBoxModel<CipherMode>(CipherMode.values()));
		encryption_padding_panel = new JPanel();
		GridBagConstraints gbc_encpadpanel = new GridBagConstraints();
		gbc_encpadpanel.fill = GridBagConstraints.BOTH;
		gbc_encpadpanel.gridx = 3;
		gbc_encpadpanel.gridy = 0;
		encryption_cipher_settings_panel.add(encryption_padding_panel, gbc_encpadpanel);
		encryption_padding_panel.setBorder(
				new TitledBorder(null, "Encryption Padding", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_encpadpanel = new GridBagLayout();
		gbl_encpadpanel.columnWidths = new int[] {
				0, 0
		};
		gbl_encpadpanel.rowHeights = new int[] {
				0, 0
		};
		gbl_encpadpanel.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gbl_encpadpanel.rowWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		encryption_padding_panel.setLayout(gbl_encpadpanel);
		encryption_padding_select = new JComboBox<CipherPadding>();
		GridBagConstraints gbc_encpad = new GridBagConstraints();
		gbc_encpad.fill = GridBagConstraints.HORIZONTAL;
		gbc_encpad.gridx = 0;
		gbc_encpad.gridy = 0;
		encryption_padding_panel.add(encryption_padding_select, gbc_encpad);
		encryption_padding_select.setModel(new DefaultComboBoxModel<CipherPadding>(CipherPadding.values()));
		encryption_base64_option_panel = new JPanel();
		encryption_base64_option_panel
				.setBorder(new TitledBorder(null, "Extra Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_encusebase64panel = new GridBagConstraints();
		gbc_encusebase64panel.insets = new Insets(0, 0, 5, 0);
		gbc_encusebase64panel.fill = GridBagConstraints.BOTH;
		gbc_encusebase64panel.gridx = 0;
		gbc_encusebase64panel.gridy = 1;
		encryption_settings_panel.add(encryption_base64_option_panel, gbc_encusebase64panel);
		GridBagLayout gbl_encusebase64panel = new GridBagLayout();
		gbl_encusebase64panel.columnWidths = new int[] {
				0, 0
		};
		gbl_encusebase64panel.rowHeights = new int[] {
				0, 0, 0, 0
		};
		gbl_encusebase64panel.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gbl_encusebase64panel.rowWeights = new double[] {
				0.0, 0.0, 0.0, Double.MIN_VALUE
		};
		encryption_base64_option_panel.setLayout(gbl_encusebase64panel);
		excludemp = new JCheckBox("Excludes mode and padding");
		GridBagConstraints gbc_excludemp = new GridBagConstraints();
		gbc_excludemp.anchor = GridBagConstraints.WEST;
		gbc_excludemp.insets = new Insets(0, 0, 5, 0);
		gbc_excludemp.gridx = 0;
		gbc_excludemp.gridy = 0;
		encryption_base64_option_panel.add(excludemp, gbc_excludemp);
		excludemp.addActionListener((e) -> {
			encryption_mode_select.setEnabled(!excludemp.isSelected());
			encryption_padding_select.setEnabled(!excludemp.isSelected());
		});
		encryption_encode_output_base64 = new JCheckBox("Encode output with Base64");
		GridBagConstraints gbc_encusebase64 = new GridBagConstraints();
		gbc_encusebase64.insets = new Insets(0, 0, 5, 0);
		gbc_encusebase64.anchor = GridBagConstraints.WEST;
		gbc_encusebase64.gridx = 0;
		gbc_encusebase64.gridy = 1;
		encryption_base64_option_panel.add(encryption_encode_output_base64, gbc_encusebase64);
		encryption_use_custom_key = new JCheckBox("Encrypt with specified key");
		encryption_use_custom_key.addActionListener((e) -> {
			boolean b = ((CipherAlgorithm) encryption_algorithm_select.getSelectedItem()).isAsymmetricKeyEncryption;
			boolean b2 = !encryption_use_custom_key.isSelected();
			encryption_custom_key_panel.setEnabled(encryption_use_custom_key.isSelected());
			custom_key_label.setEnabled(encryption_use_custom_key.isSelected());
			custom_key.setEnabled(encryption_use_custom_key.isSelected());
			custom_key_select_button.setEnabled(encryption_use_custom_key.isSelected());
			decodebase64.setEnabled(encryption_use_custom_key.isSelected());
			encryption_pubkeypanel.setEnabled(b && b2);
			public_key_area.setEnabled(b && b2);
			publickeysave2file.setEnabled(b && b2);
			encode_public_key.setEnabled(b && b2);
			encryption_pubkeypanel.setEnabled(b && b2);
			encryption_privkeypanel.setEnabled(b && b2);
			private_key_area.setEnabled(b && b2);
			privkeysave2file.setEnabled(b && b2);
			encode_private_key.setEnabled(b && b2);
			encryption_secretkeypanel.setEnabled(!b && b2);
			secret_key_area.setEnabled(!b && b2);
			secretkeysave2file.setEnabled(!b && b2);
			encode_secret_key.setEnabled(!b && b2);
			encasymmetrickeyoptpanel.setEnabled(b && b2);
			usepublic.setEnabled(b && b2);
			useprivate.setEnabled(b && b2);
		});
		GridBagConstraints gbc_encusecustomkey = new GridBagConstraints();
		gbc_encusecustomkey.anchor = GridBagConstraints.WEST;
		gbc_encusecustomkey.gridx = 0;
		gbc_encusecustomkey.gridy = 2;
		encryption_base64_option_panel.add(encryption_use_custom_key, gbc_encusecustomkey);
		encryption_custom_key_panel = new JPanel();
		encryption_custom_key_panel.setEnabled(false);
		encryption_custom_key_panel.setBorder(
				new TitledBorder(null, "Key Specification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_encexternalkeypanel = new GridBagConstraints();
		gbc_encexternalkeypanel.insets = new Insets(0, 0, 5, 0);
		gbc_encexternalkeypanel.fill = GridBagConstraints.BOTH;
		gbc_encexternalkeypanel.gridx = 0;
		gbc_encexternalkeypanel.gridy = 2;
		encryption_settings_panel.add(encryption_custom_key_panel, gbc_encexternalkeypanel);
		GridBagLayout gbl_encexternalkeypanel = new GridBagLayout();
		gbl_encexternalkeypanel.columnWidths = new int[] {
				0, 0, 0, 0
		};
		gbl_encexternalkeypanel.rowHeights = new int[] {
				0, 0
		};
		gbl_encexternalkeypanel.columnWeights = new double[] {
				0.0, 1.0, 0.0, Double.MIN_VALUE
		};
		gbl_encexternalkeypanel.rowWeights = new double[] {
				Double.MIN_VALUE, 0.0
		};
		encryption_custom_key_panel.setLayout(gbl_encexternalkeypanel);
		custom_key_label = new JLabel("Secret Key or Public Key file");
		custom_key_label.setEnabled(false);
		GridBagConstraints gbc_externalkeylabel = new GridBagConstraints();
		gbc_externalkeylabel.anchor = GridBagConstraints.WEST;
		gbc_externalkeylabel.insets = new Insets(5, 5, 5, 5);
		gbc_externalkeylabel.gridx = 0;
		gbc_externalkeylabel.gridy = 0;
		encryption_custom_key_panel.add(custom_key_label, gbc_externalkeylabel);
		custom_key = new JTextField();
		custom_key.setEnabled(false);
		GridBagConstraints gbc_extrakey = new GridBagConstraints();
		gbc_extrakey.insets = new Insets(5, 0, 5, 5);
		gbc_extrakey.fill = GridBagConstraints.BOTH;
		gbc_extrakey.gridx = 1;
		gbc_extrakey.gridy = 0;
		encryption_custom_key_panel.add(custom_key, gbc_extrakey);
		custom_key.setColumns(10);
		custom_key_select_button = new JButton("Select");
		custom_key_select_button.addActionListener((e) -> {
			JFileChooser chooser = new JFileChooser();
			if (custom_key.getText() != null && !custom_key.getText().isEmpty())
			{
				File f = new File(custom_key.getText());
				if (f.exists())
					chooser.setSelectedFile(f);
			}
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (encryption_custom_key_path_cache != null)
				chooser.setCurrentDirectory(encryption_custom_key_path_cache);
			int result = chooser.showOpenDialog(this);
			if (result == 0)
			{
				SwingUtilities.invokeLater(() -> {
					custom_key.setText(chooser.getSelectedFile().getAbsolutePath());
					encryption_custom_key_path_cache = chooser.getSelectedFile();
				});
			}
		});
		custom_key_select_button.setEnabled(false);
		GridBagConstraints gbc_extrakeyinputbutton = new GridBagConstraints();
		gbc_extrakeyinputbutton.fill = GridBagConstraints.BOTH;
		gbc_extrakeyinputbutton.insets = new Insets(5, 0, 5, 0);
		gbc_extrakeyinputbutton.gridx = 2;
		gbc_extrakeyinputbutton.gridy = 0;
//		custom_key_select_button.addActionListener((e) -> {
//			JFileChooser chooser = new JFileChooser();
//			if (custom_key.getText() != null && !custom_key.getText().isEmpty())
//			{
//				chooser.setSelectedFile(new File(custom_key.getText()));
//			}
//			chooser.setMultiSelectionEnabled(false);
//			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//			if (encryption_custom_key_path_cache != null)
//				chooser.setCurrentDirectory(encryption_custom_key_path_cache);
//			int result = chooser.showOpenDialog(this);
//			if (result == 0)
//			{
//				SwingUtilities.invokeLater(() -> {
//					custom_key.setText(chooser.getSelectedFile().getAbsolutePath());
//					encryption_custom_key_path_cache = chooser.getSelectedFile();
//				});
//			}
//		});
		encryption_custom_key_panel.add(custom_key_select_button, gbc_extrakeyinputbutton);
		decodebase64 = new JCheckBox("Decode key with Base64");
		decodebase64.setEnabled(false);
		GridBagConstraints gbc_decodebase64 = new GridBagConstraints();
		gbc_decodebase64.anchor = GridBagConstraints.WEST;
		gbc_decodebase64.insets = new Insets(0, 0, 0, 5);
		gbc_decodebase64.gridx = 0;
		gbc_decodebase64.gridy = 1;
		encryption_custom_key_panel.add(decodebase64, gbc_decodebase64);
		encasymmetrickeyoptpanel = new JPanel();
		encasymmetrickeyoptpanel.setEnabled(false);
		encasymmetrickeyoptpanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Asymmetric Key Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_encasymmetrickeyoptpanel = new GridBagConstraints();
		gbc_encasymmetrickeyoptpanel.fill = GridBagConstraints.BOTH;
		gbc_encasymmetrickeyoptpanel.gridx = 0;
		gbc_encasymmetrickeyoptpanel.gridy = 3;
		encryption_settings_panel.add(encasymmetrickeyoptpanel, gbc_encasymmetrickeyoptpanel);
		GridBagLayout gbl_encasymmetrickeyoptpanel = new GridBagLayout();
		gbl_encasymmetrickeyoptpanel.columnWidths = new int[] {
				0, 0, 0
		};
		gbl_encasymmetrickeyoptpanel.rowHeights = new int[] {
				0, 0
		};
		gbl_encasymmetrickeyoptpanel.columnWeights = new double[] {
				1.0, 1.0, Double.MIN_VALUE
		};
		gbl_encasymmetrickeyoptpanel.rowWeights = new double[] {
				0.0, Double.MIN_VALUE
		};
		encasymmetrickeyoptpanel.setLayout(gbl_encasymmetrickeyoptpanel);
		usepublic = new JRadioButton("Use Public key to encrypt");
		usepublic.setEnabled(false);
		usepublic.setSelected(true);
		usepublic.addActionListener(e -> {
			useprivate.setSelected(!usepublic.isSelected());
		});
		GridBagConstraints gbc_usepublic = new GridBagConstraints();
		gbc_usepublic.anchor = GridBagConstraints.WEST;
		gbc_usepublic.insets = new Insets(0, 0, 0, 5);
		gbc_usepublic.gridx = 0;
		gbc_usepublic.gridy = 0;
		encasymmetrickeyoptpanel.add(usepublic, gbc_usepublic);
		useprivate = new JRadioButton("Use Private key to encrypt");
		useprivate.setEnabled(false);
		useprivate.addActionListener(e -> {
			usepublic.setSelected(!useprivate.isSelected());
		});
		GridBagConstraints gbc_rdbtnUsePrivateKey = new GridBagConstraints();
		gbc_rdbtnUsePrivateKey.anchor = GridBagConstraints.EAST;
		gbc_rdbtnUsePrivateKey.gridx = 1;
		gbc_rdbtnUsePrivateKey.gridy = 0;
		encasymmetrickeyoptpanel.add(useprivate, gbc_rdbtnUsePrivateKey);
		encryption_pubkeypanel = new JPanel();
		encryption_pubkeypanel.setEnabled(false);
		encryption_pubkeypanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Generated Public Key", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_encpubkeypanel = new GridBagConstraints();
		gbc_encpubkeypanel.insets = new Insets(0, 0, 5, 0);
		gbc_encpubkeypanel.fill = GridBagConstraints.BOTH;
		gbc_encpubkeypanel.gridx = 0;
		gbc_encpubkeypanel.gridy = 2;
		add(encryption_pubkeypanel, gbc_encpubkeypanel);
		GridBagLayout gbl_encpubkeypanel = new GridBagLayout();
		gbl_encpubkeypanel.columnWidths = new int[] {
				0, 0, 0
		};
		gbl_encpubkeypanel.rowHeights = new int[] {
				0, 0, 0
		};
		gbl_encpubkeypanel.columnWeights = new double[] {
				1.0, 0.0, Double.MIN_VALUE
		};
		gbl_encpubkeypanel.rowWeights = new double[] {
				0.0, 0.0, Double.MIN_VALUE
		};
		encryption_pubkeypanel.setLayout(gbl_encpubkeypanel);
		public_key_area = new JTextField();
		public_key_area.setEnabled(false);
		public_key_area.setEditable(false);
		GridBagConstraints gbc_publickey = new GridBagConstraints();
		gbc_publickey.insets = new Insets(0, 0, 5, 5);
		gbc_publickey.fill = GridBagConstraints.HORIZONTAL;
		gbc_publickey.gridx = 0;
		gbc_publickey.gridy = 0;
		encryption_pubkeypanel.add(public_key_area, gbc_publickey);
		public_key_area.setColumns(10);
		publickeysave2file = new JButton("Save to File");
		publickeysave2file.addActionListener((e) -> {
			try
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = chooser.showOpenDialog(this);
				File f = null;
				if (result == 0)
				{
					f = chooser.getSelectedFile();
				}
				if (!f.exists())
					f.createNewFile();
				try (FileOutputStream fos = new FileOutputStream(f))
				{
					fos.write(encode_public_key.isSelected() ? public_key_encoded : public_key);
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error happened while saving key, check the output for details.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		publickeysave2file.setEnabled(false);
		GridBagConstraints gbc_publickeysave2file = new GridBagConstraints();
		gbc_publickeysave2file.insets = new Insets(0, 0, 5, 0);
		gbc_publickeysave2file.gridx = 1;
		gbc_publickeysave2file.gridy = 0;
		encryption_pubkeypanel.add(publickeysave2file, gbc_publickeysave2file);
		encode_public_key = new JCheckBox("Encode key with Base64");
		encode_public_key.setEnabled(false);
		encode_public_key.addActionListener((e) -> {
			public_key_area
					.setText(encode_public_key.isSelected() ? new String(public_key_encoded) : new String(public_key));
		});
		GridBagConstraints gbc_publickeyencodebase64 = new GridBagConstraints();
		gbc_publickeyencodebase64.anchor = GridBagConstraints.WEST;
		gbc_publickeyencodebase64.insets = new Insets(0, 0, 0, 5);
		gbc_publickeyencodebase64.gridx = 0;
		gbc_publickeyencodebase64.gridy = 1;
		encryption_pubkeypanel.add(encode_public_key, gbc_publickeyencodebase64);
		encryption_privkeypanel = new JPanel();
		encryption_privkeypanel.setEnabled(false);
		encryption_privkeypanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Generated Private Key", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_encprivkeypanel = new GridBagConstraints();
		gbc_encprivkeypanel.insets = new Insets(0, 0, 5, 0);
		gbc_encprivkeypanel.fill = GridBagConstraints.BOTH;
		gbc_encprivkeypanel.gridx = 0;
		gbc_encprivkeypanel.gridy = 3;
		add(encryption_privkeypanel, gbc_encprivkeypanel);
		GridBagLayout gbl_encprivkeypanel = new GridBagLayout();
		gbl_encprivkeypanel.columnWidths = new int[] {
				0, 0, 0
		};
		gbl_encprivkeypanel.rowHeights = new int[] {
				0, 0, 0
		};
		gbl_encprivkeypanel.columnWeights = new double[] {
				1.0, 0.0, Double.MIN_VALUE
		};
		gbl_encprivkeypanel.rowWeights = new double[] {
				0.0, 0.0, Double.MIN_VALUE
		};
		encryption_privkeypanel.setLayout(gbl_encprivkeypanel);
		private_key_area = new JTextField();
		private_key_area.setEnabled(false);
		private_key_area.setEditable(false);
		GridBagConstraints gbc_privkey = new GridBagConstraints();
		gbc_privkey.insets = new Insets(0, 0, 5, 5);
		gbc_privkey.fill = GridBagConstraints.HORIZONTAL;
		gbc_privkey.gridx = 0;
		gbc_privkey.gridy = 0;
		encryption_privkeypanel.add(private_key_area, gbc_privkey);
		private_key_area.setColumns(10);
		privkeysave2file = new JButton("Save to File");
		privkeysave2file.setEnabled(false);
		privkeysave2file.addActionListener((e) -> {
			try
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = chooser.showOpenDialog(this);
				File f = null;
				if (result == 0)
				{
					f = chooser.getSelectedFile();
				}
				if (!f.exists())
					f.createNewFile();
				try (FileOutputStream fos = new FileOutputStream(f))
				{
					fos.write(encode_private_key.isSelected() ? private_key_encoded : private_key);
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error happened while saving key, check the output for details.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		GridBagConstraints gbc_privkeysave2file = new GridBagConstraints();
		gbc_privkeysave2file.insets = new Insets(0, 0, 5, 0);
		gbc_privkeysave2file.gridx = 1;
		gbc_privkeysave2file.gridy = 0;
		encryption_privkeypanel.add(privkeysave2file, gbc_privkeysave2file);
		encode_private_key = new JCheckBox("Encode key with Base64");
		encode_private_key.setEnabled(false);
		encode_private_key.addActionListener((e) -> {
			private_key_area.setText(
					encode_private_key.isSelected() ? new String(private_key_encoded) : new String(private_key));
		});
		GridBagConstraints gbc_privkeyencodebase64 = new GridBagConstraints();
		gbc_privkeyencodebase64.anchor = GridBagConstraints.WEST;
		gbc_privkeyencodebase64.insets = new Insets(0, 0, 0, 5);
		gbc_privkeyencodebase64.gridx = 0;
		gbc_privkeyencodebase64.gridy = 1;
		encryption_privkeypanel.add(encode_private_key, gbc_privkeyencodebase64);
		encryption_secretkeypanel = new JPanel();
		encryption_secretkeypanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Generated Secret Key", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_encsecretkeypanel = new GridBagConstraints();
		gbc_encsecretkeypanel.insets = new Insets(0, 0, 5, 0);
		gbc_encsecretkeypanel.fill = GridBagConstraints.BOTH;
		gbc_encsecretkeypanel.gridx = 0;
		gbc_encsecretkeypanel.gridy = 4;
		add(encryption_secretkeypanel, gbc_encsecretkeypanel);
		GridBagLayout gbl_encsecretkeypanel = new GridBagLayout();
		gbl_encsecretkeypanel.columnWidths = new int[] {
				0, 0, 0
		};
		gbl_encsecretkeypanel.rowHeights = new int[] {
				0, 0, 0
		};
		gbl_encsecretkeypanel.columnWeights = new double[] {
				1.0, 0.0, Double.MIN_VALUE
		};
		gbl_encsecretkeypanel.rowWeights = new double[] {
				0.0, 0.0, Double.MIN_VALUE
		};
		encryption_secretkeypanel.setLayout(gbl_encsecretkeypanel);
		secret_key_area = new JTextField();
		secret_key_area.setEditable(false);
		GridBagConstraints gbc_secretkey = new GridBagConstraints();
		gbc_secretkey.insets = new Insets(0, 0, 5, 5);
		gbc_secretkey.fill = GridBagConstraints.HORIZONTAL;
		gbc_secretkey.gridx = 0;
		gbc_secretkey.gridy = 0;
		encryption_secretkeypanel.add(secret_key_area, gbc_secretkey);
		secret_key_area.setColumns(10);
		secretkeysave2file = new JButton("Save to File");
		secretkeysave2file.addActionListener((e) -> {
			try
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = chooser.showOpenDialog(this);
				File f = null;
				if (result == 0)
				{
					f = chooser.getSelectedFile();
				}
				if (!f.exists())
					f.createNewFile();
				try (FileOutputStream fos = new FileOutputStream(f))
				{
					fos.write(encode_secret_key.isSelected() ? secret_key_encoded : secret_key);
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error happened while saving key, check the output for details.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		GridBagConstraints gbc_secretkeysave2file = new GridBagConstraints();
		gbc_secretkeysave2file.insets = new Insets(0, 0, 5, 0);
		gbc_secretkeysave2file.gridx = 1;
		gbc_secretkeysave2file.gridy = 0;
		encryption_secretkeypanel.add(secretkeysave2file, gbc_secretkeysave2file);
		encode_secret_key = new JCheckBox("Encode key with Base64");
		encode_secret_key.addActionListener((e) -> {
			secret_key_area
					.setText(encode_secret_key.isSelected() ? new String(secret_key_encoded) : new String(secret_key));
		});
		GridBagConstraints gbc_secretkeyencodebase64 = new GridBagConstraints();
		gbc_secretkeyencodebase64.anchor = GridBagConstraints.WEST;
		gbc_secretkeyencodebase64.insets = new Insets(0, 0, 0, 5);
		gbc_secretkeyencodebase64.gridx = 0;
		gbc_secretkeyencodebase64.gridy = 1;
		encryption_secretkeypanel.add(encode_secret_key, gbc_secretkeyencodebase64);
	}

	public static void setSelected(boolean b)
	{
		enabledButton.setSelected(b);
	}

	public boolean selected()
	{
		return enabledButton.isSelected();
	}

	public CipherAlgorithm getCipherAlgorithm()
	{
		return (CipherAlgorithm) encryption_algorithm_select.getSelectedItem();
	}

	public CipherMode getCipherMode()
	{
		return (CipherMode) encryption_mode_select.getSelectedItem();
	}

	public CipherPadding getCipherPadding()
	{
		return (CipherPadding) encryption_padding_select.getSelectedItem();
	}

	public int getCipherBitBlockSize()
	{
		return (int) encryption_bit_block_size_select.getValue();
	}

	public boolean useCustomKey()
	{
		return encryption_use_custom_key.isSelected();
	}

	public boolean encodeOutput()
	{
		return encryption_encode_output_base64.isSelected();
	}

	public boolean decodeCustomKeyWithBase64()
	{
		return decodebase64.isSelected();
	}

	public String getCustomKeyFilePath()
	{
		return custom_key.getText();
	}

	public void setGeneratedPublicKey(byte[] key)
	{
		public_key = key.clone();
		public_key_encoded = Base64.getEncoder().encode(key);
		if (encode_public_key.isSelected())
			public_key_area.setText(new String(public_key_encoded));
		else
			public_key_area.setText(new String(public_key));
	}

	public void setGeneratedPrivateKey(byte[] key)
	{
		private_key = key;
		private_key_encoded = Base64.getEncoder().encode(key);
		if (encode_private_key.isSelected())
			private_key_area.setText(new String(private_key_encoded));
		else
			private_key_area.setText(new String(private_key));
	}

	public void setGeneratedSecretKey(byte[] key)
	{
		secret_key = key;
		secret_key_encoded = Base64.getEncoder().encode(key);
		if (encode_secret_key.isSelected())
			secret_key_area.setText(new String(secret_key_encoded));
		else
			secret_key_area.setText(new String(secret_key));
	}

	public boolean isExcludeModeAndPadding()
	{
		return excludemp.isSelected();
	}

	public boolean usePublicToEncrypt()
	{
		return usepublic.isSelected();
	}
}
