package com.fancige.server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerJFrame extends javax.swing.JFrame {
	
	JLabel jLabel1;
    JMenu jMenu1;
    JMenu jMenu2;
    JMenuBar jMenuBar1;
    JScrollPane jScrollPane1;
    JTextArea area;
    JTextField field;
	
	private static final long serialVersionUID = 1L;

	public ServerJFrame() {
        initComponents();
    }

    private void initComponents() {

    	
        jScrollPane1 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        field = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setTitle("服务端");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        area.setEditable(false);
        area.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        area.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        area.setForeground(new java.awt.Color(0, 153, 255));
        area.setLineWrap(true);
        area.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 13, 10, 13));
        jScrollPane1.setViewportView(area);

        jLabel1.setFont(new java.awt.Font("幼圆", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("用户列表");

        field.setEditable(false);
        field.setBackground(new java.awt.Color(240, 240, 240));
        field.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        field.setForeground(new java.awt.Color(0, 153, 204));
        field.setToolTipText("消息栏");
        field.setBorder(null);

        jMenu1.setText("文件");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("编辑");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(field)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(field, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }
}
