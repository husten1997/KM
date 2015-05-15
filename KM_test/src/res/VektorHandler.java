package res;

import assets.gasset;

public class VektorHandler {
	
	Pos posE;
	Pos posA;
	Vektor v;
	gasset asset;
	
	public VektorHandler(gasset asset, Vektor v, Pos posA, Pos posE){
		this.posA = posA;
		this.posE = posE;
		this.asset = asset;
		this.v = v;
	}
	
	public void calc(double xy){
		asset.setPos(v.red(xy, posE));
	}
	
	public gasset getO(){
		return asset;
	}
	
	public void red(){
		Vektor hv = new Vektor(Math.tanh(v.getM()), 0.1f);
		posA = hv.getNPos(posA);
		v.setVektor(posA, posE);
		asset.setPos(posA);
	}

}
