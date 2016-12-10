package me.bauer.BauerCam.Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import me.bauer.BauerCam.BauerCamPlugin;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;

public class SubSave extends ASubExportImport {

	@Override
	protected void derivedExecute(final String filename) {
		final Position[] points = PathHandler.getWaypoints();
		if (points.length == 0) {
			Utils.sendInformation(BauerCamPlugin.pathIsEmpty.toString());
			return;
		}

		final File file = new File(BauerCamPlugin.bauercamDirectory, filename + extension);

		if (file.isFile()) {
			Utils.sendInformation(BauerCamPlugin.fileAlreadyExists.toString());
			return;
		}

		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));

			for (final Position point : points) {
				writer.write(point.toString());
				writer.newLine();
			}

			Utils.sendInformation(BauerCamPlugin.exportSuccessful + file.getAbsolutePath());
		} catch (final IOException e) {
			Utils.sendInformation(BauerCamPlugin.IOError.toString());
			Utils.sendInformation(e.getMessage());
		}

		if (writer == null) {
			return;
		}
		try {
			writer.close();
		} catch (final IOException e) {
		}
	}

	@Override
	public String getBase() {
		return "save";
	}

	@Override
	public String getDescription() {
		return "/cam save <filename>";
	}

}
