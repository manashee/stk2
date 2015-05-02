#!/usr/bin/env bash
cd /home/ashok/autotrader/Trial1/PositionManager/scripts

 wget -q -U Mozilla -O ../cache/TATASTEEL.xml 'http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=TATASTEEL'

 lp=$(grep "\"lastPrice\":\"" ../cache/TATASTEEL.xml |  perl -ne '/\"lastPrice\":\"(.*?)\"/i; print "$1\n"')

 hi=$(grep "\"highPrice\":\"" ../cache/TATASTEEL.xml |  perl -ne '/\"highPrice\":\"(.*?)\"/i; print "$1\n"')

 lo=$(grep "\"lowPrice\":\"" ../cache/TATASTEEL.xml |  perl -ne '/\"lowPrice\":\"(.*?)\"/i; print "$1\n"')

 open=$(grep "\"openPrice\":\"" ../cache/TATASTEEL.xml |  perl -ne '/\"openPrice\":\"(.*?)\"/i; print "$1\n"')

 echo $lp
 #echo {"open":$open,"high":$hi,"low":$lo,"ltp":$lp}
 #echo {"open":776.50,"high":785.20,"low":772.65,"ltp":781.00}
