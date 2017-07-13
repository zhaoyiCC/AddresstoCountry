package buaa.act.baseService;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Created by luckcul on 17-6-12.
 * 连接ES的单例模式
 */
public class ESConnection {
    private static final TransportClient client = init();

    private static TransportClient init(){
        TransportClient ret = null;
        try {
            ret = new PreBuiltTransportClient(Settings.builder()
                    .put("cluster.name", "sdpes")
                    .build())
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.7.101"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.7.102"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.7.103"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.7.104"), 9300));
        } catch (UnknownHostException e) {
            System.out.println("initialize ES connection error !!");
            e.printStackTrace();
        }
        return ret;
    }


    public static TransportClient getConnection(){
        return client;
    }
}
