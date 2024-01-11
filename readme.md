# Online Book Store
The application was designed to automize the processes of the online book store, which include the following:
* managing the inventory of available books and categories (adding new items, removing outdated ones)
* populating shopping carts (with cart items) and completing orders based on their contents
* managing the user's orders (from admins prospective)
## Technologies used
* Java
* Maven
* Spring Boot
* Spring Data JPA
* Spring Boot Security
* Lombok
* Mapstruct
* Hibernate
* Mockito
* JUnit 5
* Mysql
* Liquibase
* Docker
* Swagger
## Examples of endpoints
### Authentication controller
* User registration (returns userdto) — POST:/api/auth/register
```json
{
  "email": "john@example.com",
  "password": "12345678",
  "repeatPassword": "12345678",
  "firstName": "John",
  "lastName": "Doe",
  "shippingAddress": "123 Main St, City1, Country1"
}
```
* User login (returns JWT token) — POST:/auth/login
```json
{
  "email": "john@example.com",
  "password": "12345678"
}
```
### Book controller
* Get a list of all available books (requires being logged in as user/admin, returns a pageable list of bookdtos) — GET:/api/books
* Find book by its id (requires being logged in as user/admin, returns a bookdto of a book with id) — GET:/api/books/{id}
* Save book to the inventory (requires being logged in as admin, returns a bookdto of a created book) — POST:/api/books
```json
{
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "isbn": "978-3-1136-5552-7",
  "price": 14.99,
  "description": "A novel about American dream",
  "coverImage": "the-great-gatsby.jpg",
  "categoryIds": [1, 2]
}
```
* Update an existing book by id (requires being logged in as admin, uses and returns the same dto as the save method) — PUT:/api/books/{id}
* Delete a book by its id (requires being logged in as admin, soft delete of the book from the inventory) — DELETE:/api/books/{id}
### Category controller
* Get a list of all available categories (requires being logged in as user/admin, returns a pageable list of categorydtos) — GET:/api/categories
* find category by its id (requires being logged in as user/admin, returns a categorydto of a category with id) — GET:/api/categories/{id}
* Save category (requires being logged in as admin, returns a categorydto of a created category) — POST:/api/categories
```json
{
  "name": "Novel",
  "description": "Very interesting"
}
```
* Update an existing category by id (requires being logged in as admin, uses and returns the same dto as the save method) — PUT:/api/categories/{id}
* Delete a category by its id (requires being logged in as admin, soft delete of the category from the inventory) — DELETE:/api/categories/{id}
### Order controller
* Get all orders (requires being logged in as a user, returns a list of all the orderdtos of the logged-in user) — GET:/api/orders
* Create an order (requires being logged in as a user, returns order that was created based on the shopping cart of logged-in user) — POST:/api/orders
```json
{
  "shippingAddress": "Baker street 221B"
}
```
* Update order status (requires being logged in as admin, returns the orderdto with the updated status) — PATCH:/api/orders/{id}
```json
{
  "status": "COMPLETED"
}
```
* Get order items by order id (requires being logged in as a user, returns the list of order items of the order with the id) — GET:/api/orders/{orderId}
* Get a certain order items by order and item id (requires being logged in as a user, returns an order item dto which matches order and item ids) — GET:/{orderId}/items/{itemId}
### Shopping cart Controller
* Add a cart item to Shopping cart (requires being logged in as a user, returns an updated shopping cart that includes a newly-added item) — POST:/api/cart
```json
{
  "bookId": 1,
  "quantity": 2
}
```
* Get a shopping cart with items (requires being logged in as a user, returns a shopping cart dto of the logged-in user) — GET:/api/cart
* Delete a cart item from a shopping cart (requires being logged in as a user, removes the item from the users shopping cart) — DELETE:/api/cart/cart-items/{cartItemId}
* Update a quantity of books in a shopping cart (requires being logged in as a user, returns the shopping cart dto with the updated book quantity) — PUT/api/cart/cart-items/{cartItemId}
```json
{
  "quantity": 3
}
```
## Project Structure
* **model**: Entity models representing the database schema
* **repository**: Spring Data JPA repositories for database operations
* **service**: Implementation of the business logic
* **controller**: Controllers containing endpoints with methods for handling HTTP requests
* **dto**: Data Transfer Objects used for communication between the client and server
* **mapper**: Mapper interfaces used for conversions between DTOs and entity models