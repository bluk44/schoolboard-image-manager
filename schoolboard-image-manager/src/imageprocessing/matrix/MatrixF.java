package imageprocessing.matrix;

public class MatrixF {
	protected int sizeX, sizeY;
	protected float[] data;
	
	public MatrixF(int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		data = new float[sizeX*sizeY];
	}
	
	public MatrixF(float[] data, int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		this.data = new float[sizeX*sizeY];
		
		for(int i=0;i<data.length;i++){
			this.data[i] = data[i];
		}
	}
	
	public float getElement(int x, int y){
		return data[y*sizeX+x];
	}
	
	public float getElement(int pos){
		return data[pos];
	}
	
	public void setElement(int x, int y, float element){
		data[y*sizeX+x] = element;
	}
	
	public void setElement(int pos, float element){
		data[pos] = element;
	}
	
	public float[] getContents(){
		return data;
	}
	
	public void setContents(float[] data){
		for(int i=0;i<data.length;i++){
			this.data[i] = data[i];
		}
	}
	
	public int getSizeX(){
		return sizeX;
	}
	
	public int getSizeY(){
		return sizeY;
	}
	
	public int getLength(){
		return data.length;
	}
	
	public MatrixF subMatrix(int x, int y, int sizeX, int sizeY){
		return new MatrixF(data, sizeX, sizeY);
		
	}
	
	public MatrixF transpone(){
		MatrixF T = new MatrixF(getSizeY(), getSizeX());
		for(int i = 0; i< getSizeY(); i++){
			for(int j = 0; j < getSizeX(); j++){
				T.setElement(i, j, getElement(j,i));
			}
		}
		
		return T;
	}
	
	MatrixF conv(MatrixF kernel){
		// TODO
		return null;
	}
	
	public MatrixF convY(MatrixF kernel, boolean replicate){
		MatrixF result = new MatrixF(sizeX, sizeY);
		MatrixF tmp = new MatrixF(1, sizeY + kernel.getSizeY() - 1);
	
		int ckEl = (kernel.getSizeY() + 1) / 2;
		int bkDist = ckEl;
		int ekDist = kernel.getSizeY() - ckEl - 1;
		
		double sum = 0;
		for(int i=0; i<getSizeX(); i++){
			
			for(int j=0;j<getSizeY(); j++){
				tmp.setElement(0, ekDist + j, getElement(i, j));
			}
			
			if(replicate){
				for(int j=0;j<ekDist;j++){
					tmp.setElement(0, j, getElement(i, 0));
				}
				for(int j=tmp.getSizeY() - ckEl ;j<tmp.getSizeY();j++){
					tmp.setElement(0, j, getElement(i, getSizeY() - 1));
				}					
			}
			
			for(int j=0;j<getSizeY(); j++){
				sum = 0;
				for(int k = -ekDist; k <= bkDist; k++){
					sum += tmp.getElement(0, ekDist + j+k)*kernel.getElement(0, ckEl - k);
				}
				result.setElement(i, j, (float)sum);
			}			
			
		}
		return result;
		
	}
	
	public MatrixF convX(MatrixF kernel, boolean replicate){
		MatrixF result = new MatrixF(sizeX, sizeY);
		MatrixF tmp = new MatrixF(sizeX + kernel.getSizeX() - 1, 1);
	
		int ckEl = (kernel.getSizeX() + 1) / 2;
		int bkDist = ckEl;
		int ekDist = kernel.getSizeX() - ckEl - 1;
		
		double sum = 0;
		for(int i=0; i<getSizeY(); i++){

			for(int j=0;j<getSizeX(); j++){
				tmp.setElement(ekDist + j, 0, getElement(j, i));
			}
			
			if(replicate){
				for(int j=0;j<ekDist;j++){
					tmp.setElement(j, 0, getElement(0, i));
				}
				for(int j=tmp.getSizeX() - ckEl ;j<tmp.getSizeX();j++){
					tmp.setElement(j, 0, getElement(getSizeX() - 1, i));
				}					
			}
			
			for(int j=0;j<getSizeX(); j++){
				sum = 0;
				for(int k = -ekDist; k <= bkDist; k++){
					sum += tmp.getElement(ekDist + j+k, 0)*kernel.getElement(ckEl - k, 0);
				}
				result.setElement(j, i, (float)sum);
			}			
			
		}
		return result;
		
	}
	public void print(){
		for(int i=0;i<sizeY;i++){
			for(int j=0;j<sizeX;j++){
				System.out.print(getElement(j, i)+" ");
			}
			System.out.println();
		}
	}
	
	public void printRow(int r){
		for(int i=0;i<sizeX;i++){
			System.out.print(getElement(i, r)+" ");
		}
	}
	
	public void printCol(int c){
		for(int i=0;i<sizeY;i++){
			System.out.println(getElement(c, i)+" ");
		}
	}
}
