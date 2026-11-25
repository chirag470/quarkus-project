# Questions

Here we have 3 questions related to the code base for you to answer. It is not about right or wrong, but more about what's the reasoning behind your decisions.

1. In this code base, we have some different implementation strategies when it comes to database access layer and manipulation. If you would maintain this code base, would you refactor any of those? Why?

**Answer:**
```txt
Yes, the implementation used for Warehouse part is the most appropriate among all. It clearly separates the Resource layer, the business layer and the DAO layer. 
Also it clearly defines the ports and adapters, which is a good practice as per hexagonal architecture. The other parts of the code like store, location are not as well written.
THere is no separation of concerns and modularity. Business logic also depends on an interface in the form of ports. 
The actualimplementation is part of adapters, which can be changed in future in case of DB migration. 
```
----
2. When it comes to API spec and endpoints handlers, we have an Open API yaml file for the `Warehouse` API from which we generate code, but for the other endpoints - `Product` and `Store` - we just coded directly everything. What would be your thoughts about what are the pros and cons of each approach and what would be your choice?

**Answer:**
```
Pros of OpenApi spec code generation:
- It provides a standar contract for the Api, making it easier for developers to understand, use and make changes. Validation logic like null checks and empty checks can be generated automatically, without needing to write the logic.
- It speeds up development by generating boilerplate code automatically.
- The contract spec is machine readable, so it can help in creating swagger documentation and UI. It can alsohelp create SDK if needed for other service.

Cons of OpenAPI spec and code generation:
- It requires additional setup and tool to generate code from the spec.
- It can lead to less flexibility in the implementation, as changes to the Api may require updates to
    the spec and regeneration of code.
- The code and test cycle also becomes slow because of the need to regenerate code after every change in the spec.

```
----
3. Given the need to balance thorough testing with time and resource constraints, how would you prioritize and implement tests for this project? Which types of tests would you focus on, and how would you ensure test coverage remains effective over time?
 
**Answer:**
```txt
 I would try to cover the most critical parts of the code first, like the business logic and the API endpoints. I would focus on unit tests for the business logic and integration tests for the API endpoints. I would keep the code coverage checks as part of the CI in github. But this will serve only as an indication/codebase health signal but not a blocker for deployment. Instead of writing tests for every single line of code just for targetting coverage mark, I would focus on covering tests for the most important and critical parts of the codebase and edge cases.
  A TDD approach can also help for critical pieces.
  As low priority tasks, tickets can be picked up for meeting the coverage mark criteria from time to time.
    
```

```
 ** Few other points for refactoring **:
1. The package sturcture is not separating models, db operations, resources properly. Currently adapters contains the restapi package which is wrong place. 
All rest operations should be in separate package. Also, domain package contains the usecase implementations, which is not correct.
Usecases should be in separate package. Domain package should contain only the models and ports.
2. Location is stored as a static list. It should be stored in DB.
3. Access modifiers in many places are public like Store, Location. They should be private along with getters,setters. 
 