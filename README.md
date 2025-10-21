# Member Benefits Dashboard

**Core tech stack:** React + TypeScript (frontend), Java + Spring Boot (backend), Postgres (database)

## Project Overview:

Uses React + TypeScript to build a single-page application which handles federated authentication for login, and provides an overview of claims, accumulators, and enrollment, while allowing users to filter and sort claims.

The frontend communicates with a Spring Boot backend through REST APIs, following a controller/service/repository architecture, and all data is stored in a Postgres database.  
The application supports paginated and filtered claim views, enabling smooth performance even with large datasets.

## Use Case:

Provides a member benefits dashboard for healthcare users to log in and view their active enrollments and claims, with the ability to dive into individual claim details for more information.

Users can quickly filter claims by status, provider, date, and claim number, helping them manage their healthcare benefits and track responsibilities efficiently.

## Setting Up PostgreSQL Database:

This project uses PostgreSQL as its database.
Before running the backend, make sure you have a local PostgreSQL instance up and running.

#### 1. Install PostgreSQL

If you don’t already have PostgreSQL installed:

For macOS (using Homebrew):

`brew install postgresql`

Once installed, start the PostgreSQL service:

`brew services start postgresql`

#### 2. Connect to your local PostgreSQL instance

`psql -U [your_mac_username]`

#### 3. Create a postgres superuser
We're creating a user that matches the application configurations

`CREATE ROLE postgres WITH LOGIN SUPERUSER CREATEDB CREATEROLE PASSWORD 'password';`

#### 4. Create the Database

Create a new database named test_database:

`CREATE DATABASE test_database;`

Exit the shell:

`\q`

#### 5. Verify Your Setup

Make sure your database and credentials work by connecting to it:

`psql -U postgres -d test_database`

If you see a prompt like this:

`test_database=#`

Your PostgreSQL database is set up correctly and ready to use.

## Setting Up Spring Boot Backend:

#### 1. Go to project backend directory

`cd BEMemberBenefitsDashboard`

#### 2. Install dependencies and run

`./mvnw spring-boot:run`

The backend will start on http://localhost:8080

## Setting Up React Frontend:

#### 1. Go to project frontend directory

`cd fe-mem-benefits-dashboard`

#### 2. Install dependencies

`npm install`

#### 3. Start the development server

`npm run dev`

The backend will start on http://localhost:5173

## OIDC configuration:

For this project, the google client ID is already hard coded, so no additional setup is required for OAuth authentication.
