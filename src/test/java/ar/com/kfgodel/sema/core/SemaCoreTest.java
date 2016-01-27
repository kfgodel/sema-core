package ar.com.kfgodel.sema.core;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.kfgodel.optionals.Optional;
import ar.com.kfgodel.sema.core.api.SemaConfiguration;
import ar.com.kfgodel.sema.core.api.SemaCore;
import ar.com.kfgodel.sema.core.api.SemaException;
import ar.com.kfgodel.sema.core.api.Version;
import ar.com.kfgodel.sema.core.impl.VersionImpl;
import org.assertj.core.util.Lists;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.function.Supplier;
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
        context().core(()-> SemaCore.createdFor(context().configuration()));
        context().configuration(()-> StringVariableConfiguration.create(context().variable()));
        context().variable(()-> Variable.of("Hello"));

        it("allows access to the current value when asking the current state",()->{
          assertThat(context().core().getCurrentState()).isEqualTo("Hello");
        });

        describe("a single version", () -> {
          context().version(()->  context().core().captureState());

          it("is created each time the state is captured",()->{
            assertThat(context().version()).isNotNull();
          });

          it("can be used to restore the world to a previous known state",()->{
            Version previousVersion = context().version();
            context().variable().set("bye");

            context().core().restoreStateTo(previousVersion);

            assertThat(context().variable().get()).isEqualTo("Hello");
          });

          it("throws an error if a version doesn't have state to restore",()->{
            Version incorrectVersion = new VersionImpl(){};

            try{
              context().core().restoreStateTo(incorrectVersion);
              failBecauseExceptionWasNotThrown(SemaException.class);
            }catch(SemaException e){
              assertThat(e).hasMessage("The given version["+incorrectVersion+"] doesn't have state to restore");
            }
          });

          describe("metadata", () -> {

            context().metadata(()-> context().version().metadata());

            it("is absent by default",()->{
              assertThat(context().metadata().isPresent()).isFalse();
            });

            it("is present if a metadata creator is defined in the config",()->{
              Object exampleMetadata = new Object();
              Supplier<Optional<Object>> nonEmptyMetadataCreator = () -> NaryFromNative.of(exampleMetadata);

              context().configuration().replaceMetadataCreatorWith(nonEmptyMetadataCreator);
              Optional<Object> metadata = context().metadata();

              assertThat(metadata.isPresent()).isTrue();
              assertThat(metadata.get()).isSameAs(exampleMetadata);
            });   
          });

        });

        describe("versions", () -> {

          it("are represented as an empty nary if no history",()->{
            Nary<Version> versions = context().core().versions();
            assertThat(versions.count()).isEqualTo(0);
          });

          it("are represented as a nary containing the latest version as first element, and the oldest as the last",()->{
            Version oldestVersion = context().core().captureState();
            Version newestVersion = context().core().captureState();

            List<Version> allVersion = context().core().versions().collect(Collectors.toList());
            assertThat(allVersion).isEqualTo(Lists.newArrayList(newestVersion, oldestVersion));
          });
        });



      });

    });

  }
}