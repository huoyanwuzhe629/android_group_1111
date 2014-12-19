package cn.edu.bjtu.customeview;

import cn.edu.bjtu.customerview.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button one,two,three,four,five,six,seven;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		one=(Button) findViewById(R.id.one);
		two=(Button) findViewById(R.id.two);
		three=(Button) findViewById(R.id.three);
		four=(Button) findViewById(R.id.four);
		five=(Button) findViewById(R.id.five);
		six=(Button) findViewById(R.id.six);
		seven=(Button) findViewById(R.id.seven);
		one.setOnClickListener(oneListener);
		two.setOnClickListener(twoListener);
		three.setOnClickListener(threeListener);
		four.setOnClickListener(fourListener);
		five.setOnClickListener(fiveListener);
		six.setOnClickListener(sixListener);
		seven.setOnClickListener(sevenListener);
		
	}

	OnClickListener oneListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Phone pressed", Toast.LENGTH_SHORT).show();
			
		}
	};
	OnClickListener twoListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Locker pressed", Toast.LENGTH_SHORT).show();
			
		}
	};
	OnClickListener threeListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Time pressed", Toast.LENGTH_SHORT).show();
			
		}
	};
	OnClickListener fourListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Message pressed", Toast.LENGTH_SHORT).show();
			
		}
	};
	OnClickListener fiveListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Camera pressed", Toast.LENGTH_SHORT).show();
			
		}
	};
	OnClickListener sixListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "IE pressed", Toast.LENGTH_SHORT).show();
			
		}
	};
	OnClickListener sevenListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Music pressed", Toast.LENGTH_SHORT).show();
			
		}
	};
	
	
	
	
}
