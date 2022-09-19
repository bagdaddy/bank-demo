How to run the project:

<h3>Build the project</h3>
mvn -Dmaven.test.skip package  

<h3>Create mysql container</h3>
docker-compose up -d --build

<h3>Create the app container:</h3>
docker build -t bank-app .
docker run --network bank-net --name bank-app -p 8080:8080 -d bank-app

<br/>
**for some reason the app container creation doesn't work from the docker-compose.yml file (logs suggest that the application can't reach the database for some reason)

<h3>Endpoints:</h3>
<ul>
    <li>POST: /api/customers for creating a customer (created only for demo purposes, even though a couple of customers are created in DemoApplication already)</li>
    <li>POST: /api/login for logging in, doesn't do anything other than check user credentials and create a jwt token with an expiration (auth isn't set up on the endpoints so it doesn't really matter)</li>
    <li>POST: /api/balance/import accepts a CSV file as parameter (field name = 'file'). There are multiple example import files in the repository (statement.csv, import_example.csv)</li>
    <li>POST: /api/balance/export accepts json as parameters (accounts: [id1, id2], fromDate: "2022-09-03", toDate: "2022-09-03")</li>
    <li>GET: /api/balance/{accountId} accepts fromDate and toDate as query parameters. Will return an array of balances by currency. Currently only USD,CAD and EUR are available currencies</li>
</ul>
