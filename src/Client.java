import java.io.Serializable;
import java.util.function.Consumer;
/**
 * Class client for comunicate with the server
 * @author Gustavo
 *
 */
public class Client extends Network{
	private String ip;
	private int port;

	public Client(String ip, int port,Consumer<Serializable> onRecieveCallback) {
		super(onRecieveCallback);
		this.ip = ip;
		this.port = port;
	}

	@Override
	protected boolean isServer() {
		return false;
	}

	@Override
	protected String getIP() {
		return ip;
	}

	@Override
	protected int getPort() {
		return port;
	}

}
