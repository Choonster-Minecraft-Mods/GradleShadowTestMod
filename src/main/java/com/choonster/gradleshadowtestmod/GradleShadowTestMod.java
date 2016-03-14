package com.choonster.gradleshadowtestmod;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InfoCmd;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientBuilder;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ProcessingException;
import java.io.IOException;

/**
 * A mod to test the shadow Gradle plugin.
 *
 * @author Choonster
 */
@Mod(modid = GradleShadowTestMod.MODID)
public class GradleShadowTestMod {
	public static final String MODID = "gradleshadowtestmod";

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Logger.setLogger(event.getModLog());

		try {
			final ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
			final org.slf4j.Logger testLogger = loggerFactory.getLogger("TestLogger");
			testLogger.info("This is a test of the SLF4J logger!");
		} catch (Throwable e) {
			Logger.error(e, "Error logging with SLF4J");
		}

		try (final DockerClient dockerClient = DockerClientBuilder.getInstance().build()) {
			final InfoCmd infoCmd = dockerClient.infoCmd();
			final Info exec = infoCmd.exec();
			final String name = exec.getName();
			Logger.info("Docker name: %s", name);
		} catch (IOException | ProcessingException e) {
			Logger.error(e, "Error executing docker command");
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}
