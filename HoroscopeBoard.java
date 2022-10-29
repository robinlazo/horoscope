package Horoscope;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Horoscope.signos.ZodiacSigns;

public class HoroscopeBoard extends JPanel implements ActionListener{
	private JPanel gridZodiac;
	private JLabel lab;
	private final int DESIRED_DIMENSIONS = 90;
	private BufferedImage bf;
	private Image resizedImg;
	private ZodiacInfo zodiac;
	private RegExp regExp;
	
	HoroscopeBoard() {
		initBoard();
	}
	
	public void initBoard() {
		setPreferredSize(new Dimension(300, 400));
		setBackground(new Color(0x192a56));
		
		gridZodiac = new JPanel();
		gridZodiac.setLayout(new GridLayout(4, 3, 0, 0));
		
		setHeader();
		
		initGrid();
		
	}
	
	public void setHeader() {	
		lab = new JLabel("Know Your fade right away!");
		lab.setFont(new Font("Helvetica", Font.BOLD, 14));
		lab.setForeground(Color.WHITE);
		add(lab);
	}
	
	public void initGrid() {
		for(ZodiacSigns signe : ZodiacSigns.values()) {
			try {
				
				File file = new File("src/Horoscope/signos/" + String.valueOf(signe).toLowerCase() + ".jpg");
				Image img = ImageIO.read(file);
				resizedImg = changeSize(img, DESIRED_DIMENSIONS, DESIRED_DIMENSIONS, BufferedImage.TYPE_INT_ARGB);
			    ImageIcon icon = new ImageIcon(resizedImg);
				JButton type = new JButton(icon);
				
				type.setBorder(BorderFactory.createLineBorder(Color.white));
				type.setCursor(new Cursor(Cursor.HAND_CURSOR));
				type.putClientProperty("type", signe);
				type.addActionListener(this);
				
			
				gridZodiac.add(type);
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		add(gridZodiac);
	}

	
	public Image changeSize(Image img, int w, int h, int type) {
		bf = new BufferedImage(w, h, type);
		Graphics2D gr2 = bf.createGraphics();
	    gr2.drawImage(img, 0, 0, w, h, null);
	    gr2.dispose();
	    return bf;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton btn = (JButton) e.getSource();    
	    zodiac = new ZodiacInfo();   
	    Map<String, String> info = zodiac.getInfo(btn.getClientProperty("type").toString());
	  
	    displayHoroscope(info);	
	}
	
	public void displayHoroscope(Map<String, String> info) {
		removeAll();
		regExp = new RegExp();
		
		Set<Map.Entry<String, String>> set = info.entrySet();
		for(Map.Entry<String, String> pair : set) {
			String header = regExp.capitalize(pair.getKey());
		    setContent(header, pair.getValue());
		}
		
		validate();
		repaint();		
	}
	
	public void setContent(String header, String content) {
		 String info = "<html><p width='200px'>" + header + ": " + content + "</p></html>";
		 JLabel hdr = new JLabel(info);
		 hdr.setOpaque(true);
		 hdr.setBorder(new EmptyBorder(5, 15, 5, 15));
		 hdr.setBackground(new Color(0x55efc4));
		 add(hdr);
	}
}
