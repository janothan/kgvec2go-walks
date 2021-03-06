package walkGenerators.light.DBpedia;

import walkGenerators.light.WalkGeneratorLight;

import java.io.File;
import java.util.HashSet;

public class DBpediaWalkGeneratorLight extends WalkGeneratorLight {

    public DBpediaWalkGeneratorLight(String pathToTripleFile, String pathToEntitiesFile) {
        super(pathToTripleFile, pathToEntitiesFile);
        super.entitySelector = new DBpediaLightEntitySelector(pathToEntitiesFile);
    }

    public DBpediaWalkGeneratorLight(File tripleFile, File entitiesFile) {
        super(tripleFile, entitiesFile);
    }

    public DBpediaWalkGeneratorLight(File tripleFile, HashSet<String> entitiesToProcess) {
        super(tripleFile, entitiesToProcess);
    }

    public DBpediaWalkGeneratorLight(String pathToTripleFile, HashSet<String> entitiesToProcess) {
        super(pathToTripleFile, entitiesToProcess);
    }
}
