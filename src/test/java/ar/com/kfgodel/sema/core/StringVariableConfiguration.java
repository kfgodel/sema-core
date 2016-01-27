package ar.com.kfgodel.sema.core;

import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.kfgodel.optionals.Optional;
import ar.com.kfgodel.sema.core.api.EntityStateChanger;
import ar.com.kfgodel.sema.core.api.EntityStateObserver;
import ar.com.kfgodel.sema.core.api.SemaConfiguration;
import ar.com.kfgodel.sema.core.api.StateRepository;
import ar.com.kfgodel.sema.core.impl.InMemoryRepository;

import java.util.function.Supplier;

/**
 * This class implements the configuration to version a string variable value history
 * Created by kfgodel on 22/01/16.
 */
public class StringVariableConfiguration implements SemaConfiguration {

  private Variable<String> variable;
  private InMemoryRepository repository;
  private Supplier<Optional<Object>> metadataCreator;

  public static StringVariableConfiguration create(Variable<String> variable) {
    StringVariableConfiguration configuration = new StringVariableConfiguration();
    configuration.variable = variable;
    configuration.repository = InMemoryRepository.create();
    configuration.metadataCreator = NaryFromNative::empty;
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
  public void replaceMetadataCreatorWith(Supplier<Optional<Object>> otherCreator) {
    this.metadataCreator = otherCreator;
  }

  @Override
  public Supplier<Optional<Object>> getMetadataCreator() {
    return metadataCreator;
  }
}
