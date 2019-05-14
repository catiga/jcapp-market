import com.jeancoder.app.sdk.server.DevServer;

public class ServiceStart {
	public static void main(String[] args) throws Exception {
		DevServer.start(Integer.parseInt(args[0]),args[1]);
	}
}
