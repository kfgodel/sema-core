package ar.com.kfgodel.sema.core.impl;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.kfgodel.optionals.Optional;
import ar.com.kfgodel.sema.core.api.*;

import java.util.Deque;
import java.util.LinkedList;

/**
 * This type implementes the core
 * Created by kfgodel on 22/01/16.
 */
public class SemaCoreImpl implements SemaCore {

  private SemaConfiguration config;
  private Deque<Object> versions;

  @Override
  public Object getCurrentState() {
    EntityStateObserver worldObserver = config.getWorldObserver();
    Object observedState = worldObserver.describeState();
    return observedState;
  }

  @Override
  public Object captureState() {
    Object storeId = createNewVersion();
    versions.addFirst(storeId);
    return storeId;
  }

  @Override
  public void restoreStateTo(Object version) {
    StateRepository stateRepository = config.getWorldStateRepository();
    Optional<Object> previousState = stateRepository.retrieve(version);
    EntityStateChanger changer = config.getWorldChanger();
    changer.changeStateTo(previousState.orElseThrow(()-> new SemaException("The given version["+version+"] doesn't have state to restore")));
  }

  @Override
  public Nary<Object> versions() {
    return NaryFromNative.create(versions.stream());
  }

  public static SemaCoreImpl create(SemaConfiguration configuration) {
    SemaCoreImpl core = new SemaCoreImpl();
    core.config = configuration;
    core.versions = new LinkedList<>();
    return core;
  }

  /**
   * Stores the current state as a new version
   * @return The created version
   */
  private Object createNewVersion() {
    Object worldState = getCurrentState();
    StateRepository repo = config.getWorldStateRepository();
    return repo.store(worldState);
  }


}
