package test;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.Macro;
import ij.Menus;
import ij.Undo;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.gui.ImageWindow;
import ij.gui.Roi;
import ij.plugin.PlugIn;
import ij.plugin.RGBStackConverter;
import ij.process.ImageConverter;
import ij.process.StackConverter;

public class Converter implements PlugIn {
	public static boolean newWindowCreated;
	private ImagePlus imp;

	public void run(String arg) {
		this.imp = WindowManager.getCurrentImage();
		if (this.imp != null)
			if ((this.imp.isComposite()) && (arg.equals("RGB Color"))) {
				new RGBStackConverter().run("");
			} else if (this.imp.lock()) {
				convert(arg);
				this.imp.unlock();
			} else
				IJ.noImage();
	}

	public void convert(String item) {
		int type = this.imp.getType();
		ImageStack stack = null;
		if (this.imp.getStackSize() > 1)
			stack = this.imp.getStack();
		String msg = "Converting to " + item;
		IJ.showStatus(msg + "...");
		long start = System.currentTimeMillis();
		Roi roi = this.imp.getRoi();
		this.imp.deleteRoi();
		if (this.imp.getProcessor().getMinThreshold() != -808080.0D)
			this.imp.getProcessor().resetThreshold();
		boolean saveChanges = this.imp.changes;
		this.imp.changes = (IJ.getApplet() == null);
		ImageWindow win = this.imp.getWindow();
		try {
			if (stack != null) {
				boolean wasVirtual = stack.isVirtual();

				if ((stack.isRGB()) && (item.equals("RGB Color"))) {
					new ImageConverter(this.imp).convertRGBStackToRGB();
					if (win != null)
						new ImageWindow(this.imp, this.imp.getCanvas());
				} else if ((stack.isHSB()) && (item.equals("RGB Color"))) {
					new ImageConverter(this.imp).convertHSBToRGB();
					if (win != null)
						new ImageWindow(this.imp, this.imp.getCanvas());
				} else if (item.equals("8-bit")) {
					new StackConverter(this.imp).convertToGray8();
				} else if (item.equals("16-bit")) {
					new StackConverter(this.imp).convertToGray16();
				} else if (item.equals("32-bit")) {
					new StackConverter(this.imp).convertToGray32();
				} else if (item.equals("RGB Color")) {
					new StackConverter(this.imp).convertToRGB();
				} else if (item.equals("RGB Stack")) {
					new StackConverter(this.imp).convertToRGBHyperstack();
				} else if (item.equals("HSB Stack")) {
					new StackConverter(this.imp).convertToHSBHyperstack();
				} else if (item.equals("8-bit Color")) {
					int nColors = getNumber();
					if (nColors != 0)
						new StackConverter(this.imp).convertToIndexedColor(nColors);
				} else {
					throw new IllegalArgumentException();
				}
				if (wasVirtual)
					this.imp.setTitle(this.imp.getTitle());
			} else {
				Undo.setup(2, this.imp);
				ImageConverter ic = new ImageConverter(this.imp);
				if (item.equals("8-bit")) {
					ic.convertToGray8();
				} else if (item.equals("16-bit")) {
					ic.convertToGray16();
				} else if (item.equals("32-bit")) {
					ic.convertToGray32();
				} else if (item.equals("RGB Stack")) {
					Undo.reset();
					ic.convertToRGBStack();
				} else if (item.equals("HSB Stack")) {
					Undo.reset();
					ic.convertToHSB();
				} else if (item.equals("RGB Color")) {
					ic.convertToRGB();
				} else if (item.equals("8-bit Color")) {
					int nColors = getNumber();
					start = System.currentTimeMillis();
					if (nColors != 0)
						ic.convertRGBtoIndexedColor(nColors);
				} else {
					this.imp.changes = saveChanges;
					return;
				}
				IJ.showProgress(1.0D);
			}
		} catch (IllegalArgumentException e) {
			unsupportedConversion(this.imp);
			IJ.showStatus("");
			Undo.reset();
			this.imp.changes = saveChanges;
			Menus.updateMenus();
			Macro.abort();
			return;
		}
		if (roi != null)
			this.imp.setRoi(roi);
		IJ.showTime(this.imp, start, "");
		this.imp.repaintWindow();
		Menus.updateMenus();
	}

	void unsupportedConversion(ImagePlus imp) {
		IJ.error(
				"Converter",
				"Supported Conversions:\n \n8-bit -> 16-bit*\n8-bit -> 32-bit*\n8-bit -> RGB Color*\n16-bit -> 8-bit*\n16-bit -> 32-bit*\n16-bit -> RGB Color*\n32-bit -> 8-bit*\n32-bit -> 16-bit\n32-bit -> RGB Color*\n8-bit Color -> 8-bit (grayscale)*\n8-bit Color -> RGB Color\nRGB Color -> 8-bit (grayscale)*\nRGB Color -> 8-bit Color*\nRGB Color -> RGB Stack*\nRGB Color -> HSB Stack*\nRGB Stack -> RGB Color\nHSB Stack -> RGB Color\n \n* works with stacks\n");
	}

	int getNumber() {
		if (this.imp.getType() != 4)
			return 256;
		GenericDialog gd = new GenericDialog("MedianCut");
		gd.addNumericField("Number of Colors (2-256):", 256.0D, 0);
		gd.showDialog();
		if (gd.wasCanceled())
			return 0;
		int n = (int) gd.getNextNumber();
		if (n < 2)
			n = 2;
		if (n > 256)
			n = 256;
		return n;
	}
}
