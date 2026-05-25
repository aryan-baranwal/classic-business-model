# Classic Business Model Enterprise Application

[![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot%203.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/UI%20Engine-Thymeleaf%203-darkgreen.svg)](https://www.thymeleaf.org)
[![MySQL](https://img.shields.io/badge/Database-MySQL%208.0-blue.svg)](https://www.mysql.com/)
[![Vault](https://img.shields.io/badge/Security-HashiCorp%20Vault-black.svg)](https://www.vaultproject.io/)
[![Deployment Platform](https://img.shields.io/badge/Production%20IP-13.60.34.150%3A8080-orange.svg)](http://13.60.34.150:8080/)

An enterprise-grade ERP and Supply Chain Data Engine built utilizing Spring Boot architecture to manage global operations, employee reporting lines, warehousing, retail pipeline channels, logistics tracking, and transactional analytics. The platform features an internal reporting console, high-performance RESTful APIs, declarative operational guardrails, secure secret management workflows, and an interactive presentation view.

---

## 🌐 Production Environment Access

The monolithic deployment pipeline is exposed and accessible at the following cluster nodes:
* **Application Server Core Dashboard URL:** `http://13.60.34.150:8080/`
* **Database Engine Relational Store Node:** Exposed internally cluster-side on port `3306` mapped to database instance context `classicmodels`.

---

## 🛠 Enterprise Technical Architecture Overview

The system utilizes a modern tiered decoupled layered operational stack ensuring thread safety, ACID standard execution boundaries, transaction processing pipelines, and data abstraction patterns:
              ┌─────────────────────────────────────────┐
              │          Thymeleaf Front-End UI         │
              │   (Interactive Dynamic Dashboards)      │
              └────────────────────┬────────────────────┘
                                   │
                                   ▼
              ┌─────────────────────────────────────────┐
              │       REST Controllers / View Engine    │
              │ (HTTP Traffic Aggregation & API Filters)│
              └────────────────────┬────────────────────┘
                                   │
                                   ▼
              ┌─────────────────────────────────────────┐
              │        Service & Transaction Layer      │
              │     (@Transactional Business Engines)   │
              └────────────┬───────────────────────┬────┘
                           │                       │
                           ▼                       ▼
              ┌─────────────────────────┐ ┌─────────────────────────┐
              │   Data Access Layers    │ │   HashiCorp Vault Node  │
              │ (Custom JPA / Hibernate)│ │(Secure Environment Keys)│
              └────────────┬────────────┘ └─────────────────────────┘
                           │
                           ▼
              ┌─────────────────────────────────────────┐
              │         Relational Storage Layer        │
              │         (MySQL Engine Schema Store)     │
              └─────────────────────────────────────────┘
* **View Presentation Infrastructure:** Thymeleaf template engines coupled with HTML5 boilerplate definitions and integrated layout contexts.
* **Controller Tier:** Spring Web Context processing routing protocols, request payload mappings, path variables parameters validation, and returning JSON/View templates respectively.
* **Service Processing Units:** Explicitly demarcated via Spring Framework’s `@Service` annotation, containing isolation boundaries via declarative transactional controls (`@Transactional`) for multi-entity manipulation pipelines.
* **Persistence Mapping Layer:** Custom JPA Repository implementations abstracting programmatic raw query strings into high-efficiency data model mapping bindings.
* **Data Security Engine:** Integration with HashiCorp Vault abstractions safeguarding transactional operational assets, sensitive internal metadata, API signatures, and persistent data properties files.

---

## 👥 Scrum Team Core Assignments & Module Breakdown

### 🧑‍💻 Aryan — Employee Architecture & Strategic Report Modules
* **Domain Focus:** Relational organizational design tracking multi-national corporate hierarchies, functional cross-team manager structures, executive lines, and business operational structures.
* **Subsystem Responsibilities:** Handling cascading mutations across employee node configurations, enforcing constraint trees during employee transfers, and generating corporate resource performance metrics.
* **Assigned API Endpoints & Core Reports:**
  * `POST /api/employees` — Create and onboard a new employee record.
  * `GET /api/employees` — List all active employees across the corporation.
  * `GET /api/employees/{employeeNumber}` — Fetch absolute data properties for a specific worker.
  * `PUT /api/employees/{employeeNumber}` — Update field configurations or designations of an employee.
  * `GET /api/employees/{employeeNumber}/manager` — Traverse upstream to resolve the assigned supervisor node.
  * `GET /api/employees/{employeeNumber}/subordinates` — Trace downstream to list all direct reportees.
  * `GET /api/reports/sales-by-employee` — High-impact report evaluating total closed revenue per sales representative.

---

### 🧑‍💻 Aman — Office Logistics Infrastructure & Thymeleaf Core UI View
* **Domain Focus:** Geolocation warehouse tracking, localized distribution office properties, and corporate frontend template layouts.
* **Subsystem Responsibilities:** Engineering modular, modern template components utilizing Thymeleaf, rendering uniform enterprise navigation patterns, processing search parameters, and binding HTML forms cleanly.
* **Assigned View Matrices & API Endpoints:**
  * `GET /` — UI Root Landing Page Dashboard (Served via Thymeleaf template views).
  * `GET /api/offices` — Query all active physical corporate office terminals.
  * `GET /api/offices/{officeCode}` — Retrieve structural metadata of a single localized office terminal.
  * `GET /api/offices/{officeCode}/employees` — Isolate and list all corporate personnel mapped to a specific office site.
  * `GET /offices/view` — Render administrative office terminal data control grid view dashboard.

---

### 🧑‍💻 Kushal — Customer Account Contexts & Financial Liquidity Pipelines
* **Domain Focus:** Global distributor profiles, localized contact records, credit limits, and financial ledger transaction pipelines.
* **Subsystem Responsibilities:** Enforcing structural verification rules, writing validation steps over credit allocations, executing database soft-deletes via state parameter flags, and processing data safely over client logs.
* **Assigned API Endpoints:**
  * `POST /api/customers` — Create and onboard a new customer profile.
  * `GET /api/customers` — Retrieve all customers (paginated, sortable).
  * `GET /api/customers/{customerNumber}` — Get customer details by ID.
  * `PUT /api/customers/{customerNumber}` — Update customer profile information.
  * `DELETE /api/customers/{customerNumber}` — Deactivate/soft-delete customer.
  * `GET /api/customers/{customerNumber}/credit-limit` — Fetch customer credit limit.
  * `PUT /api/customers/{customerNumber}/credit-limit` — Update customer credit limit (finance-controlled).
  * `GET /api/customers/search` — Search customers by geography (`?country=&city=`).

---

### 👩‍💻 Dahanvarshini — Product Information Catalog & Inventory Analysis Systems
* **Domain Focus:** Multi-tier inventory classifications, physical sizing specifications, product line categorization, and high-impact macro corporate analytics.
* **Subsystem Responsibilities:** Constructing optimized relational queries to filter massive product catalogs, generating chronological revenue analytical trends, and compiling global performance metrics.
* **Assigned API Endpoints & Analytical Reports:**
  * `GET /api/products` — List all products available inside the inventory system.
  * `GET /api/products/{productCode}` — Retrieve technical specifications of a unique product.
  * `GET /api/product-lines` — List all available overarching product lines.
  * `GET /api/product-lines/{productLine}/products` — Filter catalog items belonging to a particular product category.
  * `GET /api/reports/sales-by-country` — Macro analytical report compiling aggregated sales performance by country.
  * `GET /api/reports/monthly-revenue` — High-impact chronological intelligence identifying the system's monthly revenue trends.

---

### 👩‍💻 Shivani — Order Routing Pipeline & HashiCorp Vault Security Systems
* **Domain Focus:** Composite purchase transaction lifecycles, atomic line-item order calculations, data integration security, and environment-level property orchestration.
* **Subsystem Responsibilities:** Enforcing data-integrity requirements for order submission workflows, handling dynamic order state variables (`Shipped`, `Cancelled`, `Resolved`), managing individual order details breakdowns, and integrating a secure HashiCorp Vault environment to abstract operational keys and database credentials safely.
* **Assigned API Endpoints & Operational Reports:**
  * `POST /api/orders` — Instantiate a new purchase order lifecycle.
  * `PATCH /api/orders/{orderNumber}/status` — Dynamically update order status parameters.
  * `GET /api/customers/{customerNumber}/orders` — Extract complete order history data structures rows.
  * `GET /api/orders/search` — Multi-conditional search tool to track orders by status and date range (`?status=&fromDate=&toDate=`).
  * `POST /api/orders/{orderNumber}/items` — Inject a new product/line-item into an existing order structure.
  * `GET /api/orders/{orderNumber}/items` — List detailed line-items breakdown for a target order number.
  * `PUT /api/orders/{orderNumber}/items/{productCode}` — Update an existing line item's ordered quantity or active price.
  * `DELETE /api/orders/{orderNumber}/items/{productCode}` — Remove a line item safely from an order structure.
  * `GET /api/reports/customer-exposure` — Analytic compliance report contrasting customer-wise total order value vs credit limit.
  * `GET /api/reports/order-value/{orderNumber}` — Perform real-time mathematical aggregation of an order's total pricing structure.
  * `GET /api/reports/high-risk-customers` — Identify and flag customers exceeding or dangerously nearing credit limit boundaries.

---

## 🗄 Relational Data Model Architecture & Schema Enforcements

The fundamental state storage cluster is governed by eight interconnected core structures configured via relational database management conventions. The core constraints layout behaves exactly as specified within the underlying production deployment schema:

┌─────────────────┐             ┌─────────────────┐             ┌─────────────────┐
│     offices     │             │    employees    │             │    customers    │
├─────────────────┤             ├─────────────────┤             ├─────────────────┤
│ officeCode (PK) │◄───────────┼│ employeeNo (PK) │◄───────────┼│ customerNo (PK) │
│ city            │             │ lastName        │             │ customerName    │
│ phone           │             │ firstName       │             │ creditLimit     │
│ ...             │             │ reportsTo (FK)  │             │ salesRepER(FK)  │
└─────────────────┘             └─────────────────┘             └────────┬────────┘
│
┌─────────────────┐                      │
│    payments     │                      │
├─────────────────┤                      │
│ customerNo(PKFK)│◄─────────────────────┤
│ checkNumber(PK) │                      │
│ paymentDate     │                      │
│ amount          │                      │
└─────────────────┘                      │
▼
┌─────────────────┐             ┌─────────────────┐             ┌─────────────────┐
│  productlines   │             │    products     │             │     orders      │
├─────────────────┤             ├─────────────────┤             ├─────────────────┤
│ productLine(PK) │◄───────────┼│ productCode(PK) │             │ orderNumber(PK) │
│ textDescription │             │ productName     │             │ orderDate       │
│ ...             │             │ productLine(FK) │             │ customerNo (FK) │
└─────────────────┘             └────────┬────────┘             └────────┬────────┘
│                               │
│    ┌──────────────────────┐   │
│    │     orderdetails     │   │
│    ├──────────────────────┤   │
└───┼│ orderNumber (PK)(FK) │◄──┘
     │ productCode (PK)(FK) │
     | quantityOrdered      │
     │ priceEach            │
     └──────────────────────┘

### Constraint Definitions Matrix
1.  **`offices` Table:** Primary Key (`officeCode`). Core geographic terminal mapping table.
2.  **`employees` Table:** Primary Key (`employeeNumber`). Self-referencing structural constraint mapping via `reportsTo` tracking standard relational corporate trees. Foreign Key link to `offices` table on tracking field `officeCode`.
3.  **`customers` Table:** Primary Key (`customerNumber`). Maintains relational field index pointing towards corresponding service representatives via column variable `salesRepEmployeeNumber` pointing towards `employees(employeeNumber)`.
4.  **`payments` Table:** Compound Key composite layout tracking combinations of variables `customerNumber` along with transactional check string value `checkNumber`. Connects seamlessly to matching customer identities via `customerNumber`.
5.  **`productlines` Table:** Primary Key (`productLine`). Top-tier indexing anchor catalog categorization data structure.
6.  **`products` Table:** Primary Key (`productCode`). Integrates upstream matching categories through parameter data property link `productLine` referencing mapping configuration target row entry `productlines(productLine)`.
7.  **`orders` Table:** Primary Key (`orderNumber`). Relational binding trace tracker variable column row entry `customerNumber` mapping back directly towards original purchaser entry via configuration identifier index `customers(customerNumber)`.
8.  **`orderdetails` Table:** Highly structured atomic mapping linking entries via compound combination fields `orderNumber` coupled to secondary attribute field entry parameter `productCode`. Bridges mapping linkages across two discrete parent structures simultaneously via structural foreign key constraints referencing `orders(orderNumber)` as well as `products(productCode)`.

---

# Project Core Contributor Registry

* Aryan (Admin)
* Aman
* Kushal
* Dahanvarshini
* Shivani


