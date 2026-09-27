package gus.game5.core.exp.main;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.util.UtilMap;

public class MainExp3 {


	public static void main(String[] args) {
		
		try {
			Context context = new Context();
			
			System.out.println("______________ str.size>3 ...");
			context.addData("str","coucou");
			context.resolve("str.size>3").printData();
			context.resolve("str.size=6").printData();
			context.resolve("str.size>6").printData();

			System.out.println("______________ size>3 ...");
			context.addExpression("size","str.size");
			context.resolve("size>3").printData();
			context.resolve("size=6").printData();
			context.resolve("size>6").printData();

			System.out.println("______________ size>3 ...");
			context.addData("str","coucoucou");
			context.resetAll();
			context.resolve("size>3").printData();
			context.resolve("size=6").printData();
			context.resolve("size>6").printData();

			System.out.println("______________ size=v");
			context.addData("str","abc");
			context.resetAll();
			context.resolveWith("size=v", UtilMap.asMap("v",1)).printData();
			context.resolveWith("size=v", UtilMap.asMap("v",2)).printData();
			context.resolveWith("size=v", UtilMap.asMap("v",3)).printData();
			context.resolveWith("size=v", UtilMap.asMap("v",4)).printData();

			System.out.println("______________ function");
			context.addData("str","abc");
			context.addFunction("func", "o.size>3");
			context.resolveWith("v.func", UtilMap.asMap("v","aaaa")).printData();
			context.resolveWith("v.func", UtilMap.asMap("v","aaa")).printData();
			context.resolveWith("v.func", UtilMap.asMap("v","aa")).printData();

			System.out.println("______________ list size");
			context.resolve("['a','b',3]").printData();
			context.resolve("['a','b',3].size").printData();
			context.resolve("['a']").printData();
			context.resolve("['a'].size").printData();
			context.resolve("[]").printData();
			context.resolve("[].size").printData();
			System.out.println("______________ list #");
			context.resolve("['a','b',3]#0").printData();
			context.resolve("['a','b',3]#1").printData();
			context.resolve("['a','b',3]#2").printData();
			context.resolve("['a','b',3]#(-1)").printData();
			context.resolve("['a','b',3]#(-2)").printData();
			context.resolve("['a','b',3]#(-3)").printData();
			
			System.out.println("______________ has");
			context.resolve("['a','b','c'].has('a')").printData();
			
			System.out.println("______________ find");
			context.resolve("[1,2,3,4].find(o>3)").printData();
			context.resolve("[1,2,3,4].find(o<3 && o>1)").printData();
			
			System.out.println("______________ findAll");
			context.resolve("[1,2,3,4].findAll(o<3)").printData();
			context.resolve("[1,2,3,4].findAll(o<3 || o>3)").printData();
			context.addData("a",2);
			context.resolve("[1,2,3,4].findAll(o<a)").printData();
			
			System.out.println("______________ collect");
			context.resolve("['a','b','c','d'].collect(o.upper)").printData();
			context.addFunction("plusOne", "o+1");
			context.resolve("[1,2,3,4].collect(10)").printData();
			context.resolve("[1,2,3,4].collect(o+1)").printData();
			context.resolve("[1,2,3,4].collect(o.plusOne)").printData();
			
			System.out.println("______________ $size");
			context.resolve("['a','b','c','d']$size").printData();
			context.resolve("['a','b','c','d']$contains('a')").printData();
			context.resolve("['a','b','c','d']$contains('A')").printData();
			
			System.out.println("______________ func o+p1");
			context.addFunction("add", "o+p1");
			context.resolve("15.add(2)").printData();
			
			System.out.println("______________ set");
			context.resolve("{'a','b','c'}").printData();
			context.resolve("{}").printData();
			
			System.out.println("______________ map");
			context.resolve("{'a':'A','b':'B','c':'C'}").printData();
			context.resolve("{:}").printData();
		}
		catch (ExpException e) {
			e.printStackTrace();
		}
	}
}
