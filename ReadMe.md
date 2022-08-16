# Perfect Portfolios

The team uses Java and Spring Boot stack for their product.  Currently the codebase is using Jackson, a JSON library that is available out-of-box with Spring.  However,  based on the [benchmark results](https://www.overops.com/blog/the-ultimate-json-library-json-simple-vs-gson-vs-jackson-vs-json/) when comparing various JSON libraries like - Jackson, JSONP, GSON, JSON.simple, GSON won both benchmarks for big (~180MB size) and small (~80KB size) files when the tests ran on Java 11. GSON processes those files faster by almost 200+ ms before the next candidate.

This product needs to process small and big JSON both, hence migrating to GSON is important for the faster response time of the App.  But alongside this migration, new features are needed to be delivered as customers have been asking for them since long.  Below is a prioritized story backlog for this.

## Iterations and Stories

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
#### Story #4 Track a Portfolio
```
As an administrator 
I want to update existing portfolio with latest market prices
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
 2. Current NetWorth (Based on Current Market Price)
 3. Profit/Loss (a negative value indicates loss)
```

### Next Iteration
At this point, create 2 forks as there are 2 constraints and we want to be able to have:
1. Common starting point for each
2. Compare and contrast the two approaches

### Next Iteration Using Separate Branch Constraint 

#### Story #5 Migrate to GSON
You must use a separate branch on which this long story will be developed.  You may or may not use short-lived branches for other stories. After finishing this story  merge it back to main when you are done with that story.

```

```

#### Story #6 Cache National Stock Service Calls 

```

```


### Next Iteration Using Main Branch Only
Use the main branch only for all the stories, you are not allowed to create a branch for any stories.

#### Story #5 Migrate to GSON
```

```


#### Story #6 Cache National Stock Service Calls 
```

```

## How do we collaborate on stories?
* You may form 2-3 different ensembles, with one working on long-lived change, and the other ensembles working on faster releases of the short stories or

* You may form 3-4 pairs, with one pair working on long-lived change, and the   remaining pairs working on faster releases of the short stories.

## Build 
* To build, simply run ```gradle clean build```

## (Re-)Generate IDE Specific files
* To generate Eclipse project: use ```gradle cleanEclipse eclipse```
* To generate an Idea project: use ```gradle cleanIdea idea```

## Instructions for running Perfect Portfolios
Please see [Help](HELP.md)

## Retrospectives
* What do you think about Long-lived branches?
* What was your experience with merges?
* What if, business changes their mind while story is progressing on long-lived branch?

