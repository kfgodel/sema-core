package ar.com.kfgodel.sema.core.api;

/**
 * This type represents an world entity changer, that is able to modify the state of the entity
 * Created by kfgodel on 24/01/16.
 */
public interface EntityStateChanger {
  /**
   * Modifies the state of the world to match the given state
   * @param worldState The state representation to match in the observed world
   */
  void changeState(Object worldState);
}
