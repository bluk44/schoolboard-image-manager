package imageprocessing.geometry.houghtransform;

import imageprocessing.matrix.MatrixB;
import imageprocessing.matrix.MatrixI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * kat 0 <= theta < 180 wspolrzedna pozioma x -> kat theta wspolrzedna pionowa y
 * -> odleglosc rho
 * 
 * @author Towariszcz_Admin
 * 
 */

public abstract class HoughTransform {

	public static HoughMatrix createHoughMatrix(MatrixB binaryImage) {

		int maxRho = (int) Math.ceil(Math.sqrt(binaryImage.getSizeX()
				* binaryImage.getSizeX() + binaryImage.getSizeY()
				* binaryImage.getSizeY()));
		int rhoSize = 2 * maxRho + 1;

		HoughMatrix result = new HoughMatrix(rhoSize);

		double rad = Math.PI / 180.d;
		int rho = 0, thetaIdx = 0, rhoIdx = 0;
		for (int y = 0; y < binaryImage.getSizeY(); y++) {
			for (int x = 0; x < binaryImage.getSizeX(); x++) {
				if (binaryImage.getElement(x, y)) {
					for (int th = 0; th < 180; th++) {
						rho = (int) Math.round(x * Math.cos(th * rad) + y
								* Math.sin(th * rad));
						rhoIdx = result.getRhoIdx(rho);
						thetaIdx = th;
						result.setElement(thetaIdx, rhoIdx,
								result.getElement(thetaIdx, rhoIdx) + 1);
					}
				}
			}
		}
		return result;
	}

	public static List<HoughLine> getLines(MatrixI houghMatrix, int minVotes) {
		int w = houghMatrix.getSizeX(), h = houghMatrix.getSizeY();
		List<HoughLine> lines = new ArrayList<HoughLine>();
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int votes = houghMatrix.getElement(j, i);
				if (votes >= minVotes)
					lines.add(new HoughLine(j, i, votes, houghMatrix.getSizeY()));
			}
		}
		return lines;
	}

	public static List<HoughLine> getPercentLines(MatrixI houghMatrix, int value) {
		List<HoughLine> allLines = getLines(houghMatrix, 1);
		Collections.sort(allLines, new HoughLine.DescComparator());

		double p = value / 100.d;
		int idx = allLines.size() - (int) Math.round(allLines.size() * p);

		List<HoughLine> result = new ArrayList<HoughLine>(allLines.size() - idx);
		for (int i = idx; i < allLines.size(); i++) {
			result.add(allLines.get(i));
		}

		return result;
	}

	public static List<HoughLine> avgLines(List<HoughLine> lines, int rhoBound,
			int thetaBound) {
		if (lines.size() == 1)
			return lines;
		List<HoughLine> result = new ArrayList<HoughLine>(lines.size());

		for (int i1 = 0; i1 < lines.size() - 1; i1++) {
			int i2 = i1 + 1;
			HoughLine l1 = lines.get(i1);
			for (; i2 < lines.size(); i2++) {
				HoughLine l2 = lines.get(i2);
				if (checkIfNear(l1, l2, rhoBound, thetaBound)) {
					double newRho = (l1.getRho() + l2.getRho()) / 2.d;
					lines.set(i1, null);
					l2.setRho(newRho);
					lines.set(i2, l2);
					break;
				}
			}
		}
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i) != null)
				result.add(lines.get(i));
		}

		return result;
	}

	public static List<HoughLine> avgLines2(List<HoughLine> lines,
			int rhoBound, int thetaBound) {
		if (lines.size() == 1)
			return lines;
		List<HoughLine> result = new ArrayList<HoughLine>(lines.size());

		for (int i1 = 0; i1 < lines.size() - 1; i1++) {
			int i2 = i1 + 1;
			HoughLine l1 = lines.get(i1);
			for (; i2 < lines.size(); i2++) {
				HoughLine l2 = lines.get(i2);
				if (checkIfNear2(l1, l2, rhoBound, thetaBound)) {
					double newTheta = (l1.getTheta() + l2.getTheta()) / 2.d;
					lines.set(i1, null);
					l2.setTheta(newTheta);
					lines.set(i2, l2);
					break;
				}
			}
		}
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i) != null)
				result.add(lines.get(i));
		}

		return result;
	}

	private static boolean checkIfNear(HoughLine l1, HoughLine l2,
			int rhoBound, int thetaBound) {
		if (Math.abs(l1.getRho() - l2.getRho()) <= rhoBound)
			return true;
		return false;
	}

	private static boolean checkIfNear2(HoughLine l1, HoughLine l2,
			int rhoBound, int thetaBound) {
		double dTheta = Math.abs(l1.getTheta() - l2.getTheta());
		if ((dTheta >= 0 && dTheta <= 30) || (dTheta >= 150 && dTheta <= 180))
			return true;

		else
			return false;
	}



	private static int[] countAvg(MatrixI houghMatrix, int x, int y, int rhoR,
			int thetaR) {
		int rhoAvg = 0, thetaAvg = 0, votes = 0;
		int n = 0;
		for (int i = y - rhoR; i <= y + rhoR; i++) {
			for (int j = x - thetaR; j <= x + thetaR; j++) {
				votes = houghMatrix.getElement(j, i);
				if (votes != 0) {
					rhoAvg += i;
					thetaAvg += j;
					++n;
				}
			}
		}
		rhoAvg /= n;
		thetaAvg /= n;

		int[] result = new int[3];
		result[0] = rhoAvg;
		result[1] = thetaAvg;
		result[2] = votes;
		return result;
	}

	private static int[] countWeightedtAvg(MatrixI houghMatrix, int x, int y,
			int rhoR, int thetaR) {
		long rhoAvg = 0, thetaAvg = 0;
		long sumVotes = 0;
		for (int i = y - rhoR; i <= y + rhoR; i++) {
			for (int j = x - thetaR; j <= x + thetaR; j++) {
				int v = houghMatrix.getElement(j, i);
				rhoAvg += i * v;
				thetaAvg += j * v;
				sumVotes += v;

			}
		}
		rhoAvg /= sumVotes;
		thetaAvg /= sumVotes;

		int[] result = new int[3];
		result[0] = (int) rhoAvg;
		result[1] = (int) thetaAvg;
		result[2] = (int) sumVotes;
		return result;
	}


	
}
