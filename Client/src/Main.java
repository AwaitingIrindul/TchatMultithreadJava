import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        InetAddress ia = InetAddress.getByName("134.214.117.41");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append("test".hashCode());
        }

        while(true){
            try{
                byte[] data = sb.toString().getBytes();
                //System.out.println(new String(data));
                DatagramPacket dp = new DatagramPacket(data, data.length, ia, 2000);
                ds.send(dp);
                DatagramPacket dp2 = new DatagramPacket(data, data.length, ia, 2000);
                ds.receive(dp2);
                System.out.println(Arrays.toString(data));
                

            } catch (Exception e){

            }

        }

    }
}
//[B@1540e19d