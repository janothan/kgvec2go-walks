

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import training.Word2VecConfiguration;

import java.io.File;

/**
 * Mini command line tool for server application.
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * word2vec configuration
     */
    private Word2VecConfiguration configuration = Word2VecConfiguration.CBOW;

    private static File lightEntityFile = null;

    private static File knowledgeGraphFile = null;

    public static void main(String[] args) {

        if(args.length == 0){
            LOGGER.error("Not enough arguments.");
        }

        String lightEntityFilePath = getValue("-light", args);
        if(lightEntityFilePath != null){
            lightEntityFile = new File(lightEntityFilePath);
            if(!lightEntityFile.exists()){
                LOGGER.error("The given file does not exist: " + lightEntityFilePath);
            }
        }

        String knowledgeGraphFilePath = getValue("-graph", args);
        if(knowledgeGraphFilePath != null){
            knowledgeGraphFile = new File(knowledgeGraphFilePath);
            if(!knowledgeGraphFile.exists()){
                LOGGER.error("The given file does not exist: " + knowledgeGraphFilePath);
            }
        }
    }


    /**
     * Helper method.
     *
     * @param key       Arg key.
     * @param arguments Arguments as received upon program start.
     * @return Value of argument if existing, else null.
     */
    private static String getValue(String key, String[] arguments) {
        int positionSet = -1;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equalsIgnoreCase(key)) {
                positionSet = i;
                break;
            }
        }
        if (positionSet != -1 && arguments.length >= positionSet + 1) {
            return arguments[positionSet + 1];
        } else return null;
    }


    public static String getHelp(){
        return "TODO";
    }

}
