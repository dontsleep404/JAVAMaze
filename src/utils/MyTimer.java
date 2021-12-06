package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

public class MyTimer {
	private long preUpdate;
	private long lastUpdate;
	public MyTimer(int timeToUpdate, int timeToRender, ActionListener update, ActionListener render) {
		Date d = new Date();
		this.lastUpdate = d.getTime();
		this.preUpdate = this.lastUpdate;
		Timer uTimer = new Timer(timeToUpdate, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				preUpdate = lastUpdate;
				lastUpdate = new Date().getTime();
				update.actionPerformed(null);				
			}
		});
		uTimer.start();
		Timer rTimer = new Timer(timeToRender, render);
		rTimer.start();
	}
	public float getParaticks() {
		Date d = new Date();
		return (float) ((d.getTime() - getLastUpdate()) / Math.max(getLastUpdate() - getPreUpdate(), 0.00001));
	}
	public long getPreUpdate() {
		return preUpdate;
	}
	public void setPreUpdate(int preUpdate) {
		this.preUpdate = preUpdate;
	}
	public long getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(int lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
}
