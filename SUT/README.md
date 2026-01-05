# Installing the System Under Test (SUT)
This page contains instructions on how to install the SUT and prepare the testing environment.

In general, the Docker option is the easiest way to install the SUTs. However, if you prefer to install the SUT locally, you can follow the instructions below.

## PrestaShop
PrestaShop is an efficient and innovative e-commerce solution with all the features you need to create an online store and grow your business.

### Installing using Docker
Go to the [prestashop folder](prestashop) and run in the terminal:
```bash
docker-compose up -d
```
Once the container installation is complete, it will take a few more minutes for the database to be created, and the PrestaShop installation to be completed. You can access the PrestaShop installation by going to http://localhost:8080 in your web browser.

### Credentials
- Clients login:
  - Address: http://localhost:8080
  - Email: pub@prestashop.com
  - Password: 123456789
- Admin login:
  - Address: http://localhost:8080/admina
  - Email: demo@prestashop.com
  - Password: prestashop_demo

### Installing locally
Follow these instructions to install PrestaShop locally: https://devdocs.prestashop-project.org/9/basics/installation/
