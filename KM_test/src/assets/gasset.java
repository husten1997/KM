package assets;

import res.Pos;

public abstract class gasset {
	int sort = 1;
	
	public void setSort(int s){
		sort = s;
	}
	
	public int getSort(){
		return sort;
	}
	public abstract void draw();

	public abstract void draw2();
	
	public abstract float getX();
	public abstract float getY();

	public abstract void setX(float x);
	public abstract void setY(float y);

	public abstract Pos getPos(); 
	
	public abstract void setPos(Pos p);
	
	


}
