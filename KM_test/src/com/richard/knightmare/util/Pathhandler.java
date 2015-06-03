package com.richard.knightmare.util;

import java.util.HashMap;

import com.matze.knightmare.meshes.Soldat;

public class Pathhandler {

	private int i = 1;
	private HashMap<Integer, Pathfinding> pathfinding = new HashMap<>(), toDo = new HashMap<>();
	private HashMap<Integer, Pos> toChange = new HashMap<>();
	private HashMap<Integer, Integer> maxTrys = new HashMap<>(), trys= new HashMap<>(); 

	public Pathhandler() {

	}

	public boolean isCurrentlyPathfinding(int key){
		if(pathfinding.get(key)!=null){
			return true;
		}
		if(toChange.get(key)!=null){
			return true;
		}
		if(toDo.get(key)!=null){
			return true;
		}
		return false;
	}
	
	public void handle(Soldat s, Pos ziel, int trys) {
		if (s.getID() == 0) {
			s.register(i);
			i++;
		}
		maxTrys.put(s.getID(), trys);
		this.trys.put(s.getID(), trys);
		if (pathfinding.containsKey(s.getID())) {
			if (!((int) pathfinding.get(s.getID()).getZiel().getX() == (int) (ziel.getX() / 32)
					&& (int) pathfinding.get(s.getID()).getZiel().getY() == (int) (ziel.getY() / 32))) {
				pathfinding.get(s.getID()).setContinuing();
				toChange.put(s.getID(), ziel);
			}
		}else{
			Pathfinding p = new Pathfinding(s, ziel);
			com.richard.knightmare.util.Pathfinding.Pos pos = p.pathfind();
			System.out.println("Moving to " + pos);
			if(pos==null){
				pathfinding.put(s.getID(),p);
			}else{
				this.trys.put(s.getID(), this.trys.get(s.getID())-1);
				toDo.put(s.getID(), p);
			}
		}
	}
	
	public void move(){
		Object[] keysToDo = toDo.keySet().toArray();
		for(int i = 0; i<keysToDo.length; i++){
			Pathfinding p = toDo.get(keysToDo[i]);
			Soldat s = p.getSoldat();
			com.richard.knightmare.util.Pathfinding.Pos pos = p.pathfind();
			if(pos==null){
				pathfinding.put(s.getID(), p);
				toDo.remove(keysToDo[i]);
			}else{
				if(trys.get(s.getID())<1 && trys.get(s.getID())>-maxTrys.get(s.getID())){
					toDo.put((Integer) keysToDo[i], new Pathfinding(s, new Pos(pos.x*32+16, pos.y*32 +16)));
				}
				if(trys.get(s.getID())<=-maxTrys.get(s.getID())){
					toDo.remove(s.getID());
				}
			}
			trys.put(s.getID(), trys.get(s.getID())-1);
		}
		
		Object[] keys = pathfinding.keySet().toArray();
		for(int i = 0; i<keys.length;i++){
			if(!pathfinding.get(keys[i]).move()){
				if(pathfinding.get(keys[i]).getFinished()){
					pathfinding.remove(keys[i]);
				}else{
					if(!pathfinding.get(keys[i]).getContinuing()){
						Pathfinding p = new Pathfinding(pathfinding.get(keys[i]).getSoldat(), toChange.get(keys[i]));
						com.richard.knightmare.util.Pathfinding.Pos pos = p.pathfind();
						if(pos==null){
							pathfinding.put((Integer) keys[i],p);
						}else{
							this.trys.put((Integer) keys[i], trys.get(keys[i])-1);
							toDo.put((Integer) keys[i], p);
						}
					}else{
						Soldat s = pathfinding.get(keys[i]).getSoldat();
						Pathfinding p = new Pathfinding(pathfinding.get(keys[i]).getSoldat(), pathfinding.get(keys[i]).getZiel());
						com.richard.knightmare.util.Pathfinding.Pos pos = p.pathfind();
						if(pos==null){
							pathfinding.put(s.getID(),p);
						}else{
							this.trys.put(s.getID(), this.trys.get(s.getID())-1);
							toDo.put(s.getID(), p);
						}
					}
				}
			}
		}
		
		
	}
}
