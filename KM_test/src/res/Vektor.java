package res;

public class Vektor {
	
	double x;
	double y;
	
	public Vektor(Pos pos1, Pos pos2){
		x = pos2.getxPos() - pos1.getxPos();
		y = pos2.getyPos() - pos1.getyPos();
	}
	
	public Vektor(Pos pos1){
		x = pos1.getxPos();
		y = pos1.getyPos();
	}
	
	public Vektor(double win, float l){
		x = l * Math.cos(win);
		y = l * Math.sin(win);
	}
	
	public Vektor(double v1, double v2){
		this.x = v1;
		this.y = v2;
	}
	
	public double getLenght(){
		return Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Pos getNPos(Pos pos){
		pos.setxPos(pos.getxPos()+x);
		pos.setyPos(pos.getyPos()+y);
		return pos;
	}
	
	public void setVektor(Pos pos1, Pos pos2){
		x = pos2.getxPos() - pos1.getxPos();
		y = pos2.getyPos() - pos1.getyPos();
	}
	
	public Pos red(double xy, Pos pos){
		
		if(getLenght() > 0){
			if(x < 0){
				x += xy;
			}else {
				x -= xy;
			}
			if(y < 0){
				y += xy;
			}else {
				y -= xy;
			}
		}
		return getNPos(pos);
	}
	
	public double getM(){
		return y/x;
	}
	
	public Vektor middl(Vektor v1, Vektor v2){
		double x1 = (v1.getX() + v2.getX())/2;
		double y1 = (v1.getY() + v2.getY())/2;
		return new Vektor(x1, y1);
	}
	
	

}
