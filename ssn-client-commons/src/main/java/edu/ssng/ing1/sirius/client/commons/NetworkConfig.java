package edu.ssng.ing1.sirius.client.commons;

public class NetworkConfig {
    private String ipaddress;
    private int tcpport;
    private int tcpportServerNotify;

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public int getTcpportServerNotify() {
        return tcpportServerNotify;
    }
    public void setTcpportServerNotify(int tcpportServerNotify) {
        this.tcpportServerNotify = tcpportServerNotify;
    }


    public int getTcpport() {
        return tcpport;
    }

    public void setTcpport(int tcpport) {
        this.tcpport = tcpport;
    }

    @Override
    public String toString() {
        return "NetworkConfig{" +
                "ipAddress='" + ipaddress + '\'' +
                ", tcpPort=" + tcpport +
                ", tcpPortServerNotify=" + tcpportServerNotify +
                '}';
    }
}
