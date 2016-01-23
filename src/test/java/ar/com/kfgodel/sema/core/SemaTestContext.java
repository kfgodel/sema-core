package ar.com.kfgodel.sema.core;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.kfgodel.sema.core.api.SemaCore;

import java.util.function.Supplier;

/**
 * This type defines test variables used on sema tests
 * Created by kfgodel on 22/01/16.
 */
public interface SemaTestContext extends TestContext {
  SemaCore core();
  void core(Supplier<SemaCore> definition);

  Variable<String> variable();
  void variable(Supplier<Variable<String>> definition);


}
