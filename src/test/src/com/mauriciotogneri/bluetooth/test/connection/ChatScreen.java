package com.mauriciotogneri.bluetooth.test.connection;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.mauriciotogneri.bluetooth.connection.ConnectionInterface;
import com.mauriciotogneri.bluetooth.connection.ConnectionManager;
import com.mauriciotogneri.bluetooth.test.R;

public class ChatScreen extends Activity implements ConnectionInterface
{
	private ConnectionManager connectionManager;
	public static final String PARAMETER_DEVICE = "device";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_screen);
		
		Button send = (Button)findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				send();
			}
		});
		
		Button disconnect = (Button)findViewById(R.id.disconnect);
		disconnect.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				disconnect();
			}
		});
	}
	
	private void addHistory(String text)
	{
		TextView console = (TextView)findViewById(R.id.chat_history);
		console.append(text + "\r\n");
		
		ScrollView scroll = (ScrollView)findViewById(R.id.scroll);
		scroll.fullScroll(View.FOCUS_DOWN);
	}
	
	private void disconnect()
	{
		this.connectionManager.close();
		finish();
	}
	
	private void send()
	{
		EditText editText = (EditText)findViewById(R.id.message);
		String message = editText.getText().toString();
		
		this.connectionManager.send(message.getBytes());
		
		addHistory(">>> " + message);
	}
	
	@Override
	public void onReceive(BluetoothDevice device, byte[] message)
	{
		addHistory("<<< " + new String(message));
	}
	
	@Override
	public void onConnect(BluetoothDevice device)
	{
		addHistory("Connected...");
	}
	
	@Override
	public void onDisconnect(BluetoothDevice device)
	{
		addHistory("Disconnected...");
	}
}