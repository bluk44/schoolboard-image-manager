package test;

import ij.gui.PointRoi;
import mpicbg.ij.InteractiveInvertibleCoordinateTransform;
import mpicbg.models.HomographyModel2D;
import mpicbg.models.Point;
import mpicbg.models.PointMatch;

public class TransformationPlugin extends InteractiveInvertibleCoordinateTransform<HomographyModel2D>{
	/*  8 */   protected final HomographyModel2D model = new HomographyModel2D();
	/*    */ 
	/*    */   protected final HomographyModel2D myModel() {
	/* 11 */     return this.model;
	/*    */   }
	/*    */ 
	/*    */   protected final void setHandles()
	/*    */   {
	/* 16 */     int[] x = { this.imp.getWidth() / 4, 3 * this.imp.getWidth() / 4, 3 * this.imp.getWidth() / 4, this.imp.getWidth() / 4 };
	/* 17 */     int[] y = { this.imp.getHeight() / 4, this.imp.getHeight() / 4, 3 * this.imp.getHeight() / 4, 3 * this.imp.getHeight() / 4 };
	/*    */ 
	/* 19 */     this.p = new Point[] { 
	/* 20 */       new Point(new float[] { x[0], y[0] }), 
	/* 21 */       new Point(new float[] { x[1], y[1] }), 
	/* 22 */       new Point(new float[] { x[2], y[2] }), 
	/* 23 */       new Point(new float[] { x[3], y[3] }) };
	/*    */ 
	/* 25 */     this.q = new Point[] { 
	/* 26 */       this.p[0].clone(), 
	/* 27 */       this.p[1].clone(), 
	/* 28 */       this.p[2].clone(), 
	/* 29 */       this.p[3].clone() };
	/*    */ 
	/* 31 */     this.m.add(new PointMatch(this.p[0], this.q[0]));
	/* 32 */     this.m.add(new PointMatch(this.p[1], this.q[1]));
	/* 33 */     this.m.add(new PointMatch(this.p[2], this.q[2]));
	/* 34 */     this.m.add(new PointMatch(this.p[3], this.q[3]));
	/*    */ 
	/* 36 */     this.handles = new PointRoi(x, y, 4);
	/* 37 */     this.imp.setRoi(this.handles);
	/*    */   }
	/*    */ 
	/*    */   protected final void updateHandles(int x, int y)
	/*    */   {
	/* 43 */     float[] fq = this.q[this.targetIndex].getW();
	/*    */ 
	/* 45 */     int[] rx = new int[this.q.length];
	/* 46 */     int[] ry = new int[this.q.length];
	/*    */ 
	/* 48 */     for (int i = 0; i < this.q.length; ++i)
	/*    */     {
	/* 50 */       rx[i] = (int)this.q[i].getW()[0];
	/* 51 */       ry[i] = (int)this.q[i].getW()[1];
	/*    */     }
	/*    */ 
	/* 54 */     rx[this.targetIndex] = x;
	/* 55 */     ry[this.targetIndex] = y;
	/*    */ 
	/* 57 */     this.handles = new PointRoi(rx, ry, 4);
	/* 58 */     this.imp.setRoi(this.handles);
	/*    */ 
	/* 60 */     fq[0] = x;
	/* 61 */     fq[1] = y;
	/*    */   }
}
