package com.biol.biolbg.web.servlets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegCodeImage extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 70;

	private static final int HEIGHT = 32;

	private static final Color BACKGROUND_COLOR = new Color(100,149,237);

	private static final Color COLOR = new Color(0,0,139);

	private static final Font FONT = new Font("Times New Roman", Font.BOLD, 20);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("image/jpg");
		ServletOutputStream out = resp.getOutputStream();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_BYTE_INDEXED);

		Graphics graphics = image.getGraphics();
		graphics.setColor(BACKGROUND_COLOR);
		graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
		graphics.setColor(COLOR);
		graphics.setFont(FONT);

		String randomCode = getRandomCode();
		graphics.drawString(randomCode, 15, 23);

		//draw grid
		Random random = new Random();
		int step = random.nextInt(5) + 5;
		for (int x = 0; x < 70; x = x + step)
		{
			graphics.drawLine(x, 0, x, 30);
		}
		step = random.nextInt(5) + 5;
		for (int y = 0; y < 30; y = y + step)
		{
			graphics.drawLine(0, y, 70, y);
		}

		ImageIO.write(image, "png", out);

		//store random code in session
		req.getSession().setAttribute("RandomRegCode", randomCode);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		super.doPost(req, resp);
	}

	private String getRandomCode()
	{
		Random random = new Random();
		Integer num = random.nextInt(9999);
		String res = num.toString();
		int len = res.length();
		if (res.length() < 4)
		{
			for (int i = len; i < 4; i++)
			{
				res = "0".concat(res);
			}
		}

		return res;
	}
}
