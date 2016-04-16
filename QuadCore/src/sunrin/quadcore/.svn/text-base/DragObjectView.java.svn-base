package sunrin.quadcore;

import Helper.Global;
import Helper.Util;
import Object.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DragObjectView extends SurfaceView implements SurfaceHolder.Callback{
	public static DragObjectViewThread m_thread;
	public static TimerThread t_thread;
	private int width,height;
	private boolean bMove = false;
	private Drag drag;
	private Drag Circle;
	private Drag smallCircle;
	private Paint paint;
	private int sec;
	private SoundPool sounds;
	private int drog;
	private int go;
	private RectF smallBounds;
	private RectF dragBounds;
	private Bitmap small;
	private Bitmap dragObj;
	private Bitmap gameBg;

	public DragObjectView(Context context , AttributeSet attrs){
		super(context,attrs);
		getHolder().addCallback(this);
		width = new Util().getWidth(getContext());
		height = new Util().getHeight(getContext());
		paint = new Paint();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		drag = new Drag(height / 20 , width / 4 , height / 4);
		Circle = new Drag(height / 10 , width / 4 , height / 4);
		smallCircle = new Drag(height / 11 , width / 4, height / 4);
		sec = 3;
		m_thread = new DragObjectViewThread(getHolder(), this);
		t_thread = new TimerThread();
		makeDrag();
		setClickable(true);
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		drog = sounds.load(context, R.raw.dragbgm , 1);
		go=0;
		smallBounds = new RectF();
		dragBounds = new RectF();
		small = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_object_dragtarget); // 비트맵 불러오
		small = Bitmap.createScaledBitmap(small, smallCircle.getRadius()*2, smallCircle.getRadius()*2, true); //비트맵 크기 지
		dragObj = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_object_dragobj); // 비트맵 불러오
		dragObj = Bitmap.createScaledBitmap(dragObj, drag.getRadius()*2, drag.getRadius()*2, true); //비트맵 크기 지
		gameBg = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_bg); // 비트맵 불러오
		gameBg = Bitmap.createScaledBitmap(gameBg, width/2, height/2, true); //비트맵 크기 지
	}

	public DragObjectView(Context context, AttributeSet attrs , int defStyle){
		super(context,attrs,defStyle);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Global.setLife(1);
//		m_thread.start();
		t_thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int keyAction = event.getAction();
		switch (keyAction){
		case MotionEvent.ACTION_MOVE:
			if (bMove){
				drag.setX((int)event.getX());
				drag.setY((int)event.getY());
			}

			break;
		case MotionEvent.ACTION_UP:
			bMove = false;
			break;
		case MotionEvent.ACTION_DOWN:
			this.checkImageMove((int)event.getX() , (int)event.getY());
			break;
		}
		if(drag.getX() <= smallCircle.getX()+Circle.getRadius() && drag.getX() >= smallCircle.getX()-Circle.getRadius() && 
				drag.getY() <= smallCircle.getY()+Circle.getRadius() && drag.getY() >= smallCircle.getY()-Circle.getRadius()){
			Circle.setRadius(0);
			Circle.setX(0);
			Circle.setY(0);
			smallCircle.setRadius(0);
			smallCircle.setX(0);
			smallCircle.setY(0);
			sec = ((int)(Math.random() * 5) + 1);
			sounds.play(drog, 0.5f,0.5f,0,0,1);
		}
		// 함수 override 해서 사용하게 되면  return  값이  super.onTouchEvent(event) 되므로
		// MOVE, UP 관련 이벤트가 연이어 발생하게 할려면 true 를 반환해주어야 한다.
		return true;
	}

	private void checkImageMove(int f, int g){
		int inRadius = drag.getRadius() * 2;
		if ((drag.getX() - inRadius < f) && (f < drag.getX() + inRadius)){
			if ((drag.getY() - inRadius < g) && (g < drag.getY() + inRadius)){
				bMove = true;
			}
		}
	}

	public void makeDrag(){
		Circle.setRadius(height / 10);
		Circle.setX((int)(Math.random() * (width / 2)+Circle.getRadius()));
		Circle.setY((int)(Math.random() * (height / 2)+Circle.getRadius()));
		while(Circle.getX()+Circle.getRadius() >= (width / 2) || 
				Circle.getY()+Circle.getRadius() >= (height / 2)){
			Circle.setX((int)(Math.random() * (width / 2))+Circle.getRadius());
			Circle.setY((int)(Math.random() * (height / 2))+Circle.getRadius());
			if(drag.getX() <= Circle.getX()+Circle.getRadius() && drag.getX() >= Circle.getX()-Circle.getRadius() && 
					drag.getY() <= Circle.getY()+Circle.getRadius() && drag.getY() >= Circle.getY()-Circle.getRadius()){
				continue;
			}
		}
		smallCircle.setRadius(height / 11);
		smallCircle.setX(Circle.getX());
		smallCircle.setY(Circle.getY());
	}

	public class DragObjectViewThread extends Thread{
		private SurfaceHolder m_surfaceHolder;

		public DragObjectViewThread(SurfaceHolder surfaceHolder, DragObjectView CircleObjectView){
			m_surfaceHolder = surfaceHolder;
		}

		public void run(){
			while(Global.isLife() == 1){
				Canvas c = null;

				try {
					c = m_surfaceHolder.lockCanvas(null);
					synchronized (m_surfaceHolder) {
						doDraw(c);
					}
				} finally {
					if (c != null){
						m_surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

	public void doDraw(Canvas c){
		//c.drawColor(Color.MAGENTA);  //배경
		c.drawBitmap(gameBg, 0, 0, paint);
		paint.setColor(Color.WHITE);
		c.drawLine(width / 2 , 0, width / 2 , height / 2,paint);
		c.drawLine(0, height/2, width/2, height/2, paint);
		
		smallBounds.set(smallCircle.getX()-smallCircle.getRadius(),smallCircle.getY()-smallCircle.getRadius(),
				smallCircle.getX()+smallCircle.getRadius(),smallCircle.getY()+smallCircle.getRadius());
		dragBounds.set(drag.getX()-drag.getRadius(), drag.getY()-drag.getRadius(), 
				drag.getX()+drag.getRadius(), drag.getY()+drag.getRadius());
		paint.setTextSize(height / 24);
		paint.setColor(Color.WHITE);
		c.drawCircle(Circle.getX(), Circle.getY(), Circle.getRadius(), paint); // 공 도착지
		paint.setColor(Color.BLACK);
		//c.drawCircle(smallCircle.getX(), smallCircle.getY(), smallCircle.getRadius(), paint);
		c.drawBitmap(small, null, smallBounds, paint);
		paint.setColor(Color.WHITE);
		c.drawText(""+sec, smallCircle.getX(), smallCircle.getY(), paint); //남은 시간
		//c.drawCircle(drag.getX(), drag.getY(), drag.getRadius(), paint); 
		c.drawBitmap(dragObj, null, dragBounds, paint);//끌고 가는 공
	}

	public class TimerThread extends Thread{
		public void run(){
			while(Global.isLife() == 1){
				try
				{
					sleep(1000);
					go++;
					if(go>15){
						sec--;
						if(sec == 0 && Circle.getRadius() == height / 10)
							Global.setLife(0);
						else if(sec == 0 && Circle.getRadius() == 0){
							makeDrag();
							sec = 3;
						}
					}
				}
				catch(InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(go==15)
					m_thread.start();
			}
		}
	}
}
