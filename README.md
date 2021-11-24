# ofc-bot-scala
Discord Bot using Scala, Slick and Ackord

Tools required for building:
- Scala (2.13.5)
- SBT (1.4.7)
- Docker/Docker Compose

If you want to try it by yourself, you will need an application.conf
file with a namespace "db" containing parameters to start the database connection
and a "bot" namespace with a discord bot token
and a string representing a prefix to the bot's commands.


Running Database
- docker-compose up (postgres will start at 5432)

Building
- sbt clean
- sbt compile

Running
- sbt run Main
