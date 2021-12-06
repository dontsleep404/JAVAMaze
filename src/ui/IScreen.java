package ui;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
public interface IScreen {
	public void onUpdate();
	public void onRender(Graphics g2d);
	public void onMouseClick(MouseEvent e);
	public void onMouseRelease(MouseEvent e);
	public void onMouseMove(MouseEvent e);
	public void onKeyPress(KeyEvent e);
	public void onKeyRelease(KeyEvent e);
}
