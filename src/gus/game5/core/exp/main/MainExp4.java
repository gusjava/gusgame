package gus.game5.core.exp.main;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.util.UtilExp;

public class MainExp4 {


	public static void main(String[] args) {
		
		try {
			System.out.println(UtilExp.getDependencies("a + b - c"));
			System.out.println(UtilExp.getDependencies("a ? 2 : b"));
			System.out.println(UtilExp.getDependencies("[a,'a',R].size"));
			System.out.println(UtilExp.getDependencies("a.size"));
			
			Context context = new Context();
			context.addFunction("add", "o+a+pp+p1");
			System.out.println(UtilExp.getDependencies("b.add(r)", context));
			
			
			context.addExpression("a","c");
			context.addExpression("b","a+d");
			
			System.out.println("up a: " + context.getDep().up("a"));
			System.out.println("up b: " + context.getDep().up("b"));
			
			System.out.println("upDeep a: " + context.getDep().upDeep("a"));
			System.out.println("upDeep b: " + context.getDep().upDeep("b"));
			
			System.out.println("missing a: " + context.getDep().missing("a"));
			System.out.println("missing b: " + context.getDep().missing("b"));
			
			System.out.println("down a: " + context.getDep().down("a"));
			System.out.println("down b: " + context.getDep().down("b"));
			System.out.println("down c: " + context.getDep().down("c"));
			System.out.println("down d: " + context.getDep().down("d"));
			
			System.out.println("downDeep a: " + context.getDep().downDeep("a"));
			System.out.println("downDeep b: " + context.getDep().downDeep("b"));
			System.out.println("downDeep c: " + context.getDep().downDeep("c"));
			System.out.println("downDeep d: " + context.getDep().downDeep("d"));
			
		}
		catch (ExpException e) {
			e.printStackTrace();
		}
	}
}
