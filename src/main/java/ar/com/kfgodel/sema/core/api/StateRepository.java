package ar.com.kfgodel.sema.core.api;

import ar.com.kfgodel.optionals.Optional;

/**
 * This type knows how to represent and store state in order to reproduce it later
 * Created by kfgodel on 24/01/16.
 */
public interface StateRepository {
  /**
   * Stores the given state and returns an id to identify it
   * @param state The stored state
   * @return The given Id for the stored state
   */
  Object store(Object state);

  /**
   * Retrieves the state persisted in this repository with the given identifier.<br>
   *   An empty optional is returned if the identifier doesn't belong to a persisted state
   * @param identifier The identifier of teh once persisted state
   * @return The retrieved state or empty optional
   */
  Optional<Object> retrieve(Object identifier);
}
