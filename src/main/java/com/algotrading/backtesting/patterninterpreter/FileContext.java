package com.algotrading.backtesting.patterninterpreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileContext extends Context{

    FileContext(String filename) throws IOException {
        List<String> tokenList = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(
                                     new FileReader(filename));
        String input = null;
        while((input = reader.readLine()) != null) {
            for(String token : input.trim().split("\\s+")) {
                tokenList.add(token);
            }
        }
        reader.close();
        tokens = tokenList.iterator();
        nextToken();
    }
	
}