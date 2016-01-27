package ar.com.kfgodel.sema.core.api;

import ar.com.kfgodel.optionals.Optional;

/**
 * This type represents a semantic version, identifying the state of an entity
 *
 * Created by kfgodel on 27/01/16.
 */
public interface Version {

  /**
   * @return The optional metadata created with this version holding extra information about the world context
   */
  Optional<Object> metadata();

  /**
   * @return The identifier of the persisted state for this version
   */
  Object getStateId();
}
