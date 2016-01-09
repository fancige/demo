package com.fancige.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.fancige.util.MyImages;

public class ClientMainFrame extends javax.swing.JFrame
{
	private static final long serialVersionUID = 1L;

	public ClientMainFrame()
	{
		initComponents();
	}

	private void initComponents()
	{// GEN-BEGIN:initComponents

		jPanelTop = new JPanel();
		closeBtn = new JLabel();
		minimizeBtn = new JLabel();
		menuBtn = new JLabel();
		jLabelCurrentUser = new JLabel();
		jLabel5 = new JLabel();
		jScrollPaneUserList = new JScrollPane();
		jPanelUserListRoot = new JPanel();
		jLabelUserList = new JLabel();
		jPanelUserList = new JPanel();
		bottonPanel = new JPanel();
		jLabelGroupChat = new JLabel();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setUndecorated(true);
		getContentPane().setLayout(null);

		jPanelTop.setBackground(new Color(65, 158, 253));
		jPanelTop.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent evt)
			{
				topPanelMousePressed(evt);
			}

			public void mouseReleased(MouseEvent evt)
			{
				topPanelMouseReleased(evt);
			}
		});
		jPanelTop.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent evt)
			{
				topPanelMouseDragged(evt);
			}
		});
		jPanelTop.setLayout(null);

		closeBtn.setIcon(MyImages.close_btn_01); // NOI18N
		closeBtn.setToolTipText("关闭");
		closeBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent evt)
			{
				jLabel1MouseClicked(evt);
			}

			public void mouseEntered(MouseEvent evt)
			{
				jLabel1MouseEntered(evt);
			}

			public void mouseExited(MouseEvent evt)
			{
				jLabel1MouseExited(evt);
			}
		});
		jPanelTop.add(closeBtn);
		closeBtn.setBounds(200, 0, 43, 21);

		minimizeBtn.setIcon(MyImages.minimize_btn_01); // NOI18N
		minimizeBtn.setToolTipText("最小化");
		minimizeBtn.addMouseListener(new MouseAdapter()
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
		jPanelTop.add(minimizeBtn);
		minimizeBtn.setBounds(170, 0, 33, 21);

		menuBtn.setIcon(MyImages.menu_btn_01); // NOI18N
		menuBtn.setToolTipText("菜单");
		menuBtn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent evt)
			{
				jLabel3MouseClicked(evt);
			}

			public void mouseEntered(MouseEvent evt)
			{
				jLabel3MouseEntered(evt);
			}

			public void mouseExited(MouseEvent evt)
			{
				jLabel3MouseExited(evt);
			}
		});
		jPanelTop.add(menuBtn);
		menuBtn.setBounds(140, 0, 31, 21);

		jLabelCurrentUser.setFont(new Font("宋体", 0, 14)); // NOI18N
		jLabelCurrentUser.setForeground(new Color(255, 255, 255));
		jPanelTop.add(jLabelCurrentUser);
		jLabelCurrentUser.setBounds(10, 40, 200, 50);

		jLabel5.setForeground(new Color(255, 255, 255));
		jLabel5.setText("客户端");
		jPanelTop.add(jLabel5);
		jLabel5.setBounds(10, 0, 40, 20);

		getContentPane().add(jPanelTop);
		jPanelTop.setBounds(0, 0, 244, 110);
		jPanelTop.getAccessibleContext().setAccessibleDescription("");

		jScrollPaneUserList.setBackground(new Color(244, 246, 255));
		jScrollPaneUserList.setBorder(null);
		jScrollPaneUserList.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPaneUserList.setPreferredSize(new Dimension(244, 430));

		jPanelUserListRoot.setBackground(new Color(244, 246, 255));
		jPanelUserListRoot.setPreferredSize(new Dimension(244, 400));
		jPanelUserListRoot.setLayout(null);

		jLabelUserList.setBackground(new Color(244, 246, 255));
		jLabelUserList.setFont(new Font("宋体", 0, 14)); // NOI18N
		jLabelUserList.setLabelFor(jPanelUserList);
		jLabelUserList.setText(" >  在线用户               0");
		jLabelUserList.setOpaque(true);
		jLabelUserList.setPreferredSize(new Dimension(244, 30));
		jPanelUserListRoot.add(jLabelUserList);
		jLabelUserList.setBounds(0, 0, 244, 30);

		jPanelUserList.setBackground(new Color(244, 246, 255));
		jPanelUserList.setPreferredSize(new Dimension(244, 400));
		jPanelUserList.setLayout(null);
		jPanelUserListRoot.add(jPanelUserList);
		jPanelUserList.setBounds(0, 30, 244, 400);

		jScrollPaneUserList.setViewportView(jPanelUserListRoot);

		getContentPane().add(jScrollPaneUserList);
		jScrollPaneUserList.setBounds(0, 110, 244, 430);
		jScrollPaneUserList.getVerticalScrollBar().setUI(new BasicScrollBarUI()
		{
			@Override
			protected void configureScrollBarColors()
			{

				LookAndFeel.installColors(scrollbar, "ScrollBar.background",
						"ScrollBar.foreground");
				thumbHighlightColor = null;
				thumbLightShadowColor = null;
				thumbDarkShadowColor = null;
				thumbColor = new Color(214, 222, 254);
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

				return new BasicArrowButton(orientation,
						new Color(244, 246, 255), null, null, null);
			}

		});
		jScrollPaneUserList.getVerticalScrollBar()
				.setPreferredSize(new Dimension(13, 380));

		bottonPanel.setBackground(new Color(65, 158, 253));
		bottonPanel.setLayout(null);

		jLabelGroupChat.setBackground(new Color(65, 158, 253));
		jLabelGroupChat.setIcon(MyImages.groupChat); // NOI18N
		jLabelGroupChat.setToolTipText("多人聊天");
		jLabelGroupChat.setOpaque(true);
		jLabelGroupChat.addMouseListener(new MouseAdapter()
		{

			private Color colorEnter = new Color(7, 128, 252);
			private Color colorExit = new Color(65, 158, 253);

			public void mouseEntered(MouseEvent evt)
			{
				jLabelGroupChat.setBackground(colorEnter);
			}

			public void mouseExited(MouseEvent evt)
			{
				jLabelGroupChat.setBackground(colorExit);
			}
		});
		bottonPanel.add(jLabelGroupChat);
		jLabelGroupChat.setBounds(10, 5, 50, 50);

		getContentPane().add(bottonPanel);
		bottonPanel.setBounds(0, 540, 244, 60);

		setSize(new Dimension(244, 600));
		setLocationRelativeTo(null);
	}// GEN-END:initComponents

	private void jLabel1MouseEntered(MouseEvent evt)
	{// GEN-FIRST:event_jLabel1MouseEntered

		closeBtn.setIcon(MyImages.close_btn_02);
	}// GEN-LAST:event_jLabel1MouseEntered

	private void jLabel1MouseExited(MouseEvent evt)
	{// GEN-FIRST:event_jLabel1MouseExited

		closeBtn.setIcon(MyImages.close_btn_01);
	}// GEN-LAST:event_jLabel1MouseExited

	private void jLabel1MouseClicked(MouseEvent evt)
	{// GEN-FIRST:event_jLabel1MouseClicked

		closeBtn.setIcon(MyImages.close_btn_03);
		System.exit(0);
	}// GEN-LAST:event_jLabel1MouseClicked

	private void jLabel2MouseEntered(MouseEvent evt)
	{// GEN-FIRST:event_jLabel2MouseEntered

		minimizeBtn.setIcon(MyImages.minimize_btn_02);
	}

	private void jLabel2MouseExited(MouseEvent evt)
	{// GEN-FIRST:event_jLabel2MouseExited

		minimizeBtn.setIcon(MyImages.minimize_btn_01);
	}

	private void jLabel2MouseClicked(MouseEvent evt)
	{// GEN-FIRST:event_jLabel2MouseClicked

		minimizeBtn.setIcon(MyImages.minimize_btn_03);
		setExtendedState(WindowConstants.HIDE_ON_CLOSE);
	}// GEN-LAST:event_jLabel2MouseClicked

	private void jLabel3MouseEntered(MouseEvent evt)
	{// GEN-FIRST:event_jLabel3MouseEntered

		menuBtn.setIcon(MyImages.menu_btn_02);
	}// GEN-LAST:event_jLabel3MouseEntered

	private void jLabel3MouseClicked(MouseEvent evt)
	{// GEN-FIRST:event_jLabel3MouseClicked

		menuBtn.setIcon(MyImages.menu_btn_03);
	}// GEN-LAST:event_jLabel3MouseClicked

	private void jLabel3MouseExited(MouseEvent evt)
	{// GEN-FIRST:event_jLabel3MouseExited

		menuBtn.setIcon(MyImages.menu_btn_01);
	}// GEN-LAST:event_jLabel3MouseExited

	private boolean isDraging = false;
	private int xx, yy;

	private void topPanelMousePressed(MouseEvent evt)
	{// GEN-FIRST:event_topPanelMousePressed

		isDraging = true;
		xx = evt.getX();
		yy = evt.getY();
	}// GEN-LAST:event_topPanelMousePressed

	private void topPanelMouseReleased(MouseEvent evt)
	{// GEN-FIRST:event_topPanelMouseReleased

		isDraging = false;
	}// GEN-LAST:event_topPanelMouseReleased

	private void topPanelMouseDragged(MouseEvent evt)
	{// GEN-FIRST:event_topPanelMouseDragged

		if (isDraging)
		{

			setLocation(evt.getXOnScreen() - xx, evt.getYOnScreen() - yy);
		}
	}// GEN-LAST:event_topPanelMouseDragged

	private JPanel bottonPanel;
	public JScrollPane jScrollPaneUserList;
	public JLabel jLabelCurrentUser;
	public JLabel jLabelGroupChat;
	private JLabel closeBtn;
	private JLabel minimizeBtn;
	private JLabel menuBtn;
	private JLabel jLabel5;
	private JPanel jPanelTop;
	public JLabel jLabelUserList;
	public JPanel jPanelUserList;
	public JPanel jPanelUserListRoot;
	// End of variables declaration//GEN-END:variables

}