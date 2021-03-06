package com.fancige.security;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Code {

	public static void getVerificationCode(HttpServletRequest req, HttpServletResponse res) {
		int imageWidth = 70;
		int imageHeight = 30;
		int lines = 5;
		int codeLength = 4;
		String codeChars = "1234567890";
		String code = "";

		BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = bi.getGraphics();

		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.DIALOG, Font.ITALIC, 25));
		g.fillRect(0, 0, imageWidth, imageHeight);

		Random random = new Random();
		for (int i = 0; i < codeLength; i++) {
			code += codeChars.charAt(random.nextInt(codeChars.length()));
		}

		req.getSession().setAttribute("verificode", code);

		g.setColor(Color.BLACK);
		g.drawString(code, 3, 25);
		for (int i = 0; i < lines; i++) {
			g.drawLine(random.nextInt(imageWidth), random.nextInt(imageHeight), random.nextInt(imageWidth),
					random.nextInt(imageHeight));
		}
		try (OutputStream os = res.getOutputStream()) {

			ImageIO.write(bi, "png", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
