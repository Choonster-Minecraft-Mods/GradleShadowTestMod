package com.choonster.gradleshadowtestmod.coremod;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;

import java.util.Map;

/**
 * Coremod to exclude dependencies from {@link LaunchClassLoader}.
 *
 * @author Choonster
 */
@IFMLLoadingPlugin.MCVersion("1.8.9")
@IFMLLoadingPlugin.Name("Gradle Shadow Test Mod Core Plugin")
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.TransformerExclusions("com.choonster.gradleshadowtestmod.coremod.")
public class GradleShadowTestModCorePlugin implements IFMLLoadingPlugin {
	/**
	 * Return a list of classes that implements the IClassTransformer interface
	 *
	 * @return a list of classes that implements the IClassTransformer interface
	 */
	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	/**
	 * Return a class name that implements "ModContainer" for injection into the mod list
	 * The "getName" function should return a name that other mods can, if need be,
	 * depend on.
	 * Trivially, this modcontainer will be loaded before all regular mod containers,
	 * which means it will be forced to be "immutable" - not susceptible to normal
	 * sorting behaviour.
	 * All other mod behaviours are available however- this container can receive and handle
	 * normal loading events
	 */
	@Override
	public String getModContainerClass() {
		return null;
	}

	/**
	 * Return the class name of an implementor of "IFMLCallHook", that will be run, in the
	 * main thread, to perform any additional setup this coremod may require. It will be
	 * run <strong>prior</strong> to Minecraft starting, so it CANNOT operate on minecraft
	 * itself. The game will deliberately crash if this code is detected to trigger a
	 * minecraft class loading (TODO: implement crash ;) )
	 */
	@Override
	public String getSetupClass() {
		return "com.choonster.gradleshadowtestmod.coremod.GradleShadowTestModCorePlugin$CallHook";
	}

	/**
	 * Inject coremod data into this coremod
	 * This data includes:
	 * "mcLocation" : the location of the minecraft directory,
	 * "coremodList" : the list of coremods
	 * "coremodLocation" : the file this coremod loaded from,
	 *
	 * @param data
	 */
	@Override
	public void injectData(Map<String, Object> data) {

	}

	/**
	 * Return an optional access transformer class for this coremod. It will be injected post-deobf
	 * so ensure your ATs conform to the new srgnames scheme.
	 *
	 * @return the name of an access transformer class or null if none is provided
	 */
	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	/**
	 * Excludes dependencies from {@link LaunchClassLoader}.
	 */
	public static class CallHook implements IFMLCallHook {
		/**
		 * Injected with data from the FML environment:
		 * "classLoader" : The FML Class Loader
		 *
		 * @param data
		 */
		@Override
		public void injectData(Map<String, Object> data) {
			LogManager.getLogger(this).info("Excluding SLF4J from ClassLoader");

			final LaunchClassLoader classLoader = (LaunchClassLoader) data.get("classLoader");
			classLoader.addTransformerExclusion("org.slf4j.");
			classLoader.addTransformerExclusion("org.apache.logging.slf4j.");
		}

		/**
		 * Computes a result, or throws an exception if unable to do so.
		 *
		 * @return computed result
		 * @throws Exception if unable to compute a result
		 */
		@Override
		public Void call() throws Exception {
			return null;
		}
	}
}
