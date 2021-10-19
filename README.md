# BackEndPortfolio
The backend implementation of a full stack portfolio piece for CT!

## Features
### Core Features
- Create an account and customize your user card
- Login and edit your user card
- View and edit your own secret information
- Select and save your theme
- Browse other user's cards
- Browse groups of users by title
- View their information
- Search and filter users by query
- Delete your own account
### Admin Features
- Login and view other user's secret information
  - Admins cannot edit other user's secret information
  - They can only edit their own secret information
- Delete any users, including yourself
- Modify themes
  - Changes to themes affect all users of the site!
## Class Design
### API Layer
User Controller

- Responsible for handling API call related to the user table and information
- Responsible for handling API call related to the user login and logout attempts
- Responsible for constructing the HTTP response and JSON object related to user API requests

Theme Controller

- Responsible for handling API call related to the theme table and information
- Responsible for constructing the HTTP response related to theme API requests
### Service Layer
User Service

- Responsible for input validation, login validation, and checking for data integrity
- Responsible for role and user based information filtering
- Responsible for facilitating communication between the user API controller and the data access layer for the user table 
- Responsible for building title objects and their collections of users 

Theme Service

- Responsible for login validation
- Responsible for facilitating communication between the theme API controller and the data access layer for the theme table
### Data Access Layer
User Repository

- Responsible for access to the user table

Theme Repository

- Responsible for access to the theme table
### Assorted Bean Configurations
Web Security Config

- Contains the configurations for CORS and user logins
## Technologies
Angular (Frontend TypeScript Framework)

TypeScript/JavaScript

HTML and CSS

Jasmine (Testing Framework)

Jackson (JSON)

REST and HTTP

Java (Backend)

Spring MVC (Backend Framework), Spring Security (Authentication), and Spring Testing (Spring's Testing Framework) (Backend)

JUnit (Testing Framework) (Backend)

Hibernate/JPA (No-SQL) (Backend)

SQL/JDBC and PostgreSQL (Backend)

## Testing with Jasmine and JUnit
Manual Testing

- Application Tests
  - Procedure for checking that the components of the site fills out correctly
    - Procedure for checking that Toolbar has loaded correctly
    - Procedure for checking that each of the components in the main section loads correctly
  - Procedure for checking displayed objects' integrities and construction
  - Procedure for checking the functionality and display of different popups and forms
  - Procedure for checking the functionality of and state changes from buttons and other event sources on each of the three pages of the site
  - Procedure for checking data transfer to the backend and data permanence
  - Procedure for checking proper state and display change to the site upon change in login status
  - Procedure for verifying login and display logic
  - Procedure for verifying user privacy
  - Procedure for verifying user permissions by role
    - Procedure for individual user permissions towards their own profile
    - Procedure for verifying admin privileges

Intergration Testing

- Frontend Jasmine Tests
  - Automatically tests for proper interactions and side effects between different components and services and their state
  - Automatically tests for proper values for display triggers in the model
  - Automatically tests for the proper flow of information between the controller (service) layer and model layer
  - Automatically tests for the proper flow of information within the controller (service) layer
  - Automatically tests for proper placement of data and objects upon reception of them from the backend
  - Automatically tests event handlers for proper state change and backend requests
  - Automatically checks for the flow of login information and current user state between different components and services
- Backend JUnit Tests
  - Automatically tests input validation
  - Automatically tests information filtering
  - Automatically tests information access by account and role
  - Automatically tests for data integrity
  - Automatically tests API request and response data formats

## User Login Information *
"Bob Smith" - User ID : 4, Password : AmFirstUser, Role : User

"Jane Smith" - User ID : 5, Password : AmSecondUser, Role : Admin

"Fred Joe" - User ID : 7, Password : AmThirdUser, Role : User

\* User IDs are auto generated and system dependent, check the backend console or database to confirm for your system



Made By CT!

See the Frontend at [github.com/AnInvertedFern/FrontEndPortfolio](https://github.com/AnInvertedFern/FrontEndPortfolio)!