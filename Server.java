
// / A Java program for a Server
import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    // initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private int account_balance = 30000;

    public static boolean isPrime(int n) {
        if (n == 2)
            return true;

        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPalindrome(String message) {
        int size = message.length();

        StringBuilder reversed = new StringBuilder(message).reverse();
        String result = reversed.toString();

        if (result.equals(message)) {
            return true;
        }

        return false;
    }

    // constructor with port
    public Server(int port) {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            out = new DataOutputStream(socket.getOutputStream());

            String line = "";
            boolean success = false ;

            // reads message from client until "Over" is sent

            // start time : 
            long start_time = System.currentTimeMillis() ;
            while (!line.equals("Over")) {
                try {
                    line = in.readUTF();
                    System.out.println("message from client: " + line);

                    String[] parts = line.split("\\s+");

                    String operation = parts[0];
                    String operand = parts[1];
                    if (operation.equals("uppercase")) {
                        line = operand;
                        line = line.toUpperCase();

                    } else if (operation.equals("prime")) {
                        int number = Integer.parseInt(operand);
                        boolean flag = isPrime(number);

                        if (flag) {
                            line = number + " is a prime number ";
                        } else {
                            line = number + " is not a prime number ";
                        }
                    } else if (operation.equals("palindrome")) {
                        boolean flag = isPalindrome(operand);

                        if (flag) {
                            line = operand + " is a palindrome";

                        } else {
                            line = operand + " is not palindrome ";
                        }

                    } else if (operation.equals("withdraw")) {

                        Random random = new Random() ;

                        int randomNumber = random.nextInt(10)  ;
                        int number = Integer.parseInt(operand);

                        if(randomNumber>4)
                        {
                            line="Transaction failure";

                        }else 
                       {
                            if (account_balance >= number) {
                                account_balance = account_balance - number;
                                line = number + " has been withdrawn from your account. Your current balance is "  + account_balance;
                            } else {
                                line = "You donot have sufficient balanace to withdraw";
                            }
                            success = true ;
                       }
                    } else if (operation.equals("deposit")) {
                        int number = Integer.parseInt(operand);

                        Random random = new Random() ;

                        int randomNumber = random.nextInt(10)  ;

                        if(randomNumber>4)
                        {
                            line = "deposite failure" ;
                        }
                        account_balance = account_balance + number;
                        line = number + " has been deposited to your account. Your current balance is " + account_balance;
                    }
                    out.writeUTF(line);
                    if(success)break ;

                } catch (IOException i) {
                    System.out.println(i);
                }
            }

            // last time :

            long end_time = System.currentTimeMillis() ;

            System.out.println(end_time - start_time) ;


            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(5555);
    }
}
// rifat