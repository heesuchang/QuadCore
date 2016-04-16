package sunrin.quadcore;

import java.util.Random;
import Helper.Global;
import Helper.Util;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BalanceObjectView extends SurfaceView implements
SurfaceHolder.Callback {
	private SurfaceHolder Holder;
	private int width, height;
	private Paint paint;
	private RectF ballBounds;
	private float ballRadius;
	private float ballX;
	private float ballY;
	private float mouthAngle = 180;
	private float fruitX;
	private float fruitY;
	private float fruitRadius;
	private RectF fruitBounds;
	private float speed;
	private float distance;
	private int comingfrom = 2;
	public static BalanceThread balance;
	public static ScoreThread score;
	Random oRandom;
	private int rndnum;
	private SoundPool sounds;
	private int eating;
	private boolean started;
	private Bitmap fruit;
	private Bitmap pacman;
	private int turn;
	private Bitmap gameBg;
	
	public BalanceObjectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		width = new Util().getWidth(getContext());
		height = new Util().getHeight(getContext());
		balance = new BalanceThread();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ballX = (width / 8) * 2;
		ballY = width / 5;
		ballBounds = new RectF();
		ballRadius = width / 14;
		fruitX = ballX;
		fruitY = 0;
		fruitRadius = height / 24;
		fruitBounds = new RectF();
		speed = width / 400;
		rndnum = width /2;
		oRandom = new Random();
		score = new ScoreThread();
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		eating = sounds.load(context, R.raw.eat, 1);
		started = false;
		fruit = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_object_fruit); // 비트맵 불러오
		fruit = Bitmap.createScaledBitmap(fruit, (int)fruitRadius*2, (int)fruitRadius*2, true); //비트맵 크기 지
		pacman = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_object_pacman); // 비트맵 불러오
		pacman = Bitmap.createScaledBitmap(pacman, (int)ballRadius*2, (int)ballRadius*2, true); //비트맵 크기 지
		turn = 270;
		gameBg = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_bg); // 비트맵 불러오
		gameBg = Bitmap.createScaledBitmap(gameBg, width/2, height/2, true); //비트맵 크기 지
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		Holder = holder;
		balance.start();
		score.start();
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	
	public class BalanceThread extends Thread {
		public void run() {
			while (Global.isLife() == 1) {
				if (Global.getmOrientation() != OrientationEventListener.ORIENTATION_UNKNOWN) {
					if (Global.getmOrientation() - 90 <= 275
							&& Global.getmOrientation() - 90 >= 95)
						mouthAngle = Global.getmOrientation() - 90;
				}
				if (mouthAngle > 95 && mouthAngle < 160) {
					mouthAngle = 95;
					turn=180;
				} else if (mouthAngle >= 160 && mouthAngle < 210) {
					mouthAngle = 185;
					turn=270;
				} else if (mouthAngle >= 210 && mouthAngle < 275) {
					mouthAngle = 275;
					turn=0;
				} else {
				}
				distance = (float) Math.sqrt(Math.pow(ballX - fruitX, 2)
				                             + Math.pow(ballY - fruitY, 2));
				if(started){
					switch (comingfrom) {
					case 1: 
						
						fruitX += speed;
						if (mouthAngle == 95) {
							if (ballRadius > distance) {
								Log.e("sound","is playing");
								sounds.play(eating, 1.0f, 1.0f, 0, 0, 1.5f);
								comingfrom = oRandom.nextInt(3) + 1;
								if (comingfrom == 1) {
									fruitX = -(oRandom.nextInt(rndnum) + width /2);
									fruitY = ballY;
								}
								if (comingfrom == 2) {
									fruitY = -(oRandom.nextInt(rndnum) + width /2);
									fruitX = ballX;
								}
								if (comingfrom == 3) {
									fruitX = (oRandom.nextInt(rndnum) + width /2);
									fruitY = ballY;
								}
								speed = speed + width/800; // 속도 증가 
							}
						} else if (ballRadius + fruitRadius > distance) {
							// gameover
							Global.setLife(0);
						}
						break;
					case 2:
						fruitY += speed;
						if (mouthAngle == 185) {
							if (ballRadius > distance) {
								Log.e("sound","is playing");
								sounds.play(eating, 1.0f, 1.0f, 0, 0, 1.5f);
								comingfrom = oRandom.nextInt(3) + 1;
								if (comingfrom == 1) {
									fruitX = -(oRandom.nextInt(rndnum) + width /2);
									fruitY = ballY;
								}
								if (comingfrom == 2) {
									fruitY = -(oRandom.nextInt(rndnum) + width /2);
									fruitX = ballX;
								}
								if (comingfrom == 3) {
									fruitX = (oRandom.nextInt(rndnum) + width /2);
									fruitY = ballY;
								}
								speed = speed + width/800; // 속도 증가
							}
						} else if (ballRadius + fruitRadius > distance) {
							// gameover
							Global.setLife(0);
						}
						break;
					case 3:
						
						fruitX -= speed;
						if (mouthAngle == 275) {
							if (ballRadius > distance) {
								Log.e("sound","is playing");
								sounds.play(eating, 1.0f, 1.0f, 0, 0, 1.5f);
								comingfrom = oRandom.nextInt(3) + 1;
								if (comingfrom == 1) {
									fruitX = -(oRandom.nextInt(rndnum) + width /2);
									fruitY = ballY;
								}
								if (comingfrom == 2) {
									fruitY = -(oRandom.nextInt(rndnum) + width /2);
									fruitX = ballX;
								}
								if (comingfrom == 3) {
									fruitX = (oRandom.nextInt(rndnum) + width /2);
									fruitY = ballY;
								}
								speed = speed + width/800; // 속도 증
							}
						} else if (ballRadius + fruitRadius > distance) {
							// gameover
							Global.setLife(0);
						}
					}
					//turn = (int)(mouthAngle+90);
					//if(turn>360)
						//turn = turn - 360;
				}
				
				Canvas canvas = Holder.lockCanvas(null);
				
				if(!started){
					canvas.drawColor(Color.BLACK); //겜 시작 전에도 점수 보이도록
					paint.setColor(Color.WHITE);
					paint.setTextSize(width / 20);
					paint.setTextAlign(Align.RIGHT);
					canvas.drawText("점수 : "+Global.getScore(),width / 2,50,paint);
				}
				else{
					
					//canvas.drawColor(Color.CYAN);
					canvas.drawBitmap(gameBg, 0, 0, paint);
					paint.setColor(Color.WHITE);
					canvas.drawLine(width / 2 , 0, width / 2 , height / 2,paint);
					canvas.drawLine(0, height/2, width/2, height/2, paint);
					
					ballBounds.set(ballX - ballRadius, ballY - ballRadius, ballX
					               + ballRadius, ballY + ballRadius);
					fruitBounds.set(fruitX - fruitRadius, fruitY - fruitRadius, 
							fruitX + fruitRadius, fruitY + fruitRadius);
					paint.setColor(Color.WHITE);
					paint.setTextSize(width / 20);
					paint.setTextAlign(Align.RIGHT);
					canvas.drawText("점수 : "+Global.getScore(),width / 2,50,paint);
					//canvas.drawCircle(fruitX, fruitY, fruitRadius, paint);
					canvas.drawBitmap(fruit, null, fruitBounds, paint);
					
					paint.setColor(Color.YELLOW);
					//canvas.drawArc(ballBounds, mouthAngle + 130, 270, true, paint);
					canvas.rotate(turn, ballX, ballY);
					canvas.drawBitmap(pacman, null, ballBounds, paint);
				}
				
				
				
				
				Holder.unlockCanvasAndPost(canvas);
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	public class ScoreThread extends Thread{
		
		public void run(){
			while(Global.isLife() == 1){
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Global.setScore(Global.getScore()+1);
				if(Global.getScore() == 5){	
					started=true;
				}
			}
		}
	}
}

