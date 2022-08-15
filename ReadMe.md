# Long Story

The team uses Java and Spring Boot stack for their product.  Currently the codebase is using Jackson, a JSON library that is available out-of-box with Spring.  However,  based on the [benchmark results](https://www.overops.com/blog/the-ultimate-json-library-json-simple-vs-gson-vs-jackson-vs-json/) when comparing various JSON libraries like - Jackson, JSONP, GSON, JSON.simple, GSON won both benchmarks for big (~180MB size) and small (~80KB size) files when the tests ran on Java 11. GSON processes those files faster by almost 200+ ms before the next candidate.

This product needs to process small and big JSON both, hence migrating to GSON is important for the faster response time of the App.  But alongside this migration, new features are needed to be delivered as customers have been asking for them since long.  Below is a prioritized story backlog for this.

## Stories
#### Story #1 View All Portfolios
As an administrator
I want to see all the portfolios in the system
so that I can use that data for my own analysis. 

#### Story #2 Show net worth of individual portfolios along with total net worth
As an administrator 
I want to see individual net worth of all the portfolios along with total net worth 
so that I can get an idea of the individual and total exposure to the markets

#### Story #3  Track a Portfolio


#### Story #4 Migrate to GSON


#### Story #5 Apply Caching for Rate Limiting National Stock Service Calls 


## How do we collaborate on stories?
* You may form 2-3 different ensembles, with one working on long-lived change, and the other ensembles working on faster releases of the short stories or

* You may form 3-4 pairs, with one pair working on long-lived change, and the   remaining pairs working on faster releases of the short stories.

## What are the Constraints?
You will solve the same problem once with each constraint.  So you may have to clone this project twice for each constraint.

### Constraint #1 - Must use a branch for Replace Jackson story
You must use an SCM branch on which this long story will be developed.  You may or may not use short-lived branches for other stories and then merge it back to main when you are done with that story.

### Constraint #2 - Must use Main Branch Only for all stories
Use the main branch only for all the stories, you are not allowed to create a branch for any stories.

## Instructions for running Perfect Portfolios
Please see [Help](HELP.md)

## Retrospectives
* What do you think about Long-lived branches?
* What was your experience with merges?
* What if, business changes their mind while story is progressing on long-lived branch?

