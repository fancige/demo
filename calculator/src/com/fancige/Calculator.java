package com.fancige;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Calculator  extends  KeyAdapter {


	static JTextField printText = new JTextField();
	static JTextField resultText = new JTextField("0");
	static Font BIGFONT = new Font(Font.DIALOG,Font.PLAIN,24);
	static Font	MIDDLEFONT = new Font(Font.DIALOG,Font.PLAIN,17);
	static Font SMALLFONT = new Font(Font.DIALOG,Font.PLAIN,15);
	
	
	public static void main(String args[]) {
		
		Calculator keyListener = new Calculator();	
		Lis actionListener = new Lis();
		
		// 菜单栏。

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(150,200,250));
		
		JMenu menu = new JMenu("編輯");
		menuBar.add(menu);
		
		JMenuItem item = new JMenuItem("   复制");
		item.setPreferredSize(new Dimension(170,20));
		item.addActionListener(actionListener);
		menu.add(item);
		
		
		// 创建一堆按钮。
		
		JButton buttonAdd = new JButton("+");
		JButton buttonSub = new JButton("-");
		JButton buttonMul = new JButton("*");
		JButton buttonDiv = new JButton("/");
		JButton buttonEqu = new JButton("=");
		JButton buttonDot = new JButton(".");
		
		JButton buttonClear = new JButton("c");
		JButton buttonBackspace = new JButton("←");
		
		JButton button1 = new JButton("1");
		JButton button2 = new JButton("2");
		JButton button3 = new JButton("3");
		JButton button4 = new JButton("4");
		JButton button5 = new JButton("5");
		JButton button6 = new JButton("6");
		JButton button7 = new JButton("7");
		JButton button8 = new JButton("8");
		JButton button9 = new JButton("9");
		JButton button0 = new JButton("0");
		
		buttonAdd.setFont(SMALLFONT);
		buttonSub.setFont(SMALLFONT);
		buttonMul.setFont(SMALLFONT);
		buttonDiv.setFont(SMALLFONT);
		
		// 创建一个面板用于放置一些按钮。
		
		JPanel buttonPanel = new JPanel(new GridLayout(3,4,3,3));
		buttonPanel.setPreferredSize(new Dimension(218,110));
		buttonPanel.setBackground(new Color(150,200,250));
		
		buttonPanel.add(buttonAdd);
		buttonPanel.add(buttonSub);
		buttonPanel.add(buttonMul);
		buttonPanel.add(buttonDiv);
		
		buttonPanel.add(button7);
		buttonPanel.add(button8);
		buttonPanel.add(button9);
		buttonPanel.add(buttonBackspace);
		
		buttonPanel.add(button4);
		buttonPanel.add(button5);
		buttonPanel.add(button6);
		buttonPanel.add(buttonClear);
		
		// 新建两个面板，一个放置两个textField,一个放置一些按钮。
		
		JPanel textPanel = new JPanel(null);
		textPanel.add(printText);
		textPanel.add(resultText);
		
		printText.setBounds(0,0,210,18);
		printText.setHorizontalAlignment(JTextField.RIGHT);
		printText.setFont(SMALLFONT);
		printText.setBackground(Color.WHITE);
		printText.setEditable(false);
		printText.setBorder(new EmptyBorder(0,4,0,4));
		
		resultText.setBounds(0,18,210,33);
		resultText.setHorizontalAlignment(JTextField.RIGHT);
		resultText.setFont(BIGFONT);
		resultText.setBackground(Color.WHITE);
		resultText.setEditable(false);
		resultText.setBorder(new EmptyBorder(0,4,0,4));
		
		JPanel panel = new JPanel(null);
		panel.setBackground(new Color(150,200,250));
		
		// 面板上添加：放置有显示屏的面板，放置了一些按钮的面板，以及一些剩余的按钮。
		
		panel.add(textPanel);
		panel.add(buttonPanel);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(buttonEqu);
		panel.add(button0);
		panel.add(buttonDot);
		
		textPanel.setBounds(18,20,210,51);
		buttonPanel.setBounds(18,90,210,110);
		button1.setBounds(18,202,50,34);
		button2.setBounds(71,202,50,34);
		button3.setBounds(124,202,50,34);
		buttonEqu.setBounds(177,202,50,71);
		button0.setBounds(18,239,103,34);
		buttonDot.setBounds(124,239,50,34);
		
		// 为所有组件添加 KeyLiistener
		
		printText.addKeyListener(keyListener);
		resultText.addKeyListener(keyListener);
		
		buttonAdd.addKeyListener(keyListener);
		buttonSub.addKeyListener(keyListener);
		buttonMul.addKeyListener(keyListener);
		buttonDiv.addKeyListener(keyListener);
		buttonEqu.addKeyListener(keyListener);
		buttonDot.addKeyListener(keyListener);

		buttonClear.addKeyListener(keyListener);
		buttonBackspace.addKeyListener(keyListener);
		
		button0.addKeyListener(keyListener);
		button1.addKeyListener(keyListener);
		button2.addKeyListener(keyListener);
		button3.addKeyListener(keyListener);
		button4.addKeyListener(keyListener);
		button5.addKeyListener(keyListener);
		button6.addKeyListener(keyListener);
		button7.addKeyListener(keyListener);
		button8.addKeyListener(keyListener);
		button9.addKeyListener(keyListener);
		
		// 为所以按钮添加 ActionListener.
		
		button0.addActionListener(actionListener);
		button1.addActionListener(actionListener);
		button2.addActionListener(actionListener);
		button3.addActionListener(actionListener);
		button4.addActionListener(actionListener);
		button5.addActionListener(actionListener);
		button6.addActionListener(actionListener);
		button7.addActionListener(actionListener);
		button8.addActionListener(actionListener);
		button9.addActionListener(actionListener);
		
		buttonAdd.addActionListener(actionListener);
		buttonSub.addActionListener(actionListener);
		buttonMul.addActionListener(actionListener);
		buttonDiv.addActionListener(actionListener);
		buttonDot.addActionListener(actionListener);
		buttonEqu.addActionListener(actionListener);
		
		buttonClear.addActionListener(actionListener);
		buttonBackspace.addActionListener(actionListener);
		
		
		// 窗体。
		
		JFrame frame = new JFrame("计算器");
		frame.add(menuBar,BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
		frame.setBounds(300,250,250,340);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	// 监听器里的方法。
	
	public void keyPressed(KeyEvent e){
		
		// 键值等于8的键是退格键。
		if(e.getKeyCode() == 8){
			
			arithmetic('b');
			
		}else{
		
			arithmetic(e.getKeyChar());
		}
		
		if(displayResult.length() > 13 | resultText.getText().length() > 13){
			
			resultText.setFont(MIDDLEFONT);
			
		}else{
			
			resultText.setFont(BIGFONT);
		}
		
		printText.setText(displayPrint);
		resultText.setText(displayResult);
	}
	
	
	
	// 		下面所有代码实现计算器功能。
	
	
	// 下面两个字符串分别显示在屏幕上下行。
	
	private static String displayPrint = "";
	private static String displayResult = "0";
	
	public static String getDisplayPrint(){
		
		return displayPrint;
	}
	
	public static String getDisplayResult(){
		
		return displayResult;
	}
	
	private static String printNumber = "";
	
	private static int digitCount = 0;
	
	private static BigDecimal result = BigDecimal.ZERO;
	private static BigDecimal currentNumber = null;
	private static BigDecimal BIG = new BigDecimal("9999999999999999");
	private static BigDecimal SMALL = new BigDecimal("0.1");
	private static BigDecimal LIMITUP = new BigDecimal("1E100");
	private static BigDecimal LIMITDOWN = new BigDecimal("-1E100");
	
	private static boolean canOperator = false;
	private static boolean isDecimal = false;
	
	private static char lastOperator = '=';
	private static char lastKeyChar = '=';
	private static char[] operators = {'+','-','*','/','='};
	private static char[] oneToNine = {'0','1','2','3','4','5','6','7','8','9'};
	
	public static void arithmetic(char keyChar){

		// 对当前输入数的退格。
		
		if(keyChar == 'b' & printNumber.length() > 0){
			
			if(printNumber.charAt(printNumber.length() - 1) == '.'){
				
				isDecimal = false;
				
			}else if(printNumber.charAt(printNumber.length() - 1) != '-'){
				
				digitCount --;
			}
			printNumber = printNumber.substring(0,printNumber.length() - 1);
			displayResult = printNumber;
			displayPrint = displayPrint.substring(0,displayPrint.length() - 1);
			
			if(printNumber.length() == 0){
				
				displayResult = "0";
				printNumber = "";
				canOperator = false;
			}
		}
		
		//	清空所有数据。
		
		if(keyChar == 'c'){

			displayPrint = "";
			displayResult = "0";
			printNumber = "";
			
			digitCount = 0;
			result = BigDecimal.ZERO;

			isDecimal = false;
			canOperator = false;
			
			lastOperator = '=';
			lastKeyChar = '=';
			
		}
		
		//	下面会产生一个用于运算的数。
		//	for循环的目的是为了判断当前keyChar是否是数字。
		
		for(char number : oneToNine ){
			
			if( keyChar == number & digitCount < 16 ){
				
				printNumber = printNumber + number;
				displayResult = printNumber;
				displayPrint = displayPrint + number;
				digitCount ++;
				
				// 只有至少输入一个数字才被允许可以进入四则运算。
				canOperator = true;
				break;
			}
		}
		
		// isDecimal == false 如果本身就是小数，再输入小数点也不会有任何变化。
		
		if(keyChar == '.' & isDecimal == false & digitCount < 16){
		
			printNumber = printNumber + '.';
			displayResult = printNumber;
			displayPrint = displayPrint + '.';
			isDecimal = true;
			
		}
		if( keyChar == '-' & canOperator == false & printNumber.length() == 0 ){
			
			printNumber = "-";
			displayResult = "-";
			displayPrint = displayPrint + "-";
		}
		
		// 	下面是进行运算的操作。
		//	设置for循环的目的是为了判断当前keyChar是否属于事先限定的运算符中的一个。
		
		for(char currentOperator : operators){

			//	printNumber.equals(".") == false 的原因是最开始haveOperator的值是false，
			//	此时按下运算符可以进行运算，result的值默认为0，但如果最开始只输入小数点后就按下运算符，会报错。
			
			if( keyChar == currentOperator & canOperator == true & printNumber.equals(".") == false ){
				
				//	当当前存在输入数时，如果上一个字符是等号，赋给result，并且将lastOperator初始化，确保不会被下面用到导致不该有的运算。
				//	否则赋给currentNumber。
				
				if(printNumber != ""){
					
					if(lastKeyChar == '='){
						
						result = new BigDecimal(printNumber);
						lastOperator = '=';
					}else{
					
						currentNumber = new BigDecimal(printNumber);
					}
				}

				//	当上一个符号是等号时，
				//	如果当前符号也是等号，由于lastOperator没发生改变，现在的result将进行与上一次同样的运算。
				//	即 result lastOperaton currentNumber。
				//	如果当前符号不是等号、result将会和下次输入的数进行运算，并且此次不会进行任何运算。
				
				if(lastKeyChar == '=' & currentOperator != '='){

				}else if(lastOperator == '+'){
					
					result = result.add(currentNumber);
					
				}else if(lastOperator == '-'){
					
					result = result.subtract(currentNumber);
					
				}else if(lastOperator == '*'){
					
					result = result.multiply(currentNumber);
					
				}else if(lastOperator == '/'){
					
					if(currentNumber.toPlainString().equals("0") == false){
						
						result = result.divide(currentNumber,101,RoundingMode.HALF_EVEN).stripTrailingZeros();
						
					}else{
						
						displayResult = "        除数不能为零";
					}
				}
				
				// 超出设置的计算范围或除数为 0 都会终止计算并重置数据。
				
				if(result.compareTo(LIMITUP) == 1 | result.compareTo(LIMITDOWN) == -1 
						| displayResult.equals("        除数不能为零")){
					
					if(displayResult.equals("        除数不能为零") == false){
						
						displayResult = "          结果超出范围";
					}
					canOperator = false;
					result = BigDecimal.ZERO;
					lastOperator = '=';
					lastKeyChar = '=';
					printNumber = "";
					digitCount = 0;
					displayPrint = "";
					isDecimal = false;
					
					break;
				}
				
				// lastOperator 会把当前输入的除等号外的运算符保留下来，用于下次运算。
				
				if(currentOperator != '='){
					
					canOperator = false;
					lastOperator = currentOperator;
					displayPrint = displayPrint + currentOperator;
					
				}else{
					
					displayPrint = "";
				}
				
				lastKeyChar = keyChar;
				printNumber = "";
				digitCount = 0;
				isDecimal = false;
				System.out.println(result);
				
				//	下面对结果进行处理，
				//	当结果不多于16位数时直接输出，
				//	否则截取成16位数或用科学计算法表示,不进行四舍五入处理。
					
				// 正负号。
				String sign = "";

				if(result.signum() == -1){
					
					sign = "-";
				}
				
				displayResult = result.abs().toPlainString();
				
				
				// 考虑到可能输入等于0但小数位有多个0的数的时候，BigDecimal会用科学计数法导致显示时会超出屏幕的情况。
				
				if(result.compareTo(BigDecimal.ZERO) == 0){
					
					result = BigDecimal.ZERO;
					displayResult = "0";
					
					// 下面是绝对值比9999999999999999还大的数用科学计算法表示。
					
				}else if(result.abs().compareTo(BIG) == 1){
					
					if(result.scale() > 0){
					
						// displayResult.indexOf(46) 表示小数点的位置。
						
						displayResult = sign + displayResult.charAt(0) + "." 
								+ displayResult.substring(1,14) + "E" 
								+ (displayResult.indexOf(46) - 1);
					}else{
						
						displayResult = sign + displayResult.charAt(0) + "." 
								+ displayResult.substring(1,14) + "E" 
								+ (displayResult.length() - 1);
					}
					
					// 比 0.1 小的数如果小数位大于15，用科学计算法表示。
					
				}else if(result.abs().compareTo(SMALL) == -1){
					
					
					// 从左到右把0和小数点去掉，直到出现非0非小数点为止。
					
					int count = 0;
					while(displayResult.charAt(0) == '0' | displayResult.charAt(0) == '.'){

						if(displayResult.charAt(0) == '.'){

							count = 0;
						}
						count ++;
						displayResult = displayResult.substring(1);
					}
					
					// 如果 result 本身小数位数就小于16，则直接把 result 赋给 displayResult,无需用科学计数法。
					
					if(result.scale() < 16){
						
						displayResult = result.toPlainString();
						
					}else if(displayResult.length() == 1){
						
						displayResult = sign + displayResult + "E-" + count;
						
					}else if(displayResult.length() > 15){
						
						
						displayResult = sign + displayResult.charAt(0) + "."
								+ displayResult.substring(1,15) + "E-"
								+ count;	
						
					}else{
						
						displayResult = sign + displayResult.charAt(0) + "."
								+ displayResult.substring(1) + "E-" + count;
					}
					
					// 下面对位数大于16的数进行截取。如果无小数说明位数不会大于16，不做处理。
					
				}else if(result.scale() > 0 & displayResult.length() > 17){
					
					displayResult = sign + displayResult.substring(0,16);	
					
				}else{
					
					displayResult = result.toPlainString();
				}
				
				break;
			}
		}
	}
}

class Lis implements ActionListener{

	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand() == "←"){
			
			Calculator.arithmetic('b');
			
		}else{
		
			Calculator.arithmetic(e.getActionCommand().charAt(0));
		}
		
		if(Calculator.getDisplayResult().length() > 13 | Calculator.resultText.getText().length() > 13){
			
			Calculator.resultText.setFont(Calculator.MIDDLEFONT);
			
		}else{
			
			Calculator.resultText.setFont(Calculator.BIGFONT);
		}
		
		Calculator.printText.setText(Calculator.getDisplayPrint());
		Calculator.resultText.setText(Calculator.getDisplayResult());
		
	}
}
