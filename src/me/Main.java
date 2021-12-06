package me;

import java.awt.EventQueue;

import javax.swing.JFrame;

import ui.MenuScreen;
import utils.MyTimer;

public class Main {
	public static MyTimer timer;
	public static void main(String[] args) {
		init();
	}
	public static void init() {
		EventQueue.invokeLater(new Runnable() {	
			public void run() {
				JFrame frame = new JFrame("Maze Game :V");	
				MenuScreen ms = new MenuScreen(30, 20, 30);
				timer = new MyTimer(1000/10, 1000/60, e-> ms.onUpdate(), e-> ms.repaint());
				frame.add(ms);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setResizable(false);;
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
}
