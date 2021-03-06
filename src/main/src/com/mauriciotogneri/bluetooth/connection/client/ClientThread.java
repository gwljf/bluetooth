package com.mauriciotogneri.bluetooth.connection.client;

import java.util.UUID;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

class ClientThread extends Thread
{
	private final ClientConnection clientConnection;
	private final BluetoothSocket socket;
	
	public ClientThread(ClientConnection clientConnection, BluetoothDevice device, String uuid)
	{
		this.clientConnection = clientConnection;
		
		this.socket = createSocket(device, uuid);
	}
	
	private BluetoothSocket createSocket(BluetoothDevice device, String uuid)
	{
		BluetoothSocket result = null;
		
		try
		{
			result = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid));
		}
		catch (Exception e)
		{
			this.clientConnection.onErrorConnecting();
		}
		
		return result;
	}
	
	@Override
	public void run()
	{
		try
		{
			this.socket.connect();
			
			this.clientConnection.connected(this.socket);
		}
		catch (Exception e)
		{
			this.clientConnection.onErrorConnecting();
			
			close();
		}
	}
	
	public void close()
	{
		try
		{
			this.socket.close();
		}
		catch (Exception e)
		{
		}
	}
}