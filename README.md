# npb-app

Simple console java application for http://api.nbp.pl/ with some features
. 

## Getting Started

``` 
usage:
 -a <YYYY-MM-DD>                           returns sorted (ascending)
                                           difference between bid and ask
                                           prize for certain day
                                           
 -b <currency>                             for a currency return 2
                                           api.date when currency was the
                                           cheapest and the most expensive
                                           
 -getAverageGoldPrize <YYYY-MM-DD>         returns average gold prize from
                                           a period
                                           
 -getCurrencyPrize <currency_YYYY-MM-DD>   returns currency prize - if
                                           currency contains 2 words they
                                           must be divided with '_'
                                           
 -getGoldPrize <YYYY-MM-DD>                returns gold prize
 
 -getMinBidPrice <YYYY-MM-DD>              returns minimum bid prize for
                                           certain day
                                           
 -getMostVolatileCurrency <YYYY-MM-DD>     returns most volatile currency
                                           from a period // not working yet
                                           
 -help                                     prints this
```

More to find on JavaDoc or in code :>

#### Project uses [simple java library](https://github.com/radekoziol/java-date-library) , [apache options](http://commons.apache.org/proper/commons-cli/javadocs/api-release/org/apache/commons/cli/Options.html), , [apache options](https://sites.google.com/site/gson/)


