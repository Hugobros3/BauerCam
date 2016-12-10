package me.bauer.BauerCam;

import io.xol.chunkstories.client.RenderingConfig;

public final class DynamicFOV {

	//private static final GameSettings settings = Utils.mc.gameSettings;

	/**
	 * Extends default of {@link Options#FOV#getValueMax()}
	 */
	private static final float upperBound = 150;
	/**
	 * Extends default of {@link Options#FOV#getValueMin()}
	 */
	private static final float lowerBound = 5;
	/**
	 * Should be 70
	 */
	private static final float defaultFOV = 70f;//(Options.FOV.getValueMax() + Options.FOV.getValueMin()) / 2;

	private static final float fovPerKeyPress = 0.25f;

	public static void increase() {
		RenderingConfig.fov += fovPerKeyPress;
		verify();
	}

	public static void decrease() {
		RenderingConfig.fov -= fovPerKeyPress;
		verify();
	}

	public static void reset() {
		RenderingConfig.fov = defaultFOV;
	}

	public static void set(final float fov) {
		RenderingConfig.fov = fov;
		verify();
	}

	public static float get() {
		return RenderingConfig.fov;
	}

	private static void verify() {
		if (RenderingConfig.fov > upperBound) {
			RenderingConfig.fov = upperBound;
		} else if (RenderingConfig.fov < lowerBound) {
			RenderingConfig.fov = lowerBound;
		}
	}

}
