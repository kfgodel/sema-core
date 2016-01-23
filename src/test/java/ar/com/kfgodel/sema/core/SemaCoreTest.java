package ar.com.kfgodel.sema.core;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.kfgodel.sema.core.api.SemaConfiguration;
import ar.com.kfgodel.sema.core.api.SemaCore;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies the general behavior of the sema core api
 * Created by kfgodel on 22/01/16.
 */
@RunWith(JavaSpecRunner.class)
public class SemaCoreTest extends JavaSpec<SemaTestContext> {
  @Override
  public void define() {
    describe("a sema core", () -> {
      it("is created with a configuration",()->{
        SemaConfiguration config = StringVariableConfiguration.create(Variable.create());

        assertThat(SemaCore.createdFor(config)).isNotNull();
      });

      describe("when versioning a string variable history", () -> {
        context().core(()-> SemaCore.createdFor(StringVariableConfiguration.create(context().variable())));
        
        it("allows access to the current value when asking the current state",()->{
          context().variable(()-> Variable.of("Hello"));

          assertThat(context().core().getCurrentState()).isEqualTo("Hello");
        });   
      });

    });

  }
}