package ar.com.kfgodel.sema.core.api;

import ar.com.kfgodel.optionals.Optional;

import java.util.function.Function;

/**
 * This type represents the configuration needed for a semantic core
 * Created by kfgodel on 22/01/16.
 */
public interface SemaConfiguration {
  /**
   * @return The observer that is able to describe the world state
   */
  EntityStateObserver getWorldObserver();

  /**
   * @return The changer that is able to modify the world state
   */
  EntityStateChanger getWorldChanger();

  /**
   * @return The repository to use for storing world state
   */
  StateRepository getWorldStateRepository();

  /**
   * Defines a metadata creator to attach to each version
   * @param otherCreator
   */
  void replaceMetadataCreatorWith(Function<Object, Optional<Object>> otherCreator);

  /**
   * @return the metadata creator defined for this config.
   * The metadata is attached to each created version
   */
  Function<Object, Optional<Object>> getMetadataCreator();
}
