import com.jeancoder.app.sdk.server.DevServer;

public class MainTestMarket {
	public static void main(String[] args) throws Exception {
		String fp = System.getProperty("user.dir");
		DevServer. start(8088, fp);
	 
	}
}
