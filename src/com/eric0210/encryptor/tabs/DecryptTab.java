package com.eric0210.encryptor.tabs;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.eric0210.encryptor.Constants.CipherAlgorithm;
import com.eric0210.encryptor.Constants.CipherMode;
import com.eric0210.encryptor.Constants.CipherPadding;

public class DecryptTab extends JPanel
{
	private static final long serialVersionUID = -5871305458166537875L;
	private static JCheckBox enabledButton;
	private JPanel decryption_settings_panel;
	private JPanel decryption_mode_panel;
	private JComboBox<CipherAlgorithm> decryption_algorithm_select;
	private JComboBox<CipherMode> decryption_mode_select;
	private JPanel decryption_padding_panel;
	private JComboBox<CipherPadding> decryption_padding_select;
	private JPanel decryption_bit_block_size_panel;
	private JSpinner decryption_bit_block_size_select;
	private JPanel decryption_cipher_settings_panel;
	private JPanel decryption_custom_key_panel;
	private File decryption_custom_key_path_cache;
	private JLabel custom_key_label;
	private JTextField custom_key;
	private JButton custom_key_select_button;
	private JCheckBox excludemp;
	private byte[] public_key;
	private byte[] private_key;
	private byte[] secret_key;
	private byte[] public_key_encoded;
	private byte[] private_key_encoded;
	private byte[] secret_key_encoded;
	private JCheckBox decode_custom_key;
	private JPanel decryption_extra_options_panel;
	private JCheckBox decode_input_select;

	/**
	 * Create the panel.
	 */
	public DecryptTab()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {
				0, 0
		};
		gridBagLayout.rowHeights = new int[] {
				0, 231, 0
		};
		gridBagLayout.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gridBagLayout.rowWeights = new double[] {
				0.0, 0.0, Double.MIN_VALUE
		};
		setLayout(gridBagLayout);
		enabledButton = new JCheckBox("enable");
		enabledButton.addActionListener((e) -> {
				if (selected())
					EncryptTab.setSelected(false);
		});
		GridBagConstraints gbc_enabledButton = new GridBagConstraints();
		gbc_enabledButton.insets = new Insets(0, 0, 5, 0);
		gbc_enabledButton.anchor = GridBagConstraints.WEST;
		gbc_enabledButton.gridx = 0;
		gbc_enabledButton.gridy = 0;
		add(enabledButton, gbc_enabledButton);
		decryption_settings_panel = new JPanel();
		decryption_settings_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Decryption Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_encsettingspanel = new GridBagConstraints();
		gbc_encsettingspanel.anchor = GridBagConstraints.NORTH;
		gbc_encsettingspanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_encsettingspanel.gridx = 0;
		gbc_encsettingspanel.gridy = 1;
		add(decryption_settings_panel, gbc_encsettingspanel);
		GridBagLayout gbl_encsettingspanel = new GridBagLayout();
		gbl_encsettingspanel.columnWidths = new int[] {
				438, 0
		};
		gbl_encsettingspanel.rowHeights = new int[] {
				0, 0, 0, 0
		};
		gbl_encsettingspanel.columnWeights = new double[] {
				1.0, Double.MIN_VALUE
		};
		gbl_encsettingspanel.rowWeights = new double[] {
				1.0, 1.0, 1.0, Double.MIN_VALUE
		};
		decryption_settings_panel.setLayout(gbl_encsettingspanel);
		decryption_cipher_settings_panel = new JPanel();
		decryption_cipher_settings_panel.setBorder(
				new TitledBorder(null, "Cipher Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_encciphersettingspanel = new GridBagConstraints();
		gbc_encciphersettingspanel.insets = new Insets(0, 0, 5, 0);
		gbc_encciphersettingspanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_encciphersettingspanel.anchor = GridBagConstraints.NORTH;
		gbc_encciphersettingspanel.gridx = 0;
		gbc_encciphersettingspanel.gridy = 0;
		decryption_settings_panel.add(decryption_cipher_settings_panel, gbc_encciphersettingspanel);
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
		decryption_cipher_settings_panel.setLayout(gbl_encciphersettingspanel);
		JPanel encalgpanel = new JPanel();
		GridBagConstraints gbc_encalgpanel = new GridBagConstraints();
		gbc_encalgpanel.fill = GridBagConstraints.BOTH;
		gbc_encalgpanel.insets = new Insets(0, 0, 0, 5);
		gbc_encalgpanel.gridx = 0;
		gbc_encalgpanel.gridy = 0;
		decryption_cipher_settings_panel.add(encalgpanel, gbc_encalgpanel);
		encalgpanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Decryption Algorithm",
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
		decryption_algorithm_select = new JComboBox<CipherAlgorithm>();
//		decryption_algorithm_select.addItemListener((e) -> {
//			boolean b = ((CipherAlgorithm) decryption_algorithm_select.getSelectedItem()).isAsymmetricKeyEncryption;
//			boolean b2 = !decryption_use_custom_key.isSelected();
//			decryption_pubkeypanel.setEnabled(b && b2);
//			public_key_area.setEnabled(b && b2);
//			publickeysave2file.setEnabled(b && b2);
//			encode_public_key.setEnabled(b && b2);
//			decryption_pubkeypanel.setEnabled(b && b2);
//			decryption_privkeypanel.setEnabled(b && b2);
//			private_key_area.setEnabled(b && b2);
//			privkeysave2file.setEnabled(b && b2);
//			encode_private_key.setEnabled(b && b2);
//			decryption_secretkeypanel.setEnabled(!b && b2);
//			secret_key_area.setEnabled(!b && b2);
//			secretkeysave2file.setEnabled(!b && b2);
//			encode_secret_key.setEnabled(!b && b2);
//			encasymmetrickeyoptpanel.setEnabled(b && b2);
//			usepublic.setEnabled(b && b2);
//			useprivate.setEnabled(b && b2);
//		});
		GridBagConstraints gbc_encalg = new GridBagConstraints();
		gbc_encalg.fill = GridBagConstraints.HORIZONTAL;
		gbc_encalg.gridx = 0;
		gbc_encalg.gridy = 0;
		encalgpanel.add(decryption_algorithm_select, gbc_encalg);
		decryption_algorithm_select.setModel(new DefaultComboBoxModel<CipherAlgorithm>(CipherAlgorithm.values()));
		decryption_bit_block_size_panel = new JPanel();
		GridBagConstraints gbc_encbitblockpanel = new GridBagConstraints();
		gbc_encbitblockpanel.fill = GridBagConstraints.BOTH;
		gbc_encbitblockpanel.insets = new Insets(0, 0, 0, 5);
		gbc_encbitblockpanel.gridx = 1;
		gbc_encbitblockpanel.gridy = 0;
		decryption_cipher_settings_panel.add(decryption_bit_block_size_panel, gbc_encbitblockpanel);
		decryption_bit_block_size_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Decryption Bit Block Size", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		decryption_bit_block_size_panel.setLayout(gbl_encbitblockpanel);
		decryption_bit_block_size_select = new JSpinner();
		GridBagConstraints gbc_encbitblock = new GridBagConstraints();
		gbc_encbitblock.fill = GridBagConstraints.HORIZONTAL;
		gbc_encbitblock.gridx = 0;
		gbc_encbitblock.gridy = 0;
		decryption_bit_block_size_panel.add(decryption_bit_block_size_select, gbc_encbitblock);
		decryption_bit_block_size_select.setModel(new SpinnerNumberModel(128, 1, 8192, 1));
		decryption_mode_panel = new JPanel();
		GridBagConstraints gbc_encmodepanel = new GridBagConstraints();
		gbc_encmodepanel.fill = GridBagConstraints.BOTH;
		gbc_encmodepanel.insets = new Insets(0, 0, 0, 5);
		gbc_encmodepanel.gridx = 2;
		gbc_encmodepanel.gridy = 0;
		decryption_cipher_settings_panel.add(decryption_mode_panel, gbc_encmodepanel);
		decryption_mode_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Decryption Mode",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		decryption_mode_panel.setLayout(gbl_encmodepanel);
		decryption_mode_select = new JComboBox<CipherMode>();
		GridBagConstraints gbc_encmode = new GridBagConstraints();
		gbc_encmode.fill = GridBagConstraints.HORIZONTAL;
		gbc_encmode.gridx = 0;
		gbc_encmode.gridy = 0;
		decryption_mode_panel.add(decryption_mode_select, gbc_encmode);
		decryption_mode_select.setModel(new DefaultComboBoxModel<CipherMode>(CipherMode.values()));
		decryption_padding_panel = new JPanel();
		GridBagConstraints gbc_encpadpanel = new GridBagConstraints();
		gbc_encpadpanel.fill = GridBagConstraints.BOTH;
		gbc_encpadpanel.gridx = 3;
		gbc_encpadpanel.gridy = 0;
		decryption_cipher_settings_panel.add(decryption_padding_panel, gbc_encpadpanel);
		decryption_padding_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Decryption Padding", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		decryption_padding_panel.setLayout(gbl_encpadpanel);
		decryption_padding_select = new JComboBox<CipherPadding>();
		GridBagConstraints gbc_encpad = new GridBagConstraints();
		gbc_encpad.fill = GridBagConstraints.HORIZONTAL;
		gbc_encpad.gridx = 0;
		gbc_encpad.gridy = 0;
		decryption_padding_panel.add(decryption_padding_select, gbc_encpad);
		decryption_padding_select.setModel(new DefaultComboBoxModel<CipherPadding>(CipherPadding.values()));
		
		decryption_extra_options_panel = new JPanel();
		decryption_extra_options_panel.setBorder(new TitledBorder(null, "Extra Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_decryption_extra_options_panel = new GridBagConstraints();
		gbc_decryption_extra_options_panel.insets = new Insets(0, 0, 5, 0);
		gbc_decryption_extra_options_panel.fill = GridBagConstraints.BOTH;
		gbc_decryption_extra_options_panel.gridx = 0;
		gbc_decryption_extra_options_panel.gridy = 1;
		decryption_settings_panel.add(decryption_extra_options_panel, gbc_decryption_extra_options_panel);
		GridBagLayout gbl_decryption_extra_options_panel = new GridBagLayout();
		gbl_decryption_extra_options_panel.columnWidths = new int[]{0, 0};
		gbl_decryption_extra_options_panel.rowHeights = new int[]{0, 0, 0};
		gbl_decryption_extra_options_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_decryption_extra_options_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		decryption_extra_options_panel.setLayout(gbl_decryption_extra_options_panel);
		excludemp = new JCheckBox("Exclude mode and padding");
		GridBagConstraints gbc_excludemp = new GridBagConstraints();
		gbc_excludemp.anchor = GridBagConstraints.WEST;
		gbc_excludemp.insets = new Insets(0, 0, 5, 0);
		gbc_excludemp.gridx = 0;
		gbc_excludemp.gridy = 0;
		decryption_extra_options_panel.add(excludemp, gbc_excludemp);
		excludemp.addActionListener((e) -> {
			decryption_mode_select.setEnabled(!excludemp.isSelected());
			decryption_padding_select.setEnabled(!excludemp.isSelected());
		});
		
		decode_input_select = new JCheckBox("Decode input with Base64");
		GridBagConstraints gbc_decode_input_select = new GridBagConstraints();
		gbc_decode_input_select.anchor = GridBagConstraints.WEST;
		gbc_decode_input_select.gridx = 0;
		gbc_decode_input_select.gridy = 1;
		decryption_extra_options_panel.add(decode_input_select, gbc_decode_input_select);
		decryption_custom_key_panel = new JPanel();
		decryption_custom_key_panel.setBorder(
				new TitledBorder(null, "Key Specification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_encexternalkeypanel = new GridBagConstraints();
		gbc_encexternalkeypanel.fill = GridBagConstraints.BOTH;
		gbc_encexternalkeypanel.gridx = 0;
		gbc_encexternalkeypanel.gridy = 2;
		decryption_settings_panel.add(decryption_custom_key_panel, gbc_encexternalkeypanel);
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
		decryption_custom_key_panel.setLayout(gbl_encexternalkeypanel);
		custom_key_label = new JLabel("Secret Key or Public Key file");
		GridBagConstraints gbc_externalkeylabel = new GridBagConstraints();
		gbc_externalkeylabel.anchor = GridBagConstraints.WEST;
		gbc_externalkeylabel.insets = new Insets(5, 5, 5, 5);
		gbc_externalkeylabel.gridx = 0;
		gbc_externalkeylabel.gridy = 0;
		decryption_custom_key_panel.add(custom_key_label, gbc_externalkeylabel);
		custom_key = new JTextField();
		GridBagConstraints gbc_extrakey = new GridBagConstraints();
		gbc_extrakey.insets = new Insets(5, 0, 5, 5);
		gbc_extrakey.fill = GridBagConstraints.BOTH;
		gbc_extrakey.gridx = 1;
		gbc_extrakey.gridy = 0;
		decryption_custom_key_panel.add(custom_key, gbc_extrakey);
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
			if (decryption_custom_key_path_cache != null)
				chooser.setCurrentDirectory(decryption_custom_key_path_cache);
			int result = chooser.showOpenDialog(this);
			if (result == 0)
			{
				SwingUtilities.invokeLater(() -> {
					custom_key.setText(chooser.getSelectedFile().getAbsolutePath());
					decryption_custom_key_path_cache = chooser.getSelectedFile();
				});
			}
		});
		GridBagConstraints gbc_extrakeyinputbutton = new GridBagConstraints();
		gbc_extrakeyinputbutton.fill = GridBagConstraints.BOTH;
		gbc_extrakeyinputbutton.insets = new Insets(5, 0, 5, 0);
		gbc_extrakeyinputbutton.gridx = 2;
		gbc_extrakeyinputbutton.gridy = 0;
//			custom_key_select_button.addActionListener((e) -> {
//				JFileChooser chooser = new JFileChooser();
//				if (custom_key.getText() != null && !custom_key.getText().isEmpty())
//				{
//					chooser.setSelectedFile(new File(custom_key.getText()));
//				}
//				chooser.setMultiSelectionEnabled(false);
//				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//				if (decryption_custom_key_path_cache != null)
//					chooser.setCurrentDirectory(decryption_custom_key_path_cache);
//				int result = chooser.showOpenDialog(this);
//				if (result == 0)
//				{
//					SwingUtilities.invokeLater(() -> {
//						custom_key.setText(chooser.getSelectedFile().getAbsolutePath());
//						decryption_custom_key_path_cache = chooser.getSelectedFile();
//					});
//				}
//			});
		decryption_custom_key_panel.add(custom_key_select_button, gbc_extrakeyinputbutton);
		decode_custom_key = new JCheckBox("Decode key with Base64");
		GridBagConstraints gbc_decodebase64 = new GridBagConstraints();
		gbc_decodebase64.anchor = GridBagConstraints.WEST;
		gbc_decodebase64.insets = new Insets(0, 0, 0, 5);
		gbc_decodebase64.gridx = 0;
		gbc_decodebase64.gridy = 1;
		decryption_custom_key_panel.add(decode_custom_key, gbc_decodebase64);
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
		return (CipherAlgorithm) decryption_algorithm_select.getSelectedItem();
	}

	public CipherMode getCipherMode()
	{
		return (CipherMode) decryption_mode_select.getSelectedItem();
	}

	public CipherPadding getCipherPadding()
	{
		return (CipherPadding) decryption_padding_select.getSelectedItem();
	}

	public int getCipherBitBlockSize()
	{
		return (int) decryption_bit_block_size_select.getValue();
	}

	public boolean isExcludeModeAndPadding()
	{
		return excludemp.isSelected();
	}

	public boolean decodeCustomKey()
	{
		return decode_custom_key.isSelected();
	}

	public String getCustomKeyFilePath()
	{
		return custom_key.getText();
	}
	
	public boolean decodeInput()
	{
		return decode_input_select.isSelected();
	}
}
