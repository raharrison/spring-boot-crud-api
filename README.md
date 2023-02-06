## Simple Spring Boot CRUD API

A full Spring Boot CRUD (create, read, update, delete) RESTful API with the least fuss possible. Is Spring Boot the best framework
for this? Who knows - compare this implementation with `framework x` and decide for yourself!

### Key Premises

The word `simple` can quickly get lost, so here is what this project does and doesn't cover:

- Must provide a full CRUD interface for a single entity
  - using the standard RESTful verbs, although it should be easy to change as needed
  - the entity should have a `UUID` primary key and a mix of `String` and `DateTime` properties
- All endpoints should return standard HTTP codes (`200`, `201`, `404`) for common scenarios
- An in-memory database is fine, but should be easy to change out to a 'real' database later
  - this project uses `H2` with Spring Data JPA
- The service should handle database migrations and cater for future schema changes
  - handled by `Liquibase` not JPA here (future-proof)
- The `getAll` endpoint should support full pagination
  - specify the `page` and `size` as query params
- The 'getAll' endpoint should support filtering any entity property through query params
  - e.g. `?name=Ca%` should return all entities with name beginning with `Ca`
  - multiple parameters can be specified to filter by many properties
  - wildcards should be supported (`like` query)
- Full testing suite - not just unit tests
  - integration tests for each endpoint (testing the HTTP layer)
  - tests which run the full database queries on a real database with the full schema loaded (even if in-memory), rather than mocking everywhere

### Endpoints

- `GET /products` - retrieve all saved products
- `GET /products/{id}` - retrieve a specific product by id
- `POST /products` - save a new product
- `PUT /products/{id}` - update a specific product by id
- `DELETE /products/{id}` - delete a specific product by id

### Outcomes/Remarks

A few remarks about using Spring Boot for this use case:

- Spring Boot gets you up and running with a new project extremely quickly - especially with <https://start.spring.io>
- The default autoconfiguration for creating endpoints is easy (no surprises there)
  - it can parse the `Pageable` object from query params easily and maps the entities out-of-the-box
- Choosing a DAO layer is a bit complicated. Do you go with jOOQ, Spring Data JPA or Spring Data JDBC (or others)?
  - `data-jdbc` is lighter-weight than JPA (great!) but doesn't support dynamic queries unless you write the SQL yourself. If you
can deal with only using the repository methods then it's a good choice however (it doesn't support `Criteria` etc.)
  - `jOOQ` provides a really nice api for querying, but the code-generation is painful to set up, and you have to deal with the pagination
side of things yourself. No nice built-in support for `Pageable` like Spring Data has. Producing the dynamic filtering query however is easy
  - `data-jpa` is probably the default heavyweight choice and offers the most flexibility. It has easy support for pagination, but no out-of-the-box
feature for simple filtering on entity attributes. I had to construct my own `Specification` manually which includes hard coding column names (yuck!)
- Built-in support for database migrations with `Liquibase` is really easy - literally just drop the changelogs in the right folder and that's it
- `CrudRepository.deleteById` returns `void` so there is no easy way of determining if you've actually deleted something
  - apparently the solution to this is manually write the `delete` query yourself - no thanks, that is boilerplate the framework is meant to avoid
- Testing is one of Spring Boot's strong points
  - easily test the repository layer with `H2` and running the db migrations to create the right schema
  - test the web layer endpoints with `MockMvc` - although the api for that remains awkward. I prefer to just pull it back to the object model
and write the standard assertions as opposed to using json paths or dodgy Hamcrest matchers
- It's a shame that I still have to use `Lombok` for the entity boilerplate (getters/setters etc). And also for the filter to get a nice builder
- It's Spring Boot, so you know that you can easily extend this like crazy in the future...
