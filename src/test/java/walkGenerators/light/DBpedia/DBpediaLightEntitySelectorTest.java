package walkGenerators.light.DBpedia;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DBpediaLightEntitySelectorTest {

    @Test
    public void getRedirectUrl() {
        assertEquals("http://dbpedia.org/resource/Hesteel_Group", DBpediaLightEntitySelector.getRedirectUrl("http://dbpedia.org/resource/Hebei_Iron_and_Steel"));
        assertEquals("http://dbpedia.org/resource/Nielsen_Holdings_PLC", DBpediaLightEntitySelector.getRedirectUrl("http://dbpedia.org/resource/Nielsen_N.V."));
        assertEquals("http://dbpedia.org/resource/AccorHotels", DBpediaLightEntitySelector.getRedirectUrl("http://dbpedia.org/resource/Accor"));
    }

}