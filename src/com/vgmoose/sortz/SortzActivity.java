package com.vgmoose.sortz;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater.Filter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class SortzActivity extends Activity implements OnClickListener
{

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    ImageView imageView;
    
    boolean right = true;
    boolean gameover = false;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main);

        super.onCreate(savedInstanceState);
        
        imageView = (ImageView) findViewById(R.id.imageView1);
        	
        
     

        /* ... */

        // Gesture detection
        gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        
        imageView.setOnClickListener(SortzActivity.this); 
        imageView.setOnTouchListener(gestureListener);

    }
    
    

    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
            	
//                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
//                    return false;
                
                if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE  && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                	   Log.v("swipe","up swipe");
                   	Toast.makeText(SortzActivity.this, "up swipe", Toast.LENGTH_SHORT).show();
                   	
                }  
                
               else if(e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE  && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
             	   Log.v("swipe","down swipe");
                	Toast.makeText(SortzActivity.this, "down swipe", Toast.LENGTH_SHORT).show();
                }  
                
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                   Log.v("swipe","left swipe");
                   swipeLeft();
//                	Toast.makeText(SortzActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    Log.v("swipe","right swipe");
                    swipeRight();
//                	Toast.makeText(SortzActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }
        
        void getNext()
        {
        	if (!gameover)
        	{
        	if ((int)(Math.random()*2) == 0)
        	{
        		imageView.setImageDrawable(getImageByName("mudkip", SortzActivity.this));
        		right = true;
        	}
        	else
        	{
        		imageView.setImageDrawable(getImageByName("phillyd", SortzActivity.this));
        		right = false;
        	}
        	}
        	else 
        		imageView.setImageDrawable(getImageByName("black", SortzActivity.this));
        }
        
        void gameOver()
        {
        	gameover = true;
        	getNext();
        }
        
        void swipeLeft()
        {
        	if (right)
        		gameOver();
        	else
        		getNext();
        		
        }
        
        void swipeRight()
        {
        	if (!right)
        		gameOver();
        	else
        		getNext();
        }

    }
    
    public static Drawable getImageByName(String nameOfTheDrawable, Activity a) {
	    Drawable drawFromPath;
	    int path = a.getResources().getIdentifier(nameOfTheDrawable, 
	                                    "drawable", "com.vgmoose.sortz"); 

	    Options options = new BitmapFactory.Options();
//	    options.inScaled = false;
	    Bitmap source = BitmapFactory.decodeResource(a.getResources(), path, options);

	    drawFromPath = new BitmapDrawable(source);  

	    return drawFromPath;
	}

	@Override
	public void onClick(View v) {
		 Filter f = (Filter) v.getTag();
//	     FilterFullscreenActivity.show(this, input, f);
		
	}
}