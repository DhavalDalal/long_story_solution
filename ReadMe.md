# Perfect Portfolios

The team uses Java and Spring Boot stack for their product.  Currently the codebase is using Jackson, a JSON library that is available out-of-box with Spring.  However,  based on the [benchmark results](https://www.harness.io/blog/ultimate-json-library-comparison) when comparing various JSON libraries like - Jackson, JSONP, GSON, JSON.simple, GSON won both benchmarks for big (~180MB size) and small (~80KB size) files when the tests ran on Java 11. GSON processes those files faster by almost 200+ ms before the next candidate.

This product needs to process small and big JSON both, hence migrating to GSON is important for the faster response time of the App.  But alongside this migration, new features are needed to be delivered as customers have been asking for them since long.  Below is a prioritized story backlog for this.

## Iterations
You may want to read [Glossary Of Terms](#Glossary-Of-Terms) used in this domain to familiarize yourself with them.

### Past Iteration
#### Story #1 View all Portfolios
```
As an administrator
I want to see all the portfolios in the system
so that I can use that data for my own analysis. 
```
#### Story #2 View a Portfolio
```
As an administrator 
I want to see an individual portfolio
so that I can get an idea of the details in that portfolio.
```

#### Story #3 View total net worth and individual portfolio net worth
```
As an administrator 
I want to see individual net worth of the portfolios 
and I want to see total net worth across all the portfolios
so that I can get an idea of the individual and total exposure to the markets.
```
### This Iteration
* The stories are already in the prioritized order. 


* There is a dependency of Story #5 on Story #4, hence Story #4 needs to be delivered first.

* Form an ensemble and work on delivering them.


#### Story #4 Track a Portfolio
```
As an administrator 
I want to update an existing portfolio with latest market prices
so that I can find out its current market net worth.
```

#### Story #5  View a Portfolio's Profit/Loss

```
As an administrator 
I want to find out the current PnL situation in a given portfolio
so that I can take necessary steps to reduce losses or increase profits.

Given a portfolio
When I view the PnL
Then I should see the following:
 1. Purchase NetWorth (Purchase Price Networth)
 2. Current NetWorth (Based on Last Market Price)
 3. Profit/Loss (a negative value indicates loss)
```

### Next Iteration

* The stories are already in the prioritized order.

* You may form 2-3 different ensembles, with one working on long-lived change, and the other ensembles working on faster releases of the short stories OR

* You may form 3-4 pairs, with one pair working on long-lived change, and the   remaining pairs working on faster releases of the short stories.

* This iteration totally will be played twice, once for each constraint. So, create 2 forks as there are 2 constraints.  This is because we want to be able to:
	1. Have a common starting point for each and
	2. Compare and contrast the two approaches.


	#### 1. Using Separate Branch Constraint 

	#### Story #6 Migrate to GSON
	You must use a separate branch on which this long story will be developed.  You may or may not use short-lived branches for other stories. After finishing this story  merge it back to main when you are done with that story.
	
	After doing a spike on measuring response times for requests, the team has discovered that the bottleneck is not the database, but the currently used JSON library is not performant, especially for big JSON responses.  Hence the team has decided to migrate to GSON library.

	```
	As an administrator, 
	I want to see a very quick reponse after I make a request to the app for things like portfolios, PnL by portfolios etc... 
	So that I don't have to wait long for portfolios that are quite diversified and big.
	```
	
	You may refer to help on migrating to GSON library in spring Boot and other related articles [here](https://github.com/DhavalDalal/long-story/blob/main/HELP.md#configuring-spring-boot-to-use-gson-instead-of-jackson).
	
	#### Story #7 Cache National Stock Service Calls 
	Due to increasing volume of users and the increase in the cost of making API calls to the National Stock Service, the PO has decided to stop real-time calls to the National Stock service for a stock tick, instead a call is made only once in a day, i.e., after the close of market. 
	
	```
	In order to reduce the costs of making outbound calls for stock ticks 
	And to further increase the response time of the app,
	I want to implement caching.
	```
	
	#### Story #8 Add a new Portfolio
	```
	As an administrator 
	I want to create a portfolio in the system
	so that I can grow the business.
	```
	
	
	#### 2. Using Main Branch Only
	Use the main branch only for all the stories, you are not allowed to create a branch for any stories.
	
	#### Story #6 Migrate to GSON
	After doing a spike on measuring response times for requests, the team has discovered that the bottleneck is not the database, but the currently used JSON library is not performant, especially for big JSON responses.  Hence the team has decided to migrate to GSON library.

	```
	As an administrator, 
	I want to see a very quick reponse after I make a request to the app for things like portfolios, PnL by portfolios etc... 
	So that I don't have to wait long for portfolios that are quite diversified and big.
	```
	
	You may refer to help on migrating to GSON library in spring Boot and other related articles [here](https://github.com/DhavalDalal/long-story/blob/main/HELP.md#configuring-spring-boot-to-use-gson-instead-of-jackson).

	#### Story #7 Cache National Stock Service Calls 
	```
	In order to reduce the costs of making outbound calls for stock ticks 
	And to further increase the response time of the app,
	I want to implement caching.
	```
	
	#### Story #8 Add a new Portfolio
	```
	As an administrator 
	I want to create a portfolio in the system
	so that I can grow the business.
	```

## Glossary Of Terms
1. Stock - is a security that represents the ownership of a fraction of the issuing company. 
2. Holding - Quantity of stocks purchased at a given price.
3. Portfolio - A bucket containing all the stock holdings purchased so far by a customer.
4. Net Worth - is the total monetary value of the stocks in a given portfolio.
5. Tick - is any change in the price of the security, whether that movement is up or down.
6. PnL - Profit and Loss

## (Re-)Generate IDE Specific files
* To generate or regenerate after adding/removing a new dependency, for creating an Eclipse project: use ```gradle cleanEclipse eclipse```
* To generate or regenerate after adding/removing a new dependency, for creating an Idea project: use ```gradle cleanIdea idea```

## Instructions for building and running Perfect Portfolios
Please see [Help](HELP.md)

## Retrospectives
* What do you think about Long-lived branches?
* What was your experience with merges?
* What if, business changes their mind while story is progressing on long-lived branch?

