package walkGenerators.base;

import org.junit.jupiter.api.Test;
import org.rdfhdt.hdt.exceptions.NotFoundException;
import org.rdfhdt.hdt.hdt.HDT;
import org.rdfhdt.hdt.hdt.HDTManager;
import org.rdfhdt.hdt.triples.IteratorTripleString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class HdtParserTest {

    /**
     * Default logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HdtParser.class);

    @Test
    public void randomDrawFromHashSet() {
        HashSet hashSet = new HashSet(Arrays.asList(new String[]{"A", "B", "C"}));
        int aCount = 0;
        int bCount = 0;
        int cCount = 0;

        for (int i = 0; i < 1000; i++) {
            String drawValue = HdtParser.<String>randomDrawFromHashSet(hashSet);
            switch (drawValue) {
                case "A":
                    aCount++;
                    break;
                case "B":
                    bCount++;
                    break;
                case "C":
                    cCount++;
                    break;
                default:
                    fail("Invalid value: " + drawValue);
            }
        }
        assertTrue(aCount > 0, "A was never drawn.");
        assertTrue(bCount > 0, "B was never drawn.");
        assertTrue(cCount > 0, "C was never drawn.");
        LOGGER.info("A : B : C  :   " + aCount + " : " + bCount + " : " + cCount);
    }

    /**
     * Just making sure that the method behaves as assumed.
     */
    @Test
    public void randomNumbers() {
        int zeroCount = 0;
        int oneCount = 0;
        for (int i = 0; i < 1000; i++) {
            int randomNumber = ThreadLocalRandom.current().nextInt(2);
            assertTrue(randomNumber < 2);
            assertTrue(randomNumber >= 0);
            if (randomNumber == 0) {
                zeroCount++;
            } else if (randomNumber == 1) {
                oneCount++;
            } else {
                fail("randomNumber out of bounds: " + randomNumber);
            }
        }
        LOGGER.info("Zero count: " + zeroCount);
        LOGGER.info("One count: " + oneCount);
        if (zeroCount != 0) LOGGER.info("Ratio (one / zero): " + (double) oneCount / (double) zeroCount);
    }

    @Test
    public void testHdtAndTestFile() {
        String hdtPath = getClass().getClassLoader().getResource("swdf-2012-11-28.hdt").getPath();
        assertNotNull(hdtPath, "Cannot find test resource.");
        try {
            HDT hdtDataSet = HDTManager.loadHDT(hdtPath);
            IteratorTripleString it = hdtDataSet.search("", "", "");
            //while(it.hasNext()){
            //    TripleString ts = it.next();
            //    System.out.println(ts);
            //}
            assertTrue(it.hasNext(), "The iterator needs to be filled.");
        } catch (Exception e) {
            fail("Exception occurred while loading test HDT data set.");
        }
    }


    @Test
    public void generateMidWalkForEntity() {
        try {
            HdtParser parser = new HdtParser(getClass().getClassLoader().getResource("swdf-2012-11-28.hdt").getPath());
            String concept = "http://data.semanticweb.org/workshop/semwiki/2010/programme-committee-member";

            for (int depth = 1; depth < 10; depth++) {
                List<String> walk1 = parser.generateMidWalkForEntity(concept, depth);
                assertNotNull(walk1);
                assertTrue(walk1.size() <= depth * 2 + 1, "The walk is supposed to have at most " + (depth * 2 + 1) + " elements. It has: " + walk1.size()
                        + "\nWalk:\n" + walk1);
                assertTrue(walk1.size() >= 3, "The walk must consist of at least 3 elements. Walk:\n" + walk1);

                String[] walkArray = new String[walk1.size()];
                for (int i = 0; i < walkArray.length; i++) {
                    walkArray[i] = walk1.get(i);
                }
                String hdtPath = getClass().getClassLoader().getResource("swdf-2012-11-28.hdt").getPath();
                try {
                    HDT hdtDataSet = HDTManager.loadHDT(hdtPath);
                    for (int i = 2; i < walkArray.length - 1; i += i + 2) {
                        IteratorTripleString iterator = hdtDataSet.search(walkArray[i - 2], walkArray[i - 1], walkArray[i]);
                        assertTrue(iterator.hasNext(), "The following triple appeared in the walk but not in the data set:\n"
                                + walkArray[i - 2] + " " + walkArray[i - 1] + " " + walkArray[i]
                                + "\nSentence:\n" + walk1);
                    }
                } catch (NotFoundException e) {
                    fail("Exception", e);
                } catch (IOException e) {
                    fail("Exception", e);
                }
            }
        } catch (IOException ioe) {
            LOGGER.error("HDT Init error.");
            fail("Init should not fail.");
        }
    }


    @Test
    public void generateMidWalksForEntity() {
        try {
            HdtParser parser = new HdtParser(getClass().getClassLoader().getResource("swdf-2012-11-28.hdt").getPath());
            String concept = "http://data.semanticweb.org/person/amelie-cordier";
            int numberOfWalks = 12;
            int depth = 10;
            List<String> walks1 = parser.generateMidWalksForEntity(concept, numberOfWalks, depth);
            assertNotNull(walks1);

            // check number of generated walks
            assertTrue(walks1.size() == numberOfWalks);

            nextWalk:
            for (String walk : walks1) {

                // check walk size
                assertTrue((walk.split(" ").length % 2) == 1.0, "Walks must be uneven. Number of elements in walk: " + walk.split(" ").length + "\nWalk:\n" + walk);

                for (String component : walk.split(" ")) {
                    if (component.equals(concept)) {
                        continue nextWalk;
                    }
                }

                // check whether the target entity occurs
                fail("No occurrence of " + concept + " in sentence: " + walk);
            }


            String hdtPath = getClass().getClassLoader().getResource("swdf-2012-11-28.hdt").getPath();
            try {
                HDT hdtDataSet = HDTManager.loadHDT(hdtPath);
                for (String walk : walks1) {
                    String[] walkArray = walk.split(" ");
                    for (int i = 2; i < walkArray.length - 1; i += i + 2) {
                        IteratorTripleString iterator = hdtDataSet.search(walkArray[i - 2], walkArray[i - 1], walkArray[i]);
                        assertTrue(iterator.hasNext(), "The following triple appeared in the walk but not in the data set:\n"
                                + walkArray[i - 2] + " " + walkArray[i - 1] + " " + walkArray[i]
                                + "\nSentence:\n" + walk);
                    }
                }
            } catch (IOException e) {
                fail("No exception should occur.", e);
            } catch (NotFoundException e) {
                fail("No exception should occur.", e);
            }
        } catch (IOException ioe) {
            LOGGER.error("HDT Init error.");
            fail("Init should not fail.");
        }
    }


}