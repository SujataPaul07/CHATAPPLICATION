import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;

     BufferedReader br;
     PrintWriter out;

    public Client()
    {
        try{
            System.out.println("Sending request to server");
            socket = new Socket("localhost",7777);
            System.out.println("connection done.");

             br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();


            }catch (Exception e){
            //TODO: handle expectation
        }
    }
    public void startReading()
    {
      //thread-read karke deta rehega
        Runnable r1=()->{
            System.out.println("reader started..");
            try{

            while(true){
                
                String msg = br.readLine();
                if(msg.equals("exit")) {
                    System.out.println("Client terminated the chat");
                    socket.close();
                    break;
                }
                System.out.println("Client : "+ msg);
            }
            }catch(Exception e) {
                e.printStackTrace();
            }

        };
    
    new Thread(r1).start();
}
public void startWriting()
{
   //thread- data user lega and the send karega client tak
   Runnable r2 = () -> {
       System.out.println("writer started..");
       try
       {
       while(true) {
           

              BufferedReader br1 =new BufferedReader(new InputStreamReader(System.in));
             String content=br1.readLine();
               out.println(content);
               out.flush();
       }

           }catch(Exception e){
               //TODO: handle exception
               e.printStackTrace();
         
            }
        };       
   
     new Thread(r2).start();
}

public static void main(String[] args){
        System.out.println("this is client..");
        new Client();
        new Scanner(System.in).nextLine();
    }
    
};

