package ar.com.kfgodel.sema.core;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.kfgodel.optionals.Optional;
import ar.com.kfgodel.sema.core.api.SemaConfiguration;
import ar.com.kfgodel.sema.core.api.SemaCore;
import ar.com.kfgodel.sema.core.api.StateRepository;
import ar.com.kfgodel.sema.core.api.Version;

import java.util.function.Supplier;

/**
 * This type defines test variables used on sema tests
 * Created by kfgodel on 22/01/16.
 */
public interface SemaTestContext extends TestContext {

  SemaConfiguration configuration();
  void configuration(Supplier<SemaConfiguration> definition);


  SemaCore core();
  void core(Supplier<SemaCore> definition);

  Variable<String> variable();
  void variable(Supplier<Variable<String>> definition);

  StateRepository repo();
  void repo(Supplier<StateRepository> definition);

  Version version();
  void version(Supplier<Version> definition);

  Optional<Object> metadata();
  void metadata(Supplier<Optional<Object>> definition);

}
