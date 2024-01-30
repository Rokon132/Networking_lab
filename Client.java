
// A Java program for a Client
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataInputStream input_server = null;
    private DataOutputStream out = null;

    // constructor to put ip address and port
    public Client(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            // takes input from terminal
            input = new DataInputStream(System.in);
            input_server = new DataInputStream(socket.getInputStream());

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
        // string to read message from input
        // System.out.println("Which operation and number do you want?");

        String line = "";
        String message_from_server = "";
        System.out.println("Choose any option :");
        System.out.println("1. Uppercase (eg. uppercase rifat)");
        System.out.println("2. To check whether a number is prime (or palindrome) or not (eg. prime 7) ");
        System.out.println("3. Withdraw or deposit money to an account and see the balance (eg. withdraw 10000)");

        // keep reading until "Over" is input
        // input terminal theke na niye direct ekhan theke pathacci
        

        // Scanner sc = new Scanner(System.in) ;
        // line = sc.nextLine() ;

        line = "withdraw 2000" ;
         message_from_server = "Transaction failure" ;

         long start_time  = System.currentTimeMillis() ;

        while (message_from_server.equals("Transaction failure")) {
            try {
            
                out.writeUTF(line) ;
                message_from_server = input_server.readUTF();
                System.out.println("Response from server : " + message_from_server);
               
            } catch (IOException i) {
                System.out.println(i);
            }
        }
        long end_time = System.currentTimeMillis() ;
        System.out.println("withdraw operation will take time " + (end_time - start_time));




        line = "deposit 1000" ;
        message_from_server = "deposite failure" ;

        start_time  = System.currentTimeMillis() ;

       while (message_from_server.equals("deposite failure")) {
           try {
           
               out.writeUTF(line) ;
               message_from_server = input_server.readUTF();
               System.out.println("Response from server : " + message_from_server);
              
           } catch (IOException i) {
               System.out.println(i);
           }
       }
       end_time = System.currentTimeMillis() ;
       System.out.println("deposite operation will take time " + (end_time - start_time));


        // close the connection
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("localhost", 5555);
    }

}