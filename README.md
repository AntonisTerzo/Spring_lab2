# **Laboration 2**
## This project is a Spring Boot 3.x.x REST API designed to manage categories and locations. The application uses MySQL for persistent storage and adheres to modern security practices with Spring Security and OAuth2-based JWT authentication. The API is implemented in Java 23 and uses JSON for data exchange.

## Features

Category Management:
GET: Retrieve all categories or a specific category.
POST: Create a new category (requires admin role).
Note: Category names must be unique.

Location Management:
GET:
Retrieve all public locations or a specific public location (accessible by anonymous users).
Retrieve all public locations in a specific category.
Retrieve all locations (public and private) for the logged-in user.
Retrieve all locations within a specific area (radius from a center).
POST: Create a new location (requires user authentication).
PUT: Update an existing location (requires user authentication).
DELETE: Remove an existing location (requires user authentication).

Security:
Anonymous users can only access public locations.
Authenticated users can access both public locations and their own private locations.
Admin users can add new categories.
