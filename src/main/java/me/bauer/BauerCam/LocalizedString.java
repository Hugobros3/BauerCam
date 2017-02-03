package me.bauer.BauerCam;

/**
 * Use this class via {@link Object#toString()}
 */
public final class LocalizedString {

	// Sadly cannot cache a key mapping, because there is no event
	// indicating a changed setting

	private final String key;

	public LocalizedString(final String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "#{"+key+"}"; //I18n.format(this.key);
		
		//No localization support yet in ChunkStories, porting work will be done once there is
	}

}
