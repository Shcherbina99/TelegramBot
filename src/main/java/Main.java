import java.net.Authenticator;
import java.net.PasswordAuthentication;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Main {
  private static String BOT_NAME = "JavaChatBot";;
  private static String BOT_TOKEN = "746379722:AAFrKO3i2-xsRrGyXWXTp7pEH2s9WLqKM-s" /* your bot's token here */;
  private static final boolean IsProxy = false;

  private static String PROXY_HOST = "odinmillion-vpn.cloudapp.net" /* proxy host */;
  private static Integer PROXY_PORT = 31337 /* proxy port */;
  private static String PROXY_USER = "sockduser" /* proxy user */;
  private static String PROXY_PASSWORD = "fuck_rkn_2018" /* proxy password */;

  public static void main(String[] args) {
    try {
      ApiContextInitializer.init();
      TelegramBotsApi botsApi = new TelegramBotsApi();


      if (IsProxy) {
        // Create the Authenticator that will return auth's parameters for proxy authentication
        Authenticator.setDefault(new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray());
          }
        });

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(PROXY_HOST, PROXY_PORT),
            new UsernamePasswordCredentials(PROXY_USER, PROXY_PASSWORD));
        HttpHost httpHost = new HttpHost(PROXY_HOST, PROXY_PORT);
        RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost)
            .setAuthenticationEnabled(true).build();
        botOptions.setHttpProxy(httpHost);
        botOptions.setRequestConfig(requestConfig);
        botOptions.setCredentialsProvider(credsProvider);
        botsApi.registerBot(new Bot(BOT_TOKEN, BOT_NAME, botOptions));
      }
      else {
        botsApi.registerBot(new Bot(BOT_TOKEN, BOT_NAME));
      }

    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

}
