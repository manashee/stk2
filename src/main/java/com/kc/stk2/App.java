package com.kc.stk2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        App app = new App();        
        System.out.println( "AutoTrader v1.0" );
        for (String s : args)
        {
            System.out.print(s + " ");            
        }
        System.out.println( ".." );
        
        if ( 5 != args.length) {
            System.out.println("Usage: AutoTrader <TraderName> <StockName> <BuyPrice> <SellPrice> <Quantity> ");
            return;
        }        
                
        String traderName = args[0];
        String stockName = args[1];
        double buyPrice = app.parseDouble(args[2]);        
        double sellPrice = app.parseDouble(args[3]);        
        long qty = Long.parseLong(args[4]);

        while (true)
        {
            // if (isMarketOpen())
            {            
                double ltp = app.scrape(stockName);
                System.out.println("[stockName] = " + stockName + "[LTP] = " + ltp);	
                
                if ( Double.compare (ltp, buyPrice + 1) <= 0 ) {
                    System.out.println("Very close to Buying.. [stockName] = " + stockName + " [ltp] = " + ltp + " [buyPrice] = " + buyPrice );
                    
                    if ( Double.compare (ltp, buyPrice) <= 0 )
                    {
                        String trade = app.createTrade(traderName, stockName, buyPrice, true, qty) ; 
                        System.out.println(trade);                        
                    } 
                }
                else if ( Double.compare (ltp, sellPrice - 1) >= 0 ) {
                    System.out.println("Very close to Selling.. [stockName] = " + stockName + " [ltp] = " + ltp + " [sellPrice] = " + sellPrice );
                    
                    if ( Double.compare (ltp, sellPrice) >= 0 ) 
                    {
                        String trade = app.createTrade(traderName, stockName, sellPrice, false, qty) ; 
                        System.out.println(trade);                        
                    }
                }
                                
                Thread.sleep(6000);
            }
        }
            
	   
}
    
public double scrape(String stockName) throws Exception {    	

    double price = 0.0;
    
	//System.out.println("scrape: " + " name = [" + stockName + "]" );		
				
    try {
        String line;

        // TODO: Fix the hardcoding
        Process p = Runtime.getRuntime().exec( "/home/ashok/apps/anp/stk2/AT1/scripts/" + stockName + ".sh");
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        line = in.readLine();
        if ( line != null) {
            price = parseDouble(line);
        }

        in.close();

    } catch (Exception e) {

    }
    return price;
}


public double parseDouble(String price) throws Exception {    	
    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
    format.setParseBigDecimal(true);
	BigDecimal number = (BigDecimal) format.parse(price);
    return number.doubleValue();
}

    
public String createTrade(String traderName, String stockName, double buyPrice, boolean isBuy , long qty) {
    return new String 
        ("New Trade: [traderName] = " + traderName + 
         " [stockName] " + stockName + 
         " [ Buy/Sell ] " + ( isBuy ? "Buy" : "Sell" ) + 
         " [buyPrice] " + buyPrice + 
         " [ Qty ] " + qty);    
}
    
}         
            
