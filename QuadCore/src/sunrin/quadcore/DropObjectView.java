package sunrin.quadcore;

import Helper.Global;
import Helper.Util;
import Object.Drop;
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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DropObjectView extends SurfaceView implements SurfaceHolder.Callback{
	public static DropObjectViewThread m_thread;
	public static TimerThread t_thread;
	private int width,height,sec;
	private RectF ballBounds;
	private Drop drop;
	private Paint paint;
	private SoundPool sounds;
	private int pop;
	private int go;
	private Bitmap bomb;
	private Bitmap gameBg;
	
	public DropObjectView(Context context , AttributeSet attrs){
		super(context,attrs);
		getHolder().addCallback(this);
		m_thread = new DropObjectViewThread(getHolder(),this);
		t_thread = new TimerThread();
		ballBounds = new RectF();
		paint = new Paint();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		width = new Util().getWidth(getContext());
		height = new Util().getHeight(getContext());
		drop = new Drop(0,0,0);
		makeDrop();
		sec = 3;
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		pop = sounds.load(context, R.raw.bipbip , 1);
		go = 0;
		bomb = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_object_bomb); // 비트맵 불러오
		bomb = Bitmap.createScaledBitmap(bomb, drop.getRadius()*2, drop.getRadius()*2, true); //비트맵 크기 지
		gameBg = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_bg); // 비트맵 불러오
		gameBg = Bitmap.createScaledBitmap(gameBg, width/2, height/2, true); //비트맵 크기 지
	}
	
	public DropObjectView(Context context, AttributeSet attrs , int defStyle){
		super(context,attrs,defStyle);
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		Global.setLife(1);
		//m_thread.start();
		t_thread.start();
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e(""+event.getX(),""+event.getY());
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(drop.getX() <= (int)event.getX()+drop.getRadius() && drop.getX() >= (int)event.getX()-drop.getRadius() && 
					drop.getY() <= (int)event.getY()+drop.getRadius() && drop.getY() >= (int)event.getY()-drop.getRadius()){
				sounds.play(pop, 1,1,0,0,1);
				
				drop.setRadius(0);
				drop.setX(0);
				drop.setY(0);
				sec = ((int)(Math.random() * 5) + 1);
				Log.e("Drop sec",""+sec);
			}
		}
		return true;
	}
	
	public void makeDrop(){
		drop.setRadius(height / 12);
		drop.setX((int)(Math.random() * (width / 2)+drop.getRadius()));
		drop.setY((int)(Math.random() * (height / 2)+drop.getRadius()));
		while(drop.getX()+drop.getRadius() >= (width / 2) || 
				drop.getY()+drop.getRadius() >= (height / 2)){
			drop.setX((int)(Math.random() * (width / 2))+drop.getRadius());
			drop.setY((int)(Math.random() * (height / 2))+drop.getRadius());
		}
	}
	
	public class DropObjectViewThread extends Thread{
		private SurfaceHolder m_surfaceHolder;
		private DropObjectView m_DropObjectView;
		
		public DropObjectViewThread(SurfaceHolder surfaceHolder, DropObjectView DropObjectView){
			m_surfaceHolder = surfaceHolder;
			m_DropObjectView = DropObjectView;
		}
		
		public void run(){
			Canvas canvas = null;
			paint.setColor(Color.BLUE);
			
			while(Global.isLife() == 1){
				try{
					canvas = m_surfaceHolder.lockCanvas(null);
					paint.setColor(Color.WHITE);
					synchronized(m_surfaceHolder){
						//canvas.drawColor(Color.GREEN); //배경
						canvas.drawBitmap(gameBg, 0, 0, paint);
						canvas.drawLine(width / 2 , 0, width / 2 , height / 2,paint);
						canvas.drawLine(0, height/2, width/2, height/2, paint);
						
						paint.setColor(Color.LTGRAY);
						
						ballBounds.set(drop.getX() - drop.getRadius(), drop.getY() - drop.getRadius(),  // 떨어지는 공 경계값
						               drop.getX() + drop.getRadius(), drop.getY() + drop.getRadius());
						
						canvas.drawBitmap(bomb, null, ballBounds, paint); //비트맵 그리

						paint.setColor(Color.WHITE);
						
						paint.setTextSize(height / 24); 
						canvas.drawText("" + sec, drop.getX(), drop.getY() + (drop.getRadius()/2), paint); // 남은 시간
					}
				}finally{
					if(canvas != null){
						m_surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}
	
	public class TimerThread extends Thread{
		public void run(){
			while(Global.isLife() == 1){
				try
				{
					sleep(1000);
					go++;
					if(go>10){
						sec--;
						if(sec == 0 && drop.getRadius() == height / 12)
							Global.setLife(0);
						else if(sec == 0 && drop.getRadius() == 0){
							makeDrop();
							sec = 3;
						}
					
						Log.e("x = "+drop.getX(),"y = "+drop.getY());
						Log.e("width = "+width ,"height = "+height);
					}
				}
				catch(InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(go==10)
					m_thread.start();
			}
		}
	}
}
