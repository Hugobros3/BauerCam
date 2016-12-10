package me.bauer.BauerCam;

import io.xol.chunkstories.api.events.EventHandler;
import io.xol.chunkstories.api.events.Listener;
import io.xol.chunkstories.core.events.CameraSetupEvent;
import io.xol.chunkstories.core.events.ClientInputPressedEvent;
import io.xol.chunkstories.core.events.WorldPostRenderingEvent;
import io.xol.chunkstories.core.events.WorldTickEvent;
import me.bauer.BauerCam.Interpolation.CubicInterpolator;
import me.bauer.BauerCam.Interpolation.IAdditionalAngleInterpolator;
import me.bauer.BauerCam.Interpolation.IPolarCoordinatesInterpolator;
import me.bauer.BauerCam.Interpolation.Interpolator;
import me.bauer.BauerCam.Path.IPathChangeListener;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;

public final class EventListener implements Listener, IPathChangeListener {

	public static final EventListener instance = new EventListener();
	/**
	 * Describes how many lines per block intersection should be drawn
	 */
	private static final double previewFineness = 2.5;

	private Position[] previewPoints;

	/**
	 * Use {@link EventListener#instance}
	 */
	private EventListener() {
		this.previewPoints = null;
		PathHandler.addPathChangeListener(this);
	}

	@Override
	public void onPathChange() {
		if (PathHandler.getWaypointSize() > 1) {
			final Position[] path = PathHandler.getWaypoints();
			final Interpolator interpolater = new Interpolator(path, CubicInterpolator.instance,
					IPolarCoordinatesInterpolator.dummy, IAdditionalAngleInterpolator.dummy);

			double distances = 0;

			Position prev = path[0];

			// The use of direct distances instead of the actual interpolated
			// slope means that there will always be less drawn lines per block,
			// however this is a good enough approximation

			for (int i = 1; i < path.length; i++) {
				final Position next = path[i];
				distances += prev.distance(next);
				prev = next;
			}

			int iterations = (int) (distances * previewFineness + 0.5);
			// Snap to next mod 2
			iterations += iterations & 1;

			this.previewPoints = new Position[iterations];
			for (int i = 0; i < iterations; i++) {
				this.previewPoints[i] = interpolater.getPoint((double) i / (iterations - 1));
			}

		} else {
			this.previewPoints = null;
		}
	}

	@EventHandler
	public void onRender(final WorldPostRenderingEvent e) {
		if (this.previewPoints == null || !PathHandler.showPreview()) {
			return;
		}

		//TODO When the basic thing works, make a proper line renderer ( ChunkStories uses OpenGL Core 3.x+ with an in-house abstraction layer )
		
		/*final Entity renderEntity = Utils.mc.getRenderViewEntity();
		final float partial = e.getPartialTicks();
		final double renderX = renderEntity.lastTickPosX + (renderEntity.posX - renderEntity.lastTickPosX) * partial;
		final double renderY = renderEntity.lastTickPosY + (renderEntity.posY - renderEntity.lastTickPosY) * partial;
		final double renderZ = renderEntity.lastTickPosZ + (renderEntity.posZ - renderEntity.lastTickPosZ) * partial;

		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();

		// the render entity is basically only the player (which means that the
		// eye height is not automatically considered in its y value)
		GlStateManager.translate(-renderX, renderEntity.getEyeHeight() - renderY, -renderZ);
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();
		GlStateManager.glLineWidth(5f);
		GlStateManager.color(1.0f, 0.2f, 0.2f);

		GL11.glBegin(GL11.GL_LINES);
		for (int i = 0; i < (this.previewPoints.length - 1); i++) {
			final Position prev = this.previewPoints[i];
			final Position next = this.previewPoints[i + 1];
			GL11.glVertex3d(prev.x, prev.y, prev.z);
			GL11.glVertex3d(next.x, next.y, next.z);
		}
		GL11.glEnd();

		GlStateManager.popAttrib();
		GlStateManager.popMatrix();*/
		
		//Moved from the other event handler
		PathHandler.tick();
	}

	@EventHandler
	public void onKeyInput(final ClientInputPressedEvent e) {
		if (PathHandler.isTravelling()) {
			return;
		}

		if (BauerCamPlugin.point.isPressed()) {
			final Position playerPos = Utils.getPosition();
			PathHandler.addWaypoint(playerPos);
			Utils.sendInformation(BauerCamPlugin.pathAdd.toString() + PathHandler.getWaypointSize());
		}

		if (BauerCamPlugin.cameraReset.isPressed()) {
			CameraRoll.reset();
		}

		if (BauerCamPlugin.fovReset.isPressed()) {
			DynamicFOV.reset();
		}
	}

	@EventHandler
	public void onTick(final WorldTickEvent e) {
		if (PathHandler.isTravelling()) {
			return;
		}

		if (BauerCamPlugin.cameraClock.isPressed()) {
			CameraRoll.rotateClockWise();
		}

		if (BauerCamPlugin.cameraCounterClock.isPressed()) {
			CameraRoll.rotateCounterClockWise();
		}

		if (BauerCamPlugin.fovHigh.isPressed()) {
			DynamicFOV.increase();
		}

		if (BauerCamPlugin.fovLow.isPressed()) {
			DynamicFOV.decrease();
		}
	}

	/*@EventHandler
	public void onRender(final RenderTickEvent e) {
		//Moved to WorldPostRenderingEvent ;
		//Why was there two seperate events in the first place ? Are RenderingTickEvents not the same frequency as RenderWorldLastEvents in Forge ?
		
		//PathHandler.tick();
	}*/

	@EventHandler
	public void onOrientCamera(final CameraSetupEvent e) {
		// Do not explicitly set roll to 0 (when the player is hurt for example
		// minecraft uses roll)
		if (CameraRoll.roll == 0) {
			//return; don't mess with this for now, we want the roll to stop when we're done
		}
		
		//Janky-ass job of going straight into the implementation quircks instead of proper abstraction
		e.getCamera().rotationZ = CameraRoll.roll;
		
		//e.setRoll(CameraRoll.roll);
	}

}
