package ui;

public class Screen {
	private int width;
	private int height;
	public Screen() {
		this.width = 0;
		this.height = 0;
	}
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
	}	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
