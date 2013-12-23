package imagemanager;

import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

import java.util.Collection;

import dataaccess.IODatabase;

public class BoardImageTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//createNewBoardImageTest(); // OK
	}

	public static void createNewBoardImageTest() {
		Collection<SourceImage> srcImages = IODatabase.getInstance()
				.importSourceThumbnails();
		if (srcImages != null) {
			for (SourceImage sourceImage : srcImages) {
				sourceImage.importContent();
				sourceImage.selectBoardRegion(new Polygon(new Point(10, 10),
						new Point(210, 10), new Point(210, 210), new Point(10,
								210)));
				
				Collection<BoardImage> boardImages = sourceImage.getBoardImages();
				
				for (BoardImage boardImage : boardImages) {
					System.out.println(boardImage);
				}
				
			}
		}
	}
}
