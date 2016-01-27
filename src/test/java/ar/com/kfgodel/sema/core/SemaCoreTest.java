package ar.com.kfgodel.sema.core;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.sema.core.api.SemaConfiguration;
import ar.com.kfgodel.sema.core.api.SemaCore;
import ar.com.kfgodel.sema.core.api.SemaException;
import org.assertj.core.util.Lists;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

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
        context().variable(()-> Variable.of("Hello"));

        it("allows access to the current value when asking the current state",()->{
          assertThat(context().core().getCurrentState()).isEqualTo("Hello");
        });

        describe("versions", () -> {
          it("are created each time the state is captured",()->{
            Object createdVersion = context().core().captureState();
            assertThat(createdVersion).isNotNull();
          });
          
          it("can be used to restore the world to a previous known state",()->{
            Object version = context().core().captureState();
            context().variable().set("bye");

            context().core().restoreStateTo(version);

            assertThat(context().variable().get()).isEqualTo("Hello");
          });   
          
          it("throws an error if a version doesn't have state to restore",()->{
            Object incorrectVersion = new Object();

            try{
              context().core().restoreStateTo(incorrectVersion);
              failBecauseExceptionWasNotThrown(SemaException.class);
            }catch(SemaException e){
              assertThat(e).hasMessage("The given version["+incorrectVersion+"] doesn't have state to restore");
            }
          });
          
          it("are represented as an empty nary if no history",()->{
            Nary<Object> versions = context().core().versions();
            assertThat(versions.count()).isEqualTo(0);
          });

          it("contains the latest version as first element, and the oldest as the last",()->{
            Object oldestVersion = context().core().captureState();
            Object newestVersion = context().core().captureState();

            List<Object> allVersion = context().core().versions().collect(Collectors.toList());
            assertThat(allVersion).isEqualTo(Lists.newArrayList(newestVersion, oldestVersion));
          });
        });

      });

    });

  }
}