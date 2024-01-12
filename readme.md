# Online Book Store
The application was designed to automize the processes of the online book store, which include the following:
* managing the inventory of available books and categories (adding new items, removing outdated ones)
* populating shopping carts (with cart items) and completing orders based on their contents
* managing the user's orders (from admins prospective)
## Technologies used
Core Technologies:
* Java
* Maven (Build tool)
### Spring Framework:
* Spring Boot
* Spring Data JPA
* Spring Boot Security
* Lombok
* Mapstruct
### Database and Persistence:
* Hibernate
* MySQL
* Liquibase
### Testing:
* JUnit 5
* Mockito
* Docker
### API Documentation:
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
## How to test the project from your side? 
* Make sure to have JDK 17+, Docker and Postman installed
* Clone this repository
* Create the .env file with the corresponding variables
* Build images using _docker-compose build_ and run the service in containers using _docker-compose up_
* You are welcome to try out this application options using the next Postman collection: there will be a collection here tomorrow (I hope). Just let me know if the other steps are valid
## What was difficult about creating the project?
First of all, it was quite a challenge to understand the whole architecture of the application, however, after many tries and a decent amount of time spent on research and communication with some experienced specialists I managed to find the suitable structure. 
Besides this, Spring Security was a pretty challenging topic to learn and use in such an application. Despite that, after a while I manage to configure it as well.
I cannot avoid talking about Docker setup here as well. It took a lot of my patience and efforts to configure it.
Anyway, I must admit that despite pretty difficult challenges, I really enjoyed creating this project and cannot wait to get some more practice.
## What is next?
It may look like this project is already finished, but I don't intend to leave it this way. Every shop needs to give customers the opportunity to return their goods and this one won't be an exception. So, the next step is adding the return feature. 
Also, I guess adding some front-end and good design would make it much more user-friendly. You are welcome to follow me for updates on this project :)