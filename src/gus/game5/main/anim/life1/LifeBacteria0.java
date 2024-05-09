package gus.game5.main.anim.life1;

import static gus.game5.main.anim.life1.LifeConst.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.features.p.P;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.control.ShapeControl;
import gus.game5.core.shape.control.ShapeControlBorderRebound;
import gus.game5.core.util.UtilDisplay;
import gus.game5.core.util.UtilRandom;

public abstract class LifeBacteria0 extends Game1 {
	
	protected abstract List<P<BacteriaType>> typeCustList();
	
	protected void initSettings(Settings s) {
		s.setTitle("Bacteria simulation 1");
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(GAME_SPEED);
		s.setBackground(Color.BLACK);
	}
	
	private ShapeList<Food> foodList;
	private ShapeList<Bacteria> bacteriaList;
	private List<BacteriaType> types;
	private ShapeControl borderRebound;
	
	protected void initialize1() {
		foodList = newShapeList();
		bacteriaList = newShapeList();
		types = new ArrayList<>();
		borderRebound = new ShapeControlBorderRebound(this);
		
		// initialize types
		for(P<BacteriaType> cust : typeCustList()) {
			BacteriaType type = new BacteriaType(this);
			cust.p(type);
			types.add(type);
		}
		
		// initialize bacteria
		for(BacteriaType type : types) {
			bacteriaList.addAll(type.generateNew());
		}
		
		newDrawingText(Color.WHITE, p1(5,15), ()-> "Foods : " + foodList.size());
		newDrawingText(Color.WHITE, p1(5,30), ()-> "Bacteria : " + bacteriaList.size());
		newDrawingText(Color.WHITE, p1(5,45), ()-> "Energy : " + UtilDisplay.dec2(bacteriaList.sumDouble(Bacteria::getEnergy)));
		for(int i=0;i<types.size();i++) {
			BacteriaType type = types.get(i);
			newDrawingText(type.getColor(), p1(5,60+15*i), type::getDescription);
		}
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		processFoodEating();
		processBacteriaActivity();
		processBacteriaSpawning();
		processFoodGeneration();
	}
	
	
	private void processFoodEating() {
		for(Food food : foodList) {
			List<Bacteria> eatingBacteria = bacteriaList.findAllInter(food);
			if(eatingBacteria.isEmpty()) continue;
			
			double gain = food.getEnergy() / eatingBacteria.size();
			eatingBacteria.forEach(b->b.gainEnergy(gain));
			food.setOver(true);
		}
		foodList.clean();
	}
	
	private void processFoodGeneration() {
		if(getCount()%FOOD_RATE==0) {
			foodList.add(new Food(UtilRandom.randomPoint1(this)));
		}
	}
	
	private void processBacteriaActivity() {
		bacteriaList.goNext();
		bacteriaList.clean();
		bacteriaList.control(borderRebound);
	}
	
	private void processBacteriaSpawning() {
		List<Bacteria> spawns = new ArrayList<>();
		for(Bacteria bacteria : bacteriaList) {
			Bacteria spawn = bacteria.generateSpawn();
			if(spawn!=null) spawns.add(spawn);
		}
		bacteriaList.addAll(spawns);
	}
}
