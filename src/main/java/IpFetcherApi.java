import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

public class IpFetcherApi {
    public static String[] fetchCurrentMachineIp()
    {
        String ip[] = new String[2];
        // Get private IP(s)
        System.out.println("Private IP addresses:");
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            while (nets.hasMoreElements()) {
                NetworkInterface netint = nets.nextElement();
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        System.out.println(" - " + inetAddress.getHostAddress());
                        ip[0] = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // Get public IP
        System.out.println("Public IP address:");
        try {
            URL url = new URL("https://api.ipify.org");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String publicIP = br.readLine();
            System.out.println(" - " + publicIP);
            ip[1] = publicIP;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

//    public static void main(String[] args) {
//        fetchCurrentMachineIp();
//    }
}
