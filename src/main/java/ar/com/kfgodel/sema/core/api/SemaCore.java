package ar.com.kfgodel.sema.core.api;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.sema.core.impl.SemaCoreImpl;

/**
 * This type represents the main communication point of a semantic history tracker
 * Created by kfgodel on 22/01/16.
 */
public interface SemaCore {
  /**
   * Creates a new core using the given configuration
   * @param config The configuration that maps the core to an observable world
   * @return The created core for the world described as configuration
   */
  static SemaCore createdFor(SemaConfiguration config) {
    return SemaCoreImpl.create(config);
  }

  /**
   * Extracts the current state of the observed world using the configuration definition, and returns it
   * as an object.<br>
   *   This operation doesn't affect the history. It only allows access to the world state representation available to the core
   * @return The observed world state representation
   */
  Object getCurrentState();

  /**
   * Creates a backup copy of the current state of the observed world and stores its internally to be able to
   * restore it later if needed
   * @return The created version that represents the captured state
   */
  Object captureState();

  /**
   * Modifies the current state of the observed world to match the stored state represented by the given version
   * @param version The version to restore the world to
   */
  void restoreStateTo(Object version);

  /**
   * @return All the recorded versions in order, with the latest as first
   */
  Nary<Object> versions();
}
