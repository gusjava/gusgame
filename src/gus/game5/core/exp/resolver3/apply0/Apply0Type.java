package gus.game5.core.exp.resolver3.apply0;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.resolver.ResolverResult.Type;
import gus.game5.core.exp.resolver3.apply.Apply0;
import gus.game5.core.exp.token.TokenSequence;

public class Apply0Type extends Apply0 {

	public Object apply(TokenSequence sequence, Context context, Object data) throws ExpException {
		if(data==null) return Type.NULL.toString();
		if(data instanceof Type) return Type.STRING;

		if(data instanceof String) return Type.STRING.toString();
		if(data instanceof Boolean) return Type.BOOLEAN.toString();
		if(data instanceof Date) return Type.DATE.toString();
		if(data instanceof Integer) return Type.INTEGER.toString();
		if(data instanceof Double) return Type.DOUBLE.toString();
		if(data instanceof List) return Type.LIST.toString();
		if(data instanceof Set) return Type.SET.toString();
		if(data instanceof Map) return Type.MAP.toString();
		
		return Type.OBJECT.toString();
	}
}
