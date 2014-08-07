package com.example.sensoclient;

import com.example.sensoclient.manager.ClientManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button b1, b2, b3, b4, b5, b6;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		b1 = (Button) findViewById(R.id.btn_load);
		b2 = (Button) findViewById(R.id.btn_delete_all);
		b3 = (Button) findViewById(R.id.btn_write_file);
		b4 = (Button) findViewById(R.id.btn_display);

		b5 = (Button) findViewById(R.id.btn_loadweb);
		b6 = (Button) findViewById(R.id.btn_write_web);

		tv = (TextView) findViewById(R.id.TextView01);

		b1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tv.setBackgroundColor(Color.RED);
				// ClientManager.getInstance(getContext()).loadDataFromFile();
				ClientManager.getInstance(getContext()).loadDataToDB();
				// ClientManager.getInstance(getContext()).writeDataToFile();
			}
		});

		b2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tv.setBackgroundColor(Color.BLUE);
				ClientManager.getInstance(getContext()).deleteAll();
			}
		});

		b3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tv.setBackgroundColor(Color.GREEN);
				ClientManager.getInstance(getContext()).writeDataToFile();
			}
		});

		b4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tv.setBackgroundColor(Color.YELLOW);

				Intent intent = new Intent(MainActivity.this,
						DataListActivity.class);
				startActivity(intent);

			}
		});

		b5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tv.setBackgroundColor(Color.RED);
				// ClientManager.getInstance(getContext()).loadDataFromFile();
				//ClientManager.getInstance(getContext()).loadDataFromWeb();
				loadDataFromWeb();
				// ClientManager.getInstance(getContext()).writeDataToFile();
			}
		});
		b6.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tv.setBackgroundColor(Color.RED);
				// ClientManager.getInstance(getContext()).loadDataFromFile();
				//ClientManager.getInstance(getContext()).loadDataFromWeb();
				saveDataToWeb();
				// ClientManager.getInstance(getContext()).writeDataToFile();
			}
		});

	}

	public void loadDataFromWeb() {
		new Thread() {
			@Override
			public void run() {
				// ��Ҫִ�еķ���
				ClientManager.getInstance(getContext()).loadDataFromWeb();
				// ִ����Ϻ��handler����һ������Ϣ
				handler.sendEmptyMessage(0);
			}

			private Handler handler = new Handler() {
				@Override
				// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					// ����UI
				}
			};
		}.start();

	}
	
	public void saveDataToWeb() {
		new Thread() {
			@Override
			public void run() {
				// ��Ҫִ�еķ���
				ClientManager.getInstance(getContext()).saveDataToWeb();
				// ִ����Ϻ��handler����һ������Ϣ
				handler.sendEmptyMessage(0);
			}

			private Handler handler = new Handler() {
				@Override
				// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					// ����UI
				}
			};
		}.start();

	}

	protected void onDestroy() {
		super.onDestroy();
		ClientManager.getInstance(getContext()).onDestroy();
	}

	protected Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
}
