package ar.com.kfgodel.sema.core.api;

/**
 * This type represents the observer of a world entity that is able to describe it as an object
 * Created by kfgodel on 22/01/16.
 */
@FunctionalInterface
public interface EntityStateObserver {
  /**
   * Describes the current entity state
   * @return The state of the entity
   */
  Object describeState();
}
