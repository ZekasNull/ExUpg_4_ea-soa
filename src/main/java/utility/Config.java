package utility;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    private static Logger logger = Logger.getLogger(Config.class.getName());
    private static final Dotenv dotenv;

    //ensure .env is loaded and has non-null values
    static
    {
        try
        {
            dotenv = Dotenv.configure()
                           .load();
            logger.info(".env file loaded successfully");

            //if this got thrown you probably forgot to enter values in the .env and/or rename it
            if (getApiKey().isEmpty() || getContextCode().isEmpty())
                throw new RuntimeException("API KEY or CONTEXT KEY UNCONFIGURED");


        } catch (DotenvException e)
        {
            logger.log(Level.SEVERE, "Failed to load .env file. Did you remember to rename it to .env?");
            throw new RuntimeException("Could not load .env file", e);
        }
    }

    public static String getApiKey()
    {
        return dotenv.get("CANVAS_API_KEY");
    }

    public static String getContextCode()
    {
        return dotenv.get("CALENDAR_ENTITY");
    }

    public static void main(String[] args)
    {
        System.out.println("Key is: " + Config.getApiKey());

    }
}
