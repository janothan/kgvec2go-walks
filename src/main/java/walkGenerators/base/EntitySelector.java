package walkGenerators.base;

import java.util.Set;

/**
 * Select the entities for which walks shall be generated.
 */
public interface EntitySelector {

    /**
     * Obtain all entities for which walks shall be generated.
     * @return The entities to be returned.
     */
    Set<String> getEntities();
}
