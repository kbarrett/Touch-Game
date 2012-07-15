package tom.birthday;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class FirstActivity extends Activity{
   
	private Judge judge;
	private String s = "";
	
	private Bitmap originalButton;
	private Bitmap[] wrongbuttons = new Bitmap[9];
	private Bitmap[] rightbuttons = new Bitmap[9];
	private Bitmap[] clickedbuttons = new Bitmap[9];
	
	private ImageView previm;
	
	private int vibratetime = 40;

	private int current = 0;
	private float[][] xvalues = new float[9][2];
	private float[][] yvalues = new float[9][2];
	
	private class LineDraw extends View
	{
		public LineDraw(Context context) {
			super(context);
		}
		
		public LineDraw(Context context, AttributeSet attrs)
		{
			super(context, attrs);
		}

		@Override
		protected void onDraw(Canvas canvas) 
		{
			for(int i = 0; i<current; i++)
			{
				Paint p = new Paint();
		        p.setColor(Color.WHITE);
			    p.setStrokeWidth(15);
			    canvas.drawLine(xvalues[i][0], yvalues[i][0], xvalues[i][1], yvalues[i][1], p);
			}
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        originalButton = BitmapFactory.decodeResource(getResources(),R.drawable.original_button);
        
    	wrongbuttons[0] = BitmapFactory.decodeResource(getResources(),R.drawable.wrong_button1);
    	wrongbuttons[1] = BitmapFactory.decodeResource(getResources(),R.drawable.wrong_button2);
    	wrongbuttons[2] = BitmapFactory.decodeResource(getResources(),R.drawable.wrong_button3);
    	wrongbuttons[3] = BitmapFactory.decodeResource(getResources(),R.drawable.wrong_button4);
    	wrongbuttons[4] = BitmapFactory.decodeResource(getResources(),R.drawable.wrong_button5);
    	wrongbuttons[5] = BitmapFactory.decodeResource(getResources(),R.drawable.wrong_button6);
    	wrongbuttons[6] = BitmapFactory.decodeResource(getResources(),R.drawable.wrong_button7);
    	wrongbuttons[7] = BitmapFactory.decodeResource(getResources(),R.drawable.wrong_button8);
    	wrongbuttons[8] = BitmapFactory.decodeResource(getResources(),R.drawable.wrong_button9);
    	
    	rightbuttons[0] = BitmapFactory.decodeResource(getResources(),R.drawable.right_button1);
    	rightbuttons[1] = BitmapFactory.decodeResource(getResources(),R.drawable.right_button2);
    	rightbuttons[2] = BitmapFactory.decodeResource(getResources(),R.drawable.right_button3);
    	rightbuttons[3] = BitmapFactory.decodeResource(getResources(),R.drawable.right_button4);
    	rightbuttons[4] = BitmapFactory.decodeResource(getResources(),R.drawable.right_button5);
    	rightbuttons[5] = BitmapFactory.decodeResource(getResources(),R.drawable.right_button6);
    	rightbuttons[6] = BitmapFactory.decodeResource(getResources(),R.drawable.right_button7);
    	rightbuttons[7] = BitmapFactory.decodeResource(getResources(),R.drawable.right_button8);
    	rightbuttons[8] = BitmapFactory.decodeResource(getResources(),R.drawable.right_button9);
    	
    	clickedbuttons[0] = BitmapFactory.decodeResource(getResources(),R.drawable.clicked_button1);
    	clickedbuttons[1] = BitmapFactory.decodeResource(getResources(),R.drawable.clicked_button2);
    	clickedbuttons[2] = BitmapFactory.decodeResource(getResources(),R.drawable.clicked_button3);
    	clickedbuttons[3] = BitmapFactory.decodeResource(getResources(),R.drawable.clicked_button4);
    	clickedbuttons[4] = BitmapFactory.decodeResource(getResources(),R.drawable.clicked_button5);
    	clickedbuttons[5] = BitmapFactory.decodeResource(getResources(),R.drawable.clicked_button6);
    	clickedbuttons[6] = BitmapFactory.decodeResource(getResources(),R.drawable.clicked_button7);
    	clickedbuttons[7] = BitmapFactory.decodeResource(getResources(),R.drawable.clicked_button8);
    	clickedbuttons[8] = BitmapFactory.decodeResource(getResources(),R.drawable.clicked_button9);
    	
    	judge = new Judge();
    	
        final View rellayout = findViewById(R.id.relativelayout);
        
    	final LineDraw lineDraw = new LineDraw(this);
    	lineDraw.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    	
    	RelativeLayout rel = ((RelativeLayout)rellayout);
    	rel.addView(lineDraw);
    	
    	for(int i = rel.getChildCount()-1; i>=0; i--)
    	{
    		View childView = rel.getChildAt(i);
    		if(childView instanceof ImageView)
    		{
    			childView.bringToFront();
    		}
    	}
        
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
	                    
	                    if(!(view instanceof ImageView)) {continue;}
	                    
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
	                        
		                        if(view instanceof ImageView)
		                        {
			                        if(previm==null)
			                        {
			                        	previm = (ImageView) view;
			                        }
			                        else
			                        {
			                        	float[] x =
			                        		{
			                        			(float) (previm.getLeft() + (0.5 * previm.getWidth())),
			                        			(float) (view.getLeft() + (0.5 * view.getWidth()))
			                        		};
			                        	float[] y = 
			                        		{
			                        			(float) (previm.getTop() + (0.5 * previm.getHeight())), 
			                        			(float) (view.getTop() + (0.5 * view.getHeight()))
			                        		};
			                        	
			                        	xvalues[current] = x;
			                        	yvalues[current] = y;
			                        	
			                        	current++;
			                        	
			                        	lineDraw.invalidate();
			                        	
			                        	previm = (ImageView) view;
			                        }
			                        
			                        previm.setImageBitmap(clickedbuttons[current]);
		                        }
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
		if(s.equals(""))
		{
			xvalues = new float[9][2];
			yvalues = new float[9][2];
			current = 0;
			previm = null;
			
			RelativeLayout rellay = ((RelativeLayout)findViewById(R.id.relativelayout));
			for(int i = 0; i<rellay.getChildCount(); i++)
			{
				View v = rellay.getChildAt(i);
				if(v instanceof ImageView)
				{
					ImageView view = (ImageView) v;
					view.setImageBitmap(originalButton);
				}
			}
			return;
		}
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
				Log.d("s.indexOf(i)", s.indexOf(i+"")+"");
				if(isNice) {view.setImageBitmap(rightbuttons[s.indexOf(i+"")]);}
				else {view.setImageBitmap(wrongbuttons[s.indexOf(i+"")]);}
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