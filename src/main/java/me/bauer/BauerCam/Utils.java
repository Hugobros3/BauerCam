package me.bauer.BauerCam;

import io.xol.chunkstories.api.Location;
import io.xol.chunkstories.api.client.ClientInterface;
import io.xol.chunkstories.api.entity.interfaces.EntityControllable;
import io.xol.chunkstories.api.entity.interfaces.EntityRotateable;
import io.xol.engine.math.lalgb.Vector3d;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;

public final class Utils {

	public static void setup(BauerCamPlugin instance) {
		client = instance.getClientInterface();
	}

	public static ClientInterface client;
	// public static final Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Describes how often {@link PathHandler#tick()} is called per frame
	 * <p>
	 * calls are made from
	 * {@link EventListener#onRender(net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent)}
	 * )
	 */
	//public static final int renderPhases = Phase.values().length;

	/**
	 * Do only call this method if a world is loaded!
	 */
	public static Position getPosition() {

		Vector3d playerLocation = new Vector3d(0.0);
		float rotationX = 0.0f;
		float rotationY = 0.0f;

		EntityControllable entity = client.getClientSideController().getControlledEntity();
		if (entity != null) {
			playerLocation = entity.getLocation();
			if(entity instanceof EntityRotateable)
			{
				EntityRotateable erot = (EntityRotateable)entity;
				rotationX = erot.getEntityRotationComponent().getHorizontalRotation();
				rotationY = erot.getEntityRotationComponent().getVerticalRotation();
			}
		}

		// final EntityPlayerSP player = mc.thePlayer;
		return new Position(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ(), rotationX,
				rotationY, CameraRoll.roll, DynamicFOV.get());
	}

	/**
	 * @param pos
	 *            Position to teleport the player to
	 * @param force
	 *            should be true when teleporting over a presumably large
	 *            distance
	 */
	public static void teleport(final Position pos, final boolean force) {
		if (verify()) {
			final EntityControllable player = client.getClientSideController().getControlledEntity();
			// force tackles desync
			
			//CS port notice: useless as CS doesn't do any movements checks for now
			/*if (force) {
				// teleport command syntax: /tp [target player] <x> <y> <z>
				// [<y-rot>
				// <x-rot>]
				final String tpCommand = "/tp " + pos.x + " " + pos.y + " " + pos.z + " " + pos.yaw + " " + pos.pitch;
				player.sendChatMessage(tpCommand);
			}*/
			
			// TODO: Add additional local server sync and maybe send a warning
			// if doing this on a remote server (may be better doing this to
			// Minema, too)
			setPositionProperly(player, pos);
			CameraRoll.roll = pos.roll;
			DynamicFOV.set(pos.fov);
		}
	}

	public static void sendInformation(final String msg) {
		if (verify()) {
			client.print(msg);
			//mc.thePlayer.addChatMessage(new TextComponentString(msg));
		}
	}

	private static void setPositionProperly(final EntityControllable entity, final Position pos) {
		// This procedure here is crucial! When not done properly (eg.
		// setPositionAndRotation is not properly) it can lead to
		// spinning camera movement (probably yaw angle which may incorrectly be
		// bounded inside -180 and 180 degrees)
		// FUN FACT: PixelCam had the same issue!
		// FUN FACT 2: setLocationAndAngles solves this but instead results in
		// desync when setting the position of entities both in the client world
		// and server world
		// -> not loading chunks anymore on the client side
		// Workaround: Send a teleport command
		
		entity.setLocation(new Location(entity.getWorld(), pos.x, pos.y, pos.z));
		if(entity instanceof EntityRotateable)
		{
			EntityRotateable erot = (EntityRotateable)entity;
			
			System.out.println(pos.yaw+":"+pos.pitch);
			erot.getEntityRotationComponent().setRotation(180+pos.yaw, pos.pitch);
		}
		//entity.setLocationAndAngles(pos.x, pos.y, pos.z, pos.yaw, pos.pitch);
		
		// Prevents inaccurate/wobbly/jerky angle movement (setLocationAndAngles
		// only sets previous values for x,y,z -> partial tick interpolation is
		// still also done for angles by the engine)
		
		//entity.prevRotationYaw = pos.yaw;
		//entity.prevRotationPitch = pos.pitch;
	}

	private static boolean verify() {
		if (client.getClientSideController().getControlledEntity() == null) {
			PathHandler.stopTravelling();
			return false;
		}
		return true;
	}

	public static int parseSafely(final String input, final int def) {
		try {
			return Integer.parseInt(input);
		} catch (final NumberFormatException e) {
			return def;
		}
	}

	public static double parseSafely(final String input, final double def) {
		try {
			return Double.parseDouble(input);
		} catch (final NumberFormatException e) {
			return def;
		}
	}

}
