package walkGenerators.base;

/**
 * Interface for all walk generators
 */
public interface IWalkGenerator {

    /**
     * The walk file(s) will be persisted in "./walks".
     * @param numberOfThreads The number of threads to be run.
     * @param numberOfWalksPerEntity The number of walks that shall be performed per entity.
     * @param depth The depth of each walk.
     */
    void generateRandomWalks(int numberOfThreads, int numberOfWalksPerEntity, int depth);

    /**
     * Generates random walks.
     * @param numberOfThreads The number of threads to be run.
     * @param numberOfWalksPerEntity The number of walks that shall be performed per entity.
     * @param depth The depth of each walk.
     * @param filePathOfFileToBeWritten The path to the file that shall be written.
     */
    void generateRandomWalks(int numberOfThreads, int numberOfWalksPerEntity, int depth, String filePathOfFileToBeWritten);

    /**
     * Generates duplicate free random walks.
     * @param numberOfThreads The number of threads to be run.
     * @param numberOfWalksPerEntity The number of walks that shall be performed per entity.
     * @param depth The depth of each walk.
     * @param filePathOfFileToBeWritten The path to the file that shall be written.
     */
    void generateRandomWalksDuplicateFree(int numberOfThreads, int numberOfWalksPerEntity, int depth, String filePathOfFileToBeWritten);

    /**
     * Generates duplicate free random walks.
     * @param numberOfThreads The number of threads to be run.
     * @param numberOfWalksPerEntity The number of walks that shall be performed per entity.
     * @param depth The depth of each walk.
     */
    void generateRandomWalksDuplicateFree(int numberOfThreads, int numberOfWalksPerEntity, int depth);


    /**
     * Generates mid walks, duplicate walks are possible.
     * A mid walk is a random walk that involves a given entity but may not start or end with the entity in question.
     * @param numberOfThreads The number of threads to be used.
     * @param numberOfWalksPerEntity The maximal number of walks that shall be performed per entity.
     * @param depth The depth of each walk where the depth is the number of node-hops, i.e. depth 1 leads to a sentence with one hop and three elements: S → P → O.
     */
    void generateRandomMidWalks(int numberOfThreads, int numberOfWalksPerEntity, int depth);

    /**
     * Generates mid walks, duplicate walks are possible.
     * A mid walk is a random walk that involves a given entity but may not start or end with the entity in question.
     * @param numberOfThreads The number of threads to be used.
     * @param numberOfWalksPerEntity The maximal number of walks that shall be performed per entity.
     * @param depth The depth of each walk where the depth is the number of node-hops, i.e. depth 1 leads to a sentence with one hop and three elements: S → P → O.
     * @param filePathOfFileToBeWritten The path to the file that shall be written.
     */
    void generateRandomMidWalks(int numberOfThreads, int numberOfWalksPerEntity, int depth, String filePathOfFileToBeWritten);

}
