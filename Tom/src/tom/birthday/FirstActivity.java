package tom.birthday;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class FirstActivity extends Activity{
   
	private Judge judge;
	private String s = "";
	
	private Bitmap originalButton;
	private Bitmap wrongbutton;
	private Bitmap rightbutton;
	
	private int vibratetime = 40;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        originalButton = BitmapFactory.decodeResource(getResources(),R.drawable.original_button);
    	wrongbutton = BitmapFactory.decodeResource(getResources(),R.drawable.wrong_button);
    	rightbutton = BitmapFactory.decodeResource(getResources(),R.drawable.right_button);
    	
    	judge = new Judge();
        
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
	                        	vib.vibrate(vibratetime);
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	menu.add("Change Vibrate");
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getTitle().equals("Change Vibrate"))
    	{
    		final View menuView = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.vibrate_popup, null);
    		final AlertDialog alert = new AlertDialog.Builder(this)
    	    .setTitle("Set vibrate length:")
    	    .setView(menuView)
		    .show();
    		
    		((Spinner)menuView.findViewById(R.id.optionSpinner)).setSelection((vibratetime/10)-4);
    		
    		menuView.findViewById(R.id.okbutton).setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					String item = (String)((Spinner)menuView.findViewById(R.id.optionSpinner)).getSelectedItem();
					if(item!=null)
					{
						vibratetime = Integer.parseInt(item);
					}
					alert.dismiss();
				}
			});
    		menuView.findViewById(R.id.cancelbutton).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					alert.dismiss();
				}
			});
    	}
    	return super.onOptionsItemSelected(item);
    }
   
	private void reset()
	{
		boolean isNice = judge.isNice(Drawing.fromOrderedToWhere(s));
		Log.d(isNice+"", "s is " + s + ", with where value: " + Drawing.fromOrderedToWhere(s));
		
		for(int i = 1; i<=9; i++)
		{
			ImageView view = null;
			switch(i)
			{
			case 1 : {view = (ImageView)findViewById(R.id.topleft); break;}
			case 2 : {view = (ImageView)findViewById(R.id.topmiddle); break;}
			case 3 : {view = (ImageView)findViewById(R.id.topright); break;}
			case 4 : {view = (ImageView)findViewById(R.id.middleleft); break;}
			case 5 : {view = (ImageView)findViewById(R.id.middlemiddle); break;}
			case 6 : {view = (ImageView)findViewById(R.id.middleright); break;}
			case 7 : {view = (ImageView)findViewById(R.id.bottomleft); break;}
			case 8 : {view = (ImageView)findViewById(R.id.bottommiddle); break;}
			case 9 : {view = (ImageView)findViewById(R.id.bottomright); break;}
			}
			
			if(s.contains(i+""))
			{				
				if(isNice) {view.setImageBitmap(rightbutton);}
				else {view.setImageBitmap(wrongbutton);}
			}
			else
			{
				view.setImageBitmap(originalButton);
			}
		}
		
		s = "";
	}
	
	/*private void resetImages()
	{
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativelayout);
		for(int i = 0; i<layout.getChildCount(); i++)
		{
			View view1 = layout.getChildAt(i);
			if(view1 instanceof ImageView)
			{
				ImageView view = (ImageView) view1;
				view.setImageBitmap(originalButton);
			}
		}
	}*/
	
	private void onSuccess()
	{
		KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE); 
		KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE); 
		lock.disableKeyguard();
		finish();
	}

}