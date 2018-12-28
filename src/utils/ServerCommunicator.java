package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerCommunicator {

    public static String getSolutionFromServer(String ip, int port, String problem) {

        Socket s=null;
        PrintWriter out=null;
        BufferedReader in=null;
        try{
//            "127.0.0.1"
            // Send the server our board and read the result.
            s=new Socket(ip, port);
            System.out.println(problem + " - Connected ");
			s.setSoTimeout(1000);
            out=new PrintWriter(s.getOutputStream());
            in=new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println(problem + " - Writers initiated ");
            out.println(problem);
            out.println("done");
            out.flush();
            // Now read the response
            System.out.println(problem + " - Done writing board ");
            String line=in.readLine();
            String result = line;
            while (line==null || !line.equals("done")) {
                line = in.readLine();
                if (line != null) {
                    result = String.join("\n", new String[] {result, line});
                }
            }
            return result;
        }catch (IOException e){
            System.out.println(problem + "The Server ran into a problem while trying to solve the problem");
        } finally{
            try {
                assert in != null;
                in.close();
                out.close();
                s.close();
            } catch (Exception e) {
                System.out.println("The Server ran into some IOException" + e.toString());
            }
        }
        return null;
    }

}
