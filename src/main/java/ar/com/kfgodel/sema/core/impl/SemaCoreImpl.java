package ar.com.kfgodel.sema.core.impl;

import ar.com.kfgodel.optionals.Optional;
import ar.com.kfgodel.sema.core.api.*;

/**
 * This type implementes the core
 * Created by kfgodel on 22/01/16.
 */
public class SemaCoreImpl implements SemaCore {

  private SemaConfiguration config;

  @Override
  public Object getCurrentState() {
    EntityStateObserver worldObserver = config.getWorldObserver();
    Object observedState = worldObserver.describeState();
    return observedState;
  }

  @Override
  public Object captureState() {
    Object worldState = getCurrentState();
    StateRepository repo = config.getWorldStateRepository();
    Object storeId = repo.store(worldState);
    return storeId;
  }

  @Override
  public void restoreStateTo(Object version) {
    Optional<Object> previousState = config.getWorldStateRepository().retrieve(version);
    EntityStateChanger changer = config.getWorldChanger();
    changer.changeState(previousState.orElseThrow(()-> new SemaException("The given version["+version+"] doesn't have state to restore")));
  }

  public static SemaCoreImpl create(SemaConfiguration configuration) {
    SemaCoreImpl core = new SemaCoreImpl();
    core.config = configuration;
    return core;
  }

}
