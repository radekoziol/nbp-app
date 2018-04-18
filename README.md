# npb-app

Simple console java application for http://api.nbp.pl/ with some features
. 

## Getting Started

``` 
usage:
 -a <YYYY-MM-DD>                           prints sorted (ascending)
                                           difference between bid and ask
                                           prize for certain day
                                           
 -b <currency>                             for a currency prints dates
                                           when currency was the
                                           cheapest and the most expensive
                                           
 -getAverageGoldPrize <YYYY-MM-DD>         prints average gold prize from
                                           a period
                                           
 -getCurrencyPrize <currency> <YYYY-MM-DD> prints currency prize for given date
                                            
 -getGoldPrize <YYYY-MM-DD>                prints gold prize
 
 -getMinBidPrice <YYYY-MM-DD>              prints minimum bid prize for
                                           certain day
                                           
 -getMostVolatileCurrency 2x <YYYY-MM-DD>  prints most volatile currency
                                           from a period 
                                           
 -help                                     prints this
```

#### Project uses [simple java library](https://github.com/radekoziol/java-date-library) , [apache options](http://commons.apache.org/proper/commons-cli/javadocs/api-release/org/apache/commons/cli/Options.html), , [apache options](https://sites.google.com/site/gson/)


