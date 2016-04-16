package sunrin.quadcore;

import java.util.Random;

import Helper.*;
import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.Align;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.*;
import android.view.*;

public class JumpingObjectView extends SurfaceView implements
		SurfaceHolder.Callback {
	int width, height;
	private float ballRadius;
	private float ballX;
	private float ballY;
	private float ballSpeed = 0;
	private RectF ballBounds;
	private float gravity;
	private float obstSpeed = 100;
	private float startLine;
	private float endLine;
	private float lengthLine;
	private Paint paint;
	private boolean jumpPressed = false;
	private boolean falling = false;
	private double ground;
	private int rndnum;
	Random oRandom;
	public static updateThread u_thread;
	private SoundPool sounds;
	private int jump;
	private Bitmap wheel;
	private int rotating;
	private float accel;
	private Bitmap gameBg;

	public JumpingObjectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		width = new Util().getWidth(getContext());
		height = new Util().getHeight(getContext());
		ballBounds = new RectF();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ground = (int) (height / 3);
		ballRadius = height / 20;
		ballX = width / 15 + ballRadius;
		ballY = (float) (ground - ballRadius - 1);
		gravity = height / 380;
		startLine = width / 2;
		endLine = width / 2;
		lengthLine = width / 6;
		rndnum = width + width / 2;
		oRandom = new Random();
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		jump = sounds.load(context, R.raw.jump , 1);
		wheel = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_object_wheel); // 비트맵 불러오
		wheel = Bitmap.createScaledBitmap(wheel, (int)ballRadius*2, (int)ballRadius*2, true); //비트맵 크기 지
		rotating=0;
		accel=5;
		gameBg = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_bg); // 비트맵 불러오
		gameBg = Bitmap.createScaledBitmap(gameBg, width/2, height/2, true); //비트맵 크기 지
	}

	@Override
	protected void onDraw(Canvas canvas) {
	}

	public class updateThread extends Thread {
		private SurfaceHolder Holder;

		public updateThread(SurfaceHolder Holder) {
			this.Holder = Holder;
		}

		public void run() {
			while (Global.isLife() == 1) {

				ballY -= ballSpeed;
				ballSpeed -= gravity;

				startLine -= (width / obstSpeed);
				if (endLine - startLine >= lengthLine) {
					endLine -= (width / obstSpeed);
				}
				if (endLine < 0) {
					rndnum = oRandom.nextInt(rndnum) + width;
				}

				if (ballX >= startLine && ballX <= endLine) {
					jumpPressed = true;
					if (ballY + ballRadius >= ground - 1)
						falling = true;
				} else if (ballY + ballRadius >= ground - 1 && falling == false) {
					ballSpeed = 0;
					ballY = (float) (-ballRadius + ground);
					jumpPressed = false;
				}

				if (ballY - ballRadius >= ground) {
					Global.setLife(0);
				}
				if (endLine < -rndnum) {
					startLine = width / 2;
					endLine = width / 2;
					obstSpeed -= 4;
					accel+=1;
				}
				if(rotating>=360)
					rotating=rotating-360;
				if(!jumpPressed){
				rotating+=accel;
				}
				Canvas canvas = null;
				try {
					canvas = Holder.lockCanvas(null);
					synchronized (Holder) {
					//	canvas.drawColor(Color.YELLOW); //배경
						canvas.drawBitmap(gameBg, 0, 0, paint);
						paint.setColor(Color.WHITE);
						canvas.drawLine(width / 2 , 0, width / 2 , height / 2,paint);
						canvas.drawLine(0, height/2, width/2, height/2, paint);
				
						
					//	canvas.drawRect(0, (int)ground, width/2, height/2, paint); //땅  
						canvas.drawRect(0, (int)ground, startLine, (int)ground+width/200, paint);
						canvas.drawRect(endLine, (int)ground, width/2, (int)ground+width/200, paint);
						paint.setColor(Color.YELLOW);
					//	canvas.drawRect(startLine, (int) ground,  // 구멍
					//			endLine, height/2 , paint);
						
						ballBounds.set(ballX - ballRadius, ballY - ballRadius,  //구멍의 경계선
								ballX + ballRadius, ballY + ballRadius);
						paint.setColor(Color.GREEN);
						//canvas.drawOval(ballBounds, paint);   //뛰는 케릭터
						canvas.rotate(rotating, ballX, ballY);
						canvas.drawBitmap(wheel, null, ballBounds, paint);
						
					}
				}

				finally {
					if (canvas != null) {
						Holder.unlockCanvasAndPost(canvas);
					}
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
					}
				}

			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (jumpPressed == false) {
				sounds.play(jump, 0.5f,0.5f,0,0,1);
				ballSpeed = width / 45;
				jumpPressed = true;
			}
		}
		return true;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		u_thread = new updateThread(holder);
		u_thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}
