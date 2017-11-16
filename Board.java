package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener{
	
	private BufferedImage blocks;
	
	private final int blockSize = 32;
	
	private final int boardWidth = 10, boardHeight = 20;
	
	private int [][] board = new int[boardHeight][boardWidth];
	
	private Shape[] shapes = new Shape[8];
	
	private Shape currentShape;
	
	public int score = 0;
	
	private Timer timer;
	
	private final int FPS = 60;
	
	private final int delay = 1000/FPS;
	
	
	
	public Board(){
		try {
			blocks = ImageIO.read(Board.class.getResource("/Tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		timer = new Timer(delay, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
				repaint();
				
			}
			});
		timer.start();
		
		// shape
		
		shapes[0] = new Shape(blocks.getSubimage(blockSize*0, 0, blockSize, blockSize), new int[][]{
			{1,1,1,1} // I-Shape
		}, this, 1);
		shapes[1] = new Shape(blocks.getSubimage(blockSize*1, 0, blockSize, blockSize), new int[][]{
			{1,1,0},
			{0,1,1} // Z-Shape
		}, this, 2);
		shapes[2] = new Shape(blocks.getSubimage(blockSize*2, 0, blockSize, blockSize), new int[][]{
			{0,1,1},
			{1,1,0} // S-Shape
		}, this, 3);
		shapes[3] = new Shape(blocks.getSubimage(blockSize*3, 0, blockSize, blockSize), new int[][]{
			{1,1,1},
			{0,1,0} // T-Shape
		}, this, 4);
		shapes[4] = new Shape(blocks.getSubimage(blockSize*4, 0, blockSize, blockSize), new int[][]{
			{1,1,1},
			{1,0,0} // L-Shape
		}, this, 5);
		shapes[5] = new Shape(blocks.getSubimage(blockSize*5, 0, blockSize, blockSize), new int[][]{
			{1,1,1},
			{0,0,1} // L2-Shape
		}, this, 6);
		shapes[6] = new Shape(blocks.getSubimage(blockSize*6, 0, blockSize, blockSize), new int[][]{
			{1,1},
			{1,1} // O-Shape
		}, this, 7);
		shapes[7] = new Shape(blocks.getSubimage(blockSize*7, 0, blockSize, blockSize), new int[][]{
			{1} // Mexican -Shape
		}, this ,8);
		
		setNextShape();
		
		
	}
	
	public void update(){
		score += 1;
		currentShape.update();
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		currentShape.render(g);
		
		for(int row = 0; row < board.length; row++)
			for(int col = 0; col < board[row].length; col++)
				if(board[row][col] != 0)
					g.drawImage(blocks.getSubimage((board[row][col]-1)*blockSize, 0, blockSize, blockSize), col*blockSize, row*blockSize, null);	
		
		
		for(int i = 0; i < boardHeight ; i++){
			g.drawLine(0, i*blockSize, boardWidth*blockSize, i*blockSize);
		
		}
		for(int j = 0; j < boardWidth; j++ ){
			g.drawLine(j*blockSize,  0 , j*blockSize, boardHeight*blockSize);
		}
		g.setColor(Color.gray);
		g.fillRect(320, 0, 100, 638);
		g.setColor(Color.black);
		g.drawRect(0, 0, 320, 638);
		g.drawString("Score: "+ score, 325, 40);
		
		
	}
	
	public void setNextShape(){
		
		int index = (int)(Math.random()*shapes.length);
		
		Shape newShape = new Shape(shapes[index].getBlock(), shapes[index].getCoords(), this, shapes[index].getColor());
		
		currentShape = newShape;
		
	}
	
	
	
	
	
	public int getBlockSize(){
		return blockSize;
		
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_A){
			currentShape.setDeltaX(-1);
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			currentShape.setDeltaX(1);
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			currentShape.speedDown();
			
		}
		if(e.getKeyCode() == KeyEvent.VK_R){
			currentShape.rotate();
		}	
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_S){
			currentShape.normalSpeed();
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e){
		
		
	}
	
	
	
	
	
}
