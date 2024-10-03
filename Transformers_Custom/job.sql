-- 1. Create a database named 'job'
CREATE DATABASE job;

-- 2. Switch to the 'job' database
USE job;

-- 3. Create a table named 'ml_engineer'
CREATE TABLE ml_engineer (
    job_title VARCHAR(255),
    job_description TEXT,
    location VARCHAR(255),
    status VARCHAR(50), -- Status like 'Open', 'Closed', etc.
    recruiter_name VARCHAR(255),
    recruiter_profile VARCHAR(255),
    recruiter_linkedin VARCHAR(255),
    company_website VARCHAR(255)
);
