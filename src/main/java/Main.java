

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

    /**
     * The number of threads to be used for the walk generation and for the training.
     */
    private static int threads = -1;

    /**
     * Dimensions for the vectors.
     */
    private static int dimensions = -1;

    /**
     * Where the walks will be persisted (directory).
     */
    private static File walkDirectory = null;

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

        String walkDirectoryPath = getValue("-walkDir", args);
        walkDirectoryPath = (walkDirectoryPath == null) ? getValue("-walkDirectory", args) : walkDirectoryPath;
        if(walkDirectoryPath != null){
            walkDirectory = new File(walkDirectoryPath);
            if(!walkDirectory.isDirectory()){
                System.out.println("Walk directory is no directory! Using default.");
                walkDirectory = null;
            }
        }

        String threadsText = getValue("-threads", args);
        if(threadsText != null){
            try {
                threads = Integer.parseInt(threadsText);
            } catch (NumberFormatException nfe){
                System.out.println("Could not parse the number of threads. Using default.");
            }
        }

        String dimensionText = getValue("-dimension", args);
        dimensionText = (dimensionText == null) ? getValue("-dimensions", args) : dimensionText;
        if(dimensionText != null){
            try {
                dimensions = Integer.parseInt(dimensionText);
            } catch (NumberFormatException nfe){
                System.out.println("Could not parse the number of dimensions. Using default.");
            }
        }

        Word2VecConfiguration configuration = Word2VecConfiguration.CBOW;

        // setting training threads
        if(threads > 0){
            configuration.setNumberOfThreads(threads);
        }




        // actual execution

        if(lightEntityFile == null){
            // TODO run classic
        } else {
            RDF2VecLight rdf2VecLight;
            if(walkDirectory == null) rdf2VecLight = new RDF2VecLight(knowledgeGraphFile, lightEntityFile);
            else rdf2VecLight = new RDF2VecLight(knowledgeGraphFile, lightEntityFile, walkDirectory);

            // setting threads
            if(threads > 0) rdf2VecLight.setNumberOfThreads(threads);

            rdf2VecLight.setConfiguration(configuration);
            rdf2VecLight.train();
            System.out.println("Training completed.");
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
