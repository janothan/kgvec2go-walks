import org.junit.jupiter.api.Test;
import training.Word2VecConfiguration;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class RDF2VecLightTest {

    @Test
    void getWalkFilePath() {
        File entityFilePath = new File(this.getClass().getClassLoader().getResource("emptyFile.nt").getFile());
        File graphFilePath = new File(this.getClass().getClassLoader().getResource("emptyFile.txt").getPath());
        RDF2VecLight light = new RDF2VecLight(graphFilePath, entityFilePath);
        assertEquals("./walks/walk_file.gz", light.getWalkFilePath());
        assertTrue(light.getWalkFileDirectoryPath().endsWith("/walks"));
    }

    @Test
    void train() {
        File entityFilePath = new File(this.getClass().getClassLoader().getResource("dummyEntities.txt").getFile());
        File graphFilePath = new File(this.getClass().getClassLoader().getResource("dummyGraph.nt").getPath());
        RDF2VecLight light = new RDF2VecLight(graphFilePath, entityFilePath);
        Word2VecConfiguration configuration = Word2VecConfiguration.CBOW;
        configuration.setVectorDimension(10);
        light.train();
    }

}