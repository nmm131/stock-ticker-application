# Stock Ticker Application

Stock Ticker Application is a Java Application for retrieving stock data from an online API and saving validated records into a MySQL Relational Database.

Its process follows the steps below:
```
1. Ask user whether to use local or online API
2. If local, ask user to add a person or upload an XML file containing stock quotes to a local database
3. If online, enter a Ticker Symbol, time  series and output size and ask whether to store results in a local database
```

## Installation

Download or Clone the repository to your local system using GitHub.com, GitHub Desktop application, Bitbucket.com, GitHub SourceTree application, or .zip

Ensure that the database below exists before running the application:
```
jdbc:mysql://localhost:3306/stocks
```
with a table called:
```
quotes
```

## Usage

Made with Eclipse, Maven and the open source libraries below:
```
1. JUnit
2. MySQL JDBC
3. ORM
4. JAXB
5. Hibernate
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
