package com.fancige;

import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class PreferenceController
{
	private PreferenceModel initModel;
	private PreferenceModel model;
	private PreferenceView view;
	private JFileChooser chooser;

	private static final String TIP_DIR_ERROR = "指定的保存目录无效";

	public PreferenceController(Window parent)
	{
		initData();
		initComponents();
		initView(parent);
	}

	private void initData()
	{
		initModel = new PreferenceModel();
		model = new PreferenceModel();
	}

	private void initComponents()
	{
		chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	private void initView(Window parent)
	{
		view = new PreferenceView(parent);
		String dir = model.getSaveDir();
		view.fieldDir.setText(dir);
		if (model.isCover())
		{
			setCover(true);
		}
		else
		{
			view.radioDir.setSelected(true);
			if (!new File(dir).isDirectory())
			{
				view.fieldTip.setText(TIP_DIR_ERROR);
			}
		}

		view.radioCover.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				setCover(view.radioCover.isSelected());
			}
		});

		// set dir
		view.fieldDir.addCaretListener(new CaretListener()
		{
			@Override
			public void caretUpdate(CaretEvent e)
			{
				setSaveDir(view.fieldDir.getText());
			}
		});

		// set dir
		view.btnDir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int returnVal = chooser.showOpenDialog(view.dialog);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					String dir = chooser.getSelectedFile().getAbsolutePath();
					view.fieldDir.setText(dir);
					setSaveDir(dir);
				}
			}
		});

		view.areaExts.setText(model.getExtensionString());

		view.areaExts.addCaretListener(new CaretListener()
		{
			@Override
			public void caretUpdate(CaretEvent e)
			{
				model.setExtensionString(view.areaExts.getText());
			}
		});

		view.btnOK.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				model.store();
				view.dialog.dispose();
			}
		});

		view.btnNo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.dialog.dispose();
			}
		});

		view.dialog.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				if (isChanged())
				{
					int returnVal = JOptionPane.showConfirmDialog(view.dialog,
							"是否保存设置？");
					if (returnVal == JOptionPane.YES_OPTION)
						model.store();

					if (returnVal != JOptionPane.CANCEL_OPTION)
						view.dialog.dispose();
				}
				else
					view.dialog.dispose();
			}
		});

		view.dialog.setLocationRelativeTo(parent);
		view.dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		view.dialog.setVisible(true);
	}

	private void setCover(boolean cover)
	{
		model.setCover(cover);

		view.radioCover.setSelected(cover);
		view.radioDir.setSelected(!cover);
		view.fieldDir.setEnabled(!cover);
		view.btnDir.setEnabled(!cover);
	}

	private void setSaveDir(String dir)
	{
		model.setSaveDir(dir);

		if (new File(dir).isDirectory())
		{
			if (view.fieldTip.getText().equals(TIP_DIR_ERROR))
				view.fieldTip.setText("");
		}
		else
		{
			view.fieldTip.setText(TIP_DIR_ERROR);
		}
	}

	/**
	 * The return value is true if the user did a change, otherwise false
	 */
	public boolean isChanged()
	{
		return !initModel.equals(model);
	}
}