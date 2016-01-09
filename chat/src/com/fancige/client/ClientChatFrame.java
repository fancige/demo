package com.fancige.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.fancige.util.MyImages;

public class ClientChatFrame extends javax.swing.JFrame
{

	private static final long serialVersionUID = 1L;

	public ClientChatFrame()
	{
		initComponents();
	}

	private void initComponents()
	{// GEN-BEGIN:initComponents

		jPanel1 = new JPanel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g)
			{
				g.drawImage(MyImages.panel1, 0, 0, 600, 80, null);
			}
		};
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jPanel2 = new JPanel()
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g)
			{
				g.drawImage(MyImages.panel2, 0, 0, 600, 430, null);
			}
		};
		jScrollPane1 = new JScrollPane();
		jTextAreaOutput = new JTextArea();
		jScrollPane2 = new JScrollPane();
		jTextAreaInput = new JTextArea();
		jButtonSend = new JButton();
		jButtonClear = new JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setUndecorated(true);
		getContentPane().setLayout(null);

		jPanel1.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent evt)
			{
				jPanel1MousePressed(evt);
			}

			public void mouseReleased(MouseEvent evt)
			{
				jPanel1MouseReleased(evt);
			}
		});
		jPanel1.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent evt)
			{
				jPanel1MouseDragged(evt);
			}
		});
		jPanel1.setLayout(null);

		jLabel1.setIcon(MyImages.close_btn_01); // NOI18N
		jLabel1.setToolTipText("关闭");
		jLabel1.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent evt)
			{
				jLabel1.setIcon(MyImages.close_btn_03);
				setVisible(false);
			}

			public void mouseEntered(MouseEvent evt)
			{
				jLabel1.setIcon(MyImages.close_btn_02);
			}

			public void mouseExited(MouseEvent evt)
			{
				jLabel1.setIcon(MyImages.close_btn_01);
			}
		});
		jPanel1.add(jLabel1);
		jLabel1.setBounds(560, 0, 43, 21);

		jLabel2.setIcon(MyImages.minimize_btn_01); // NOI18N
		jLabel2.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent evt)
			{
				jLabel2MouseClicked(evt);
			}

			public void mouseEntered(MouseEvent evt)
			{
				jLabel2MouseEntered(evt);
			}

			public void mouseExited(MouseEvent evt)
			{
				jLabel2MouseExited(evt);
			}
		});
		jPanel1.add(jLabel2);
		jLabel2.setBounds(530, 0, 33, 21);

		getContentPane().add(jPanel1);
		jPanel1.setBounds(0, 0, 600, 80);

		jPanel2.setLayout(null);

		jTextAreaOutput.setColumns(20);
		jTextAreaOutput.setFont(new Font("微软雅黑", 0, 18)); // NOI18N
		jTextAreaOutput.setEditable(false);
		jTextAreaOutput.setLineWrap(true);
		jTextAreaOutput.setRows(5);
		jTextAreaOutput.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 5));
		jTextAreaOutput.setMargin(new Insets(2, 2, 2, 5));
		jTextAreaOutput.setOpaque(false);
		jScrollPane1.setViewportView(jTextAreaOutput);

		jPanel2.add(jScrollPane1);
		jScrollPane1.setBounds(10, 0, 410, 270);
		jScrollPane1.getViewport().setOpaque(false);
		jScrollPane1.getViewport().setBorder(null);
		jScrollPane1.setOpaque(false);
		jScrollPane1.setBorder(null);
		jScrollPane1.getVerticalScrollBar().setUI(new Mui());
		jScrollPane1.getVerticalScrollBar()
				.setPreferredSize(new Dimension(13, 270));

		jTextAreaInput.setColumns(20);
		jTextAreaInput.setFont(new Font("微软雅黑", 0, 14)); // NOI18N
		jTextAreaInput.setLineWrap(true);
		jTextAreaInput.setRows(5);
		jTextAreaInput.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 5));
		jTextAreaInput.setOpaque(false);
		jScrollPane2.setViewportView(jTextAreaInput);

		jPanel2.add(jScrollPane2);
		jScrollPane2.setBounds(10, 290, 410, 100);
		jScrollPane2.getViewport().setOpaque(false);
		jScrollPane2.getViewport().setBorder(null);
		jScrollPane2.setOpaque(false);
		jScrollPane2.setBorder(null);
		jScrollPane2.getVerticalScrollBar().setUI(new Mui());
		jScrollPane2.getVerticalScrollBar()
				.setPreferredSize(new Dimension(13, 100));

		jButtonSend.setText("发送");
		jPanel2.add(jButtonSend);
		jButtonSend.setBounds(360, 400, 60, 23);

		jButtonClear.setText("清除");
		jPanel2.add(jButtonClear);
		jButtonClear.setBounds(290, 400, 60, 23);

		getContentPane().add(jPanel2);
		jPanel2.setBounds(0, 80, 600, 430);

		setSize(new Dimension(600, 510));
		setLocationRelativeTo(null);
	}

	private void jLabel2MouseEntered(MouseEvent evt)
	{// GEN-FIRST:event_jLabel2MouseEntered

		jLabel2.setIcon(MyImages.minimize_btn_02);
	}// GEN-LAST:event_jLabel2MouseEntered

	private void jLabel2MouseExited(MouseEvent evt)
	{// GEN-FIRST:event_jLabel2MouseExited

		jLabel2.setIcon(MyImages.minimize_btn_01);
	}// GEN-LAST:event_jLabel2MouseExited

	private void jLabel2MouseClicked(MouseEvent evt)
	{// GEN-FIRST:event_jLabel2MouseClicked

		jLabel2.setIcon(MyImages.minimize_btn_03);
		setExtendedState(WindowConstants.HIDE_ON_CLOSE);
	}// GEN-LAST:event_jLabel2MouseClicked

	private boolean isDraging = false;
	int xx, yy;

	private void jPanel1MousePressed(MouseEvent evt)
	{// GEN-FIRST:event_jPanel1MousePressed

		isDraging = true;
		xx = evt.getX();
		yy = evt.getY();
	}// GEN-LAST:event_jPanel1MousePressed

	private void jPanel1MouseReleased(MouseEvent evt)
	{// GEN-FIRST:event_jPanel1MouseReleased

		isDraging = false;
	}// GEN-LAST:event_jPanel1MouseReleased

	private void jPanel1MouseDragged(MouseEvent evt)
	{// GEN-FIRST:event_jPanel1MouseDragged

		if (isDraging)
		{

			int x = evt.getXOnScreen();
			int y = evt.getYOnScreen();
			setLocation(x - xx, y - yy);
		}
	}

	public JButton jButtonClear;
	public JButton jButtonSend;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	public JTextArea jTextAreaOutput;
	public JTextArea jTextAreaInput;

	class Mui extends BasicScrollBarUI
	{

		@Override
		protected void configureScrollBarColors()
		{

			LookAndFeel.installColors(scrollbar, "ScrollBar.background",
					"ScrollBar.foreground");
			thumbHighlightColor = null;
			thumbLightShadowColor = null;
			thumbDarkShadowColor = null;
			thumbColor = new Color(65, 158, 253);
			trackColor = new Color(244, 246, 255);
			trackHighlightColor = null;
		}

		@Override
		protected JButton createIncreaseButton(int orientation)
		{

			BasicArrowButton button = new BasicArrowButton(orientation,
					new Color(244, 246, 255), null, null, null);
			return button;
		}

		@Override
		protected JButton createDecreaseButton(int orientation)
		{

			return new BasicArrowButton(orientation, new Color(244, 246, 255),
					null, null, null);
		}
	}
}
