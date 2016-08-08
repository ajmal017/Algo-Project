package com.algotrading.backtesting.patterninterperter;

import java.util.ArrayList;
import java.util.List;

import com.algotrading.backtesting.pattern.StockSignal;
import com.algotrading.backtesting.pattern.SeSignal;

public class Se implements Node {
	private String name;
	private List<String> list = new ArrayList<String>();

	public void parse(Context context) throws ParseException {
		while (true) {
			if (context.currentToken() == null) {
				throw new ParseException();
			} else if (context.currentToken().equals("SE[")) {
				context.skipToken("SE[");
			} else if (context.currentToken().equals("]")) {
				context.skipToken("]");
				break;
			} else {
				name = context.currentToken();
				context.skipToken(name);
//				list.add(name);
			}
		}
	}

	public StockSignal execute() {
//		StringBuffer sb = new StringBuffer(50);
//		sb.append("SE[ ");
//		for (String l : list) {
//			sb.append(l).append(" ");
//		}
//		sb.append("]");
//		return sb.toString();
//		return null;
		return new SeSignal(name);
	}
}
