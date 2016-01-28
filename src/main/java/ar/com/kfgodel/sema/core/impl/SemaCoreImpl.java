package ar.com.kfgodel.sema.core.impl;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.optionals.Optional;
import ar.com.kfgodel.sema.core.api.*;

import java.util.function.Function;

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
  public void changeCurrentStateTo(Object newState) {
    EntityStateChanger changer = config.getWorldChanger();
    changer.changeStateTo(newState);
  }


  @Override
  public Version captureState() {
    Version createdVersion = createNewVersion();
    VersionRepository repository = config.getVersionRepository();
    repository.store(createdVersion);
    return createdVersion;
  }

  @Override
  public void restoreStateTo(Version version) {
    Optional<Object> previousState = retrieveState(version);
    Object newState = previousState.orElseThrow(() -> new SemaException("The given version[" + version + "] doesn't have state to restore"));
    changeCurrentStateTo(newState);
  }

  @Override
  public Nary<Version> versions() {
    return config.getVersionRepository().getVersions();
  }

  public static SemaCoreImpl create(SemaConfiguration configuration) {
    SemaCoreImpl core = new SemaCoreImpl();
    core.config = configuration;
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
    VersionImpl createdVersion = VersionImpl.create(stateId, metadata);
    return createdVersion;
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

  /**
   * Gets the state that the world had in the given version
   * @param version The version to restore
   * @return The retrieved world state
   */
  private Optional<Object> retrieveState(Version version) {
    Object stateId = version.getStateId();
    StateRepository stateRepository = config.getWorldStateRepository();
    return stateRepository.retrieve(stateId);
  }


}
