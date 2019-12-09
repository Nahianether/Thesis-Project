
public class IpPortConfiguration {
	String ipAdderss="localhost";
	int portNumber=8800;
	
	public IpPortConfiguration(String ipAddress,int portNumber) {
		this.ipAdderss=ipAddress;
		this.portNumber=portNumber;
	}
	
	public IpPortConfiguration() {
		// TODO Auto-generated constructor stub
	}

	public String getIpAddress() {
		return ipAdderss;
	}
	public int getPortNumber() {
		return portNumber;
	}
}
