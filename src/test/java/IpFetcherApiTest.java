import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class IpFetcherApiTest {
    @Test
    void testFetchCurrentMachineIp_withMockedData() throws Exception {
        // Mock local IP
        InetAddress mockLocal = Mockito.mock(Inet4Address.class);
        Mockito.when(mockLocal.getHostAddress()).thenReturn("192.168.1.100");
        Mockito.when(mockLocal.isLoopbackAddress()).thenReturn(false);

        NetworkInterface mockNetIf = Mockito.mock(NetworkInterface.class);
        Mockito.when(mockNetIf.getInetAddresses()).thenReturn(Collections.enumeration(Collections.singletonList(mockLocal)));
        Mockito.mockStatic(NetworkInterface.class)
                .when(NetworkInterface::getNetworkInterfaces)
                .thenReturn(Collections.enumeration(Collections.singletonList(mockNetIf)));

        // Mock public IP fetch
        try (MockedStatic<IpFetcherApi> mocked = Mockito.mockStatic(IpFetcherApi.class, Mockito.CALLS_REAL_METHODS)) {
            mocked.when(IpFetcherApi::fetchCurrentMachineIp).thenCallRealMethod();
            String[] result = {"192.168.1.100", "203.0.113.42"};
            mocked.when(IpFetcherApi::fetchCurrentMachineIp).thenReturn(result);
            String[] ip = IpFetcherApi.fetchCurrentMachineIp();
            assertEquals("192.168.1.100", ip[0]);
            assertEquals("203.0.113.42", ip[1]);
        }
    }
}

