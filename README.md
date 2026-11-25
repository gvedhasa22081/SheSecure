# SheSecure — Digital Privacy & Online Harassment Evidence Management Portal

A Java Spring Boot + Thymeleaf + MySQL starter implementation with:
- User registration and login (passwords hashed with BCrypt)
- Privacy education page and per-user safety checklist
- Incident creation and chronological incident list
- Evidence uploads encrypted at rest with AES-256-GCM
- Per-user access checks for incident details and evidence downloads
- Cyber-safety sidebar with links to The Times of India and Indian cyber-safety resources

## Requirements
- Java 17+
- Maven 3.9+
- MySQL 8+

## 1. Create the database
```sql
CREATE DATABASE shesecure;
```

## 2. Configure environment variables
Set your MySQL credentials and a private 32-byte AES key. Generate a key with:
```bash
openssl rand -base64 32
```
Then set:
```bash
export DB_URL='jdbc:mysql://localhost:3306/shesecure?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
export DB_USERNAME='root'
export DB_PASSWORD='your_mysql_password'
export SHESecure_AES_KEY='paste_the_generated_base64_key_here'
export SHESecure_STORAGE='./private-evidence'
```
On Windows PowerShell use `$env:NAME="value"` instead of `export`.

**Important:** Keep `SHESecure_AES_KEY` private and back it up securely. If you lose it, encrypted evidence cannot be recovered. Do not commit the key, database password, or `private-evidence` folder to GitHub.

## 3. Run locally
```bash
mvn spring-boot:run
```
Open http://localhost:8080 and create an account.

## 4. Push to GitHub
Create an empty GitHub repository, then run:
```bash
git init
git add .
git commit -m "Initial SheSecure implementation"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/YOUR-REPOSITORY.git
git push -u origin main
```

## Pages
- `/register` — create account
- `/login` — sign in
- `/dashboard` — overview, saved incidents, checklist and news panel
- `/education` — privacy education
- `/incidents/new` — create incident and upload evidence
- `/incidents/{id}` — incident details and evidence download

## Notes before public deployment
This is an educational MVP, not a certified evidence-preservation or production-grade safety service. Before handling real sensitive evidence, add HTTPS, CSRF-aware upload/download testing, malware scanning, file-type allowlisting, rate limiting, audit logs, encrypted database backups, key management (KMS/HSM), retention controls, stronger account recovery, and security testing. Do not deploy with sample data or a development secret. Consider storing evidence outside the public web root and restricting server access.

The news sidebar currently links to publisher/topic pages; it does not scrape or automatically syndicate article headlines. For a live feed, integrate a permitted RSS feed or licensed news API and display source attribution.
