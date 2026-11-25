# Case Study Scenarios to discuss

## Scenario 1: Cost Allocation and Tracking
**Situation**: The company needs to track and allocate costs accurately across different Warehouses and Stores. The costs include labor, inventory, transportation, and overhead expenses.

**Task**: Discuss the challenges in accurately tracking and allocating costs in a fulfillment environment. Think about what are important considerations for this, what are previous experiences that you have you could related to this problem and elaborate some questions and considerations

**Questions you may have and considerations:**

Important points for cost allocation:
1. All steps/resource in the operations should be broken down into substeps and accounted for in the final costs and spends.
2. Costs should be allocated using appropriate factors such as labor hours, shipment weight, fuel cost, distance between stores, storage space,,rents/leases or order volume.
3. Inventory transfers between warehouses and stores should not create duplicate costs. Trips delivering to multiple locations should not be double counted.
4. A warehouse may serve multiple stores, and a store may receive products from multiple warehouses. We need to understand the actual flow of goods.
5. Transportation rates, labor costs, inventory prices, and warehouse locations can change over time. Historical costs should remain accurate. Rates, wage charges, prices should be decoupled from the actual transactions. Easy update of these should happen. 
6. Account for missing trip details, incorrect quantities, duplicate invoices, damages and spillages, missing labor records, inaccurate inventory records. This can lead to incorrect cost calculations.


Overall approach: First identify all cost categories, all entities where cost will be consumed then determine where each cost originates from. Then establish fair and consistent allocation criteria, and ensure that the total costs are accurately reconciled later.

Few Questions:--
1. Can trip and Purchase order have many to many mapping? As in can a trip deliver to more than one store. Also can a PO be delivered through multiple trips over days? 
2. How will the cost be segregated among different stores/warehouses in case of shared resources like fleet, trips, labour, equipments.
3.Are there multiple timezones and currencies in picture?


 
## Scenario 2: Cost Optimization Strategies
**Situation**: The company wants to identify and implement cost optimization strategies for its fulfillment operations. The goal is to reduce overall costs without compromising service quality.

**Task**: Discuss potential cost optimization strategies for fulfillment operations and expected outcomes from that. How would you identify, prioritize and implement these strategies?

**Questions you may have and considerations:**

**Answer**: 
Areas of optimization:
1. Optimize warehouse locations and reduce extra warehouse capacity, as to lower rent and maintenance costs.
2. Optimize delivery routes (traveling salesman problem), combine multiple stores in lesser trips, have varied sized fleet. This will help lower fuel and transportation costs. Store-warehouse allocation can be optimized to fulfill orders from the most cost-effective and nearest warehouse	
3. Improve demand forecasting stores and inventory distribution. This will lower storage costs and less excess inventory
4. Keep track and care of packaging so that damages are less. 
5. Optimize staffing based on workload and demand.

Define metrics for stores and warehouses to track the operations efficiency. 
Example: Cost per purchase order fulfillment,
		 Cost per unit shipped
		 Transportation cost per shipment
		 Warehouse operating cost per month
		 Average delivery time
		 Per labor cost 
		 Damage losses per day

Regularly track the metrics for all stores/warehouses along with adopting optimization strategies, in order to measure the effectiveness of the strategies. What works and what doesnt. Measure percentage savings per strategy. 


## Scenario 3: Integration with Financial Systems
**Situation**: The Cost Control Tool needs to integrate with existing financial systems to ensure accurate and timely cost data. The integration should support real-time data synchronization and reporting.

**Task**: Discuss the importance of integrating the Cost Control Tool with financial systems. What benefits the company would have from that and how would you ensure seamless integration and data synchronization?

**Questions you may have and considerations:**

**Answer** 
Importance of integration and benefits:
1. Accurate cost tracking: Actual expenses such as labor, transportation, inventory and overhead can be obtained from financial systems rather than relying on manually entered data.
2. Better reporting and decision making: Provides a consolidated view of costs across warehouses, stores, products, trips, staff and time periods. Cleanand pivoted reports can give finance teams clear view of how much spend is happening across different entities.  This can help in budgeting.
3.Timely reporting: Integration can help in tracking and monitoring near real time expenses .
4. Reconciliation: Allocated fulfillment costs can be compared with actual expenses recorded in the accounting system.
5. Reduced manual work: Eliminates manual data entries, and errors caused by manual transfers.
6. Cost optimization: Helps identify cost overruns and compare actual spending against budgets.

How to ensure seamless integration and synchronization:
1. Establish data mapping of financial records to the relevant warehouses, stores, cost categories and business units. Ensure clear andstandard identifiers 
3. Use event-based integration or APIs with retries and error reporting for timely updates, along wtith by scheduled batch synchronization for reconciliation and reporting.
4. Handle duplicate events, failed transactions and out-of-order updates. Use idempotency keys. THis wil maintain consistency
5. Track synchronization delays, failures, missing records and discrepancies between systems.
6. Support reconciliation: Periodically compare the total costs received from financial systems with the costs recorded and allocated by the Cost Control Tool

Few Questions:---
- How much real time data is needed in the financial systems. Is it perfect real time, T+1 day, T+1 week? 
-Are there multiple timezones and currencies in picture?
- What are the entities present in the finalcial systems. This is needed to accuratly map and sumup the costs from the operations system to the financial system. 

## Scenario 4: Budgeting and Forecasting
**Situation**: The company needs to develop budgeting and forecasting capabilities for its fulfillment operations. The goal is to predict future costs and allocate resources effectively.

**Task**: Discuss the importance of budgeting and forecasting in fulfillment operations and what would you take into account designing a system to support accurate budgeting and forecasting?

**Questions you may have and considerations:**

**Answer**
Importance of budgeting and forecasting: 
1. Helps the company estimate future expenses and costallocations for warehouses, stores, labor, inventory and transportation.
2. Helps determine how much manpower, warehouse capacity and transportation fleets, etc will be required in coming months/years.
3. Allows leadership to compare actual expenses against cost allocations and identify overspending, improving cost models,cost optimizations. 
4. Helps leadership to determine future demands of products, labour and other resources.
5. Drive key decisions like opening new warehouses, archiving existing ones, shifting locations, changing distribution routes in order to reduce cost.

Things take into account designing a system to support accurate budgeting and forecasting:
1. Historical data relating to orders, fulfillments, damages, trips, over spends.
2. Data quering methods and systems in order to drive machine learning models
3. Future expected changes in market entities like fuel prices, labour wages, warehouse rents, government policies. 
4. Rate of business growth currently and in coming months in order to predict resource requirements. 
   Seasonal variations in demand and supply.
5. Cost allocation vs the actual expenditure to find areas of over spending, damages, cost modeling corrections.  

 
## Scenario 5: Cost Control in Warehouse Replacement
**Situation**: The company is planning to replace an existing Warehouse with a new one. The new Warehouse will reuse the Business Unit Code of the old Warehouse. The old Warehouse will be archived, but its cost history must be preserved.

**Task**: Discuss the cost control aspects of replacing a Warehouse. Why is it important to preserve cost history and how this relates to keeping the new Warehouse operation within budget?

**Questions you may have and considerations:**

**Answer**
Cost control aspects of replacing warehouse:
1. Relocation and transportation of inventory.
2. New warehouse setup, govt procedures, lease aggreeents and infrastructure.
3. Employee relocation or re-recruitment.
4. Temporary overlapping operational costs.

Why is preserving cost history important?
1. Allows the company to understand how much it previously spent on operating the old warehouse, including rent, labor, transportation and overhead
2. Historical expenses provide a refernce for estimating the new warehouse's operating budget.
3. Archived cost records are important for financial reportingon year end, audits and investigating past expenses.
4. Helps compare the operational costs of the old and new warehouses and determine whether the replacement has improved cost or not.


## Instructions for Candidates
Before starting the case study, read the [BRIEFING.md](BRIEFING.md) to quickly understand the domain, entities, business rules, and other relevant details.

**Analyze the Scenarios**: Carefully analyze each scenario and consider the tasks provided. To make informed decisions about the project's scope and ensure valuable outcomes, what key information would you seek to gather before defining the boundaries of the work? Your goal is to bridge technical aspects with business value, bringing a high level discussion; no need to deep dive.
