package moonsnake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MPanel extends JPanel implements KeyListener, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageIcon title;
	ImageIcon up;
	ImageIcon down;
	ImageIcon left;
	ImageIcon right;
	ImageIcon body;
	ImageIcon food;
	
	//�����ߵĳ��Ⱥ�����
	int len = 3;
	int[] snakex = new int[750];
	int[] snakey = new int[750];
	//������ͷ����,R,L,U,D
	String direct;
	String foreDirect;
	//������Ϸ״̬
	boolean isStart = false;
	boolean isFailed = false;
	//����ʱ��
	Timer timer = new Timer(500,this);
	Timer timer1 = new Timer(300,this);
	Timer timer2 = new Timer(100,this);
	int cnt=0;
	//����ʳ��λ��
	int foodx;
	int foody;
	Random rand = new Random();
	//�������
	int score = 0;
	
	public MPanel() {
		//��ʼ����
		initSnake();
		loadImages();
		this.setFocusable(true);	//��ȡ�����¼�
		this.addKeyListener(this);
		timer.start();				//����ʱ��
		
	}
	
	//����
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		//������
		title.paintIcon(this,g,25,11);
		
		//����Ϸ��
		g.fillRect(25, 75, 850, 600);
	
		//������
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.BOLD,18));
		g.drawString("Len: "+len, 750, 35);
		g.drawString("Score: "+score, 750, 55);
		
		//����
		if(direct == "R") {
			right.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if(direct == "L") {
			left.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if(direct == "U") {
			up.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else {
			down.paintIcon(this, g, snakex[0], snakey[0]);
		}
		
		for(int i = 1; i<len; i++)
		{
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		
		//����ʳ��
		food.paintIcon(this, g, foodx, foody);
		
		//������Ϸ
		if(isStart == false) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial",Font.BOLD,40));
			g.drawString("Press Space Start", 280, 300);
		}
		//��Ϸʧ��
		if(isFailed) {
			//��ӡʧ��
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial",Font.BOLD,60));
			g.drawString("Game    Over", 290, 300);
			//��ӡ����
			g.setFont(new Font("arial",Font.BOLD,18));
			g.drawString("|--  Len: "+len, 400, 400);
			g.drawString("|--  Score: "+score, 400,430);
			//��ӡ��ʾ
			g.drawString("|--  Space Again", 400, 460);
		}
	}
	
	//��ʼ��
	public void initSnake(){
		//��ʼ����������
		len = 3;
		score = 0;
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
		direct = "R";
		foreDirect = "R";
		
		//�������ʳ��
		foodx = 25 + 25*rand.nextInt(34);
		foody = 75 + 25*rand.nextInt(24);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	//�����¼�,�ı��ߵķ���
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE) {
			if(isFailed) {
				isFailed = false;
				initSnake();
			}
			else isStart = !isStart;
			repaint();
		}
		else if(keyCode == KeyEvent.VK_LEFT) {
			if(foreDirect == "R") cnt-=1;	//�ٶȼ�1
			else if(foreDirect == "L") cnt+=1;//�ٶȼ�1
			else {
				cnt = 0;	//���¸�ֵΪ0
				direct = "L";
				foreDirect = "L";
			}
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			if(foreDirect == "L" ) cnt-=1;
			else if(foreDirect == "R" ) cnt+=1;
			else {
				cnt = 0;
				direct = "R";
				foreDirect = "R";
			}
		}
		else if(keyCode == KeyEvent.VK_DOWN) {
			if(foreDirect == "U") cnt-=1;
			else if(foreDirect == "D") cnt+=1;
			else {
				cnt = 0;
				direct = "D";
				foreDirect = "D";
			}
		}
		else if(keyCode == KeyEvent.VK_UP) {
			if(foreDirect == "D") cnt-=1;
			else if(foreDirect == "U") cnt+=1;
			else {
				cnt = 0;
				direct = "U";
				foreDirect = "U";
			}
		}
		if(cnt > 2) cnt =2;
		else if(cnt < 0) cnt = 0; 
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	//ʱ���¼�,�ı��ߵ�λ��
	@Override
	public void actionPerformed(ActionEvent e) {
		//�ı��ߵ�����
		if(isStart && !isFailed) {
			
			//�ı���������
			for(int i = len-1; i>0; i--) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			//�ı�ͷ����
			if(direct == "R") {
				snakex[0] = snakex[0]+25;
//				if(snakex[0] > 850) isFailed = true;//snakex[0] = 25;
			}			
			else if(direct == "L") {
				snakex[0] = snakex[0]-25;
//				if(snakex[0] < 25) isFailed = true;//snakex[0] = 850;
			}
			else if(direct == "U") {
				snakey[0] = snakey[0]-25;
//				if(snakey[0] < 75) isFailed = true;//snakey[0] = 650;
			}
			else if(direct == "D") {
				snakey[0] = snakey[0]+25;
//				if(snakey[0] > 650) isFailed = true;//snakey[0] = 75;
			}
			
			/******* �÷� *********/
			//�Ե�ʳ��
			if(snakex[0] == foodx && snakey[0] == foody) {
				len++;
				foodx = 25 + 25*rand.nextInt(34);
				foody = 75 + 25*rand.nextInt(24);
				score += 10;
			}
			//���»���
			repaint();
			
			/*********** ��Ϸ���� ******/
			//�Ե��Լ�
			for(int i=1; i < len; i++) {
				if(snakex[i] == snakex[0] && snakey[i] == snakey[0]) {
					isFailed = true;
				}
			}
			//����ǽ
			if(snakex[0]>850 || snakex[0]<25 || snakey[0]>650 || snakey[0]<75) {
				isFailed = true;
			}
		}
		if(cnt == 0) {
			timer.start();
			timer1.stop();
			timer2.stop();
		}
		else if(cnt == 1) {
			timer1.start();
			timer.stop();
			timer2.stop();
		}
		else if(cnt == 2) {
			timer2.start();
			timer.stop();
			timer1.stop();
		}
	}
	
	private void loadImages() {
		InputStream is;
		try {
			is = getClass().getClassLoader().getResourceAsStream("images/title.png");
			title = new ImageIcon(ImageIO.read(is));
			
			is = getClass().getClassLoader().getResourceAsStream("images/up.png");
			up = new ImageIcon(ImageIO.read(is));
			
			is = getClass().getClassLoader().getResourceAsStream("images/down.png");
			down = new ImageIcon(ImageIO.read(is));
			
			is = getClass().getClassLoader().getResourceAsStream("images/left.png");
			left = new ImageIcon(ImageIO.read(is));
			
			is = getClass().getClassLoader().getResourceAsStream("images/right.png");
			right = new ImageIcon(ImageIO.read(is));
			
			is = getClass().getClassLoader().getResourceAsStream("images/body.png");
			body = new ImageIcon(ImageIO.read(is));
			
			is = getClass().getClassLoader().getResourceAsStream("images/food.png");
			food = new ImageIcon(ImageIO.read(is));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
