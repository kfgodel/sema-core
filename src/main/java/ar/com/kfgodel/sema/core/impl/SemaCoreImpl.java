package ar.com.kfgodel.sema.core.impl;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.kfgodel.optionals.Optional;
import ar.com.kfgodel.sema.core.api.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;

/**
 * This type implementes the core
 * Created by kfgodel on 22/01/16.
 */
public class SemaCoreImpl implements SemaCore {

  private SemaConfiguration config;
  private Deque<Version> versions;

  @Override
  public Object getCurrentState() {
    EntityStateObserver worldObserver = config.getWorldObserver();
    Object observedState = worldObserver.describeState();
    return observedState;
  }

  @Override
  public Version captureState() {
    Version storeId = createNewVersion();
    versions.addFirst(storeId);
    return storeId;
  }

  @Override
  public void restoreStateTo(Version version) {
    StateRepository stateRepository = config.getWorldStateRepository();
    Optional<Object> previousState = stateRepository.retrieve(version.getStateId());
    EntityStateChanger changer = config.getWorldChanger();
    changer.changeStateTo(previousState.orElseThrow(()-> new SemaException("The given version["+version+"] doesn't have state to restore")));
  }

  @Override
  public Nary<Version> versions() {
    return NaryFromNative.create(versions.stream());
  }

  public static SemaCoreImpl create(SemaConfiguration configuration) {
    SemaCoreImpl core = new SemaCoreImpl();
    core.config = configuration;
    core.versions = new LinkedList<>();
    return core;
  }

  /**
   *
   * @return The created version
   */
  private Version createNewVersion() {
    Object currentState = getCurrentState();
    Object stateId = persistState(currentState);
    Optional<Object> metadata = generateMetadata(currentState);
    return VersionImpl.create(stateId, metadata);
  }

  /**
   * Generates metadata for the new version based on the configuration and the captured state
   * @return The optionally created metadata
   * @param currentState The state to generate metadata for
   */
  private Optional<Object> generateMetadata(Object currentState) {
    Function<Object, Optional<Object>> metadataCreator = config.getMetadataCreator();
    return metadataCreator.apply(currentState);
  }

  /**
   * Stores the current state into the state repository
   * @return  The persisted state id
   * @param worldState
   */
  private Object persistState(Object worldState) {
    StateRepository repo = config.getWorldStateRepository();
    return repo.store(worldState);
  }


}
