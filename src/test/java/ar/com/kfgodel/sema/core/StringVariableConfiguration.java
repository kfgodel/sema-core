package ar.com.kfgodel.sema.core;

import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.kfgodel.optionals.Optional;
import ar.com.kfgodel.sema.core.api.*;
import ar.com.kfgodel.sema.core.impl.InMemoryStateRepository;
import ar.com.kfgodel.sema.core.impl.InMemoryVersionRepository;

import java.util.function.Function;

/**
 * This class implements the configuration to version a string variable value history
 * Created by kfgodel on 22/01/16.
 */
public class StringVariableConfiguration implements SemaConfiguration {

  private Variable<String> variable;
  private InMemoryStateRepository repository;
  private Function<Object, Optional<Object>> metadataCreator;
  private InMemoryVersionRepository versionRepo;

  public static StringVariableConfiguration create(Variable<String> variable) {
    StringVariableConfiguration configuration = new StringVariableConfiguration();
    configuration.variable = variable;
    configuration.repository = InMemoryStateRepository.create();
    configuration.metadataCreator = (state)-> NaryFromNative.empty();
    configuration.versionRepo = InMemoryVersionRepository.create();
    return configuration;
  }

  @Override
  public EntityStateObserver getWorldObserver() {
    return ()-> variable.get();
  }

  @Override
  public EntityStateChanger getWorldChanger() {
    return (newState)-> variable.set((String) newState);
  }

  @Override
  public StateRepository getWorldStateRepository() {
    return repository;
  }

  @Override
  public void replaceMetadataCreatorWith(Function<Object, Optional<Object>> otherCreator) {
    this.metadataCreator = otherCreator;
  }

  @Override
  public Function<Object, Optional<Object>> getMetadataCreator() {
    return metadataCreator;
  }

  @Override
  public VersionRepository getVersionRepository() {
    return versionRepo;
  }
}
