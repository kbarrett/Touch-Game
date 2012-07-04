package tom.birthday;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FirstActivity extends Activity{
   
	Judge judge;
	String s = "";
	GestureDetector detector;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final View rellayout = findViewById(R.id.relativelayout);
        
        rellayout.setOnTouchListener(new OnTouchListener(){
        	
        	public boolean onTouch(View v, MotionEvent event) {
        		
        		if(event.getAction()!=MotionEvent.ACTION_MOVE)
        		{
        			reset();
        			return true;
        		}
        		
        		else
        		{   
	                RelativeLayout layout = (RelativeLayout)v;
	
	                for(int i = 0; i < layout.getChildCount(); i++)
	                {
	                    View view = layout.getChildAt(i);
	                    Rect outRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
	
	                    if(outRect.contains((int)event.getX(), (int)event.getY()))
	                    {
	                        char which = '0';
	                        
	                        switch(view.getId())
	                        {
		                        case R.id.topleft :
		                        { 
		                        	which = '1'; 
		                        	break; 
		                        }
		                        case R.id.topmiddle :
		                    	{ 
		                    		which = '2'; 
		                    		break; 
		                    	}
		                        case R.id.topright :
		                    	{ 
		                    		which = '3'; 
		                    		break; 
		                    	}
		                        case R.id.middleleft :
		                    	{ 
		                    		which = '4'; 
		                    		break; 
		                    	}
		                        case R.id.middlemiddle :
		                    	{ 
		                    		which = '5'; 
		                    		break; 
		                    	}
		                        case R.id.middleright :
		                    	{ 
		                    		which = '6'; 
		                    		break; 
		                    	}
		                        case R.id.bottomleft :
		                    	{ 
		                    		which = '7'; 
		                    		break; 
		                    	}
		                        case R.id.bottommiddle :
		                    	{ 
		                    		which = '8'; 
		                    		break; 
		                    	}
		                        case R.id.bottomright :
		                    	{ 
		                    		which = '9'; 
		                    		break; 
		                    	}
	                        }
	                        
	                        if(which!='0'&&!s.contains(""+which))
	                        {
	                        	Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
	                        	vib.vibrate(40);
	                        	s+=which;
	                        }
	                        
	                        return true;
	                    }
	                }
	                return false;
        		}
        	}
        });

    }
    
    public void onStart()
    {
    	super.onStart();
    	judge = new Judge();
    }
   
	private void reset()
	{
		boolean isNice = judge.isNice(Drawing.fromOrderedToWhere(s));
		Log.d("s is " + s, isNice+"");
		s = "";
		//reset pictures
	}
	
	private void onSuccess()
	{
		KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE); 
		KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE); 
		lock.disableKeyguard();
		finish();
	}

}