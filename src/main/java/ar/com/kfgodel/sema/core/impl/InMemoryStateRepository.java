package ar.com.kfgodel.sema.core.impl;

import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.kfgodel.optionals.Optional;
import ar.com.kfgodel.sema.core.api.StateRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * This type implements a state repository using memory
 * Created by kfgodel on 24/01/16.
 */
public class InMemoryStateRepository implements StateRepository {

  private int nextId;
  private Map<Integer, Object> statePerId;

  @Override
  public Object store(Object state) {
    int createdId = this.nextId++;
    statePerId.put(createdId, state);
    return createdId;
  }

  @Override
  public Optional<Object> retrieve(Object identifier) {
    Object storedState = statePerId.get(identifier);
    return NaryFromNative.ofNullable(storedState);
  }

  public static InMemoryStateRepository create() {
    InMemoryStateRepository repository = new InMemoryStateRepository();
    repository.nextId = 1;
    repository.statePerId = new HashMap<>();
    return repository;
  }

}
