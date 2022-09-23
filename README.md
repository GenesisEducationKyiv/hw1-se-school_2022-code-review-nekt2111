# bitcoin-genesis

To run a project 
1. docker build -t genesis-bitcoin-rate . 
2. docker run -p 8083:8083 genesis-bitcoin-rate 

For now we have two main providers coinbase and coingecko, that you can select via ENV variable in docker file.
Also we have exceptional provider - kucoin. If main provider have problems, we will query it.

Caching feautre can be enbaled or disabled by changing boolean (feature.cache.price.enabled) value in applicaiton.properites file.
