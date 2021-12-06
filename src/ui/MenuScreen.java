package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import me.Main;


@SuppressWarnings("serial")
public class MenuScreen extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener,KeyListener{
	private int width;
	private int height;
	
	public void addListener() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		addMouseWheelListener(this);
	}
	public MenuScreen() {
		addListener();
		this.width = 0;
		this.height = 0;
		setFocusable(true);
	}
	public MenuScreen(int col, int row,int size) {
		addListener();
		this.size = size;
		this.col = col;
		this.row = row;
		this.height = row*size;
		this.width = col*size;
		setFocusable(true);
		setBackground(new Color(27, 27, 27));		
		arr = new int[col][row];
		//newGame();
	}
	int mouseX = 0;
	int mouseY = 0;
	int size = 20;
	int arr[][];
	int col;
	int row;
	boolean gameOver = true;
	boolean hardEst = false;
	public void giai(int x,int y,Graphics2D g2d) {		
		List<int[]> tlist = new ArrayList<>();
		tlist.add(new int[] {1,0});
		tlist.add(new int[] {-1,0});
		tlist.add(new int[] {0,-1});
		tlist.add(new int[] {0,1});
		if(x == firstX && y == firstY) {
			return;
		}		
		for(int[] i : tlist) {
			if(canGo2(x+i[0], y+i[1]) && arr[x][y] - arr[x+i[0]][y+i[1]] == 1) {				
				g2d.drawLine(x*size, y*size, ((x+i[0])*size), ((y+i[1])*size));
				giai(x+i[0],y+i[1],g2d);
				break;
			}
		}
	}	
	Font arial = new Font("Arial", 1, 30);
	public void drawGameOver(Graphics2D g2d) {		
		g2d.setFont(arial);
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(new Color(255, 80, 95));
		String s = "Press Space for new game!";
		g2d.drawString(s, (this.width - g2d.getFontMetrics().stringWidth(s))/2, (this.height - g2d.getFontMetrics().getHeight())/2);
		String hard = hardEst ? "Farest" : "Random";
		g2d.drawString(hard, (this.width - g2d.getFontMetrics().stringWidth(hard))/2, 2*g2d.getFontMetrics().getHeight() + (this.height - g2d.getFontMetrics().getHeight())/2);
		String tut = "Press V to change Difficult";
		g2d.drawString(tut, (this.width - g2d.getFontMetrics().stringWidth(tut))/2, g2d.getFontMetrics().getHeight() + (this.height - g2d.getFontMetrics().getHeight())/2);
	}
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;		
		super.paintComponent(g);
		smoothPaint(g2d);
		if(gameOver) {
			drawGameOver(g2d);
			return;
		}
		g2d.setStroke(new BasicStroke(8));
		//g2d.translate(size/2, size/2);
		if(pX == firstX && pY == firstY) {
			gameOver = true;
		}
		int space = 1;
		for(int y = 0; y < row;y++) {
			for(int x =0;x<col;x++) {
				g2d.setColor(Color.white);
				if(x==firstX && y==firstY) {
					g2d.setColor(new Color(20, 200, 122));
				}
				if(x==lastX && y==lastY) {
					//g2d.setColor(new Color(200, 100, 122));
				}
				g2d.fillRect(x*size+space, y*size+space, size-2*space , size-2*space);
				if(canGo2(x+1, y) && Math.abs(arr[x][y]-arr[x+1][y]) == 1) {
					g2d.fillRect((x+1)*size-space, y*size+space, 2*space , size-2*space);
				}
				if(canGo2(x, y+1) && Math.abs(arr[x][y]-arr[x][y+1]) == 1) {
					g2d.fillRect(x*size+space, (y+1)*size-space, size-2*space , 2*space);
				}
				//g2d.setColor(Color.black);
				//g2d.drawString(arr[x][y]+"", x*size+size/2, y*size+size/2);
			}
		}		
		if(showDir) {
			g2d.setStroke(new BasicStroke(8));
			g2d.setColor(new Color(0.3f, 0.3f, 0.3f, 0.3f));
			g2d.translate(size/2, size/2);
			giai(pX, pY, g2d);
			g2d.translate(-size/2, -size/2);
		}
		//g2d.setColor(new Color(0.7f, 0.3f, 0.3f, 1f));
		g2d.setColor(new Color(255, 80, 95));
		int pXd = pX*size+4*space;
		int pYd = pY*size+4*space;
		int preXd = preX*size+4*space;
		int preYd = preY*size+4*space;
		int smoothX = (int) (preXd + (pXd - preXd)*Main.timer.getParaticks());
		int smoothY = (int) (preYd + (pYd - preYd)*Main.timer.getParaticks());
		g2d.fillRect(smoothX, smoothY, size-8*space , size-8*space);
		g2d.dispose();
	}
	int lastX = 0;
	int lastY = 0;
	int pX = 0;
	int pY = 0;
	int preX = 0;
	int preY = 0;
	int firstX = 0;
	int firstY = 0;
	boolean showDir = false;
	boolean dir[] = {false,false,false,false};
	
	public void go(int x,int y,int val) {
		if(hardEst && val > arr[lastX][lastY]) {
			lastX = x;
			lastY = y;
		}		
		arr[x][y] = val;
		List<int[]> tlist = new ArrayList<>();
		tlist.add(new int[] {1,0});
		tlist.add(new int[] {-1,0});
		tlist.add(new int[] {0,-1});
		tlist.add(new int[] {0,1});
		//int list[][] = {{1,0},{-1,0},{0,1},{0,-1}};
		Collections.shuffle(tlist);
		for(int[] i : tlist) {
			//System.out.println(canGo(x+i[0],y+i[1]));
			if(canGo(x+i[0],y+i[1])) go(x+i[0],y+i[1],val+1);
		}		
	}
	public boolean canGo2(int x, int y) {
		if(x<0 || y<0 || x>=col || y>=row) {
			return false;
		}
		return true;
	}
	public boolean canGo(int x, int y) {
		if(x<0 || y<0 || x>=col || y>=row) {
			return false;
		}
		return arr[x][y] == 0;
	}
	public void onUpdate() {
		preX = pX;
		preY = pY;
		int mX = (dir[2] ? -1 : 0) + (dir[3] ? 1 : 0);
		int mY = mX == 0 ? (dir[0] ? -1 : 0) + (dir[1] ? 1 : 0) : 0;
		if(canGo2(pX+mX, pY+mY) && Math.abs(arr[pX][pY] - arr[pX+mX][pY+mY]) == 1) {
			pX+=mX;
			pY+=mY;
		}		
	}
	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), getHeight());
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
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void newGame() {
		gameOver = false;
		firstX = (int) (Math.random()*col);
		firstY = (int) (Math.random()*row);
		for(int y = 0; y < row;y++)
			for(int x =0;x<col;x++) arr[x][y] = 0;
		go(firstX,firstY,1);
		if(!hardEst) {
			lastX = (int) (Math.random()*col);
			lastY = (int) (Math.random()*row);
		}
		pX = lastX;
		pY = lastY;
	}
	@Override
	public void keyPressed(KeyEvent e) {		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			showDir = true;
		}
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			dir[0] = true;
			break;
		case KeyEvent.VK_DOWN:
			dir[1] = true;
			break;
		case KeyEvent.VK_LEFT:
			dir[2] = true;
			break;
		case KeyEvent.VK_RIGHT:
			dir[3] = true;
			break;
		default:
			break;
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_V && gameOver) {
			hardEst = !hardEst;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			showDir = false;
			if(gameOver) {
				newGame();
			}
		}
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			dir[0] = false;
			break;
		case KeyEvent.VK_DOWN:
			dir[1] = false;
			break;
		case KeyEvent.VK_LEFT:
			dir[2] = false;
			break;
		case KeyEvent.VK_RIGHT:
			dir[3] = false;
			break;
		default:
			break;
		}
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub		
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void smoothPaint(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);            
        g2d.setStroke(new BasicStroke(6,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	}
}
