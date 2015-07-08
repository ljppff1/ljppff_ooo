package com.james.mshop.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

public class getJson {
	private getJson(){};
	public static String getData(String str){
		InputStream is = null;
		ByteArrayBuffer arrayBuffer = new ByteArrayBuffer(3000);
		try
		{
			URL url = new URL(str);
			URLConnection openConnection = url.openConnection();
			is = openConnection.getInputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while (-1 != (len = is.read(buffer)))
			{
				arrayBuffer.append(buffer, 0, len);
			}
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return new String(arrayBuffer.toByteArray(), 0,
				arrayBuffer.length());
	
	
	
	}
}
