package Geek_connect.Geek_inc.server;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Created by thoma on 4-4-2017.
 */
public class Server
{
    private static Logger logger = LoggerFactory.getLogger(DatabaseImp.class);
    private static Database DB_INSTANCE;
    private int port = 3306;

    public Server()
    {
        try {
            HttpServer server = initWebserver();
            server.start();
            logger.info("Server is gestart op port " + port);
            while (true) {
                Thread.sleep(1000);
            }
        }
        catch (Exception e)
        {
            logger.error("init Webserver is mislukt ", e);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            DB_INSTANCE = (args.length == 1 && args[0].equals("--mock-db"))

                                    ? new MockDatabase()
                                    : new DatabaseImp();

            logger.info("database instantie is " + DB_INSTANCE.getClass().toString());

            new Server();
        }

        catch (Exception e)
        {
            logger.error("database instantie mislukt :( ", e);
        }
    }

    private HttpServer initWebserver()
    {
        ResourceConfig config = new ResourceConfig(DatabaseImp.class);
        config.register(JacksonJaxbJsonProvider.class);
        URI uri = URI.create("Http://0.0.0.0:" + port);

       /*
         * Code voor het initialiseren van je HTTP server met SSL connectie
         * Ook de return statement aangepast
         */

          SSLContextConfigurator sslConf = new SSLContextConfigurator();
          sslConf.setKeyStoreFile("./keystore_server"); // contains server keypair
          sslConf.setKeyStorePass("asdfgh");
          sslConf.setTrustStoreFile("./truststore_server"); // contains client certificate
          sslConf.setTrustStorePass("asdfgh");

        return GrizzlyHttpServerFactory.createHttpServer(uri, config, true, new SSLEngineConfigurator(sslConf));
    }

    public static Database getDatabse()
    {
        return DB_INSTANCE;
    }
}


